import { ref } from "vue";
import { defineStore } from "pinia";

export const useHouseStore = defineStore(
  "house",
  () => {
    const houseId = ref("");
    const changeId = (newId) => {
      houseId.value = newId;
    };

    const houseDetail = ref({});
    const changeDetail = (newDetail) => {
      houseDetail.value = newDetail;
    };

    const lat = ref();
    const changeLat = (v) => {
      lat.value = v;
    }

    const lng = ref();
    const changeLng = (v) => {
      lng.value = v;
    }

    return { houseId, changeId, houseDetail, changeDetail, lat, lng, changeLat, changeLng };
  },
  {
    persist: true,
  }
);
