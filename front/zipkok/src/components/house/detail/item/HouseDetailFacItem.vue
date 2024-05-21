<script setup>
import { ref } from "vue";
import { dong, news, malePopulation, femalePopulation } from "@/util/houseDetail";
import { numberFormat } from "@/util/util";
const { houseInfo } = defineProps({
  houseInfo: Object,
});
import { Bar } from "vue-chartjs";
import {
  Chart as ChartJS,
  Title,
  Tooltip,
  Legend,
  BarElement,
  CategoryScale,
  LinearScale,
} from "chart.js";

ChartJS.register(Title, Tooltip, Legend, BarElement, CategoryScale, LinearScale);

const chartOptions = ref({
  responsive: true,
  // maintainAspectRatio: false,
});

const chartData = ref({
  labels: ["0~9세", "10~19세", "20~29세", "30~39세", "40~49세", "50~59세", "60세~"],
  datasets: [
    {
      label: "남성",
      data: malePopulation.value,
      // fill: false,
      backgroundColor: "#00b4d8",
      // tension: 0.1,
    },
    {
      label: "여성",
      data: femalePopulation.value,
      // fill: false,
      backgroundColor: "#D8004E",
      // tension: 0.1,
    },
  ],
});

const noImage = ref("/src/assets/noImage.jpg");
</script>

<template>
  <div class="me-2">
    <!--인구정보-->
    <div>
      <p style="font-size: large; font-weight: bold">
        <span style="color: #00b3d6">{{ dong }}</span
        ><span>의 인구구조</span>
      </p>
      <!--그래프-->
      <div class="mb-5">
        <Bar id="my-chart-id" :options="chartOptions" :data="chartData" />
      </div>
      <!--테이블-->
      <!-- <div class="table-responsive card" style="max-height: 380px; overflow-y: auto">
        <table class="table mb-0">
          <thead class="table-light">
            <tr>
              <th scope="col" style="padding-left: 20px">연령</th>
              <th scope="col">인구 수</th>
            </tr>
          </thead>
          <tbody>
            <tr>
              <td style="padding-left: 20px">0~9세</td>
              <td>{{ numberFormat(population.zero) }}명</td>
            </tr>
            <tr>
              <td style="padding-left: 20px">10~19세</td>
              <td>{{ numberFormat(population.ten) }}명</td>
            </tr>
            <tr>
              <td style="padding-left: 20px">20~29세</td>
              <td>{{ numberFormat(population.twenty) }}명</td>
            </tr>
            <tr>
              <td style="padding-left: 20px">30~39세</td>
              <td>{{ numberFormat(population.thirty) }}명</td>
            </tr>
            <tr>
              <td style="padding-left: 20px">40~49세</td>
              <td>{{ numberFormat(population.forty) }}명</td>
            </tr>
            <tr>
              <td style="padding-left: 20px">50~59세</td>
              <td>{{ numberFormat(population.fifty) }}명</td>
            </tr>
            <tr>
              <td style="padding-left: 20px">60~세</td>
              <td>{{ numberFormat(population.old) }}명</td>
            </tr>
          </tbody>
          <tfoot class="table-light">
            <tr>
              <th scope="col" style="padding-left: 20px">총계</th>
              <th scope="col">{{ numberFormat(population.total) }}명</th>
            </tr>
          </tfoot>
        </table>
      </div> -->
      <!--테이블 끝-->
    </div>
    <!--인구정보 끝-->
    <!-- <hr style="background: #00b3d6; border: 0; height: 1.5px" /> -->
    <!--뉴스 시작-->
    <div class="me-2">
      <p style="font-size: large; font-weight: bold">
        <span style="color: #00b3d6">{{ houseInfo.aptName }}</span
        ><span>가 나온 뉴스</span>
      </p>
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
              <a :href="item.link" target="_blank"
                ><h5 class="card-title" :innerHTML="item.title"></h5
              ></a>
              <p class="card-text" :innerHTML="item.detail"></p>
              <p class="card-text">
                <small class="text-body-secondary">{{ item.pubDate }}</small>
              </p>
            </div>
          </div>
        </div>
      </div>
    </div>
    <!--뉴스 끝-->
  </div>
</template>

<style scoped>
.table-responsive.card::-webkit-scrollbar {
  width: 10px;
}
.table-responsive.card::-webkit-scrollbar-thumb {
  background: #f3f3f3;
  border-radius: 10px;
}
a {
  text-decoration-line: none;
  color: black;
}
</style>
