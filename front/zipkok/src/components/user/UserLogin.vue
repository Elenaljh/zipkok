<script setup>
import { ref, onMounted } from "vue";
import { useCookies } from "vue3-cookies";

const loginInfo = ref({
  memberId: "",
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

    //로그인 성공시 then에서 쿠키 설정
    setCookie();
  }
};

const cookieConfig = {
  expireTimes: "10d",
  path: "/user/login",
  domain: "",
  secure: false, // HTTPS가 아닌 경우 false로 설정하세요.
  sameSite: "Lax",
  partitioned: false,
};
// 쿠키 설정
const setCookie = () => {
  // const cookies = useCookies();
  if (rememberMe.value) {
    // const cookies = useCookies();
    console.log("아이디 기억하기");
    cookies.set("rememberMe", loginInfo.value.memberId, "10d");
    console.log(cookies);
  } else {
    console.log("쿠키 삭제");
    cookies.remove("rememberMe");
  }
};

// 쿠키 가져오기 및 아이디 창에 설정
onMounted(() => {
  try {
    const { cookies } = useCookies();
    let cookieValue = cookies.get("rememberMe");
    console.log("쿠키값: " + cookieValue);
    if (cookieValue) {
      loginInfo.value.memberId = cookieValue;
    }
  } catch (e) {
    console.log(e);
  }
});

// 유효성 검사
const validation = () => {
  let info = loginInfo.value;
  if (!info.memberId && !info.password) {
    alert("정보를 입력하세요");
  } else if (!info.memberId) {
    alert("아이디를 입력하세요");
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
    <p class="text-center" style="color: #707070">HelpHome에 오신 것을 환영합니다!</p>
    <form>
      <div class="mb-3">
        <label class="form-label">이메일</label>
        <input
          type="email"
          class="form-control"
          placeholder="이메일을 입력하세요."
          v-model="loginInfo.memberId"
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
          <label class="form-check-label" for="flexCheckDefault"> 아이디 기억하기 </label>
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
      <button type="button" class="btn w-100 fw-bold" style="border-color: lightgray">
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
