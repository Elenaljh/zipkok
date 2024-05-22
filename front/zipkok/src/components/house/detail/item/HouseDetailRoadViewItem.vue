<script setup>
import RoadViewNaverItem from '@/components/house/item/RoadViewNaverItem.vue';
import RoadViewKakaoItem from '@/components/house/item/RoadViewKaKaoItem.vue';
const { houseInfo } = defineProps({
  houseInfo: Object,
});
</script>

<template>
  <div>
    <!-- <RoadViewKakaoItem :lat="houseInfo.lat" :lng="houseInfo.lng" :pan="houseInfo.pan"
    :height="'500px'"/> -->
    <RoadViewNaverItem :lat="houseInfo.lat" :lng="houseInfo.lng" :pan="houseInfo.pan" :tilt="'0'"
    :height="'500px'"/>
  </div>
</template>

<style scoped>

</style>