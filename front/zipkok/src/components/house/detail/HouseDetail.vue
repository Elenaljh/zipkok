<script setup>
import HouseDetailBasicItem from "@/components/house/detail/item/HouseDetailBasicItem.vue";
import HouseDetailEnvItemVue from "@/components/house/detail/item/HouseDetailEnvItem.vue";
import HouseDetailFacItemVue from "@/components/house/detail/item/HouseDetailFacItem.vue";
import { ref, watchEffect } from "vue";
import { useMemberStore } from "@/stores/member";
import { useHouseStore } from "@/stores/house";
import { getGrade } from "@/util/airConditionUtil";
import { getDong, getPopulation, getNews, dong } from "@/util/houseDetail";
import axios from "axios";

const store = useMemberStore();
const houseStore = useHouseStore();

watchEffect(async () => {
  const response = await axios.get(store.url + "/apt/details", {
    params: {
      aptCode: houseStore.houseId,
    },
  });
  houseInfo.value = response.data;
  houseStore.changeDetail(response.data);
  getBusStations();
  getGrade(houseInfo.value.lng, houseInfo.value.lat);
  getDong(houseInfo.value.bjdCode);
  getPopulation(houseInfo.value.bjdCode);
  getNews(houseInfo.value.aptName, dong.value);
});

const houseInfo = ref({});

const num = ref(0);
const changeTab = (val) => {
  num.value = val;
};

const busStation = ref([]);
const getBusStations = async () => {
  console.log("버스station 진입");
  const { VITE_BUS_API_KEY } = import.meta.env;
  const response = await axios.get(
    "http://apis.data.go.kr/1613000/BusSttnInfoInqireService/getCrdntPrxmtSttnList",
    {
      params: {
        serviceKey: VITE_BUS_API_KEY,
        _type: "json",
        numOfRows: 3,
        gpsLati: houseInfo.value.lat,
        gpsLong: houseInfo.value.lng,
      },
    }
  );
  const tempArr = response.data.response.body.items.item;
  busStation.value = [];
  for (let i = 0; i < 3; i++) {
    busStation.value.push(tempArr[i].nodenm);
  }
  console.log(busStation.value);
};
</script>

<template>
  <!--
    { "aptCode": "APT4719033080250018000001", "aptName": "남경앳홈비앙채",
    "drmAddress": "경상북도 구미시 삼일로 18", "bjdAddress": "", "bjdCode":
    "4719012300", "houseNum": 126, "buildYear": 2014, "dongNum": 0, "carNum": 0,
    "cctvNum": 0, "lng": 128.4179714, "lat": 36.10666837, "aptType": "",
    "facility": "", "aptAnotherCode": null }-->
  <div class="modal-dialog modal-dialog-scrollable modal-dialog-centered modal-xl">
    <div class="modal-content">
      <div class="modal-body" id="modal">
        <div class="mb-2"><img src="/src/assets/houseInfo.png" /></div>
        <div class="ms-2">
          <h5 style="font-weight: bolder">{{ houseInfo.aptName }}</h5>
          <p style="color: dimgray">
            {{ houseInfo.drmAddress }}
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
              >주변 정보</a
            >
          </li>
          <li class="nav-item">
            <a class="nav-link boardNav" :class="{ active: num === 2 }" @click="changeTab(2)"
              >동네 소식</a
            >
          </li>
        </ul>
        <div class="ms-2 mt-3">
          <HouseDetailBasicItem v-if="num == 0" :houseInfo="houseInfo" :busStation="busStation" />
          <HouseDetailEnvItemVue v-if="num == 1" :houseInfo="houseInfo" />
          <HouseDetailFacItemVue v-if="num == 2" :houseInfo="houseInfo" />
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
