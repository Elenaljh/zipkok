<script setup lang="ts">
import { ref } from "vue";
import { KakaoMap, KakaoMapMarker } from "vue3-kakao-maps";
const { houseInfo } = defineProps({
  houseInfo: Object,
});
import { schools, offices } from "@/util/houseDetail";

const parks = ref([
  { name: "칼국수 공원", address: "구미 인동" },
  { name: "멜론 공원", address: "구미 진평동" },
  { name: "사과 공원", address: "구미 인동" },
  { name: "딸기 공원", address: "구미 인동" },
  { name: "바나나 공원", address: "구미 진평동" },
  { name: "수박 공원", address: "구미 인동" },
  { name: "오렌지 공원", address: "구미 진평동" },
  { name: "참외 공원", address: "구미 인동" },
  { name: "블루베리 공원", address: "구미 인동" },
  { name: "망고 공원", address: "구미 진평동" },
  { name: "키위 공원", address: "구미 인동" },
]);
const current = ref("");
</script>

<template>
  <div class="me-2">
    <select class="form-select w-25 mb-2" v-model="current">
      <option value="" selected>선택하세요</option>
      <option value="park">공원</option>
      <option value="office">관공서</option>
      <option value="school">학교</option>
    </select>
    <KakaoMap
      :lat="houseInfo.lat"
      :lng="houseInfo.lng"
      width="65rem"
      class="mb-4"
    >
      <!--아파트 위치 마커 (고정)-->
      <KakaoMapMarker :lat="houseInfo.lat" :lng="houseInfo.lng" />

      <div v-if="current === 'office'">
        <KakaoMapMarker
          v-for="item in offices"
          :key="item"
          :lat="item.latitude"
          :lng="item.longitude"
        />
      </div>
      <div v-if="current === 'school'">
        <KakaoMapMarker
          v-for="item in schools"
          :key="item"
          :lat="item.lat"
          :lng="item.lng"
        />
      </div>
    </KakaoMap>
    <!--녹지-->
    <div v-if="current == 'park'">
      <p style="font-size: large; font-weight: bold">
        <span class="me-2">이 주변의</span
        ><span style="color: green">공원</span>
      </p>
      <!--표-->
      <div
        class="table-responsive card"
        style="max-height: 300px; overflow-y: auto"
      >
        <table class="table mb-0">
          <tbody>
            <tr v-for="item in parks" :key="item.name">
              <td style="padding-left: 20px">{{ item.name }}</td>
              <td>{{ item.address }}</td>
            </tr>
          </tbody>
        </table>
      </div>
      <!--표 끝-->
    </div>
    <!--녹지 끝-->
    <!--주요시설-->
    <div v-if="current == 'office'">
      <p style="font-size: large; font-weight: bold">
        <span>주변 </span>
        <span style="color: #00b3d6">관공서</span>
      </p>
      <div
        class="table-responsive card"
        style="max-height: 300px; overflow-y: auto"
      >
        <table class="table mb-0">
          <tbody>
            <tr v-for="item in offices" :key="item">
              <td style="padding-left: 20px">{{ item.officeName }}</td>
              <td>{{ item.officeType }}</td>
              <td>{{ item.drmAddress }}</td>
            </tr>
          </tbody>
        </table>
      </div>
    </div>
    <!--주요시설 끝-->
    <!--학교-->
    <div v-if="current == 'school'">
      <p style="font-size: large; font-weight: bold">
        <span>주변 </span>
        <span style="color: #00b3d6">학교</span>
      </p>
      <div
        class="table-responsive card"
        style="max-height: 300px; overflow-y: auto"
      >
        <table class="table mb-0">
          <tbody>
            <tr v-for="item in schools" :key="item">
              <td style="padding-left: 20px">{{ item.schoolName }}</td>
              <td>{{ item.schoolType }}</td>
              <td>{{ item.drmAddress }}</td>
            </tr>
          </tbody>
        </table>
      </div>
    </div>
    <!--학교 끝-->
  </div>
</template>

<style scoped>
.table-responsive.card::-webkit-scrollbar {
  width: 10px;
}
.table-responsive.card::-webkit-scrollbar-thumb {
  background: #e8e8e8;
  border-radius: 10px;
}
</style>
