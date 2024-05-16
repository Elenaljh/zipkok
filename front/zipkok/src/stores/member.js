import { ref, computed } from "vue";
import { defineStore } from "pinia";
import axios from "axios";
import { useRouter } from "vue-router";

export const useMemberStore = defineStore("member", () => {
  const router = useRouter();
  const isAuthorized = ref(false);

  const url = ref("http://localhost:8080");

  const redirectLogin = () => {
    alert("로그인이 필요합니다");
    router.push({ name: "login" });
  };

  const authorizationRequest = async () => {
    try {
      const response = await axios.get("/authorization");
      isAuthorized.value = response.data;
    } catch (error) {
      redirectLogin();
    }
  };

  return {
    redirectLogin,
    authorizationRequest,
    url,
    isAuthorized: computed(() => isAuthorized.value),
  };
});
