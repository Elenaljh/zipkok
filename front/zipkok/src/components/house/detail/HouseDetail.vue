<script setup>
import HouseDetailBasicItem from "@/components/house/detail/item/HouseDetailBasicItem.vue";
import HouseDetailEnvItemVue from "@/components/house/detail/item/HouseDetailEnvItem.vue";
import HouseDetailFacItemVue from "@/components/house/detail/item/HouseDetailFacItem.vue";
import HouseDetailRecItemVue from "@/components/house/detail/item/HouseDetailRecItem.vue";
import { ref } from "vue";
const { houseId } = defineProps({ houseId: String });
const houseInfo = ref({
  id: 1,
  name: "남경앳홈비앙채",
  sido: "경상북도",
  sigungu: "구미시",
  dong: "진평동",
  roadAddress: "경상북도 구미시 삼일로 18",
  averagePrice: 150000000,
  numOfDong: 1,
  numOfHouseHolds: 126,
  dongCode: 4719012300,
});

const num = ref(0);
const changeTab = (val) => {
  num.value = val;
};
</script>

<template>
  <div class="modal-dialog modal-dialog-scrollable modal-dialog-centered modal-xl">
    <div class="modal-content">
      <div class="modal-body" id="modal">
        <div class="mb-2"><img src="/src/assets/houseInfo.png" /></div>
        <div class="ms-2">
          <h5 style="font-weight: bolder">{{ houseInfo.name }}</h5>
          <p style="color: dimgray">
            {{ houseInfo.sido }} {{ houseInfo.sigungu }} {{ houseInfo.dong }}
          </p>
        </div>
        <!--탭 파트-->
        <ul class="nav nav-underline ms-2">
          <li class="nav-item">
            <a
              class="nav-link boardNav"
              :class="{ active: num === 0 }"
              aria-current="page"
              @click="changeTab(0)"
              >기본 정보</a
            >
          </li>
          <li class="nav-item">
            <a class="nav-link boardNav" :class="{ active: num === 1 }" @click="changeTab(1)"
              >환경 정보</a
            >
          </li>
          <li class="nav-item">
            <a class="nav-link boardNav" :class="{ active: num === 2 }" @click="changeTab(2)"
              >동네 정보</a
            >
          </li>
          <li class="nav-item">
            <a class="nav-link boardNav" :class="{ active: num === 3 }" @click="changeTab(3)"
              >최신 소식</a
            >
          </li>
        </ul>
        <div class="ms-2 mt-3">
          <HouseDetailBasicItem v-if="num == 0" />
          <HouseDetailEnvItemVue v-if="num == 1" />
          <HouseDetailFacItemVue v-if="num == 2" />
          <HouseDetailRecItemVue v-if="num == 3" />
        </div>
      </div>
    </div>
  </div>
</template>

<style scoped>
/* nav tab 색상 변경 */
.nav-link.boardNav {
  color: gray;
}
.nav-link.boardNav.active {
  color: #00b4d8;
}
#modal::-webkit-scrollbar {
  width: 10px;
}
#modal::-webkit-scrollbar-thumb {
  background: #e6e6e6;
  border-radius: 10px;
}
</style>
