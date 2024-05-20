<script setup>
import { ref, computed, onMounted } from "vue";
import AddressSelectBox from "../common/AddressSelectBox.vue";
import { useMemberStore } from "@/stores/member";
import axios from "axios";
import { useRouter } from "vue-router";

const url = useMemberStore().url;
const router = useRouter();
onMounted(async () => {
  try {
    const response = await axios.get(useMemberStore().url + "/member", {
      withCredentials: true,
    });
    user.value = await response.data;
    await pushList(user.value);
    console.log(user.value);
  } catch (error) {
    alert("조회 실패");
  }
});
const user = ref({});
const preferedPlaceArr = ref([]);
const pushList = async (member) => {
  if (member.preferedPlace1) {
    preferedPlaceArr.value.push(member.preferedPlace1);
  }
  if (member.preferedPlace2) {
    preferedPlaceArr.value.push(member.preferedPlace2);
  }
  if (member.preferedPlace3) {
    preferedPlaceArr.value.push(member.preferedPlace3);
  }
};

const buttonClick = async () => {
  //유효성 검사
  const valid = validation();
  //axios 요청
  if (valid) {
    await fillUserForm();
    try {
      const userPayload = JSON.parse(JSON.stringify(user.value)); // 반응형 데이터 복사
      await axios.put(url + "/member", userPayload, {
        withCredentials: true,
      });
      alert("회원정보 수정 완료");
      router.push({ name: "userDetail" });
    } catch (error) {
      alert("회원정보 수정 실패");
    }
  } else {
    alert("회원정보 수정 폼을 다시 확인하세요");
  }
};

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

//공백 + 형식 유효성 검사
const validation = () => {
  let userVal = user.value;
  if (
    userVal.name &&
    userVal.password &&
    userVal.age > 0 &&
    preferedPlaceArr.value.length > 0 &&
    userVal.preferedType &&
    validPassword.value
  ) {
    // alert("good");
    return true;
  } else {
    alert("수정 폼을 확인해주세요");
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
    if (preferedPlaceArr.value.includes(data.address)) {
      alert("중복된 값입니다!");
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
//셀렉트박스
</script>
<template>
  <div class="m-5 w-25">
    <img src="/src/assets/logo.png" width="70" class="mx-auto d-block mb-3" />
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
          :placeholder="user.age"
          v-model.number="user.age"
        />
        <small v-show="user.age <= 0" style="color: red">나이를 입력하세요</small>
      </div>
      <div class="mb-3">
        <label class="form-label">선호지역 선택</label>
        <div v-if="preferedPlaceArr.length > 0">
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

        <div class="d-flex align-items-center" v-if="preferedPlaceArr.length < 3">
          <AddressSelectBox ref="childCompRef" @requestDataFromChild="receiveDataFromChild" />
          <img
            class="ms-2"
            src="/src/assets/add.png"
            width="20px"
            height="20px"
            @click="callChildFunction"
          />
        </div>
        <small v-show="preferedPlaceArr.length < 1" style="color: red"
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
