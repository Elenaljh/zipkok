<script setup>
import { useHouseStore } from "@/stores/houses";
import { ref, computed, watch } from "vue";
import { KakaoMap, KakaoMapMarker } from "vue3-kakao-maps";

const props = defineProps({ hlw: Number, houseMarkerList: Array });
const mvw = ref(props.hlw ? 100 - props.hlw : 60);
const houseMarkerList = ref(
  props.houseMarkerList && props.houseMarkerList.length > 0 ? props.houseMarkerList : []
);
const searchMarkerList = ref([]);
const lat = ref(36.10767834691484);
const lng = ref(128.41277958423132);
const map = ref();

const onLoadKakaoMap = (mapRef) => {
  map.value = mapRef;
  drawCurrent();
  clickMap();

  searchPlace();
};

// 전달받은 houseMarkerList로 마커 찍기
const filteredMarkerList = computed(() => houseMarkerList.value);
const filteredSearchMarkerList = computed(() => searchMarkerList.value);

// marker에 맞게 지도 이동
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
    map.value.setCenter(new kakao.maps.LatLng(lat.value, lng.value));
    map.value.setLevel(4);
    map.value.setLevel(3, { animate: true });
    console.log("현재 레벨: ", map.value.getLevel());
  }
});

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
      console.log(marker);
      searchMarkerList.value.push(markerItem);
      // bounds.extend(new kakao.maps.LatLng(Number(marker.y), Number(marker.x)));
    }

    // 검색된 장소 위치를 기준으로 지도 범위를 재설정합니다
    // map.value?.setBounds(bounds);
  }
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
    >
      <!-- 검색한 집 결과 -->
      <KakaoMapMarker
        v-for="marker in filteredMarkerList"
        :key="marker.aptCode"
        :lat="marker.lat"
        :lng="marker.lng"
        :image="{
          imageSrc: '/src/assets/marker/house.png',
          imageWidth: 40,
          imageHeight: 54,
          imageOption: {},
        }"
      />
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
#map {
  z-index: 0;
}
</style>
