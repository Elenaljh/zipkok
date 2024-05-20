<script setup>
import { moneyFormat } from "@/util/util.js";
const { houseInfo } = defineProps({ houseInfo: Object });
</script>

<template>
  <div class="p-0 me-4 mb-3 cardModalButton" style="width: 40%">
    <img
      src="@/assets/houseSample.jpg"
      alt=""
      class="w-100 mx-0"
      style="border-radius: 9px 9px 0 0"
    />
    <div class="m-2">
      <p class="mb-1" style="font-weight: bold; font-size: 22px">
        {{ moneyFormat(houseInfo.averagePrice ? houseInfo.averagePrice : 160000000) }}
      </p>
      <p class="small-content">{{ houseInfo.drmAddress }}</p>
      <p style="font-weight: bold">{{ houseInfo.aptName }}</p>
    </div>
  </div>
</template>

<style scoped>
.small-content {
  display: -webkit-box;
  -webkit-line-clamp: 1; /* 최대 줄 수를 2로 설정 */
  -webkit-box-orient: vertical;
  overflow: hidden; /* 넘치는 텍스트를 숨김 */
  text-overflow: ellipsis; /* 넘치는 부분에 "..." 표시 */
}
</style>
