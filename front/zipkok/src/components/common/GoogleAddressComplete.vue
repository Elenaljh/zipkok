<script setup>
import { useHouseStore } from '@/stores/house';


const houseStore = useHouseStore();
function setPlace(val) {
    console.log("GMapAutocomplete", val)
    console.log(val.geometry?.location.lat());
    houseStore.changeLat(val.geometry?.location.lat());
    houseStore.changeLng(val.geometry?.location.lng());
 }
</script>

<template> 
    <GMapAutocomplete
        style="height: inherit; padding: 6px; width: 100%"
        placeholder=""
            @place_changed="setPlace">
        </GMapAutocomplete>
    
</template>

<style scoped></style>