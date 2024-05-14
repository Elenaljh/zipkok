<script setup>
import { computed } from "vue";

const props = defineProps({ currentPage: Number, totalPage: Number });
const emit = defineEmits(["pageChange"]);

const navigationSize = parseInt(import.meta.env.VITE_ARTICLE_NAVIGATION_SIZE);

const startPage = computed(() => {
  return parseInt((props.currentPage - 1) / navigationSize) * navigationSize + 1;
});

const endPage = computed(() => {
  let lastPage =
    parseInt((props.currentPage - 1) / navigationSize) * navigationSize + navigationSize;
  return props.totalPage < lastPage ? props.totalPage : lastPage;
});

const endRange = computed(() => {
  return parseInt((props.totalPage - 1) / navigationSize) * navigationSize < props.currentPage;
});

function range(start, end) {
  const list = [];
  for (let i = start; i <= end; i++) list.push(i);
  return list;
  //   return Array(end - start + 1)
  //     .fill()
  //     .map((val, i) => start + i);
}

function onPageChange(pg) {
  console.log(pg + "로 이동!!!");
  emit("pageChange", pg);
}
</script>

<template>
  <div class="row m-0 p-0">
    <ul class="pagination me-0 p-0 justify-content-end">
      <!-- <li class="page-item">
        <a class="page-link" @click="onPageChange(1)">최신</a>
      </li> -->
      <span class="page-item">
        <button
          class="page-link reverseButton"
          @click="onPageChange(startPage == 1 ? 1 : startPage - 1)"
        >
          이전
        </button>
      </span>
      <template v-for="pg in range(startPage, endPage)" :key="pg">
        <span :class="currentPage === pg ? 'page-item active' : 'page-item'">
          <button class="page-link reverseButton" @click="onPageChange(pg)">{{ pg }}</button>
        </span>
      </template>
      <span class="page-item">
        <button
          class="page-link reverseButton"
          @click="onPageChange(endRange ? totalPage : endPage + 1)"
        >
          다음
        </button>
      </span>
      <!-- <li class="page-item"><a class="page-link" @click="onPageChange(totalPage)">마지막</a></li> -->
    </ul>
  </div>
</template>

<style scoped>
a {
  cursor: pointer;
}
.page-link {
  font-size: small;
  font-weight: 400;
}
</style>
