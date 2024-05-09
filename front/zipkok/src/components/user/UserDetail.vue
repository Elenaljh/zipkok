<script>
import { ref } from "vue";

export default {
  setup() {
    const user = ref({
      name: "김싸피",
      email: "ssafyhouse@gmail.com",
      password: "password",
      age: 24,
      preferedPlace: [
        "대구광역시 수성구 만촌동",
        "서울특별시 동작구 사당동",
        "경상북도 구미시 진평동",
      ],
      preferedType: "녹지",
    });

    return { user };
  },
};
</script>
<template>
  <div class="m-5 w-25">
    <img src="/src/assets/house.png" class="mx-auto d-block mb-3" />
    <h3 class="text-center fw-bold">회원 정보 조회</h3>
    <form>
      <div class="mb-3">
        <label class="form-label">이름</label>
        <input
          type="text"
          class="form-control"
          :placeholder="user.name"
          readonly
        />
      </div>
      <div class="mb-3">
        <label class="form-label">이메일</label>
        <input
          type="email"
          class="form-control"
          :placeholder="user.email"
          readonly
        />
      </div>
      <div class="mb-3">
        <label class="form-label">나이</label>
        <input
          type="number"
          class="form-control"
          :placeholder="user.age"
          readonly
        />
      </div>
      <div class="mb-3">
        <label class="form-label">선호지역 선택</label>
        <div v-for="item in user.preferedPlace" :key="item">
          <input
            type="text"
            class="form-control mb-1"
            :placeholder="item"
            readonly
          />
        </div>
      </div>
      <div class="mb-3">
        <label class="form-label">거주지 선정 기준</label>
        <input
          type="text"
          class="form-control"
          :placeholder="user.preferedType"
          readonly
        />
      </div>
      <div class="d-flex justify-content-between">
        <button
          type="button"
          class="btn text-white fw-bold"
          style="background-color: #707070; width: 48%"
        >
          회원 탈퇴
        </button>
        <button
          type="button"
          class="btn text-white fw-bold"
          style="background-color: #00b4d8; width: 48%"
        >
          정보 수정
        </button>
      </div>
    </form>
  </div>
</template>

<style></style>
