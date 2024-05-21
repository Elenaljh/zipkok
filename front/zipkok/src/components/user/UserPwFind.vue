<script setup>
import { ref } from "vue";
import { sweetAlert } from "@/util/util";

const sendNumber = ref(false);
const notMember = ref(false);
const wrongNumber = ref(false);
const authenticate = ref(false);
const inputNumber = ref("");

const sendNumberFunction = () => {
  //회원여부 검사
  console.log("회원여부 검사");
  if (notMember.value) {
    sweetAlert("일치하는 회원정보가 없습니다.", "", "error");
    return;
  }
  //이메일 보내기
  console.log("이메일 보내기");
  sendNumber.value = true;
  console.log("sendNumber: " + sendNumber.value + ", authenticate: " + authenticate.value);
};
const numberTest = () => {
  //inputNumber이 정확한지 테스트
  if (wrongNumber.value) {
    sweetAlert("인증번호가 일치하지 않습니다", "", "error");
    return;
  }
  authenticate.value = true;
};
</script>
<template>
  <div class="m-5 w-25">
    <img src="/src/assets/logo.png" width="70" class="mx-auto d-block mb-3" />
    <h3 class="text-center fw-bold mb-4">비밀번호 찾기</h3>
    <form>
      <div class="mb-3" v-if="!sendNumber">
        <label class="form-label">이메일</label>
        <input type="email" class="form-control" placeholder="이메일을 입력하세요." />
      </div>
      <div class="mb-3" v-if="sendNumber && !authenticate">
        <label class="form-label">인증번호 입력</label>
        <input
          type="text"
          class="form-control"
          placeholder="인증번호를 입력하세요."
          v-model="inputNumber"
        />
      </div>
      <div class="mb-3" v-if="authenticate">
        <label class="form-label">비밀번호 변경</label>
        <input type="password" class="form-control" placeholder="새로운 비밀번호를 입력하세요." />
      </div>
      <button
        type="button"
        class="btn w-100 text-white fw-bold mb-3"
        style="background-color: #00b4d8"
        v-if="!sendNumber"
        @click="sendNumberFunction"
      >
        인증번호 보내기
      </button>
      <button
        type="button"
        class="btn w-100 text-white fw-bold mb-3"
        style="background-color: #00b4d8"
        v-if="sendNumber && !authenticate"
        @click="numberTest"
      >
        인증하기
      </button>
      <button
        type="button"
        class="btn w-100 text-white fw-bold mb-3"
        style="background-color: #00b4d8"
        v-if="authenticate"
      >
        비밀번호 변경
      </button>
    </form>
  </div>
</template>
<style></style>
