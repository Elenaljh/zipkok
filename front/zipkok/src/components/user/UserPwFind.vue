<script setup>
import { ref } from "vue";
import { sweetAlert } from "@/util/util";
import axios from "axios";

const sendNumber = ref(false);
const authenticate = ref(false);
const totalSuccess = ref(false);
const inputEmail = ref("");
const inputNumber = ref("");
const newPassword = ref("");

const sendNumberFunction = async () => {
  //이메일 보내기
  try {
    const response = await axios.post("http://localhost:8080/email/password", {
      email: inputEmail.value,
    });
    let result = response.data;
    if (result === "회원 없음") {
      sweetAlert("일치하는 회원정보가 없습니다.", "", "error");
      return;
    } else if (result === "인증메일 전송") {
      sendNumber.value = true;
      sweetAlert("인증메일 발송", "인증메일을 확인해주세요", "success");
    } else {
      sweetAlert("문제 발생", "메일을 보내는 과정에서 문제가 발생했습니다.", "error");
    }
  } catch (error) {
    sweetAlert("문제 발생", "내부 에러 발생", "error");
    console.log(error);
  }
};

const numberTest = async () => {
  try {
    const response = await axios.post("http://localhost:8080/verifyCode", {
      email: inputEmail.value,
      code: inputNumber.value,
    });
    let result = response.data;
    if (result) {
      sweetAlert("인증 성공", "비밀번호를 재설정하세요", "success");
      authenticate.value = true;
    } else {
      sweetAlert("인증 실패", "인증번호가 일치하지 않습니다. 다시 시도해주세요.", "error");
      sendNumber.value = false;
    }
  } catch (error) {
    sweetAlert("문제 발생", "내부 에러 발생", "error");
    console.log(error);
  }
};

const changePassword = async () => {
  try {
    const response = await axios.post("http://localhost:8080/password", {
      email: inputEmail.value,
      password: newPassword.value,
    });
    let result = response.data;
    if (result === "비밀번호 변경 성공") {
      sweetAlert("비밀번호 재설정 성공", "새로운 비밀번호로 로그인하세요", "success");
      totalSuccess.value = true;
    } else {
      sweetAlert("비밀번호 재설정 실패", "문제가 발생했습니다", "error");
    }
  } catch (error) {
    sweetAlert("문제 발생", "내부 에러 발생", "error");
    console.log(error);
  }
};
</script>
<template>
  <div class="m-5 w-25">
    <img src="/src/assets/logo.png" width="70" class="mx-auto d-block mb-3" />
    <h3 class="text-center fw-bold mb-4">비밀번호 찾기</h3>
    <form>
      <div class="mb-3" v-if="!sendNumber">
        <label class="form-label">이메일</label>
        <input
          type="email"
          class="form-control"
          placeholder="이메일을 입력하세요."
          v-model="inputEmail"
        />
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
      <div class="mb-3" v-if="authenticate && !totalSuccess">
        <label class="form-label">비밀번호 변경</label>
        <input
          type="password"
          class="form-control"
          placeholder="새로운 비밀번호를 입력하세요."
          v-model="newPassword"
        />
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
        v-if="authenticate && !totalSuccess"
        @click="changePassword"
      >
        비밀번호 변경
      </button>
    </form>
  </div>
  <div v-if="totalSuccess">
    <h1>성공! 다시 로그인하세요</h1>
  </div>
</template>
<style></style>
