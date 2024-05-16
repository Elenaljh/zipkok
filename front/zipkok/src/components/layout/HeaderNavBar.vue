<script setup>
import { Suspense, onMounted } from "vue";
import { useRouter } from "vue-router";
import { useMemberStore } from "@/stores/member";
import axios from "axios";

const router = useRouter();
const memberStore = useMemberStore();

// 처음 페이지 로드 시 권한 요청
//
onMounted(async () => {
  await memberStore.authorizationRequest(router);
});

// 로그아웃
const logoutUser = async () => {
  try {
    await axios.get(`${memberStore.url}/logout`, { withCredentials: true });
    memberStore.logout();
    console.log(memberStore.isAuthorized);
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
          <router-link :to="{ name: 'board' }" class="me-2"
            ><img src="@/assets/navNot.png"
          /></router-link>
          <router-link :to="{ name: 'board-list', query: { tn: 1 } }"
            ><img src="@/assets/navBoard.png" width="25" height="30"
          /></router-link>
        </span>
        <span class="me-2" v-if="!memberStore.isAuthorized">
          <router-link :to="{ name: 'login' }" class="me-2"
            ><img src="@/assets/navLogin.png"
          /></router-link>
          <router-link :to="{ name: 'createUser' }"
            ><img src="@/assets/navRegister.png"
          /></router-link>
        </span>
        <span class="me-2" v-else>
          <Suspense>
            <img
              src="@/assets/navLogout.png"
              @click="logoutUser"
              style="cursor: pointer"
            />
          </Suspense>
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
