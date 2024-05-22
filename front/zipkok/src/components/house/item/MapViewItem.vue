<script setup>
import { getAptsAvgByDong, getAptsByLatLngs } from "@/api/map";
import { useHouseStore } from "@/stores/house";
import { moneyFormat } from "@/util/util";
import { ref, computed, watch, onMounted } from "vue";
import { KakaoMap, KakaoMapMarker, KakaoMapCustomOverlay } from "vue3-kakao-maps";

const props = defineProps({ hlw: Number, houseMarkerList: Array });
const emit = defineEmits(["updateHouseList", "openModal"]);
const mvw = ref(100);
const houseMarkerList = ref(
  props.houseMarkerList && props.houseMarkerList.length > 0 ? props.houseMarkerList : []
);
const houseRangeMarkerList = ref([]);
const houseDongMarkerList = ref([]);
const searchMarkerList = ref([]);
const lat = ref(36.10767834691484);
const lng = ref(128.41277958423132);
const map = ref(null);
const houseMarkerObjectList = ref([]);
const houseStore = useHouseStore();
const showSeperateMarker = ref(true);
const dongOverlays = ref([]);

// watch(
//   () => houseStore.houseId,
//   () => {
//     const index = houseMarkerList.value.findIndex((val) => val.aptCode == houseStore.houseId);
//     console.log("changeHouseId=" + houseStore.houseId, " index", index);
//     lat.value = houseMarkerList.value[index].lat;
//     lng.value = houseMarkerList.value[index].lng;
//     moveMap();
//   }
// );

onMounted(() => {
  drawCurrent();
});

const onLoadKakaoMap = (mapRef) => {
  if (map.value == null) {
    map.value = mapRef;
    // drawCurrent();
    clickMap();
  }

  // searchPlace();
  kakao.maps.event.addListener(map.value, "dragend", function (event) {
    // 클릭한 위도, 경도 정보를 가져옵니다
    const bounds = map.value.getBounds();

    console.log(`이동한 위치의 남서쪽 좌표는 ${bounds.getSouthWest()} 이고,
      북동쪽 좌표는 ${bounds.getNorthEast()} 입니다`);
    getRangeList(bounds.getSouthWest(), bounds.getNorthEast());
  });

  // 줌 변화 이벤트
  kakao.maps.event.addListener(map.value, "zoom_changed", function (event) {
    const bounds = map.value.getBounds();
    console.log(`현재 줌 레벨은 ${map.value.getLevel()}입니다`);
    getRangeList(bounds.getSouthWest(), bounds.getNorthEast());
  });
};

// 전달받은 houseMarkerList로 마커 찍기
const filteredMarkerList = computed(() => [
  ...houseMarkerList.value,
  ...houseRangeMarkerList.value,
]);
const filteredSearchMarkerList = computed(() => searchMarkerList.value);
const filteredDongMarkerList = computed(() => {
  console.log("filteredDongMarkerList reload");
  return houseDongMarkerList.value;
});
// marker에 맞게 지도 `이동
watch(
  () => props.houseMarkerList,
  () => {
    houseMarkerList.value = props.houseMarkerList;
  }
);

watch(filteredMarkerList, () => {
  if (filteredMarkerList.value.length > 0) {
    lat.value = filteredMarkerList.value[0].lat;
    lng.value = filteredMarkerList.value[0].lng;
    // moveMap();
  }
});

// 지정된 좌표로 맵 이동
function moveMap() {
  map.value.setCenter(new kakao.maps.LatLng(lat.value, lng.value));
  map.value.setLevel(4);
  map.value.setLevel(3, { animate: true });
  console.log("현재 레벨: ", map.value.getLevel());
}

// 현재 위치를 그리는 함수
const drawCurrent = () => {
  if (!("geolocation" in navigator)) {
    return;
  }

  // get position
  navigator.geolocation.getCurrentPosition((pos) => {
    lat.value = pos.coords.latitude;
    lng.value = pos.coords.longitude;
  });
  if (map.value) {
    map.value.panTo(new kakao.maps.LatLng(lat.value, lng.value));
    map.value.setLevel(map.value.getLevel() + 1);
  }
};

