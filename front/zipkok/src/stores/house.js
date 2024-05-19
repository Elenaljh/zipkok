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

    return { houseId, changeId, houseDetail, changeDetail };
  },
  {
    persist: true,
  }
);
