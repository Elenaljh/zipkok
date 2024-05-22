<script setup lang="ts">
import { ref } from "vue";
import { KakaoMap, KakaoMapMarker, KakaoMapCustomOverlay } from "vue3-kakao-maps";
const { houseInfo } = defineProps({
  houseInfo: Object,
});
import { searchPlaceByKeyword, dataList, filteredSearchMarkerList } from "@/util/houseDetail";

const current = ref("");

const visibleRef = ref([]);
const mouseOverKakaoMapMarker = (ind) => {
  // console.log(ind + " in");
  visibleRef.value[ind] = true;
};

const mouseOutKakaoMapMarker = (ind) => {
  // console.log(ind + " out");
  visibleRef.value[ind] = false;
};

const customOverlay = (info) => {
  return `<button type="button" class="btn btn-secondary btn-sm">${info}</button>`;
};
</script>

<template>
  <div class="me-2">
    <select
      class="form-select w-25 mb-2"
      v-model="current"
      @change="searchPlaceByKeyword(current, houseInfo.lat, houseInfo.lng)"
    >
      <option value="" selected disabled>선택하세요</option>
      <option value="공원">공원</option>
      <option value="행정">관공서</option>
      <option value="학교">학교</option>
      <option value="맛집">맛집</option>
    </select>
    <KakaoMap :lat="houseInfo.lat" :lng="houseInfo.lng" width="65rem" class="mb-4">
      <!--아파트 위치 마커 (고정)-->
      <KakaoMapMarker
        :lat="houseInfo.lat"
        :lng="houseInfo.lng"
        :image="{
          imageSrc: '/src/assets/marker/houseRed.png',
          imageWidth: 29,
          imageHeight: 29 * 1.3,
        }"
      />
      <div v-if="current === '학교'">
        <KakaoMapMarker
          v-for="(marker, index) in filteredSearchMarkerList"
          :key="marker.key"
          :lat="marker.lat"
          :lng="marker.lng"
          :image="{
            imageSrc: '/src/assets/marker/school.png',
            imageWidth: 29,
            imageHeight: 29 * 1.3,
          }"
          @mouseOverKakaoMapMarker="mouseOverKakaoMapMarker(index)"
          @mouseOutKakaoMapMarker="mouseOutKakaoMapMarker(index)"
        />
        <KakaoMapCustomOverlay
          v-for="(marker, index) in filteredSearchMarkerList"
          :key="marker.key"
          :lat="marker.lat"
          :lng="marker.lng"
          :yAnchor="2"
          :visible="visibleRef[index] ? visibleRef[index] : false"
          :content="customOverlay(marker.infoWindow.content)"
        />
      </div>
      <div v-if="current === '행정'">
        <KakaoMapMarker
          v-for="(marker, index) in filteredSearchMarkerList"
          :key="marker.key"
          :lat="marker.lat"
          :lng="marker.lng"
          :image="{
            imageSrc: '/src/assets/marker/office.png',
            imageWidth: 29,
            imageHeight: 29 * 1.3,
          }"
          @mouseOverKakaoMapMarker="mouseOverKakaoMapMarker(index)"
          @mouseOutKakaoMapMarker="mouseOutKakaoMapMarker(index)"
        />
        <KakaoMapCustomOverlay
          v-for="(marker, index) in filteredSearchMarkerList"
          :key="marker.key"
          :lat="marker.lat"
          :lng="marker.lng"
          :yAnchor="2"
          :visible="visibleRef[index] ? visibleRef[index] : false"
          :content="customOverlay(marker.infoWindow.content)"
        />
      </div>
      <div v-if="current === '공원'">
        <KakaoMapMarker
          v-for="(marker, index) in filteredSearchMarkerList"
          :key="marker.key"
          :lat="marker.lat"
          :lng="marker.lng"
          :image="{
            imageSrc: '/src/assets/marker/park.png',
            imageWidth: 29,
            imageHeight: 29 * 1.3,
          }"
          @mouseOverKakaoMapMarker="mouseOverKakaoMapMarker(index)"
          @mouseOutKakaoMapMarker="mouseOutKakaoMapMarker(index)"
        />
        <KakaoMapCustomOverlay
          v-for="(marker, index) in filteredSearchMarkerList"
          :key="marker.key"
          :lat="marker.lat"
          :lng="marker.lng"
          :yAnchor="2"
          :visible="visibleRef[index] ? visibleRef[index] : false"
          :content="customOverlay(marker.infoWindow.content)"
        />
      </div>
      <div v-if="current === '맛집'">
        <KakaoMapMarker
          v-for="(marker, index) in filteredSearchMarkerList"
          :key="marker.key"
          :lat="marker.lat"
          :lng="marker.lng"
          :image="{
            imageSrc: '/src/assets/marker/food.png',
            imageWidth: 29,
            imageHeight: 29 * 1.3,
          }"
          @mouseOverKakaoMapMarker="mouseOverKakaoMapMarker(index)"
          @mouseOutKakaoMapMarker="mouseOutKakaoMapMarker(index)"
        />
        <KakaoMapCustomOverlay
          v-for="(marker, index) in filteredSearchMarkerList"
          :key="marker.key"
          :lat="marker.lat"
          :lng="marker.lng"
          :yAnchor="2"
          :visible="visibleRef[index] ? visibleRef[index] : false"
          :content="customOverlay(marker.infoWindow.content)"
        />
      </div>
    </KakaoMap>
    <!--녹지-->
    <div v-if="current">
      <p style="font-size: large; font-weight: bold" v-if="current == '공원'">
        <span class="me-2">이 주변의</span><span style="color: green">공원</span>
      </p>
      <p style="font-size: large; font-weight: bold" v-if="current == '행정'">
        <span>주변 </span>
        <span style="color: #00b3d6">행정</span>
      </p>
      <p style="font-size: large; font-weight: bold" v-if="current == '학교'">
        <span>주변 </span>
        <span style="color: #00b3d6">학교</span>
      </p>
      <p style="font-size: large; font-weight: bold" v-if="current == '맛집'">
        <span>주변 </span>
        <span style="color: #00b3d6">맛집</span>
      </p>
      <!--표-->
      <div class="table-responsive card" style="max-height: 300px; overflow-y: auto">
        <table class="table mb-0">
          <tbody>
            <tr v-for="item in dataList" :key="item.name">
              <td style="padding-left: 20px">{{ item.name }}</td>
              <td>{{ item.type }}</td>
              <td>{{ item.address }}</td>
            </tr>
          </tbody>
        </table>
      </div>
      <!--표 끝-->
    </div>
    <!--녹지 끝-->
  </div>
</template>

<style scoped>
.table-responsive.card::-webkit-scrollbar {
  width: 10px;
}
.table-responsive.card::-webkit-scrollbar-thumb {
  background: #e8e8e8;
  border-radius: 10px;
}
</style>
