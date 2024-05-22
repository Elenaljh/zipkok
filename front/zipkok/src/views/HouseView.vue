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
const showHouseList = ref(true);

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
const buttonClick = () => {
  showHouseList.value = !showHouseList.value;
  if (showHouseList.value) {
    hlw.value = 30;
  } else {
    hlw.value = 100;
  }
};
</script>

<template>
  <div class="d-flex">
    <HouseList
      v-show="showHouseList"
      :style="{ 'min-width': '450px', 'max-width': '550px', width: hlw + '%' }"
      :type="'house'"
      :houseMarkerList="houseMarkerList"
      @updateHouseList="editHouseList"
      @openModal="openModal"
    />
    <Button @click="buttonClick" id="sidebarButton">
      <img
        v-if="!showHouseList"
        src="/src/assets/sidebarOpen.png"
        width="15px"
        height="15px"
        style="margin: auto"
        class="mx-0 p-0" />
      <img
        v-if="showHouseList"
        src="/src/assets/sidebarClose.png"
        width="15px"
        height="15px"
        style="margin: auto"
        class="mx-0 p-0"
    /></Button>
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

<style scoped>
#sidebarButton {
  width: fit-content;
  border: 0;
  background-color: transparent;
  padding: 0;
}
MapViewItem {
  z-index: 0;
}
HouseList {
  z-index: 2;
}
</style>
