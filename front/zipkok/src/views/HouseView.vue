<script setup>
import HouseList from "@/components/house/HouseList.vue";
import MapViewItem from "@/components/house/item/MapViewItem.vue";
import { ref } from "vue";

// 표시할 마커들
const houseMarkerList = ref([
  { key: 1, lat: 36.11188751950904, lng: 128.40887917180015 },
  { key: 2, lat: 36.11235925001655, lng: 128.41472918511954 },
  { key: 3, lat: 36.10936293837245, lng: 128.4181624879446 },
  { key: 4, lat: 36.10718555335274, lng: 128.41787903199184 },
]);
</script>

<template>
  <div class="d-flex">
    <HouseList style="width: fit-content" :type="'main'" :houseMarkerList="houseMarkerList" />
    <MapViewItem :houseMarkerList="houseMarkerList" />
  </div>
</template>

<style scoped></style>
