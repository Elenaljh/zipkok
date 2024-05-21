<script setup>
import { moneyFormat } from "@/util/util.js";
import RoadViewNaverItem from '@/components/house/item/RoadViewNaverItem.vue';
import { computed } from "vue";
const { houseInfo } = defineProps({ houseInfo: Object });

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
  <div class="p-0 me-4 mb-3 cardModalButton" style="width: 40%">
    <RoadViewNaverItem :lat="houseInfo.lat" :lng="houseInfo.lng" :pan="houseInfo.pan" style="border-radius: 9px 9px 0 0" />
    <div class="m-2">
      <p class="mb-1" style="font-weight: bold; font-size: 22px">
        {{ moneyFormat(showPrice * 10000) }}
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
