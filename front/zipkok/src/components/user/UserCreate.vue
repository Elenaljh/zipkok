<script setup>
import { ref, computed } from "vue";
import { useRouter } from "vue-router";
import AddressSelectBox from "../common/AddressSelectBox.vue";
import axios from "axios";
import { useMemberStore } from "@/stores/member";
import Swal from "sweetalert2";
const router = useRouter();
const store = useMemberStore();
const url = store.url;
const user = ref({
  name: "",
  email: "",
  password: "",
  age: 0,
  preferedPlace1: "",
  preferedPlace2: "",
  preferedPlace3: "",
  preferedType: "",
});

const preferedPlaceArr = ref([]);
const fillUserForm = async () => {
  for (let i = 0; i <= preferedPlaceArr.value.length; i++) {
    if (i == 0) {
      user.value.preferedPlace1 = preferedPlaceArr.value[i];
    } else if (i == 1) {
      user.value.preferedPlace2 = preferedPlaceArr.value[i];
    } else if (i == 2) {
      user.value.preferedPlace3 = preferedPlaceArr.value[i];
    }
  }
  console.log("user");
  console.log(user.value);
};

const buttonClick = async () => {
  //유효성 검사
  const valid = validation();
  if (valid) {
    await fillUserForm();
    try {
      const userPayload = JSON.parse(JSON.stringify(user.value)); // 반응형 데이터 복사
      await axios.post(url + "/member", userPayload);
      sweetAlert("회원가입 성공!", "로그인하세요", "success");
      router.push({ name: "main" });
    } catch (error) {
      sweetAlert("회원가입 실패", "문제가 발생했습니다.", "error");
    }
  } else {
    sweetAlert("Warning", "회원가입폼을 다시 점검해주세요", "warning");
  }
};

//공백 + 형식 유효성 검사
const validation = () => {
  let userVal = user.value;
  if (
    userVal.name &&
    userVal.password &&
    userVal.age > 0 &&
    preferedPlaceArr.value.length > 0 &&
    userVal.preferedType &&
    validEmail.value &&
    validPassword.value
  ) {
    return true;
  } else {
    return false;
  }
};

//형식 유효성 검사
const validateEmail = /^[A-Za-z0-9_\\.\\-]+@[A-Za-z0-9\\-]+\.[A-Za-z0-9\\-]+/;
const validatePassword = /^(?=.*[a-zA-z])(?=.*[0-9])(?=.*[$`~!@$!%*#^?&\\(\\)\-_=+]).{8,16}$/;
const validEmail = computed(() => {
  return validateEmail.test(user.value.email);
});
const validPassword = computed(() => {
  return validatePassword.test(user.value.password);
});

//셀렉트박스 위한 함수와 변수
const childCompRef = ref(null);

const callChildFunction = () => {
  childCompRef.value.sendDataToParent();
};

const receiveDataFromChild = (data) => {
  console.log("데이터 수신 완료");
  if (data.address) {
    if (preferedPlaceArr.value.includes(data.address)) {
      sweetAlert("Warning", "중복된 값입니다", "warning");
      return;
    }
    preferedPlaceArr.value.push(data.address);
    console.log(preferedPlaceArr.value);
  }
};

const deleteAddress = (item) => {
  preferedPlaceArr.value = preferedPlaceArr.value.filter((address) => {
    return address !== item;
  });
  console.log(preferedPlaceArr.value);
};

const preferedTypeList = ref([
  { value: 0, text: "없음" },
  { value: 1, text: "녹지" },
  { value: 2, text: "대기오염" },
  { value: 3, text: "행정시설" },
  { value: 4, text: "학군" },
  { value: 5, text: "근방 맛집" },
  { value: 6, text: "CCTV 대수" },
]);

//셀렉트박스

//sweetAlert
const sweetAlert = (title, text, icon) => {
  Swal.fire({
    title: title,
    text: text,
    icon: icon,
    confirmButtonText: "확인",
    confirmButtonColor: "#00b4d8",
  });
};
</script>
<template>
  <div class="m-5 w-25">
    <img src="/src/assets/logo.png" width="70" class="mx-auto d-block mb-3" />
    <h3 class="text-center fw-bold">회원가입</h3>
    <form>
      <div class="mb-3">
        <label class="form-label">이름</label>
        <input
          type="text"
          class="form-control"
          placeholder="이름을 입력하세요."
          v-model="user.name"
        />
        <small v-show="!user.name" style="color: red">이름을 입력하세요</small>
      </div>
      <div class="mb-3">
        <label class="form-label">이메일</label>
        <input
          type="email"
          class="form-control"
          placeholder="이메일을 입력하세요."
          v-model="user.email"
        />
        <div v-show="!user.email">
          <small style="color: red">이메일을 입력하세요</small>
        </div>
        <div v-show="!validEmail && user.email">
          <small style="color: red">이메일을 정확히 입력하세요</small>
        </div>
      </div>
      <div class="mb-3">
        <label class="form-label">비밀번호</label>
        <input
          type="password"
          class="form-control"
          placeholder="영문, 숫자, 특수문자를 조합하여 입력해주세요 (8-16자)"
          v-model="user.password"
        />
        <div v-show="!user.password">
          <small style="color: red">비밀번호를 입력하세요</small>
        </div>
        <div v-show="!validPassword && user.password">
          <small style="color: red">영문, 숫자, 특수문자를 조합하여 입력해주세요 (8-16자)</small>
        </div>
      </div>
      <div class="mb-3">
        <label class="form-label">나이</label>
        <input
          type="number"
          class="form-control"
          placeholder="나이를 입력하세요."
          v-model.number="user.age"
        />
        <small v-show="user.age <= 0" style="color: red">나이를 입력하세요</small>
      </div>
      <div class="mb-3">
        <label class="form-label">선호지역 선택</label>
        <!--셀렉트박스-->
        <div v-show="preferedPlaceArr.length > 0">
          <div class="d-flex align-items-center mb-2" v-for="item in preferedPlaceArr" :key="item">
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
          v-if="preferedPlaceArr.length < 3"
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
        <small v-show="preferedPlaceArr.length < 1" style="color: red"
          >선호지역을 한 곳 이상 입력하세요</small
        >
      </div>
      <div class="mb-3">
        <label class="form-label">거주지 선정 기준</label>
        <select class="form-select" v-model="user.preferedType">
          <option selected disabled>선정 기준을 고르세요</option>
          <option v-for="item in preferedTypeList" :key="item.value" :value="item.text">
            {{ item.text }}
          </option>
        </select>
        <small v-show="!user.preferedType" style="color: red">선호기준을 선택하세요</small>
      </div>
      <button
        type="button"
        class="btn w-100 text-white fw-bold mb-3"
        style="background-color: #00b4d8"
        @click="buttonClick"
      >
        회원가입
      </button>
    </form>
  </div>
</template>
<style scoped></style>
