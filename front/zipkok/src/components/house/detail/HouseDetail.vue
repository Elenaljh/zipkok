<script setup>
import HouseDetailBasicItem from "@/components/house/detail/item/HouseDetailBasicItem.vue";
import HouseDetailEnvItemVue from "@/components/house/detail/item/HouseDetailEnvItem.vue";
import HouseDetailFacItemVue from "@/components/house/detail/item/HouseDetailFacItem.vue";
import { ref, watchEffect } from "vue";
import { useMemberStore } from "@/stores/member";
import { useHouseStore } from "@/stores/house";
import { getGrade } from "@/util/airConditionUtil";
import { getDong, getNews, dong, getMalePopulation, getFemalePopulation } from "@/util/houseDetail";
import axios from "axios";
import { storeToRefs } from "pinia";

const store = useMemberStore();
const houseStore = useHouseStore();
const { isAuthorized, preference } = storeToRefs(store);
const { houseId } = storeToRefs(houseStore);
const userPreference = ref("");
const houseInfo = ref({});

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
  getMalePopulation(houseInfo.value.bjdCode);
  getFemalePopulation(houseInfo.value.bjdCode);
  getNews(houseInfo.value.aptName, dong.value);
  if (isAuthorized.value) {
    getUserPreference();
  }
  console.log("preference", preference.value);
});

const num = ref(0);
const changeTab = (val) => {
  num.value = val;
};

const busStation = ref([]);
const getBusStations = async () => {
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

const getUserPreference = async () => {
  let result = null;
  if (preference.value === "행정시설") {
    result = await getOfficeNum(houseInfo.value.lat, houseInfo.value.lng);
  } else if (preference.value === "학군") {
    result = await getSchoolNum(houseInfo.value.lat, houseInfo.value.lng);
  } else if (preference.value === "근방 맛집") {
    result = await getRestaurantNum(houseInfo.value.bjdCode);
  } else if (preference.value === "CCTV 대수") {
    result = await getCCTVNum(houseId.value);
  } else if (preference.value === "녹지") {
    result = await getParkNum(houseInfo.value.lat, houseInfo.value.lng);
  }
  userPreference.value = result;
};

const getCCTVNum = async (aptCode) => {
  const result = await axios.get("http://localhost:8080/apt/cctv", {
    params: {
      aptCode: aptCode,
    },
  });
  return result.data;
};

const getOfficeNum = async (lat, lng) => {
  const result = await axios.get("http://localhost:8080/apt/office", {
    params: {
      lat: lat,
      lng: lng,
    },
  });
  return result.data;
};

const getSchoolNum = async (lat, lng) => {
  const result = await axios.get("http://localhost:8080/apt/school", {
    params: {
      lat: lat,
      lng: lng,
    },
  });
  return result.data;
};

const getParkNum = async (lat, lng) => {
  const result = await axios.get("http://localhost:8080/apt/park", {
    params: {
      lat: lat,
      lng: lng,
    },
  });
  return result.data;
};

const getRestaurantNum = async (dongCode) => {
  const result = await axios.get("http://localhost:8080/apt/restaurant", {
    params: {
      dongCode: dongCode,
    },
  });
  return result.data;
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
        <div class="mb-2 ms-2"><img src="/src/assets/house.png" /></div>
        <div class="ms-2">
          <h5 style="font-weight: bolder">{{ houseInfo.aptName }}</h5>
          <p style="color: dimgray">
            {{ houseInfo.drmAddress }}
          </p>
        </div>
        <!--preference-->
        <div
          class="alert alert-info m-0 p-0 ps-2 ms-2"
          role="alert"
          style="height: 30px"
          v-if="preference && preference !== '없음'"
        >
          <div v-if="preference === 'CCTV 대수'">
            이 아파트는 CCTV가 {{ userPreference ? userPreference : 0 }}대 있습니다.
          </div>
          <div v-if="preference === '행정시설'">
            이 아파트의 반경 2km 내에 행정시설이 {{ userPreference ? userPreference : 0 }}개
            있습니다.
          </div>
          <div v-if="preference === '학군'">
            이 아파트의 반경 2km 내에 학교가 {{ userPreference ? userPreference : 0 }}개 있습니다.
          </div>
          <div v-if="preference === '녹지'">
            이 아파트의 반경 2km 내에 도시공원이 {{ userPreference ? userPreference : 0 }}개
            있습니다.
          </div>
          <div v-if="preference === '근방 맛집'">
            이 동네에 모범음식점이 {{ userPreference ? userPreference : 0 }}곳 있습니다.
          </div>
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
