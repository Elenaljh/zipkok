import { ref } from "vue";
import { defineStore } from "pinia";

export const useHouseStore = defineStore(
  "house",
  () => {
    const houseId = ref("");
    const changeId = (newId) => {
      houseId.value = newId;
    };

    return { houseId, changeId };
  },
  {
    persist: true,
  }
);
