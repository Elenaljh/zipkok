<script setup>
import { ref } from "vue";
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

const air = ref([
  { address: "경상북도 구미시 1공단로6길 54", amount: 699359 },
  { address: "경상북도 구미시 산동읍 첨단기업로 178", amount: 97194 },
  { address: "경상북도 구미시 3공단3로 46-79(시미동)", amount: 44671 },
  { address: "경상북도 구미시 1공단로4길 40", amount: 38229 },
]);
</script>

<template>
  <div class="me-2">
    <!--녹지-->
    <div>
      <p style="font-size: large; font-weight: bold">
        <span class="me-2">이 주변의</span><span style="color: green">공원</span>
      </p>
      <div class="d-flex justify-content-between">
        <!--표-->
        <div class="table-responsive card" style="width: 48%; max-height: 300px; overflow-y: auto">
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
        <!--카카오 지도-->
        <img src="/src/assets/map.png" width="48%" alt="" />
        <!--카카오 지도 끝-->
      </div>
    </div>
    <!--녹지 끝-->
    <hr style="background: #00b3d6; border: 0; height: 1.5px" />
    <!--대기배출-->
    <div>
      <p style="font-size: large; font-weight: bold">
        <span class="me-2">이 지역</span><span style="color: dimgray">대기배출</span>
      </p>
      <div class="table-responsive card" style="max-height: 300px; overflow-y: auto">
        <table class="table mb-0">
          <tbody>
            <tr v-for="item in air" :key="item.address">
              <td style="padding-left: 20px">{{ item.address }}</td>
              <td style="text-align: right; padding-right: 20px">{{ item.amount }} kg/년</td>
            </tr>
          </tbody>
        </table>
      </div>
    </div>
    <!--대기배출 끝-->
  </div>
</template>

<style scoped>
.table-responsive.card::-webkit-scrollbar {
  width: 10px;
}
.table-responsive.card::-webkit-scrollbar-thumb {
  background: #ade8f4;
  border-radius: 10px;
}
</style>
