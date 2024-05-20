<script setup>
import { ref } from "vue";
import { dong, population, news } from "@/util/houseDetail";
import { numberFormat } from "@/util/util";
const { houseInfo } = defineProps({
  houseInfo: Object,
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
      <div class="table-responsive card" style="max-height: 380px; overflow-y: auto">
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
      </div>
    </div>
    <!--인구정보 끝-->
    <hr style="background: #00b3d6; border: 0; height: 1.5px" />
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