// 클릭한 위도, 경도 정보를 가져옵니다
const message = ref("");
const clickMap = () => {
  kakao.maps.event.addListener(map.value, "click", function (mouseEvent) {
    const latlng = mouseEvent.latLng;

    message.value = `클릭한 위치의 위도는 ${latlng.getLat()} 이고, <br>`;
    message.value += `경도는 ${latlng.getLng()} 입니다`;
    console.log(message.value);
  });
};

// 키워드로 장소를 검색합니다
// const keyword = ref("공원");
// const searchPlace = () => {
//   // 장소 검색 객체를 생성합니다
//   console.log("검색 시작");
//   const ps = new kakao.maps.services.Places();
//   // 키워드로 장소를 검색합니다
//   console.log(lat.value, lng.value);
//   ps.keywordSearch(keyword.value, placesSearchCB, {
//     location: new kakao.maps.LatLng(lat.value, lng.value),
//   });
// };
// // 키워드 검색 완료 시 호출되는 콜백함수 입니다
// const placesSearchCB = (data, status) => {
//   console.log("검색 완료");
//   if (status === kakao.maps.services.Status.OK) {
//     // 검색된 장소 위치를 기준으로 지도 범위를 재설정하기위해
//     // LatLngBounds 객체에 좌표를 추가합니다
//     // const bounds = new kakao.maps.LatLngBounds();

//     for (let marker of data) {
//       const markerItem = {
//         lat: marker.y,
//         lng: marker.x,
//         infoWindow: {
//           content: marker.place_name,
//           visible: false,
//         },
//       };
//       // console.log(marker);
//       searchMarkerList.value.push(markerItem);
//       // bounds.extend(new kakao.maps.LatLng(Number(marker.y), Number(marker.x)));
//     }

//     // 검색된 장소 위치를 기준으로 지도 범위를 재설정합니다
//     // map.value?.setBounds(bounds);
//   }
// };

// 마커 인포윈도우
function onLoadKakaoMapMarker(event) {
  console.log("마커 ", event);
  houseMarkerObjectList.value.push(event);
}

const visibleRef = ref({});

const mouseOverKakaoMapMarker = (ind) => {
  visibleRef.value[ind] = true;
};

const mouseOutKakaoMapMarker = (ind) => {
  visibleRef.value[ind] = false;
};
//------------------- 거래가 받아오는 부분
const getRangeList = (swLatLng, neLatLng) => {
  // 줌 레벨에 따라 다르게 받아온다
  if (map.value.getLevel() <= 4) {
    eraseOverlay();
    getRangeSeperateList(swLatLng, neLatLng);
    showSeperateMarker.value = true;
  } else {
    getRangeDongList(swLatLng, neLatLng);
    showSeperateMarker.value = false;
  }
};
// 검색 결과 받아오기 (범위, 개별)
const getRangeSeperateList = (swLatLng, neLatLng) => {
  getAptsByLatLngs(
    {
      slat: swLatLng.getLat(),
      slng: swLatLng.getLng(),
      elat: neLatLng.getLat(),
      elng: neLatLng.getLng(),
    },
    ({ data }) => {
      console.log("getAptsByLatLngs!!", data);
      houseRangeMarkerList.value = data;
    },
    (error) => {
      console.log(error);
    }
  );
};

// 검색 결과 받아오기 (범위, 동)
const getRangeDongList = (swLatLng, neLatLng) => {
  getAptsAvgByDong(
    {
      slat: swLatLng.getLat(),
      slng: swLatLng.getLng(),
      elat: neLatLng.getLat(),
      elng: neLatLng.getLng(),
    },
    ({ data }) => {
      console.log("getAptsAvgByDong!!", data);
      houseDongMarkerList.value = data;
      drawDongMarker();
      // const dongAvgList = [];
      // for (let element of data) {
      //   geocoder.addressSearch(element.fullName, function (result, status) {
      //     if (status === kakao.maps.services.Status.OK) {
      //       console.log(result);
      //       const obj = {
      //         ...element,
      //         lng: result[0].y,
      //         lat: result[0].x
      //       }
      //       houseDongMarkerList.value.push(obj);
      //     }
      //   })
      // }
      // console.log("dongAvgList, ", dongAvgList)
      // houseDongMarkerList.value = dongAvgList;
    },
    (error) => {
      console.log(error);
    }
  );
};

