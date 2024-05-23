<script setup>
import { onMounted, ref, watch } from "vue";
import { useRoute, useRouter } from "vue-router";
import { registBoard, getModifyBoard, modifyBoard } from "@/api/board";
import { useMemberStore } from "@/stores/member";
import { callSwal } from "@/util/util";
import { QuillEditor } from "@vueup/vue-quill";

const router = useRouter();
const route = useRoute();
const memberStore = useMemberStore();
const props = defineProps({ type: String });
const { VITE_BOARD_NOTICE, VITE_BOARD_FREE, VITE_BOARD_QNA } = import.meta.env;
// const deltaContent = ref();
const htmlContent = ref();
const isUseId = ref(false);

const board = ref({
  boardId: 0,
  title: "",
  content: "",
  memberId: memberStore.memberId,
  writer: memberStore.name,
  hit: 0,
  type: 1,
  created_at: "",
});

onMounted(() => {});

if (props.type === "modify") {
  let { boardId } = route.params;
  console.log(boardId + "번글 얻어와서 수정할거야");
  getModifyBoard(
    boardId,
    ({ data }) => {
      board.value = data;
      isUseId.value = true;
      htmlContent.value = data.content;
    },
    (error) => {
      console.log(error);
    }
  );
  isUseId.value = true;
}

const titleErrMsg = ref("");
const contentErrMsg = ref("");
watch(
  () => board.value.title,
  (value) => {
    let len = value.length;
    if (len == 0) {
      titleErrMsg.value = "제목은 비워둘 수 없습니다.";
    } else if (len > 30) {
      titleErrMsg.value = "제목을 확인해 주세요!";
    } else titleErrMsg.value = "";
  },
  { immediate: true }
);
watch(
  () => htmlContent,
  (value) => {
    console.log(value);
    let len = value.length;
    if (len == 0) {
      contentErrMsg.value = "내용은 비워둘 수 없습니다.";
    } else if (len > 500) {
      contentErrMsg.value = "내용은 500자 이상이어야 합니다.";
    } else contentErrMsg.value = "";
  },
  { immediate: true }
);

function onSubmit() {
  // event.preventDefault();

  if (titleErrMsg.value) {
    alert(titleErrMsg.value);
  } else if (contentErrMsg.value) {
    alert(contentErrMsg.value);
  } else {
    props.type === "regist" ? writeboard() : updateboard();
  }
}

function writeboard() {
  console.log("글 등록", board.value);
  // board.value.content = JSON.stringify(deltaContent);
  board.value.content = htmlContent;
  registBoard(
    board.value,
    (response) => {
      if (response.status == 201) {
        callSwal({
          icon: "success",
          title: "등록 성공",
          text: "등록이 완료되었습니다.",
        });
      } else {
        callSwal({
          title: "등록 실패",
          text: response,
        });
      }
      moveList();
    },
    (error) => {
      console.log(error);
      callSwal({
        icon: "error",
        title: "등록 실패",
        text: error.response.data,
      });
    }
  );
}

function updateboard() {
  board.value.content = htmlContent;
  console.log(board.value.boardId + "번글 수정하자!!", board.value);
  modifyBoard(
    board.value,
    (response) => {
      if (response.status == 200) {
        callSwal({
          icon: "success",
          title: "수정 성공",
          text: "수정이 완료되었습니다.",
        });
      } else {
        callSwal({
          title: "수정 실패",
          text: response,
        });
      }
      moveList();
      // router.push({ name: "board-view" });
      // router.push(`/board/view/${board.value.boardId}`);
    },
    (error) => {
      console.log(error);
      callSwal({
        icon: "error",
        title: "수정 실패",
        text: error.response.data,
      });
    }
  );
}

function moveList() {
  router.replace({ name: "board-list" });
}
</script>

<template>
  <form @submit.prevent="onSubmit">
    <div class="row mb-2">
      <h2>{{ type === "regist" ? "글 등록" : "글 수정" }}</h2>
    </div>
    <div class="row">
      <div class="mb-3 d-flex justify-content-between">
        <select class="col-3 ps-1 me-2" v-model="board.type" style="height: 40px; font-size: 15px">
          <option selected :value="VITE_BOARD_NOTICE">공지</option>
          <option :value="VITE_BOARD_FREE">자유</option>
          <option :value="VITE_BOARD_QNA">Q&A</option>
        </select>
        <input
          type="text"
          class="col ps-2"
          v-model="board.title"
          style="height: 40px; font-size: 15px"
          placeholder="제목..."
        />
      </div>
    </div>
    <div class="row py-1 mb-3">
      <div class="col-2">작성자</div>
      <div class="col">{{ board.writer }}</div>
    </div>
    <div class="mb-3">
      <QuillEditor
        theme="snow"
        v-model:content="htmlContent"
        contentType="html"
        style="height: 400px"
      />
    </div>
    <div class="col-auto">
      <button type="submit" class="btn lighterButton mb-3 ms-1" v-if="type === 'regist'">
        등록
      </button>

      <button type="submit" class="btn lighterButton mb-3 ms-1" v-else>저장</button>
      <button type="button" class="btn reverseButton mb-3 ms-1" @click="moveList">목록</button>
    </div>
  </form>
</template>

<style scoped></style>
