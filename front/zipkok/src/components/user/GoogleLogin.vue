<script setup>
import axios from "axios";
import { onMounted } from "vue";
import { useMemberStore } from "@/stores/member";
import { storeToRefs } from "pinia";
const store = useMemberStore();
const { name, email, memberId } = storeToRefs(store);

onMounted(async () => {
  try {
    let code = new URL(window.location.href).searchParams.get("code");
    const response = await axios.post(
      "http://localhost:8080/oauth2/callback",
      {
        code: code,
      },
      {
        withCredentials: true,
      }
    );
    console.log(response.data);
    store.login();
    name.value = response.data.name;
    email.value = response.data.email;
    memberId.value = response.data.memberId;
  } catch (error) {
    alert("로그인 실패");
  }
  if (window.opener && !window.opener.closed) {
    window.opener.redirectToNewPage();
  }
  window.close();
});
</script>

<template>
  <div></div>
</template>

<style scoped></style>