// 주소 받아오기

//--------------- 마커 설정
// 금액 표시
function showPrice(info) {
  if (info.avgAmount) {
    return info.avgAmount;
  } else if (info.avgDeposit) {
    return info.avgDeposit;
  }
  return "-";
}

// 금액 타입 표시
function showType(info) {
  if (info.avgAmount) {
    return "매매";
  } else if (info.avgDeposit) {
    return "전세";
  }
  return "-";
}

// 마커 클릭 설정
const markerClick = (id) => {
  console.log("setHouseId=" + id);
  houseStore.changeId(id);
  emit("openModal", id);
  // modalRef.value.show();
  // new Modal(modalRef.value).show();
};
// 동 마커 클릭
function markerDongClick(dongcode, la, ln) {
  console.log(dongcode, la, ln);
  map.value.setLevel(4);
  map.value.setCenter(new kakao.maps.LatLng(la, ln));
  // map.value.setCenter(new kakao.maps.LatLng(36.1250178, 128.3795879));
  // map.value.panTo(new kakao.maps.LatLng(la, ln));
  const bounds = map.value.getBounds();
  getRangeList(bounds.getSouthWest(), bounds.getNorthEast());
}

// 마커 틀

// 마커 커스텀 오버레이 틀
const myContent = (marker) => {
  return `<div
      class="customOverlayPrice"
      style="
        padding: 10px;
        background-color: white;
        border: 1px solid #ccc;
        border-radius: 10px;
        display: flex;
        flex-direction: column;
        align-items: flex-start;
      "
    >
      <div style="font-weight: bold; margin-bottom: 5px; color: #00b4d8">
        ${marker.aptName}
      </div>
      <div style="display: flex">
        <div style="display: flex; flex-direction: column; align-items: flex-start">
          <div
            class="small-content"
            style="overflow: hidden; text-overflow: ellipsis; white-space: nowrap"
          >
            ${marker.bjdAddress ? marker.bjdAddress : marker.drmAddress}
          </div>
          <div style="overflow: hidden; text-overflow: ellipsis; white-space: nowrap">
            ${moneyFormat(showPrice(marker) * 10000)}
          </div>
        </div>
      </div>
    </div>`;
};

const myDongContent = (marker) => {
  return `<div class="customOverlayPrice myDongContent d-flex flex-column align-items-start"
  style="border-color: #CAF0F8;
  -webkit-box-shadow: 0px 0px 20px 7px #CAF0F8 !important; 
  box-shadow: 0px 0px 20px 7px #CAF0F8;"
  >
  <div class="d-flex justify-content-center text-center fw-bold py-1 w-100 px-2" 
      style="background-color: #00b4d8; color:white; border-radius: 10px 10px 0 0; font-size: 15px;">
    <span>${marker.dongName ? marker.dongName : marker.gugunName}</span>
  </div>
  <div class="d-flex bg-white p-2 w-100 justify-content-center"
    style=" border-radius:  0 0 10px 10px">
    <div class="bg-white text-center w-100" 
    style="overflow: hidden; text-overflow: ellipsis; white-space: nowrap; font-size: 13px;">
      <span class="small-content">${showType(marker)}</span> ${moneyFormat(
    showPrice(marker) * 10000
  )}
    </div>
  </div>
</div>`;
};

// 커스텀 오버레이 리스트
const customOverlayList = computed(() => {
  const temp = [];

  for (let index in filteredMarkerList.value) {
    const marker = filteredMarkerList.value[index];
    temp.push({
      lat: marker.lat,
      lng: marker.lng,
      content: myContent(marker),
      yAnchor: 1.4,
      visible: visibleRef[index] ? visibleRef[index] : false,
    });
  }
  console.log("temp", temp, filteredMarkerList.value.length);
  return temp;
});

