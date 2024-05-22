<script setup>
import { ref, onMounted } from "vue";
import { useRoute, useRouter } from "vue-router";
import { detailBoard, deleteBoard, registComment, listComment, updateBoom } from "@/api/board";
import RouterButton from "../common/RouterButton.vue";
import { callSwal, dateFormat } from "@/util/util";
import BoardQnAAnswer from "./BoardQnAAnswer.vue";
import { useMemberStore } from "@/stores/member";

const route = useRoute();
const router = useRouter();
const { VITE_BOARD_NOTICE, VITE_BOARD_FREE, VITE_BOARD_QNA } = import.meta.env;

// const boardId = ref(route.params.boardId);
const { boardId } = route.params;
const memberStore = useMemberStore();
const board = ref({});
const comments = ref([]);
onMounted(() => {
  getBoard();
});

const getBoard = () => {
  // const { boardId } = route.params;
  console.log(boardId + "번글 얻으러 가자!!!");
  detailBoard(
    boardId,
    ({ data }) => {
      board.value = data;
      if (board.value.type == VITE_BOARD_QNA) {
        getComments();
      }
    },
    (error) => {
      console.log(error);
    }
  );
};

const getComments = () => {
  // const { boardId } = route.params;
  console.log(boardId + "번글 댓글 얻으러 가자!!!");
  listComment(
    { boardId: boardId },
    ({ data }) => {
      comments.value = data;
      console.log("코멘트", comments.value);
    },
    (error) => {
      console.log(error);
    }
  );
};

function moveList() {
  router.push({ name: "board-list" });
}

function moveModify() {
  router.push({ name: "board-modify", params: { boardId } });
}

function onDeleteBoard() {
  console.log(boardId + "번 글 삭제");
  if (window.confirm("정말 삭제하시겠습니까?")) {
    deleteBoard(
      boardId,
      (response) => {
        if (response.status == 200) moveList();
      },
      (error) => {
        console.log(error);
        callSwal({
          icon: "error",
          title: "삭제 실패",
          text: error.response.data,
        });
      }
    );
  }
}

// QNA 답변 관리
const commType = ref("read");
const newComment = ref({
  memberId: memberStore.memberId,
  writer: memberStore.name,
  content: "",
  boardId: boardId,
});
function changeComRegist() {
  commType.value = "regist";
}
function reload() {
  console.log("reload");
  router.go(0);
}

function commentRegist(content) {
  if (content == "") {
    alert("내용은 비워둘 수 없습니다.");
    return;
  }
  console.log("등록해염", content);
  newComment.value.content = content;
  registComment(
    newComment.value,
    (response) => {
      if (response.status == 200 || response.status == 201) reload();
    },
    (error) => {
      console.log(error);
    }
  );
}

function changeComRead() {
  commType.value = "read";
}

// 추천하기
function updateBoardBoom() {
  updateBoom(
    boardId,
    (response) => {
      if (response.status == 200) reload();
    },
    (error) => {
      console.log(error);
    }
  );
}
</script>

<template>
  <div>
    <div class="row justify-content-center">
      <div class="col mb-4">
        <div class="row d-flex justify-content-between">
          <div class="col">
            <h2>{{ board.title }}</h2>
          </div>
          <div class="col-auto">
            <RouterButton
              :buttonFunc="moveList"
              buttonText="글 목록"
              buttonIcon="/src/assets/buttonList.png"
            />
          </div>
        </div>
      </div>
    </div>

    <div class="row py-1">
      <div class="col-2">구분</div>
      <div class="col-auto">
        {{
          board.type == VITE_BOARD_NOTICE
            ? "공지사항"
            : board.type == VITE_BOARD_FREE
            ? "자유"
            : "Q&A"
        }}
      </div>
    </div>
    <div class="row py-1">
      <div class="col-2">조회수</div>
      <div class="col-auto">{{ board.hit }}</div>
    </div>
    <div class="row py-1">
      <div class="col-2">추천</div>
      <div class="col-auto">{{ board.boom }}</div>
    </div>
    <div class="row py-1">
      <div class="col-2">작성일</div>
      <div class="col-auto">{{ dateFormat(board.createdAt) }}</div>
    </div>
    <div class="row py-1">
      <div class="col-2">작성자</div>
      <div class="col-auto">{{ board.writer }}</div>
    </div>
    <div class="row">
      <div class="divider mt-3 mb-3"></div>
      <div
        style="white-space: pre-wrap"
        class="col board-content text-secondary p-2 m-2"
        :innerHTML="board.content"
      ></div>
      <div class="divider mt-3 mb-3"></div>
    </div>
    <div class="row" v-if="board.type == VITE_BOARD_QNA">
      <div class="col">
        <hr class="hr-dashed mb-5" />
        <p class="small-title">답변</p>
        <BoardQnAAnswer
          v-for="comment in comments"
          :key="comment.comment_id"
          :comment="comment"
          :type="'read'"
        />
        <div v-if="commType == 'regist'">
          <BoardQnAAnswer
            :comment="newComment"
            :type="'regist'"
            @regist-event="commentRegist"
            @cancel-event="changeComRead"
          />
        </div>
      </div>
    </div>
    <div class="row">
      <div class="d-flex justify-content-between">
        <div class="d-flex justify-content-start">
          <button type="button" class="btn lighterButton mb-3 ms-1" @click="moveModify">
            수정
          </button>
          <button type="button" class="btn reverseButton mb-3 ms-1" @click="onDeleteBoard">
            삭제
          </button>
        </div>
        <button
          type="button"
          class="btn lighterButton mb-3 ms-1 d-flex align-items-center"
          @click="updateBoardBoom"
        >
          <img src="@/assets/buttonThumb.png" class="me-1" height="25" width="25" />추천하기
        </button>
        <button
          type="button"
          class="btn reverseButton mb-3 ms-1"
          :style="{ visibility: board.type == VITE_BOARD_QNA ? 'visible' : 'hidden' }"
          @click="changeComRegist"
          :disabled="commType == 'regist'"
        >
          답변 작성
        </button>
      </div>
    </div>
  </div>
</template>

<style scoped></style>
