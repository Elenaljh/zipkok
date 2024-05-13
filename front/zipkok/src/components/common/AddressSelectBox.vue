<script setup>
import axios from "axios";
import { ref, onMounted } from "vue";

const emit = defineEmits(["requestDataFromChild"]);

const sido = ref([]);
const sigungu = ref([]);
const dong = ref([]);

const sidoValue = ref("");
const sigunguValue = ref("");
const dongValue = ref(""); //최종 동코드
const selectedAddress = ref(""); //최종 선택된 주소

const sendRequest = (selid, regcode) => {
  const url = "https://grpc-proxy-server-mkvo6j4wsq-du.a.run.app/v1/regcodes";
  const params = "regcode_pattern=" + regcode + "&is_ignore_zero=true";
  axios.get(url + "?" + params).then((response) => {
    const dataList = response.data.regcodes;
    if (selid === "sido") {
      sido.value = dataList;
    } else if (selid === "sigungu") {
      sigungu.value = dataList;
      sigunguValue.value = ""; // 시/도 변경 시 시/군/구 선택 해제
      dong.value = [];
      dongValue.value = "";
      selectedAddress.value = "";
    } else {
      dong.value = dataList;
    }
  });
};

const getSigungu = () => {
  if (sidoValue.value) {
    const code = sidoValue.value.substring(0, 2) + "*00000";
    sendRequest("sigungu", code);
  } else {
    sigungu.value = [];
    sigunguValue.value = "";
    dong.value = [];
  }
};

const getDong = () => {
  if (sigunguValue.value) {
    const code = sigunguValue.value.substring(0, 5) + "*";
    sendRequest("dong", code);
  } else {
    dong.value = [];
  }
};

const updateSelectedAddress = () => {
  const selectedDong = dong.value.find((item) => item.code === dongValue.value);
  if (selectedDong) {
    selectedAddress.value = selectedDong.name;
    console.log(selectedAddress.value);
  }
};

const sendDataToParent = () => {
  console.log("자식 함수 호출함");
  emit("requestDataFromChild", {
    address: selectedAddress.value,
    dongCode: dongValue.value,
  });
  sidoValue.value = "";
  sigunguValue.value = "";
  dongValue.value = "";
  selectedAddress.value = "";
  sigungu.value = [];
  dong.value = [];
};

defineExpose({ sendDataToParent }); //부모가 sendDataToParent를 호출할 수 있게 함

onMounted(() => {
  sendRequest("sido", "*00000000");
});
</script>

<template>
  <div class="w-100 d-flex justify-content-between">
    <select class="form-select" style="width: fit-content" v-model="sidoValue" @change="getSigungu">
      <option value="">시/도</option>
      <option v-for="item in sido" :key="item.code" :value="item.code">
        {{ item.name }}
      </option>
    </select>
    <select
      class="form-select ms-1"
      style="width: fit-content"
      v-model="sigunguValue"
      @change="getDong"
    >
      <option value="" selected>시/군/구</option>
      <option v-for="item in sigungu" :key="item.code" :value="item.code">
        {{ item.name.split(" ").slice(1).join(" ") }}
      </option>
    </select>
    <select
      class="form-select ms-1"
      style="width: fit-content"
      v-model="dongValue"
      @change="updateSelectedAddress"
    >
      <option value="">동</option>
      <option v-for="item in dong" :key="item.code" :value="item.code">
        {{ item.name.split(" ").slice(2).join(" ") }}
      </option>
    </select>
  </div>
</template>

<style scoped></style>