// -------------- 마커 클러스터링
const markerList = computed(() => {
  const temp = [];
  console.log("temp", temp, filteredMarkerList.value.length);
  for (let index in filteredMarkerList.value) {
    const marker = filteredMarkerList.value[index];
    const llm = {
      lat: marker.lat,
      lng: marker.lng,
    };
    temp.push(llm);
  }

  return temp;
});

// -------------- 진짜 마지막 클러스터링
const drawDongMarker = () => {
  console.log("start====================");
  for (let h of filteredDongMarkerList.value) {
    const marker = new kakao.maps.Marker({
      map: map.value,
      position: new kakao.maps.LatLng(h.lat, h.lng),
      image: new kakao.maps.MarkerImage("/src/assets/empty.png", new kakao.maps.Size(56, 78), {}),
    });
    const overlay = new kakao.maps.CustomOverlay({
      position: new kakao.maps.LatLng(h.lat, h.lng),
      yAnchor: 1.4,
    });
    overlay.setContent(myDongContent(h));
    kakao.maps.event.addListener(marker, "click", function () {
      markerDongClick(h.dongCode, h.lat, h.lng);
    });
    overlay.setMap(map.value);
    // console.log("overlay, ", overlay.getContent(), h);

    dongOverlays.value.push({
      ov: overlay,
      mk: marker,
    });
  }
};
// 커스텀 오버레이 리스트를 모두 지웁니다
function eraseOverlay() {
  for (let obj of dongOverlays.value) {
    obj.ov.setMap(null);
    obj.mk.setMap(null);
  }
}
</script>

<template>
  <div class="p-0 m-0" style="width: fit-content; height: fit-content">
    <KakaoMap
      id="map"
      :width="mvw + 'vw'"
      height="85vh"
      :lat="lat"
      :lng="lng"
      :disableClickZoom="true"
      @onLoadKakaoMap="onLoadKakaoMap"
    >
      <!-- 검색한 집 결과 -->
      <!-- 작은 마커 -->
      <div v-if="showSeperateMarker">
        <div v-for="(marker, index) in filteredMarkerList" :key="marker.aptCode">
          <KakaoMapMarker
            :lat="marker.lat"
            :lng="marker.lng"
            :image="{
              imageSrc: '/src/assets/marker/house.png',
              imageWidth: 40,
              imageHeight: 40 * 1.3,
              imageOption: {},
            }"
            :clickable="true"
            @click="markerClick(marker.aptCode)"
            @onLoadKakaoMapMaker="onLoadKakaoMapMarker"
            @onClickKakaoMapMarker="markerClick(marker.aptCode)"
            @mouseOverKakaoMapMarker="mouseOverKakaoMapMarker(index)"
            @mouseOutKakaoMapMarker="mouseOutKakaoMapMarker(index)"
          />

          <KakaoMapCustomOverlay
            :lat="marker.lat"
            :lng="marker.lng"
            :yAnchor="1.4"
            v-if="visibleRef[index] ? visibleRef[index] : false"
            :content="myContent(marker)"
          />
        </div>
      </div>
      <!-- 동 마커 -->

      <div v-show="!showSeperateMarker">
        <!--<div v-for="(marker) in filteredDongMarkerList" :key="marker.dongCode">
          <KakaoMapCustomOverlay :lat="marker.lat" :lng="marker.lng" 
          @onLoadKakaoMapCustomOverlay="onLoadKakaoMapCustomOverlay"
          :clickable="true"
          :content="myDongContent(marker)" />
        </div>-->
      </div>
      <!-- 키워드 검색 결과 -->
      <KakaoMapMarker
        v-for="marker in filteredSearchMarkerList"
        :key="marker.key"
        :lat="marker.lat"
        :lng="marker.lng"
        :image="{
          imageSrc: '/src/assets/marker/place.png',
          imageWidth: 40,
          imageHeight: 54,
          imageOption: {},
        }"
      />
    </KakaoMap>
  </div>
</template>

<style scoped>
KakaoMapMarker:hover {
  cursor: pointer;
}

.customOverlayPrice:hover {
  background-color: #90e0ef;
}

.myDongContent {
  border-color: #caf0f8;
  -webkit-box-shadow: 0px 0px 13px 7px #caf0f8 !important;
  box-shadow: 0px 0px 13px 7px #caf0f8;
}
</style>
