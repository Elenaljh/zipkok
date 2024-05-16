import { ref } from "vue";
import { defineStore } from "pinia";
import axios from "axios";
import { useRouter } from "vue-router";

export const useMemberStore = defineStore(
  "member",
  () => {
    const router = useRouter();
    const isAuthorized = ref(false);

    const url = ref("http://localhost:8080");

    const redirectLogin = () => {
      alert("로그인이 필요합니다");
      router.push({ name: "login" });
    };

    const authorizationRequest = async () => {
      try {
        const response = await axios.get(url.value + "/authorization", {
          withCredentials: true,
        });
        isAuthorized.value = response.data;
      } catch (error) {
        redirectLogin();
      }
    };

    const login = () => {
      isAuthorized.value = true;
    };

    const logout = () => {
      isAuthorized.value = false;
    };

    return {
      redirectLogin,
      authorizationRequest,
      url,
      isAuthorized,
      login,
      logout,
    };
  }
  // {
  //   persist: true,
  // }
);
