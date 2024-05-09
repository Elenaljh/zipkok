<script></script>
<template>
  <div class="m-5 w-25">
    <img src="/src/assets/house.png" class="mx-auto d-block mb-3" />
    <h3 class="text-center fw-bold">회원가입</h3>
    <form>
      <div class="mb-3">
        <label class="form-label">이름</label>
        <input
          type="text"
          class="form-control"
          placeholder="이름을 입력하세요."
        />
      </div>
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
      <div class="mb-3">
        <label class="form-label">나이</label>
        <input
          type="number"
          class="form-control"
          placeholder="나이를 입력하세요."
        />
      </div>
      <div class="mb-3">
        <label class="form-label">선호지역 선택</label>
        <div class="d-flex justify-content-between">
          <select class="form-select">
            <option selected>시/도</option>
            <option value="1">One</option>
            <option value="2">Two</option>
            <option value="3">Three</option>
          </select>
          <select class="form-select ms-1">
            <option selected>시/군/구</option>
            <option value="1">One</option>
            <option value="2">Two</option>
            <option value="3">Three</option>
          </select>
          <select class="form-select ms-1">
            <option selected>동</option>
            <option value="1">One</option>
            <option value="2">Two</option>
            <option value="3">Three</option>
          </select>
        </div>
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
      <button
        type="button"
        class="btn w-100 fw-bold"
        style="border-color: lightgray"
      >
        <img src="/src/assets/google_s.png" />
        &nbsp;Google로 시작하기
      </button>
    </form>
  </div>
</template>
<style></style>
