import { ref, computed } from "vue";
import { defineStore } from "pinia";
import axios from "axios";
import { useRouter } from "vue-router";

export const useMemberStore = defineStore("member", () => {
  const router = useRouter();

  const url = ref("http://localhost:8080");

  const login = (email, password) => {
    axios
      .post("/login", {
        email: email,
        password: password,
      })
      .catch((error) => {
        console.log(error.response);
        console.log("로그인 실패");
      });
  };

  const logout = () => {
    axios.get("/logout");
  };

  const redirectLogin = () => {
    alert("로그인이 필요합니다");
    router.push({ name: "login" });
  };

  const authorizationRequest = () => {
    axios.get("/authorization").catch(redirectLogin());
  };

  return {
    login,
    redirectLogin,
    authorizationRequest,
    logout,
    url,
  };
});
