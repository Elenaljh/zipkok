<script setup>
import HouseList from "@/components/house/HouseList.vue";
import MapViewItem from "@/components/house/item/MapViewItem.vue";
import { ref } from "vue";

const hlw = ref(30);
</script>

<template>
  <div class="d-flex">
    <HouseList
      :style="{ 'min-width': '450px', 'max-width': '550px', width: hlw + '%' }"
      :type="'main'"
    />
    <MapViewItem :hlw="hlw" />
  </div>
</template>

<style scoped></style>
