<script setup>
import { ref, computed, watchEffect } from "vue";
import axios from "axios";
import { useMemberStore } from "@/stores/member";
import { adjustedMoneyFormat } from "@/util/util";
import { useHouseStore } from "@/stores/house";

const store = useMemberStore();
const houseStore = useHouseStore();
const type = ref("매매");
const info = ref([]);
const priceAverage = ref({});
/*
amount: 0
aptCode: "APT4719033080250018000001"
aptName: "남경앳홈비앙채"
area: 26.4449
bjdCode: "4719012300"
day: 5
deposit: 4500
dong: 0
floor: 11
month: 2
monthlyRent: 0
streetCode: null
transactionId: 185926
type: "전월세"
year: 2024

{amount: 0, deposit: 3004, monthlyRent: 22}
*/

const getTransactionInfo = async () => {
  const response = await axios.get(store.url + "/apt/transaction", {
    params: {
      aptCode: houseStore.houseId,
      type: type.value,
    },
  });
  info.value = response.data;
  const avg = await axios.get(store.url + "/apt/average", {
    params: {
      aptCode: houseStore.houseId,
      type: type.value,
    },
  });
  priceAverage.value = avg.data;
};

watchEffect(async () => {
  const response = await axios.get(store.url + "/apt/transaction", {
    params: {
      aptCode: houseStore.houseId,
      type: type.value,
    },
  });
  info.value = response.data;
  const avg = await axios.get(store.url + "/apt/average", {
    params: {
      aptCode: houseStore.houseId,
      type: type.value,
    },
  });
  priceAverage.value = avg.data;
});

const visibleCount = ref(5);
const visibleInfo = computed(() => {
  return info.value.slice(0, visibleCount.value);
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
    <select
      class="form-select"
      style="width: 150px"
      v-model="type"
      @change="getTransactionInfo"
    >
      <option value="매매" selected>매매</option>
      <option value="전월세">전/월세</option>
    </select>
  </div>
  <p style="text-align: center; font-weight: bold">
    <span style="color: dimgray; font-size: 120%" class="me-2"
      >평균 거래가</span
    >
    <span style="color: #00b4d8; font-size: 150%" v-show="type === '매매'">{{
      adjustedMoneyFormat(priceAverage.amount)
    }}</span>
    <span style="color: #00b4d8; font-size: 150%" v-show="type === '전월세'"
      >{{ adjustedMoneyFormat(priceAverage.deposit) }}/{{
        adjustedMoneyFormat(priceAverage.monthlyRent)
      }}</span
    >
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
            <td style="padding-left: 20px">
              {{ item.year }}.{{ item.month }}.{{ item.day }}
            </td>
            <td>{{ Math.round(item.area) }}평</td>
            <td v-if="type === '매매'">
              {{ adjustedMoneyFormat(item.amount) }}
            </td>
            <td v-if="type === '전월세'">
              {{ adjustedMoneyFormat(item.deposit) }}/{{
                !item.monthlyRent
                  ? "전세"
                  : adjustedMoneyFormat(item.monthlyRent)
              }}
            </td>
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
      v-if="visibleInfo.length !== info.length && info.length > 5"
    >
      더보기
    </button>
    <button
      type="button"
      class="btn btn-light"
      @click="close"
      v-if="visibleInfo.length === info.length && info.length > 5"
    >
      접기
    </button>
  </div>
</template>

<style scoped></style>
