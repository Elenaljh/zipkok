<script setup>
import { computed } from "vue";
import { useRouter, onBeforeRouteUpdate } from "vue-router";
import { useMemberStore } from "@/stores/member";
import axios from "axios";

const router = useRouter();
const memberStore = useMemberStore();

// 처음 페이지 로드 시 권한 요청
// await memberStore.authorizationRequest(router);

// 페이지가 변경될 때마다 권한 요청
// onBeforeRouteUpdate(async (to, from, next) => {
//   await memberStore.authorizationRequest(router);
//   console.log("인증 상태: " + isAuthorized.value);
//   next();
// });

// const isAuthorized = computed(() => memberStore.isAuthorized);

// 로그아웃
const logoutUser = async () => {
  try {
    await axios.get(`${memberStore.url}/logout`, { withCredentials: true });
    router.push({ name: "main" });
  } catch (error) {
    alert("로그아웃 실패");
  }
};
</script>

<template>
  <nav class="navbar bg-body-tertiary">
    <div class="container-fluid">
      <a class="navbar-brand">
        <router-link :to="{ name: 'main' }">
          <img src="@/assets/logo.png" alt="Bootstrap" width="80" height="80" />
          ZIPKOK</router-link
        ></a
      >

      <div>
        <span class="me-2">
          <router-link :to="{ name: 'board' }"
            ><img src="@/assets/navNot.png"
          /></router-link>
          <router-link :to="{ name: 'board-list', query: { tn: 1 } }"
            ><img src="@/assets/navBoard.png" width="25" height="30"
          /></router-link>
        </span>
        <span>
          <router-link :to="{ name: 'login' }"
            ><img src="@/assets/navLogin.png"
          /></router-link>
          <router-link :to="{ name: 'createUser' }"
            ><img src="@/assets/navRegister.png"
          /></router-link>
        </span>
        <span>
          <img src="@/assets/navLogout.png" @click="logoutUser" />
          <router-link :to="{ name: 'userDetail' }"
            ><img src="@/assets/navUser.png"
          /></router-link>
        </span>
      </div>
    </div>
  </nav>
</template>

<style scoped>
a {
  text-decoration: none;
  color: black;
}
</style>
