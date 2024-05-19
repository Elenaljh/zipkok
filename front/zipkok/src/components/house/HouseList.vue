<script setup>
import { onMounted, ref, watch } from "vue";
import HouseDetail from "@/components/house/detail/HouseDetail.vue";
import RouterButton from "@/components/common/RouterButton.vue";
import AddressSelectBox from "@/components/common/AddressSelectBox.vue";
import { useRoute, useRouter } from "vue-router";
import HouseSidebarCardItem from "./item/HouseSidebarCardItem.vue";
import HouseSidebarListItem from "./item/HouseSidebarListItem.vue";

const { type } = defineProps({ type: String });
const route = useRoute();
const router = useRouter();
const childCompRef = ref(null);
const searchType = ref(route.query.searchType ? route.query.searchType : 0);
const searchValue = ref(route.query.searchValue ? route.query.searchValue : "");
const searchBuildingValue = ref("");
const houseId = ref(0);
const priceType = ref(0);
const houseList = ref([
  {
    id: 1,
    name: "남경앳홈비앙채",
    sido: "경상북도",
    sigungu: "구미시",
    dong: "진평동",
    averagePrice: 150000000,
  },
  {
    id: 2,
    name: "Happy Home",
    sido: "경상북도",
    sigungu: "구미시",
    dong: "진평동",
    averagePrice: 150000000,
  },
  {
    id: 3,
    name: "와이드빌",
    sido: "경상북도",
    sigungu: "구미시",
    dong: "진평동",
    averagePrice: 180000000,
  },
  {
    id: 4,
    name: "삼성전자",
    sido: "경상북도",
    sigungu: "구미시",
    dong: "진평동",
    averagePrice: 650000000,
  },
]);

const changeTab = (val) => {
  searchType.value = val;
};

const callChildFunction = () => {
  if (searchType.value == 0) {
    childCompRef.value.sendDataToParent();
  } else if (searchType.value == 1) {
    searchValue.value = searchBuildingValue.value;
  }
};

const receiveDataFromChild = (data) => {
  console.log("데이터 수신 완료");
  if (searchType.value == 0 && data.dongCode) {
    searchValue.value = data.dongCode;
    console.log("데이터 받음 " + data.dongCode);
  }
};

const getList = () => {
  console.log(searchType.value + ", " + searchValue.value + "로 새로 데이터 받아오기");
};

watch(searchValue, () => {
  console.log("watch");
  if (type == "main") {
    router.push({
      name: "house",
      query: { searchType: searchType.value, searchValue: searchValue.value },
    });
  }
  getList();
});

onMounted(() => {
  console.log("mount됨");
  getList();
});

const setHouseId = (id) => {
  console.log("setHouseId=" + id);
  houseId.value = id;
};
</script>

<template>
  <div class="container me-0 mb-0 ms-3 mt-2">
    <!-- 검색창 시작 -->
    <div class="mb-4 ms-1">
      <ul class="nav nav-underline">
        <li class="nav-item">
          <a
            class="nav-link boardNav"
            :class="{ active: searchType == 0 }"
            aria-current="page"
            @click="changeTab(0)"
            >지역</a
          >
        </li>
        <li class="nav-item">
          <a class="nav-link boardNav" :class="{ active: searchType == 1 }" @click="changeTab(1)"
            >건물명</a
          >
        </li>
      </ul>

      <div class="mt-3 d-flex justify-content-between align-items-center">
        <!-- 지역 검색 -->
        <div v-if="searchType == 0">
          <div class="d-flex align-items-center">
            <AddressSelectBox
              ref="childCompRef"
              @requestDataFromChild="receiveDataFromChild"
              :fullStyle="{ width: 'fit-content' }"
            />
          </div>
        </div>
        <!-- 건물명 검색 -->
        <div class="w-100 d-flex align-items-center me-5" v-if="searchType == 1">
          <input
            class="w-100 p-1 ps-2"
            style="height: inherit"
            type="text"
            name="buildingName"
            id="buildingName"
            v-model="searchBuildingValue"
          />
        </div>
        <RouterButton
          buttonIcon="/src/assets/buttonSearch.png"
          :buttonFunc="callChildFunction"
          class="me-3"
        />
      </div>
    </div>
    <!-- 검색창 끝 -->
    <!-- 결과 리스트 시작 -->
    <!-- 추천 매물 시작  -->
    <div class="ms-1">
      <p class="small-title" v-if="type == 'main'">추천 매물</p>
      <div
        v-if="type == 'main'"
        class="w-100 mt-2 d-flex flex-wrap justify-content-start column-gap-1"
      >
        <HouseSidebarCardItem
          data-bs-toggle="modal"
          data-bs-target="#exampleModal"
          v-for="house in houseList"
          :key="house"
          :houseInfo="house"
          @click="setHouseId(house.id)"
        />
      </div>
      <!-- 추천 매물 끝 -->
      <!-- 검색 결과 시작 -->
      <p class="small-title" v-if="type == 'house'">검색 결과</p>
      <div v-if="type == 'house'" class="w-100 mt-2">
        <HouseSidebarListItem
          data-bs-toggle="modal"
          data-bs-target="#exampleModal"
          v-for="house in houseList"
          :key="house"
          :houseInfo="house"
          :priceType="priceType"
          @click="setHouseId(house.id)"
        />
      </div>
      <!-- 검색 결과 끝 -->
    </div>
    <!-- 결과 리스트 끝 -->
    <!-- 세부 정보 버튼(임시) -->
    <!-- <button
      type="button"
      class="btn btn-primary"
      data-bs-toggle="modal"
      data-bs-target="#exampleModal"
    >
      Launch demo modal
    </button> -->
    <!-- 세부 정보 모달 -->
    <div
      class="modal fade"
      id="exampleModal"
      tabindex="-1"
      aria-labelledby="exampleModalLabel"
      aria-hidden="true"
    >
      <HouseDetail :houseId="houseId" />
    </div>
  </div>
</template>

<style scoped></style>
