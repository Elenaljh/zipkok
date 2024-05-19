import { ref, computed } from 'vue'
import { defineStore } from 'pinia'

export const useHouseStore = defineStore('house', () => {
  const houseList = ref([]);
  function setHouseList(val) {
    console.log("Get",val);
    houseList.value = val;
  }

  return { houseList,setHouseList }
})
