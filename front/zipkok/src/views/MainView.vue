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
      class="position-relative"
      :style="{ 'min-width': '450px', 'max-width': '550px', width: hlw + '%', 'z-index': 5, 'background-color': 'white' }"
      :type="'main'"
      :houseMarkerList="houseMarkerList"
      @updateHouseList="editHouseList"
      @openModal="openModal"
    />
    <div class="position-relative" style="margin-top: 40vh; margin-bottom: auto; z-index: 5;  " >
      <Button class="d-flex align-items-center" style="height: 40px; background-color: white" @click="buttonClick" id="sidebarButton">
        <img
          v-if="!showHouseList"
          src="/src/assets/sidebarOpen.png"
          width="18px"
          height="18px"
          style="margin: auto"
          class="mx-0 p-0" />
        <img
          v-if="showHouseList"
          src="/src/assets/sidebarClose.png"
          width="18px"
          height="18px"
          style="margin: auto"
          class="mx-0 p-0"
        />
      </Button>
  </div>
    
    <MapViewItem class="position-absolute start-0" style="top: 10vh; z-index: 1" :houseMarkerList="houseMarkerList" :hlw="hlw" @openModal="openModal" />
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
  padding: 0;
  border: 1px solid #90E0EF; border-left: none;
  border-radius: 0 5px 5px 0;
}
</style>
