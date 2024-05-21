<script setup>
import { getAptsByLatLngs } from "@/api/map";
import { useHouseStore } from "@/stores/house";
import { moneyFormat } from "@/util/util";
import { Modal } from "bootstrap";
import { ref, computed, watch, onMounted } from "vue";
import { KakaoMap, KakaoMapMarker, KakaoMapCustomOverlay } from "vue3-kakao-maps";
import HouseDetail from "../detail/HouseDetail.vue";

const props = defineProps({ hlw: Number, houseMarkerList: Array });
const mvw = ref(props.hlw ? 100 - props.hlw : 60);
const houseMarkerList = ref(
  props.houseMarkerList && props.houseMarkerList.length > 0 ? props.houseMarkerList : []
);
const houseRangeMarkerList = ref([]);
const searchMarkerList = ref([]);
const lat = ref(36.10767834691484);
const lng = ref(128.41277958423132);
const map = ref();
const houseMarkerObjectList = ref([]);
const houseStore = useHouseStore();
const modalRef = ref(null);

onMounted(() => {
  if (modalRef.value) {
    new Modal(modalRef.value);
  }
});

watch(
  () => houseStore.houseId,
  () => {
    const index = houseMarkerList.value.findIndex((val) => val.aptCode == houseStore.houseId);
    lat.value = houseMarkerList.value[index].lat;
    lng.value = houseMarkerList.value[index].lng;
    moveMap();
  }
);

const onLoadKakaoMap = (mapRef) => {
  map.value = mapRef;
  drawCurrent();
  // clickMap();

  // searchPlace();
  kakao.maps.event.addListener(map.value, "dragend", function (event) {
    // 클릭한 위도, 경도 정보를 가져옵니다
    const bounds = map.value.getBounds();

    console.log(`이동한 위치의 남서쪽 좌표는 ${bounds.getSouthWest()} 이고,
      북동쪽 좌표는 ${bounds.getNorthEast()} 입니다`);
    getRangeList(bounds.getSouthWest(), bounds.getNorthEast());
  });
};

// 전달받은 houseMarkerList로 마커 찍기
const filteredMarkerList = computed(() =>
  [...houseMarkerList.value, ...houseRangeMarkerList.value].slice(0, 20)
);
const filteredSearchMarkerList = computed(() => searchMarkerList.value);
const markersList = computed(() => filteredMarkerList.value);

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
// const message = ref("");
// const clickMap = () => {
//   kakao.maps.event.addListener(map.value, "click", function (mouseEvent) {
//     const latlng = mouseEvent.latLng;

//     message.value = `클릭한 위치의 위도는 ${latlng.getLat()} 이고, <br>`;
//     message.value += `경도는 ${latlng.getLng()} 입니다`;
//     console.log(message.value);
//   });
// };

// 키워드로 장소를 검색합니다
const keyword = ref("공원");
const searchPlace = () => {
  // 장소 검색 객체를 생성합니다
  console.log("검색 시작");
  const ps = new kakao.maps.services.Places();
  // 키워드로 장소를 검색합니다
  console.log(lat.value, lng.value);
  ps.keywordSearch(keyword.value, placesSearchCB, {
    location: new kakao.maps.LatLng(lat.value, lng.value),
  });
};
// 키워드 검색 완료 시 호출되는 콜백함수 입니다
const placesSearchCB = (data, status) => {
  console.log("검색 완료");
  if (status === kakao.maps.services.Status.OK) {
    // 검색된 장소 위치를 기준으로 지도 범위를 재설정하기위해
    // LatLngBounds 객체에 좌표를 추가합니다
    // const bounds = new kakao.maps.LatLngBounds();

    for (let marker of data) {
      const markerItem = {
        lat: marker.y,
        lng: marker.x,
        infoWindow: {
          content: marker.place_name,
          visible: false,
        },
      };
      // console.log(marker);
      searchMarkerList.value.push(markerItem);
      // bounds.extend(new kakao.maps.LatLng(Number(marker.y), Number(marker.x)));
    }

    // 검색된 장소 위치를 기준으로 지도 범위를 재설정합니다
    // map.value?.setBounds(bounds);
  }
};

// 마커 인포윈도우
function onLoadKakaoMapMarker(event) {
  // console.log("마커 ", event);
  houseMarkerObjectList.value.push(event);
  console.log(markersList.value);
}

const visibleRef = ref({});

const mouseOverKakaoMapMarker = (ind) => {
  visibleRef.value[ind] = true;
};

const mouseOutKakaoMapMarker = (ind) => {
  visibleRef.value[ind] = false;
};

// 검색 결과 받아오기 (범위)
const getRangeList = (swLatLng, neLatLng) => {
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

// 마커 클릭 설정
const markerClick = (id) => {
  console.log("setHouseId=" + id);
  // houseStore.changeId(id);
  // new Modal(modalRef.value).show();
};

// 마커 틀

// 마커 커스텀 오버레이 틀
const content = (marker) => {
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

// 커스텀 오버레이 리스트
const customOverlayList = computed(() => {
  const temp = [];
  for (let index in filteredMarkerList) {
    const marker = filteredMarkerList.value[index];
    temp.push({
      lat: marker.lat,
      lng: marker.lng,
      content: content(marker),
      yAnchor: 1.4,
      visible: visibleRef[index] ? visibleRef[index] : false,
    });
  }
  console.log("temp", temp);
  return temp;
});

// -------------- 마커 클러스터링

const onLoadKakaoMapMarkerCluster = () => {
  console.log("onLoadKakaoMapMarkerCluster");
};
</script>

<template>
  <div class="p-0 m-0" style="width: fit-content; height: fit-content">
    <KakaoMap
      id="map"
      :width="mvw + 'vw'"
      height="85vh"
      :lat="lat"
      :lng="lng"
      @onLoadKakaoMap="onLoadKakaoMap"
      @onLoadKakaoMapMarkerCluster="onLoadKakaoMapMarkerCluster"
      :markerCluster="{ customOverlayProps: customOverlayList }"
    >
      <!-- 검색한 집 결과 -->
      <!-- <KakaoMapMarker
        v-for="(marker, index) in filteredMarkerList"
        :key="marker.aptCode"
        :lat="marker.lat"
        :lng="marker.lng"
        :image="{
          imageSrc: '/src/assets/marker/house.png',
          imageWidth: 40,
          imageHeight: 40 * 1.3,
          imageOption: {},
        }"
        :clickable="true"
        @onClickKakaoMapMarker="markerClick(marker.aptCode)"
        @mouseOverKakaoMapMarker="mouseOverKakaoMapMarker(index)"
        @mouseOutKakaoMapMarker="mouseOutKakaoMapMarker(index)"
        @onLoadKakaoMapMarker="onLoadKakaoMapMarker($event, marker.lat, marker.lng)"
      /> -->

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

    <!-- 세부 정보 모달 -->
    <div
      class="modal fade"
      id="exampleModal2"
      tabindex="-1"
      aria-labelledby="exampleModalLabel"
      aria-hidden="true"
      ref="modalRef"
    ></div>
  </div>
</template>

<style scoped>
#map {
  z-index: 0;
}

KakaoMapMarker:hover {
  cursor: pointer;
}

.customOverlayPrice:hover {
  background-color: #90e0ef;
}
</style>
