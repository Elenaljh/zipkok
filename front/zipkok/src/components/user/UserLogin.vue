<script setup></script>

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
        />
      </div>
      <div class="mb-3">
        <label class="form-label">비밀번호</label>
        <input
          type="password"
          class="form-control"
          placeholder="비밀번호를 입력하세요."
        />
      </div>
      <div class="d-flex justify-content-between mb-3">
        <div class="form-check">
          <input
            class="form-check-input"
            type="checkbox"
            value=""
            id="flexCheckDefault"
          />
          <label class="form-check-label" for="flexCheckDefault">
            아이디 기억하기
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
