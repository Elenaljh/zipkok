<script setup>
import HouseList from "@/components/house/HouseList.vue";
import HouseDetail from "@/components/house/detail/HouseDetail.vue";
import MapViewItem from "@/components/house/item/MapViewItem.vue";
import { onMounted, ref } from "vue";
import { Modal } from "bootstrap";

const hlw = ref(30);
// 표시할 마커들
const houseMarkerList = ref([]);
const modalRef = ref(null);
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
      style="width: fit-content"
      :type="'house'"
      :houseMarkerList="houseMarkerList"
      @updateHouseList="editHouseList"
      @openModal="openModal"
    />
    <MapViewItem :houseMarkerList="houseMarkerList" @openModal="openModal" />
    <!-- 세부 정보 모달 -->
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
