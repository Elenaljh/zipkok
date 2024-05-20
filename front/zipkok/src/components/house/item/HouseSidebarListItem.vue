<script setup>
import { moneyFormat } from "@/util/util.js";
import { computed } from "vue";
const { houseInfo } = defineProps({ houseInfo: Object });

const priceTypeList = ["매매", "전/월세", ""];

const innerPType = computed(() => {
  if (houseInfo.avgAmount) {
    return 0;
  } else if (houseInfo.avgDeposit) {
    return 1;
  }
  return 2;
});

const showPrice = computed(() => {
  if (innerPType.value == 0) {
    return houseInfo.avgAmount;
  } else if (innerPType.value == 1) {
    return houseInfo.avgDeposit;
  }
  return "";
});
</script>

<template>
  <div>
    <div class="me-4 listModalItem">
      <div class="row p-0 ps-2 py-2 align-items-center">
        <div class="col-5" style="font-size: 1.2rem; font-weight: bold">
          {{ houseInfo.aptName }}
        </div>
        <div class="col-3 small-content" style="color: black">
          {{ priceTypeList[innerPType] }} {{ moneyFormat(showPrice * 10000) }}
        </div>
        <div class="col-4 small-content">
          {{ houseInfo.bjdAddress ? houseInfo.bjdAddress : houseInfo.drmAddress }}
        </div>
      </div>
    </div>
    <hr class="my-0" style="width: 95%; border-top: 1px solid gray" />
  </div>
</template>

<style scoped>
.small-content {
  display: -webkit-box;
  -webkit-line-clamp: 2; /* 최대 줄 수를 2로 설정 */
  -webkit-box-orient: vertical;
  overflow: hidden; /* 넘치는 텍스트를 숨김 */
  text-overflow: ellipsis; /* 넘치는 부분에 "..." 표시 */
}
</style>
