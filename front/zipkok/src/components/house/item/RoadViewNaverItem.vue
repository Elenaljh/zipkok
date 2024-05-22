<script setup>
import { ref, onMounted } from 'vue';

// 환경 변수에서 API 키 가져오기
const NAVER_MAPS_API_KEY = import.meta.env.VITE_NAVER_MAP_CLIENT_KEY;
const props = defineProps({ lat: Number, lng: Number, pan: Number, height: String, tilt: Number });
const ht = ref(props.height);
// 파노라마 컨테이너 참조 생성
const panoContainer = ref(null);

// 네이버 지도 스크립트를 로드하는 함수
const loadNaverMapScript = () => {
    return new Promise((resolve, reject) => {
        if (window.naver && window.naver.maps) {
            resolve();
        } else {
            const script = document.createElement('script');
            script.onload = () => resolve();
            script.onerror = () => reject(new Error('네이버 지도 API 로드 실패'));
            script.src = `https://oapi.map.naver.com/openapi/v3/maps.js?ncpClientId=${NAVER_MAPS_API_KEY}&submodules=panorama`;
            document.head.appendChild(script);
        }
    });
};

// 파노라마 초기화 함수
const initPanorama = () => {
    if (window.naver && window.naver.maps) {
        var pano = new naver.maps.Panorama(panoContainer.value, {
            position: new naver.maps.LatLng(props.lat, props.lng,),
            pov: {
                pan: props.pan ? props.pan : 20,
                tilt: props.tilt? props.tilt: 40,
                fov: 100
            },
            flightSpot: false,
            logoControl: false,
        });

        naver.maps.Event.addListener(pano, "pano_changed", function () {
            // console.log("PanoramaLocation", pano.getLocation()); // panoId, title, address, coord, photodate
            // console.log("pano lat=",pano.getLocation().coord.y," lng",pano.getLocation().coord.x);
            // console.log("place lat=",props.lat," lng",props.lng);
        
        });
    }
};

onMounted(() => {
    loadNaverMapScript()
        .then(initPanorama)
        .catch(err => {
            console.error('네이버 지도 API를 로드하는 중 오류가 발생했습니다:', err);
        });
});
</script>

<template>
    <div ref="panoContainer" :style="{width: '100%', height: ht }"></div>
</template>

<style scoped>
</style>