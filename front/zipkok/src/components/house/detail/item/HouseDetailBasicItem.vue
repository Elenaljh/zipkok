<script setup>
import { ref, computed } from "vue";

const type = ref("sale");
const saleInfo = ref({
  avgPrice: "1억 5,000만원",
  list: [
    { day: "24.04.16", area: 13, price: "1억", floor: 8 },
    { day: "24.03.10", area: 13, price: "9000만원", floor: 4 },
    { day: "24.03.9", area: 13, price: "9800만원", floor: 7 },
    { day: "24.02.20", area: 24, price: "1억 4000만원", floor: 15 },
    { day: "24.01.10", area: 13, price: "7000만원", floor: 10 },
    { day: "23.12.19", area: 24, price: "1억 1000만원", floor: 13 },
    { day: "24.04.16", area: 13, price: "1억", floor: 8 },
    { day: "24.03.10", area: 13, price: "9000만원", floor: 4 },
    { day: "24.03.9", area: 13, price: "9800만원", floor: 7 },
    { day: "24.02.20", area: 24, price: "1억 4000만원", floor: 15 },
    { day: "24.01.10", area: 13, price: "7000만원", floor: 10 },
    { day: "23.12.19", area: 24, price: "1억 1000만원", floor: 13 },
    { day: "24.04.16", area: 13, price: "1억", floor: 8 },
    { day: "24.03.10", area: 13, price: "9000만원", floor: 4 },
    { day: "24.03.9", area: 13, price: "9800만원", floor: 7 },
    { day: "24.02.20", area: 24, price: "1억 4000만원", floor: 15 },
    { day: "24.01.10", area: 13, price: "7000만원", floor: 10 },
    { day: "23.12.19", area: 24, price: "1억 1000만원", floor: 13 },
    { day: "24.04.16", area: 13, price: "1억", floor: 8 },
    { day: "24.03.10", area: 13, price: "9000만원", floor: 4 },
    { day: "24.03.9", area: 13, price: "9800만원", floor: 7 },
    { day: "24.02.20", area: 24, price: "1억 4000만원", floor: 15 },
    { day: "24.01.10", area: 13, price: "7000만원", floor: 10 },
    { day: "23.12.19", area: 24, price: "1억 1000만원", floor: 13 },
  ],
});
const rentInfo = ref({
  avgPrice: "1,000만원/40만원",
  list: [
    { day: "24.04.16", area: 13, price: "1000/38", floor: 8 },
    { day: "24.03.10", area: 13, price: "1000/39", floor: 4 },
    { day: "24.03.9", area: 13, price: "1000/40", floor: 7 },
    { day: "24.02.20", area: 24, price: "2000/43", floor: 15 },
    { day: "24.01.10", area: 13, price: "1000/40", floor: 10 },
    { day: "23.12.19", area: 24, price: "5000/28", floor: 13 },
  ],
});
const info = computed(() => {
  return type.value === "sale" ? saleInfo.value : rentInfo.value;
});
const visibleCount = ref(5);
const visibleInfo = computed(() => {
  return info.value.list.slice(0, visibleCount.value);
});
const showMore = () => {
  visibleCount.value += 5;
};
const close = () => {
  visibleCount.value = 5;
};
</script>

<template>
  <div>
    <!--셀렉트박스-->
    <select class="form-select" style="width: 150px" v-model="type">
      <option value="sale" selected>매매</option>
      <option value="rent">전/월세</option>
    </select>
  </div>
  <p style="text-align: center; font-weight: bold">
    <span style="color: dimgray; font-size: 120%" class="me-2"
      >평균 거래가</span
    >
    <span style="color: #00b4d8; font-size: 150%">{{ info.avgPrice }}</span>
  </p>
  <!--테이블-->
  <div class="d-flex justify-content-center">
    <div class="table-responsive card" style="width: 70%">
      <table class="table mb-0">
        <thead class="table-light">
          <tr>
            <th scope="col" style="padding-left: 20px">계약일 ↓</th>
            <th scope="col">면적</th>
            <th scope="col">거래가</th>
            <th scope="col">층</th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="item in visibleInfo" :key="item.day">
            <td style="padding-left: 20px">{{ item.day }}</td>
            <td>{{ item.area }}평</td>
            <td>{{ item.price }}</td>
            <td>{{ item.floor }}층</td>
          </tr>
        </tbody>
      </table>
    </div>
  </div>
  <div class="d-flex justify-content-center">
    <button
      type="button"
      class="btn btn-light"
      @click="showMore"
      v-if="visibleInfo.length !== info.list.length"
    >
      더보기
    </button>
    <button
      type="button"
      class="btn btn-light"
      @click="close"
      v-if="visibleInfo.length === info.list.length"
    >
      접기
    </button>
  </div>
</template>

<style scoped></style>
