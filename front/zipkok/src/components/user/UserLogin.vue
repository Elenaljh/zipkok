<script setup>
import { ref, onMounted } from "vue";
import { useCookies } from "vue3-cookies";
import { useMemberStore } from "@/stores/member";
import axios from "axios";
axios.defaults.withCredentials = true;

const store = useMemberStore();
const url = store.url;

const loginInfo = ref({
  email: "",
  password: "",
});
const rememberMe = ref(false);
const { cookies } = useCookies();
const buttonClick = () => {
  // 유효성 검사
  const valid = validation();

  if (valid) {
    alert("유효성 검사 통과");
    alert(rememberMe.value);

    //axios 요청
    try {
      axios.post(url + "/login", loginInfo.value);
      //로그인 성공시 then에서 쿠키 설정
      setCookie();
      alert("로그인 성공");
    } catch (error) {
      alert("로그인 실패");
    }
  }
};

// 쿠키 설정
const setCookie = () => {
  // const cookies = useCookies();
  if (rememberMe.value) {
    // const cookies = useCookies();
    console.log("이메일 기억하기");
    cookies.set("rememberMe", loginInfo.value.email, "10d");
    console.log(cookies);
  } else {
    console.log("쿠키 삭제");
    cookies.remove("rememberMe");
  }
};

// 쿠키 가져오기 및 이메일 창에 설정
onMounted(() => {
  try {
    const { cookies } = useCookies();
    let cookieValue = cookies.get("rememberMe");
    console.log("쿠키값: " + cookieValue);
    if (cookieValue) {
      loginInfo.value.email = cookieValue;
    }
  } catch (e) {
    console.log(e);
  }
});

// 유효성 검사
const validation = () => {
  let info = loginInfo.value;
  if (!info.email && !info.password) {
    alert("정보를 입력하세요");
  } else if (!info.email) {
    alert("이메일을 입력하세요");
  } else if (!info.password) {
    alert("비밀번호를 입력하세요");
  } else {
    return true;
  }
  return false;
};
</script>

<template>
  <div class="m-5 w-25">
    <img src="/src/assets/house.png" class="mx-auto d-block mb-3" />
    <h3 class="text-center fw-bold">로그인</h3>
    <p class="text-center" style="color: #707070">
      HelpHome에 오신 것을 환영합니다!
    </p>
    <form>
      <div class="mb-3">
        <label class="form-label">이메일</label>
        <input
          type="email"
          class="form-control"
          placeholder="이메일을 입력하세요."
          v-model="loginInfo.email"
        />
      </div>
      <div class="mb-3">
        <label class="form-label">비밀번호</label>
        <input
          type="password"
          class="form-control"
          placeholder="비밀번호를 입력하세요."
          v-model="loginInfo.password"
        />
      </div>
      <div class="d-flex justify-content-between mb-3">
        <div class="form-check">
          <input
            class="form-check-input"
            type="checkbox"
            value=""
            id="flexCheckDefault"
            v-model="rememberMe"
          />
          <label class="form-check-label" for="flexCheckDefault">
            이메일 기억하기
          </label>
        </div>
        <router-link
          style="color: #00b4d8; font-weight: bold; text-decoration-line: none"
          :to="{ name: 'pwFind' }"
          >비밀번호 찾기</router-link
        >
      </div>
      <button
        type="button"
        class="btn w-100 text-white fw-bold mb-3"
        style="background-color: #00b4d8"
        @click="buttonClick"
      >
        로그인
      </button>
      <button
        type="button"
        class="btn w-100 fw-bold"
        style="border-color: lightgray"
      >
        <img src="/src/assets/google_s.png" class="me-2" />
        Google로 로그인
      </button>
    </form>
    <div class="d-flex justify-content-center mt-3">
      <p class="me-2">계정이 없으십니까?</p>
      <router-link
        :to="{ name: 'createUser' }"
        style="color: #00b4d8; font-weight: bold; text-decoration-line: none"
        >회원가입</router-link
      >
    </div>
  </div>
</template>

<style scoped></style>
