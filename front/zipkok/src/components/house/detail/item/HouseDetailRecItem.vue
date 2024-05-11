<script setup>
import { ref } from "vue";
const noImage = ref("/src/assets/noImage.jpg");
const news = ref([
  {
    source: "매일경제",
    title: "[컨콜 종합] 컴투스, 탄탄한 게임사업에 경영 효율화 더하니 ‘흑전’",
    preview: "최고최고입니다.",
    link: "https://n.news.naver.com/mnews/article/009/0005301504",
    img: "/src/assets/newsSample.jpeg",
    date: "2024.05.11",
  },
  {
    source: "매일경제",
    title: "[컨콜 종합] 컴투스, 탄탄한 게임사업에 경영 효율화 더하니 ‘흑전’",
    preview: "최고최고입니다.",
    link: "https://n.news.naver.com/mnews/article/009/0005301504",
    img: null,
    date: "2024.05.11",
  },
  {
    source: "매일경제",
    title: "[컨콜 종합] 컴투스, 탄탄한 게임사업에 경영 효율화 더하니 ‘흑전’",
    preview: "최고최고입니다.",
    link: "https://n.news.naver.com/mnews/article/009/0005301504",
    img: "/src/assets/newsSample.jpeg",
    date: "2024.05.11",
  },
  {
    source: "매일경제",
    title: "[컨콜 종합] 컴투스, 탄탄한 게임사업에 경영 효율화 더하니 ‘흑전’",
    preview: "최고최고입니다.",
    link: "https://n.news.naver.com/mnews/article/009/0005301504",
    img: null,
    date: "2024.05.11",
  },
  {
    source: "매일경제",
    title: "[컨콜 종합] 컴투스, 탄탄한 게임사업에 경영 효율화 더하니 ‘흑전’",
    preview: "최고최고입니다.",
    link: "https://n.news.naver.com/mnews/article/009/0005301504",
    img: "/src/assets/newsSample.jpeg",
    date: "2024.05.11",
  },
  {
    source: "매일경제",
    title: "[컨콜 종합] 컴투스, 탄탄한 게임사업에 경영 효율화 더하니 ‘흑전’",
    preview: "최고최고입니다.",
    link: "https://n.news.naver.com/mnews/article/009/0005301504",
    img: null,
    date: "2024.05.11",
  },
  {
    source: "매일경제",
    title: "[컨콜 종합] 컴투스, 탄탄한 게임사업에 경영 효율화 더하니 ‘흑전’",
    preview: "최고최고입니다.",
    link: "https://n.news.naver.com/mnews/article/009/0005301504",
    img: null,
    date: "2024.05.11",
  },
  {
    source: "매일경제",
    title: "[컨콜 종합] 컴투스, 탄탄한 게임사업에 경영 효율화 더하니 ‘흑전’",
    preview: "최고최고입니다.",
    link: "https://n.news.naver.com/mnews/article/009/0005301504",
    img: "/src/assets/newsSample.jpeg",
    date: "2024.05.11",
  },
  {
    source: "매일경제",
    title: "[컨콜 종합] 컴투스, 탄탄한 게임사업에 경영 효율화 더하니 ‘흑전’",
    preview: "최고최고입니다.",
    link: "https://n.news.naver.com/mnews/article/009/0005301504",
    img: null,
    date: "2024.05.11",
  },
  {
    source: "매일경제",
    title: "[컨콜 종합] 컴투스, 탄탄한 게임사업에 경영 효율화 더하니 ‘흑전’",
    preview: "최고최고입니다.",
    link: "https://n.news.naver.com/mnews/article/009/0005301504",
    img: "/src/assets/newsSample.jpeg",
    date: "2024.05.11",
  },
  {
    source: "매일경제",
    title: "[컨콜 종합] 컴투스, 탄탄한 게임사업에 경영 효율화 더하니 ‘흑전’",
    preview: "최고최고입니다.",
    link: "https://n.news.naver.com/mnews/article/009/0005301504",
    img: null,
    date: "2024.05.11",
  },
]);
</script>

<template>
  <div class="me-2">
    <div class="card mb-3 w-100" v-for="item in news" :key="item">
      <div class="row g-0">
        <div class="col-md-4">
          <img
            :src="item.img == null ? noImage : item.img"
            class="img-fluid rounded-start"
            style="width: 100%; height: 100%"
          />
        </div>
        <div class="col-md-8">
          <div class="card-body">
            <small class="me-0" style="color: #00b3d6; font-weight: bold">
              {{ item.source }}
            </small>
            <a :href="item.link"
              ><h5 class="card-title">{{ item.title }}</h5></a
            >
            <p class="card-text">
              {{ item.preview }}
            </p>
            <p class="card-text">
              <small class="text-body-secondary">{{ item.date }}</small>
            </p>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<style scoped>
/* .img-fluid.rounded-start {
  object-fit: obtain;
} */
a {
  text-decoration-line: none;
  color: black;
}
</style>
