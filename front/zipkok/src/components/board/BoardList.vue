<script setup>
import { ref, onMounted } from "vue";
import { listBoard } from "@/api/board.js";

import VSelect from "@/components/common/VSelect.vue";
import BoardListItem from "@/components/board/item/BoardListItem.vue";
import PageNavigation from "@/components/common/PageNavigation.vue";
import RouterButton from "@/components/common/RouterButton.vue";
import { useRoute, useRouter } from "vue-router";
const router = useRouter();
const route = useRoute();
const selectOption = ref([
  { text: "검색 조건", value: "" },
  { text: "제목", value: "title" },
  { text: "제목, 내용", value: "title and content" },
  { text: "작성자", value: "writer" },
]);

const boards = ref([]);
const currentPage = ref(1);
const totalPage = ref(0);
const { VITE_ARTICLE_LIST_SIZE } = import.meta.env;
const { VITE_BOARD_NOTICE, VITE_BOARD_FREE, VITE_BOARD_QNA } = import.meta.env;
const param = ref({
  pgno: currentPage.value,
  spp: VITE_ARTICLE_LIST_SIZE,
  type: route.query.tn ? route.query.tn : 0,
  key: "",
  keyword: "",
  writer: "",
});

onMounted(() => {
  getBoardList();
});

const changeKey = (val) => {
  console.log("BoarList에서 선택한 조건 : " + val);
  param.value.key = val;
};

const getBoardList = () => {
  console.log("서버에서 글 얻어오기", param.value, param.value.type, VITE_BOARD_FREE);
  console.log("타입은 이거=", param.value.type, param.value.spp);
  listBoard(
    param.value,
    ({ data }) => {
      console.log("받았다!!", data);
      boards.value = data;
      // boards.value = data.boards;
      // currentPage.value = data.currentPage;
      // totalPage.value = data.totalPageCount;
    },
    (error) => {
      console.log(error);
    }
  );
};

const onPageChange = (val) => {
  console.log(val + "번 페이지로 이동 준비 끝!!!");
  currentPage.value = val;
  param.value.pgno = val;
  getBoardList();
};

const changeTab = (val) => {
  param.value.type = val;
  getBoardList();
};

const moveWrite = () => {
  router.push({ name: "board-write" });
};
</script>

<template>
  <div>
    <div class="row d-flex justify-content-between">
      <div class="col">
        <h1>게시판</h1>
        <p>서비스 공지사항과 유용한 부동산 정보를 나눠보세요.</p>
      </div>
      <div class="col-auto">
        <RouterButton
          :buttonFunc="moveWrite"
          buttonText="글쓰기"
          buttonIcon="/src/assets/buttonPlus.png"
        />
      </div>
    </div>

    <div class="container m-0 p-0">
      <div class="row justify-content-flex-start">
        <div class="col-lg-12 mb-3">
          <ul class="nav nav-underline">
            <li class="nav-item">
              <a
                class="nav-link boardNav"
                aria-current="page"
                :class="{ active: param.type == VITE_BOARD_NOTICE }"
                @click="changeTab(VITE_BOARD_NOTICE)"
                href="#"
                >공지사항</a
              >
            </li>
            <li class="nav-item">
              <a
                class="nav-link boardNav"
                :class="{ active: param.type == VITE_BOARD_FREE }"
                @click="changeTab(VITE_BOARD_FREE)"
                href="#"
                >부동산 정보</a
              >
            </li>
            <li class="nav-item">
              <a
                class="nav-link boardNav"
                :class="{ active: param.type == VITE_BOARD_QNA }"
                @click="changeTab(VITE_BOARD_QNA)"
                href="#"
                >Q&A</a
              >
            </li>
          </ul>
        </div>
        <div class="col-lg-12">
          <div class="row mb-3">
            <div class="col-md-7">
              <form class="d-flex align-items-start">
                <VSelect :selectOption="selectOption" @onKeySelect="changeKey" />
                <div class="input-group input-group-sm">
                  <input
                    type="text"
                    class="form-control"
                    v-model="param.keyword"
                    placeholder="검색어 입력"
                  />
                  <button class="btn reverseButton" type="button" @click="getBoardList">
                    검색
                  </button>
                </div>
              </form>
            </div>
          </div>
          <div class="row table-row">
            <table class="col table table-hover boardTable">
              <thead>
                <tr class="text-center">
                  <th scope="col">글번호</th>
                  <th scope="col">제목</th>
                  <th scope="col">작성자</th>
                  <th scope="col">조회수</th>
                  <th scope="col">추천수</th>
                  <th scope="col">작성일</th>
                </tr>
              </thead>
              <tbody>
                <td colspan="6" v-if="boards.length == 0" class="p-1 text-center">
                  조회 결과가 없습니다.
                </td>
                <BoardListItem
                  v-for="board in boards"
                  :key="board.boardId"
                  :board="board"
                ></BoardListItem>
              </tbody>
            </table>
          </div>
        </div>

        <PageNavigation
          :current-page="currentPage"
          :total-page="totalPage"
          @pageChange="onPageChange"
        ></PageNavigation>
      </div>
    </div>
  </div>
</template>

<style scoped></style>
