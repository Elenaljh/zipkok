<script setup>
import HouseList from "@/components/house/HouseList.vue";
import MapViewItem from "@/components/house/item/MapViewItem.vue";
import { ref } from "vue";
const hlw = ref(30);
// 표시할 마커들
const houseMarkerList = ref([]);

function editHouseList(val) {
  console.log("houseView - editHouseList ", val);
  houseMarkerList.value = val;
}
</script>

<template>
  <div class="d-flex">
    <HouseList
      style="width: fit-content"
      :type="'house'"
      :houseMarkerList="houseMarkerList"
      @updateHouseList="editHouseList"
    />
    <MapViewItem :houseMarkerList="houseMarkerList" />
  </div>
</template>

<style scoped></style>
