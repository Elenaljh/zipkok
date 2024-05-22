<script setup>
import { onMounted, ref } from "vue";
import HouseDetail from "@/components/house/detail/HouseDetail.vue";
import RouterButton from "@/components/common/RouterButton.vue";
import AddressSelectBox from "@/components/common/AddressSelectBox.vue";
import { useRoute, useRouter } from "vue-router";
import HouseSidebarCardItem from "./item/HouseSidebarCardItem.vue";
import HouseSidebarListItem from "./item/HouseSidebarListItem.vue";
import { useHouseStore } from "@/stores/house";
import { getAptsByDong, getAptsByLatLngs, getRecApts, getAptsByName } from "@/api/map";
import { useMemberStore } from "@/stores/member";
import { storeToRefs } from "pinia";
import GoogleAddressComplete from '@/components/common/GoogleAddressComplete.vue';
const memberStore = useMemberStore();
const store = useHouseStore();
const { type } = defineProps({ type: String });
const route = useRoute();
const router = useRouter();
const childCompRef = ref(null);
const emit = defineEmits(["updateHouseList", "openModal"]);
const searchType = ref(route.query.searchType ? route.query.searchType : 2);
const searchValue = ref(route.query.searchValue ? route.query.searchValue : "");

const searchDongValue = ref(route.query.searchType == 0 ? route.query.searchValue : null);
const searchBuildingValue = ref(route.query.searchType == 1 ? searchValue.value : "");
const houseId = ref("APT0");
const priceType = ref(0);

const houseList = ref([]);

// --------------- 동 검색 탭 설정
const callChildFunction = () => {
  if (searchType.value == 0) {
    childCompRef.value.sendDataToParent();
  } else if (searchType.value == 1) {
    searchValue.value = searchBuildingValue.value;
  } else if (searchType.value == 2){
    //autocomplete
  }
  console.log("검색 값은 이거입니다: ", searchType.value, " ", searchValue.value);
  search();
};

const receiveDataFromChild = (data) => {
  console.log("데이터 수신 완료");
  if (searchType.value == 0 && data.dongCode) {
    searchValue.value = data.dongCode;
    console.log("데이터 받음 " + data.dongCode);
  }
};

// --------------- 검색 작업
function search() {
  if (type == "main") {
    router.push({
      name: "house",
      query: { searchType: searchType.value, searchValue: searchValue.value },
    });
  } else {
    getList();
  }
}

onMounted(() => {
  console.log("mount됨");
  console.log("query ", route.query);
  if (type == "main") {
    getRecommend();
  } else {
    // 검색창 세팅
    getList();
  }
});

// ----------------- 검색 api
// 검색 결과 받아오기 (동, 이름)
const getList = () => {
  console.log(searchType.value + ", " + searchValue.value + "로 새로 데이터 받아오기");
  if (searchType.value == 0) {
    getAptsByDong(
      {
        dong: searchValue.value,
      },
      ({ data }) => {
        console.log("받았다!!", data);
        houseList.value = data;
        settingHouseList(data);
      },
      (error) => {
        console.log(error);
      }
    );
  } else if (searchType.value == 1) {
    getAptsByName(
      {
        name: searchValue.value,
      },
      ({ data }) => {
        console.log("받았다!!", data);
        houseList.value = data;
        settingHouseList(data);
      },
      (error) => {
        console.log(error);
      }
    );
  }
};
// 검색 결과 받아오기 (범위)
const getRangeList = () => {
  console.log(searchType.value + ", " + searchValue.value + "로 새로 데이터 받아오기");
  if (searchType.value == 0) {
    getAptsByLatLngs(
      {
        dong: searchValue.value,
      },
      ({ data }) => {
        console.log("받았다!!", data);
        houseList.value = data;
        settingHouseList(data);
      },
      (error) => {
        console.log(error);
      }
    );
  }
};
// 추천 매물 받아오기
function getRecommend() {
  //현재 로그인 상태 확인
  console.log("로그인 확인");
  const { isAuthorized } = storeToRefs(memberStore);
  const flag = isAuthorized.value ? true : false;
  if (!("geolocation" in navigator)) {
    return;
  }

  // get position
  navigator.geolocation.getCurrentPosition((pos) => {
    getRecApts(
      {
        type: flag ? "login" : "logout",
        lng: pos.coords.longitude,
        lat: pos.coords.latitude,
      },
      ({ data }) => {
        console.log("받았다!!", data);
        houseList.value = data;
        settingHouseList(data);
      },
      (error) => {
        console.log("getRecommend", error);
      }
    );
  });
}

// 집 세팅
const settingHouseList = (val) => {
  emit("updateHouseList", val);
};

// ---------------이외
// 모달 열릴 때 작동
const setHouseId = (id) => {
  console.log("setHouseId=" + id);
  store.changeId(id);
  // houseId.value = id;
  emit("openModal", id);
};
// 탭 바꾸기
const changeTab = (val) => {
  searchType.value = val;
};
</script>

<template>
  <div
    id="searchSidebar"
    class="container m-0"
    style="height: 85vh; overflow-y: scroll; overflow-x: scroll"
  >
    <!-- 검색창 시작 -->
    <div class="mb-4 ms-1 mb-0 pb-0">
      <ul class="nav nav-underline">
        <li class="nav-item">
          <a class="nav-link boardNav" :class="{ active: searchType == 2 }" aria-current="page" @click="changeTab(2)"
            >검색</a
          >
        </li>
        <li class="nav-item">
          <a
            class="nav-link boardNav"
            :class="{ active: searchType == 0 }"
            
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

      <div class="mt-3 d-flex justify-content-flex-start align-items-center" >
        <!-- 구글 autocomplete 검색 -->
        <div v-if="searchType == 2" style="width: 70%">
          <div class="d-flex align-items-center">
            <GoogleAddressComplete />
          </div>
        </div>
        <!-- 지역 검색 -->
        <div v-if="searchType == 0">
          <div class="d-flex align-items-center">
            <AddressSelectBox
              ref="childCompRef"
              @requestDataFromChild="receiveDataFromChild"
              :initAddress="searchDongValue"
              :doRefresh="false"
            />
          </div>
        </div>
        <!-- 건물명 검색 -->

        <div class="d-flex align-items-center" v-if="searchType == 1" style="width: 70%">
          <input
            class="w-100 ps-2"
            style="padding-top: 6px; padding-bottom: 6px"
            type="text"
            name="buildingName"
            id="buildingName"
            v-model="searchBuildingValue"
          />
        </div>
        <RouterButton
          buttonIcon="/src/assets/buttonSearch.png"
          :buttonFunc="callChildFunction"
          class="ms-2"
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
          v-for="house in houseList"
          :key="house"
          :houseInfo="house"
          @click="setHouseId(house.aptCode)"
        />
      </div>
      <!-- 추천 매물 끝 -->
      <!-- 검색 결과 시작 -->
      <p class="small-title" v-if="type == 'house'">검색 결과</p>
      <div v-if="type == 'house'" class="w-100 mt-2">
        <HouseSidebarListItem
          v-for="house in houseList"
          :key="house"
          :houseInfo="house"
          @click="setHouseId(house.aptCode)"
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
  </div>
</template>

<style scoped>
#searchSidebar::-webkit-scrollbar {
  width: 10px;
}

#searchSidebar::-webkit-scrollbar-thumb {
  background: #ade8f4;
  border-radius: 10px;
}
</style>
