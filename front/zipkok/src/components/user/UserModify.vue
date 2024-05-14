<script setup>
import { ref, computed } from "vue";
import AddressSelectBox from "../common/AddressSelectBox.vue";

const user = ref({
  name: "김싸피",
  email: "ssafyhouse@gmail.com",
  password: "password",
  age: 24,
  preferedPlace: ["대구광역시 수성구 만촌동", "서울특별시 동작구 사당동", "경상북도 구미시 진평동"],
  preferedType: "대기오염",
});

const buttonClick = () => {
  //유효성 검사
  const valid = validation();
  //axios 요청
  alert(valid);
};

//공백 + 형식 유효성 검사
const validation = () => {
  let userVal = user.value;
  if (
    userVal.name &&
    userVal.password &&
    userVal.age > 0 &&
    userVal.preferedPlace.length > 0 &&
    userVal.preferedType &&
    validPassword.value
  ) {
    alert("good");
    return true;
  } else {
    alert("제대로 입력하시오");
    return false;
  }
};

//형식 유효성 검사
const validatePassword = /^(?=.*[a-zA-z])(?=.*[0-9])(?=.*[$`~!@$!%*#^?&\\(\\)\-_=+]).{8,16}$/;
const validPassword = computed(() => {
  return validatePassword.test(user.value.password);
});

const preferedTypeList = ref([
  { value: 0, text: "없음" },
  { value: 1, text: "녹지" },
  { value: 2, text: "대기오염" },
  { value: 3, text: "행정시설" },
  { value: 4, text: "학군" },
  { value: 5, text: "근방 맛집" },
  { value: 6, text: "CCTV 대수" },
]);

const computedList = computed(() => {
  return preferedTypeList.value.filter((item) => item.text !== user.value.preferedType);
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
    <h3 class="text-center fw-bold">회원 정보 수정</h3>
    <form>
      <div class="mb-3">
        <label class="form-label">이름</label>
        <input type="text" class="form-control" :placeholder="user.name" v-model="user.name" />
        <small v-show="!user.name" style="color: red">이름을 입력하세요</small>
      </div>
      <div class="mb-3">
        <label class="form-label">이메일</label>
        <input type="email" class="form-control" :placeholder="user.email" readonly />
      </div>
      <div class="mb-3">
        <label class="form-label">비밀번호</label>
        <input
          type="password"
          class="form-control"
          :placeholder="user.password"
          v-model="user.password"
        />
        <div v-show="!user.password"><small style="color: red">비밀번호를 입력하세요</small></div>
        <div v-show="!validPassword && user.password">
          <small style="color: red">영문, 숫자, 특수문자를 조합하여 입력해주세요 (8-16자)</small>
        </div>
      </div>
      <div class="mb-3">
        <label class="form-label">나이</label>
        <input
          type="number"
          class="form-control"
          :placeholder="user.age"
          v-model.number="user.age"
        />
        <small v-show="user.age <= 0" style="color: red">나이를 입력하세요</small>
      </div>
      <div class="mb-3">
        <label class="form-label">선호지역 선택</label>
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

        <div class="d-flex align-items-center" v-if="user.preferedPlace.length < 3">
          <AddressSelectBox ref="childCompRef" @requestDataFromChild="receiveDataFromChild" />
          <img
            class="ms-2"
            src="/src/assets/add.png"
            width="20px"
            height="20px"
            @click="callChildFunction"
          />
        </div>
        <small v-show="user.preferedPlace.length < 1" style="color: red"
          >선호지역을 한 곳 이상 입력하세요</small
        >
      </div>
      <div class="mb-3">
        <label class="form-label">거주지 선정 기준</label>
        <select class="form-select" v-model="user.preferedType">
          <option selected>{{ user.preferedType }}</option>
          <option v-for="item in computedList" :key="item.value" :value="item.text">
            {{ item.text }}
          </option>
        </select>
        <small v-show="!user.preferedType" style="color: red">선호기준을 선택하세요</small>
      </div>
      <div class="d-flex justify-content-between">
        <button
          type="button"
          class="btn text-white fw-bold"
          style="background-color: #707070; width: 48%"
          @click="this.$router.go(-1)"
        >
          취소
        </button>
        <button
          type="button"
          class="btn text-white fw-bold"
          style="background-color: #00b4d8; width: 48%"
          @click="buttonClick"
        >
          저장
        </button>
      </div>
    </form>
  </div>
</template>

<style scoped></style>
