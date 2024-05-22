import { ref, watch } from "vue";
import { defineStore } from "pinia";
import axios from "axios";
import { useRouter } from "vue-router";

export const useMemberStore = defineStore(
  "member",
  () => {
    const router = useRouter();
    const isAuthorized = ref(false);
    const name = ref("");
    const email = ref("");
    const memberId = ref(0);
    const preference = ref(""); //user의 prefered type이 저장됨

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
        // redirectLogin();
        console.log(error);
      }
    };

    const login = () => {
      isAuthorized.value = true;
    };

    const logout = () => {
      isAuthorized.value = false;
      name.value = "";
      email.value = "";
      memberId.value = "";
      preference.value = "";
    };

    const parsedVal = () => {
      const data = localStorage.getItem("member");
      const parsedData = JSON.parse(data);
      return parsedData.isAuthorized;
    };

    return {
      redirectLogin,
      authorizationRequest,
      url,
      isAuthorized,
      login,
      logout,
      parsedVal,
      name,
      email,
      memberId,
      preference,
    };
  },
  {
    persist: true,
  }
);
