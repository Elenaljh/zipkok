<script></script>
<template>
  <div class="m-5 w-25">
    <img src="/src/assets/house.png" class="mx-auto d-block mb-3" />
    <h3 class="text-center fw-bold">비밀번호 찾기</h3>
    <form>
      <div class="mb-3">
        <label class="form-label">이메일</label>
        <input type="email" class="form-control" placeholder="이메일을 입력하세요." />
      </div>
      <div class="mb-3">
        <label class="form-label">인증번호 입력</label>
        <input type="text" class="form-control" placeholder="인증번호를 입력하세요." />
      </div>
      <div class="mb-3">
        <label class="form-label">비밀번호 변경</label>
        <input type="password" class="form-control" placeholder="새로운 비밀번호를 입력하세요." />
      </div>
      <button
        type="button"
        class="btn w-100 text-white fw-bold mb-3"
        style="background-color: #00b4d8"
      >
        인증번호 보내기
      </button>
      <button
        type="button"
        class="btn w-100 text-white fw-bold mb-3"
        style="background-color: #00b4d8"
      >
        인증하기
      </button>
      <button
        type="button"
        class="btn w-100 text-white fw-bold mb-3"
        style="background-color: #00b4d8"
      >
        비밀번호 변경
      </button>
    </form>
  </div>
</template>
<style></style>
