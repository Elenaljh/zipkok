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
        {{ moneyFormat(houseInfo.averagePrice) }}
      </p>
      <p>{{ houseInfo.sido }} {{ houseInfo.sigungu }} {{ houseInfo.dong }}</p>
      <p style="font-weight: bold">{{ houseInfo.name }}</p>
    </div>
  </div>
</template>

<style scoped></style>
