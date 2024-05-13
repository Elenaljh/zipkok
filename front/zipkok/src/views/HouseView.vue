<script setup>
import HouseList from "@/components/house/HouseList.vue";
import MapViewItem from "@/components/house/item/MapViewItem.vue";
</script>

<template>
  <div class="row">
    <HouseList class="col-5" :type="'house'" />
    <MapViewItem class="col" />
  </div>
</template>

<style scoped></style>
