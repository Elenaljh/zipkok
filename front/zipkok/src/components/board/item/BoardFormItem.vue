<script setup>
import { ref, watch } from "vue";
import { useRoute, useRouter } from "vue-router";
import { registBoard, getModifyBoard, modifyBoard } from "@/api/board";

const router = useRouter();
const route = useRoute();

const props = defineProps({ type: String });
const { VITE_BOARD_NOTICE, VITE_BOARD_FREE, VITE_BOARD_QNA } = import.meta.env;

const isUseId = ref(false);

const board = ref({
  boardId: 0,
  title: "",
  content: "",
  memberId: 1,
  writer: "김싸피",
  hit: 0,
  type: 1,
  created_at: "",
});

if (props.type === "modify") {
  let { boardId } = route.params;
  console.log(boardId + "번글 얻어와서 수정할거야");
  getModifyBoard(
    boardId,
    ({ data }) => {
      board.value = data;
      isUseId.value = true;
    },
    (error) => {
      console.log(error);
      // 테스트용 코드
      board.value = {
        boardId: 1,
        title: "공지사항 테스트",
        writer: "김싸피",
        hit: 3,
        boom: 10,
        type: 0,
        created_at: "2024.10.23",
        content:
          "공무원은 국민전체에 대한 봉사자이며, 국민에 대하여 책임을 진다. 모든 국민은 법률이 정하는 바에 의하여 국방의 의무를 진다. 국회의원은 그 지위를 남용하여 국가·공공단체 또는 기업체와의 계약이나 그 처분에 의하여 재산상의 권리·이익 또는 직위를 취득하거나 타인을 위하여 그 취득을 알선할 수 없다.\n이 헌법시행 당시에 이 헌법에 의하여 새로 설치될 기관의 권한에 속하는 직무를 행하고 있는 기관은 이 헌법에 의하여 새로운 기관이 설치될 때까지 존속하며 그 직무를 행한다.",
      };
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
  () => board.value.content,
  (value) => {
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
  console.log("글등록하자!!", board.value);
  registBoard(
    board.value,
    (response) => {
      let msg = "문제가 발생했습니다.";
      if (response.status == 201) msg = "등록이 완료되었습니다.";
      alert(msg);
      moveList();
    },
    (error) => console.log(error)
  );
}

function updateboard() {
  console.log(board.value.boardId + "번글 수정하자!!", board.value);
  modifyBoard(
    board.value,
    (response) => {
      let msg = "문제가 발생했습니다.";
      if (response.status == 200) msg = "수정이 완료되었습니다.";
      alert(msg);
      moveList();
      // router.push({ name: "board-view" });
      // router.push(`/board/view/${board.value.boardId}`);
    },
    (error) => console.log(error)
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
      <textarea class="form-control" v-model="board.content" rows="10"></textarea>
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
