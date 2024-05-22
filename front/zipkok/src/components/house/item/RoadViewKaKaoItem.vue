<script setup>
import { ref, onMounted } from 'vue';

const roadviewContainer = ref(null);
const props = defineProps({ lat: Number, lng: Number });

const loadKakaoMapScript = () => {
  return new Promise((resolve, reject) => {
    if (window.kakao && window.kakao.maps) {
      resolve();
    } else {
      const script = document.createElement('script');
      script.onload = () => resolve();
      script.onerror = () => reject(new Error('Kakao Maps API 로드 실패'));
      script.src = `//dapi.kakao.com/v2/maps/sdk.js?appkey=${import.meta.env.VITE_KAKAO_MAP_APP_KEY}&autoload=false`;
      document.head.appendChild(script);
    }
  });
};

const initRoadView = () => {
  kakao.maps.load(() => {
    const roadview = new kakao.maps.Roadview(roadviewContainer.value);
    const roadviewClient = new kakao.maps.RoadviewClient();
    const position = new kakao.maps.LatLng(props.lat, props.lng);

    roadviewClient.getNearestPanoId(position, 50, (panoId) => {
      roadview.setPanoId(panoId, position);
      const vp = roadview.getViewpoint();
      roadview.setViewpoint({pan: vp.pan, tilt: vp.tilt, zoom: -3})
    });
    
  });
};



onMounted(() => {
  loadKakaoMapScript()
    .then(initRoadView)
    .catch((err) => {
      console.error('Kakao Maps API를 로드하는 중 오류가 발생했습니다:', err);
    });
});

</script>

<template>
    <div style="background-color:gray">
        <div ref="roadviewContainer" style="width: 100%; height: 500px;"></div>
    </div>
</template>

<style scoped></style>