<script setup>
import { moneyFormat } from "@/util/util.js";
const { houseInfo, priceType } = defineProps({ houseInfo: Object, priceType: Number });

const priceTypeList = ["매매", "전/월세"];
</script>

<template>
  <div>
    <div class="me-4 listModalItem">
      <div class="row p-0 ps-2 py-2 align-items-center">
        <div class="col-5" style="font-size: 1.2rem; font-weight: bold">{{ houseInfo.name }}</div>
        <div
          class="col-3 small-content"
          style="color: black; word-break: keep-all; overflow-wrap: break-word"
        >
          {{ priceTypeList[priceType] }} {{ moneyFormat(houseInfo.averagePrice) }}
        </div>
        <div class="col-4 small-content" style="word-break: keep-all; overflow-wrap: break-word">
          {{ houseInfo.sido }} {{ houseInfo.sigungu }} {{ houseInfo.dong }}
        </div>
      </div>
    </div>
    <hr class="my-0" style="width: 95%; border-top: 1px solid gray" />
  </div>
</template>

<style scoped></style>
