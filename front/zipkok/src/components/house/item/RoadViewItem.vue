<template>
    <div ref="roadview" style="width: 100%; height: 300px;"></div>
</template>

<script setup>
import { ref, onMounted } from 'vue';

const roadviewContainer = ref(null);
const VITE_KAKAO_MAP_API_KEY = import.meta.env.VITE_KAKAO_MAP_API_KEY;

const loadKakaoMapScript = () => {
    return new Promise((resolve, reject) => {
        if (window.kakao && window.kakao.maps) {
            resolve();
        } else {
            const script = document.createElement('script');
            script.onload = () => resolve();
            script.onerror = () => reject(new Error('Kakao Maps API 로드 실패'));
            script.src = `//dapi.kakao.com/v2/maps/sdk.js?appkey=${VITE_KAKAO_MAP_API_KEY}&autoload=false`;
            document.head.appendChild(script);
        }
    });
};

const initRoadView = () => {
    kakao.maps.load(() => {
        const roadview = new kakao.maps.Roadview(roadviewContainer.value);
        const roadviewClient = new kakao.maps.RoadviewClient();
        const position = new kakao.maps.LatLng(props.latitude, props.longitude);

        roadviewClient.getNearestPanoId(position, 50, (panoId) => {
            roadview.setPanoId(panoId, position);
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

<style scoped></style>