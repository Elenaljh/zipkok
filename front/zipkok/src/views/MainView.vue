<script setup>
import HouseList from "@/components/house/HouseList.vue";
import HouseDetail from "@/components/house/detail/HouseDetail.vue";
import MapViewItem from "@/components/house/item/MapViewItem.vue";
import { Modal } from "bootstrap";
import { onMounted, ref } from "vue";

const modalRef = ref(null);
const hlw = ref(30);
const houseMarkerList = ref([]);
let modalInstance = null;

function editHouseList(val) {
  console.log("houseView - editHouseList ", val);
  houseMarkerList.value = val;
}

function openModal(id) {
  console.log("open Modal2", id, modalInstance);
  if (modalInstance) {
    modalInstance.show();
  }
}

onMounted(() => {
  if (modalRef.value) {
    modalInstance = new Modal(modalRef.value);
  }
});
</script>

<template>
  <div class="d-flex">
    <HouseList
      :style="{ 'min-width': '450px', 'max-width': '550px', width: hlw + '%' }"
      :type="'main'"
      :houseMarkerList="houseMarkerList"
      @updateHouseList="editHouseList"
      @openModal="openModal"
    />
    <MapViewItem :houseMarkerList="houseMarkerList" :hlw="hlw" @openModal="openModal" />
    <div
      class="modal fade"
      id="exampleModal"
      tabindex="-1"
      aria-labelledby="exampleModalLabel"
      aria-hidden="true"
      ref="modalRef"
    >
      <HouseDetail />
    </div>
  </div>
</template>

<style scoped></style>
