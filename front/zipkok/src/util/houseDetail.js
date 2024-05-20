import { ref, computed } from "vue";
import axios from "axios";
import { KakaoMap, KakaoMapMarker } from "vue3-kakao-maps";

const dong = ref("");
const population = ref({});
const news = ref([]);
const searchMarkerList = ref([]);
const filteredSearchMarkerList = computed(() => searchMarkerList.value);
const dataList = ref([]); //이름, 종류, 주소
const lat = ref(36.10767834691484);
const lng = ref(128.41277958423132);

const getDong = async (bjdCode) => {
  const result = await axios.get("http://localhost:8080/apt/dong", {
    params: {
      bjdCode: bjdCode,
    },
  });
  dong.value = result.data;
  // console.log("동: " + dong.value);
};

const getPopulation = async (bjdCode) => {
  const result = await axios.get("http://localhost:8080/apt/population", {
    params: {
      bjdCode: bjdCode,
    },
  });
  population.value = result.data;
};

const getNews = async (aptName, dong) => {
  const result = await axios.get("http://localhost:8080/news", {
    params: {
      keyword: aptName,
      dong: dong,
    },
  });
  news.value = result.data;
};

// 키워드로 장소를 검색합니다
const searchPlaceByKeyword = (keyword, lat, lng) => {
  dataList.value = [];
  searchMarkerList.value = [];
  // 장소 검색 객체를 생성합니다
  console.log("검색 시작");
  const ps = new kakao.maps.services.Places();
  // 키워드로 장소를 검색합니다
  ps.keywordSearch(keyword, placesSearchCB, {
    location: new kakao.maps.LatLng(lat, lng),
  });
  console.log(filteredSearchMarkerList.value);
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
      let category = marker.category_name;
      let categoryArr = category.split(" > ");
      let category2 = categoryArr[categoryArr.length - 1];
      dataList.value.push({
        name: marker.place_name,
        type: category2,
        address: marker.address_name,
      });
      searchMarkerList.value.push(markerItem);
      // bounds.extend(new kakao.maps.LatLng(Number(marker.y), Number(marker.x)));
    }
    // console.log(dataList.value);
    // 검색된 장소 위치를 기준으로 지도 범위를 재설정합니다
    // map.value?.setBounds(bounds);
  }
};

export {
  getDong,
  getPopulation,
  dong,
  population,
  getNews,
  news,
  searchPlaceByKeyword,
  filteredSearchMarkerList,
  dataList,
};
