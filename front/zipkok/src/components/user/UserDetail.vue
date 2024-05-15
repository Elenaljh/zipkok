<script setup>
import axios from "axios";
import { ref, onMounted } from "vue";
import { useRouter } from "vue-router";
import { useMemberStore } from "@/stores/member";
axios.defaults.withCredentials = true;
onMounted(async () => {
  try {
    const response = await axios.get(useMemberStore().url + "/member");
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

const router = useRouter();
const goModify = () => {
  router.push({ path: "/user/modify" });
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
        <div v-for="item in preferedPlaceArr" :key="item">
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
          @click="goModify"
        >
          정보 수정
        </button>
      </div>
    </form>
  </div>
</template>

<style></style>
