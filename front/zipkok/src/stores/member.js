import { ref } from "vue";
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
    const preference = ref("");

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
    };

    const parsedVal = () => {
      const data = localStorage.getItem("member");
      const parsedData = JSON.parse(data);
      return parsedData.isAuthorized;
    };

    const getCCTVNum = async (aptCode) => {
      const result = await axios.get("http://localhost:8080/apt/cctv", {
        params: {
          aptCode: aptCode,
        },
      });
      return result.data;
    };

    const getOfficeNum = async (lat, lng) => {
      const result = await axios.get("http://localhost:8080/apt/office", {
        params: {
          lat: lat,
          lng: lng,
        },
      });
      return result.data;
    };

    const getSchoolNum = async (lat, lng) => {
      const result = await axios.get("http://localhost:8080/apt/school", {
        params: {
          lat: lat,
          lng: lng,
        },
      });
      return result.data;
    };

    const getRestaurantNum = async (dongCode) => {
      const result = await axios.get("http://localhost:8080/apt/restaurant", {
        params: {
          dongCode: dongCode,
        },
      });
      return result.data;
    };

    const getPreferenceInfo = async ({ lat, lng, dongCode, aptCode } = {}) => {
      if (preference.value === "행정시설") {
        return await getOfficeNum(lat, lng);
      } else if (preference.value === "학군") {
        return await getSchoolNum(lat, lng);
      } else if (preference.value === "근방 맛집") {
        return await getRestaurantNum(dongCode);
      } else if (preference.value === "CCTV 대수") {
        return await getCCTVNum(aptCode);
      } else {
        return null;
      }
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
      getPreferenceInfo,
      getOfficeNum,
      getCCTVNum,
      getSchoolNum,
      getRestaurantNum,
    };
  },
  {
    persist: true,
  }
);
