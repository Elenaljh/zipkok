<script setup>
import { ref } from "vue";
import AddressSelectBox from "../common/AddressSelectBox.vue";
const user = ref({
  name: "",
  email: "",
  password: "",
  age: 0,
  preferedPlace: [],
  preferedType: "",
});
//셀렉트박스 위한 함수와 변수
const childCompRef = ref(null);

const callChildFunction = () => {
  childCompRef.value.sendDataToParent();
};

const receiveDataFromChild = (data) => {
  console.log("데이터 수신 완료");
  if (data.address) {
    if (user.value.preferedPlace.includes(data.address)) {
      alert("중복된 값입니다!");
      return;
    }
    user.value.preferedPlace.push(data.address);
    console.log(user.value.preferedPlace);
  }
};

const deleteAddress = (item) => {
  user.value.preferedPlace = user.value.preferedPlace.filter((address) => {
    return address !== item;
  });
  console.log(user.value.preferedPlace);
};
//셀렉트박스
</script>
<template>
  <div class="m-5 w-25">
    <img src="/src/assets/house.png" class="mx-auto d-block mb-3" />
    <h3 class="text-center fw-bold">회원가입</h3>
    <form>
      <div class="mb-3">
        <label class="form-label">이름</label>
        <input type="text" class="form-control" placeholder="이름을 입력하세요." />
      </div>
      <div class="mb-3">
        <label class="form-label">이메일</label>
        <input type="email" class="form-control" placeholder="이메일을 입력하세요." />
      </div>
      <div class="mb-3">
        <label class="form-label">비밀번호</label>
        <input type="password" class="form-control" placeholder="비밀번호를 입력하세요." />
      </div>
      <div class="mb-3">
        <label class="form-label">나이</label>
        <input type="number" class="form-control" placeholder="나이를 입력하세요." />
      </div>
      <div class="mb-3">
        <label class="form-label">선호지역 선택</label>
        <!--셀렉트박스-->
        <div v-if="user.preferedPlace.length > 0">
          <div
            class="d-flex align-items-center mb-2"
            v-for="item in user.preferedPlace"
            :key="item"
          >
            <input type="text" class="form-control" :placeholder="item" readonly />
            <img
              class="ms-2"
              src="/src/assets/delete.png"
              width="20px"
              height="20px"
              @click="deleteAddress(item)"
            />
          </div>
        </div>

        <div
          class="d-flex align-items-center justify-content-between"
          v-if="user.preferedPlace.length < 3"
        >
          <AddressSelectBox ref="childCompRef" @requestDataFromChild="receiveDataFromChild" />
          <img
            class="ms-2"
            src="/src/assets/add.png"
            width="20px"
            height="20px"
            @click="callChildFunction"
          />
        </div>
        <!--셀렉트박스 끝-->
      </div>
      <div class="mb-3">
        <label class="form-label">거주지 선정 기준</label>
        <select class="form-select">
          <option selected>선정 기준을 고르세요</option>
          <option value="1">녹지</option>
          <option value="2">대기오염</option>
          <option value="3">행정시설</option>
          <option value="3">학군</option>
          <option value="3">근방 맛집</option>
          <option value="3">CCTV 대수</option>
        </select>
      </div>
      <button
        type="button"
        class="btn w-100 text-white fw-bold mb-3"
        style="background-color: #00b4d8"
      >
        회원가입
      </button>
      <button type="button" class="btn w-100 fw-bold" style="border-color: lightgray">
        <img src="/src/assets/google_s.png" class="me-2" />
        Google로 시작하기
      </button>
    </form>
  </div>
</template>
<style scoped></style>
