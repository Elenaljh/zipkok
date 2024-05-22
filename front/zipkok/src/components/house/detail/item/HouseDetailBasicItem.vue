<script setup>
import { ref, computed, watchEffect, defineProps } from "vue";
import axios from "axios";
import { useMemberStore } from "@/stores/member";
import { adjustedMoneyFormat } from "@/util/util";
import { useHouseStore } from "@/stores/house";
import { grade } from "@/util/airConditionUtil";
import { Line } from "vue-chartjs";
import {
  Chart as ChartJS,
  CategoryScale,
  LinearScale,
  PointElement,
  LineElement,
  Title,
  Tooltip,
  Legend,
} from "chart.js";

//차트 js
ChartJS.register(CategoryScale, LinearScale, PointElement, LineElement, Title, Tooltip, Legend);

const chartOptions = ref({
  responsive: true,
  // maintainAspectRatio: false,
});
//차트 js 설정 끝

const store = useMemberStore();
const houseStore = useHouseStore();
const type = ref("매매");
const info = ref([{ amount: 3, deposit: 2, monthlyRent: 2, year: 2024, month: 2, day: 3 }]); //거래가 데이터 저장됨
const amountData = computed(() => info.value.map((item) => item.amount).reverse()); //매매가 데이터
const depositData = computed(() => info.value.map((item) => item.deposit)); //보증금 데이터
const dayData = computed(() =>
  info.value.map((item) => item.year + "." + item.month + "." + item.day).reverse()
);
const priceAverage = ref({});
const { houseInfo, busStation } = defineProps({
  houseInfo: String,
  busStation: Object,
});
const chartData = computed(() => {
  return {
    labels: dayData.value,
    datasets: [
      {
        label: "거래가",
        data: amountData.value,
        fill: false,
        borderColor: "#00b4d8",
        tension: 0.1,
      },
    ],
  };
});
const chartRentData = computed(() => {
  return {
    labels: dayData.value,
    datasets: [
      {
        label: "거래가",
        data: depositData.value,
        fill: false,
        borderColor: "#00b4d8",
        tension: 0.1,
      },
    ],
  };
});
/*
amount: 0
aptCode: "APT4719033080250018000001"
aptName: "남경앳홈비앙채"
area: 26.4449
bjdCode: "4719012300"
day: 5
deposit: 4500
dong: 0
floor: 11
month: 2
monthlyRent: 0
streetCode: null
transactionId: 185926
type: "전월세"
year: 2024

{amount: 0, deposit: 3004, monthlyRent: 22}
*/

const getTransactionInfo = async () => {
  const response = await axios.get(store.url + "/apt/transaction", {
    params: {
      aptCode: houseStore.houseId,
      type: type.value,
    },
  });
  info.value = response.data;
  const avg = await axios.get(store.url + "/apt/average", {
    params: {
      aptCode: houseStore.houseId,
      type: type.value,
    },
  });
  priceAverage.value = avg.data;
};

watchEffect(async () => {
  const response = await axios.get(store.url + "/apt/transaction", {
    params: {
      aptCode: houseStore.houseId,
      type: type.value,
    },
  });
  info.value = response.data;
  const avg = await axios.get(store.url + "/apt/average", {
    params: {
      aptCode: houseStore.houseId,
      type: type.value,
    },
  });
  priceAverage.value = avg.data;
});

const visibleCount = ref(5);
const visibleInfo = computed(() => {
  return info.value.slice(0, visibleCount.value);
});
const showMore = () => {
  visibleCount.value += 5;
};
const close = () => {
  visibleCount.value = 5;
};
</script>

<template>
  <link rel="preconnect" href="https://fonts.googleapis.com" />
  <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin />
  <link
    href="https://fonts.googleapis.com/css2?family=Noto+Sans+KR:wght@100..900&display=swap"
    rel="stylesheet"
  />
  <div>
    <!--셀렉트박스-->
    <select class="form-select" style="width: 150px" v-model="type" @change="getTransactionInfo">
      <option value="매매" selected>매매</option>
      <option value="전월세">전/월세</option>
    </select>
  </div>
  <p style="text-align: center; font-weight: bold">
    <span style="color: dimgray; font-size: 120%" class="me-2">평균 거래가</span>
    <span style="color: #00b4d8; font-size: 150%" v-show="type === '매매'">{{
      adjustedMoneyFormat(priceAverage.amount)
    }}</span>
    <span style="color: #00b4d8; font-size: 150%" v-show="type === '전월세'"
      >{{ adjustedMoneyFormat(priceAverage.deposit) }}/{{
        adjustedMoneyFormat(priceAverage.monthlyRent)
      }}</span
    >
  </p>
  <!--그래프-->
  <div class="d-flex justify-content-center">
    <Line
      id="my-char-id"
      :options="chartOptions"
      :data="chartData"
      class="mb-5 w-75 h-75"
      v-if="type === '매매'"
    />
    <Line
      id="my-char-id"
      :options="chartOptions"
      :data="chartRentData"
      class="mb-5 w-75 h-75"
      v-if="type === '전월세'"
    />
  </div>
  <!--테이블-->
  <div class="d-flex justify-content-center">
    <div class="table-responsive card" style="width: 70%">
      <table class="table mb-0">
        <thead class="table-light">
          <tr>
            <th scope="col" style="padding-left: 20px">계약일 ↓</th>
            <th scope="col">면적</th>
            <th scope="col">거래가</th>
            <th scope="col">층</th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="item in visibleInfo" :key="item.day">
            <td style="padding-left: 20px">{{ item.year }}.{{ item.month }}.{{ item.day }}</td>
            <td>{{ Math.round(item.area) }}평</td>
            <td v-if="type === '매매'">
              {{ adjustedMoneyFormat(item.amount) }}
            </td>
            <td v-if="type === '전월세'">
              {{ adjustedMoneyFormat(item.deposit) }}/{{
                !item.monthlyRent ? "전세" : adjustedMoneyFormat(item.monthlyRent)
              }}
            </td>
            <td>{{ item.floor }}층</td>
          </tr>
        </tbody>
      </table>
    </div>
  </div>
  <div class="d-flex justify-content-center">
    <button
      type="button"
      class="btn btn-light"
      @click="showMore"
      v-if="visibleInfo.length !== info.length && info.length > 5"
    >
      더보기
    </button>
    <button
      type="button"
      class="btn btn-light"
      @click="close"
      v-if="visibleInfo.length === info.length && info.length > 5"
    >
      접기
    </button>
  </div>
  <!--아파트 정보 섹션-->
  <div class="d-flex justify-content-around p-3 mt-5">
    <!--아파트 기본정보-->
    <div style="font-weight: 500; font-size: large">
      <h5 style="font-weight: bolder; text-align: center; color: dimgray" class="mb-4">
        아파트 기본정보
      </h5>
      <!--
    { "aptCode": "APT4719033080250018000001", "aptName": "남경앳홈비앙채",
    "drmAddress": "경상북도 구미시 삼일로 18", "bjdAddress": "", "bjdCode":
    "4719012300", "houseNum": 126, "buildYear": 2014, "dongNum": 0, "carNum": 0,
    "cctvNum": 0, "lng": 128.4179714, "lat": 36.10666837, "aptType": "",
    "facility": "", "aptAnotherCode": null }-->
      <ul style="list-style-type: none; text-align: center">
        <li>
          <span style="color: #00b4d8">{{ houseInfo.buildYear }}</span>
          <span>년 준공</span>
        </li>
        <li>
          <span>전체 </span
          ><span style="color: #00b4d8">{{ !houseInfo.dongNum ? 1 : houseInfo.dongNum }}</span
          ><span>개 동</span>
        </li>
        <li>
          <span style="color: #00b4d8">{{ houseInfo.houseNum }}</span
          ><span>개 세대</span>
        </li>
      </ul>
    </div>
    <!--아파트 상세정보-->
    <div class="mb-5">
      <h5 style="text-align: center; font-weight: bolder; color: dimgray" class="mb-4">
        아파트 상세정보
      </h5>
      <div>
        <div class="d-flex mb-3">
          <img src="/src/assets/detailIcon/cctv.png" width="70px" height="70px" class="me-4" />
          <div style="width: 100%; height: 100%">
            <div style="font-weight: bold; text-align: center">CCTV 설치</div>
            <div style="text-align: center">
              {{ houseInfo.cctvNum ? houseInfo.cctvNum : "-" }}대
            </div>
          </div>
        </div>
        <div class="d-flex mb-3">
          <img src="/src/assets/detailIcon/amenity.png" width="70px" height="70px" class="me-4" />
          <div style="width: 100%; height: 100%">
            <div style="font-weight: bold; text-align: center">아파트 부대 시설</div>
            <pre
              style="text-align: center; font-family: Noto Sans KR, sans-serif; font-size: medium"
              >{{
                houseInfo.facility ? houseInfo.facility.trim().replace(/,\s*/g, "\n") : "없음"
              }}</pre
            >
          </div>
        </div>
        <div class="d-flex">
          <img src="/src/assets/detailIcon/bus.png" width="70px" height="70px" class="me-4" />
          <div style="width: 100%; height: 100%">
            <div style="font-weight: bold; text-align: center">가까운 버스 정류장</div>
            <div v-for="item in busStation" :key="item" style="text-align: center">
              {{ item }}
            </div>
          </div>
        </div>
      </div>
    </div>
    <!--대기정보-->
    <div>
      <h5 style="text-align: center; font-weight: bolder; color: dimgray" class="mb-4">
        대기 정보
      </h5>
      <div>
        <div class="mb-3">
          <div v-if="grade.pm10Grade == 1" class="d-flex">
            <img src="/src/assets/detailIcon/mise_good.png" width="80px" class="me-4" />
            <div style="width: 100%; height: 100%">
              <div style="text-align: center; font-weight: bold">미세먼지</div>
              <div style="color: blue; text-align: center">매우 좋음</div>
            </div>
          </div>
          <div v-if="grade.pm10Grade == 2" class="d-flex">
            <img src="/src/assets/detailIcon/mise_soso.png" width="80px" class="me-4" />
            <div style="width: 100%; height: 100%">
              <div style="text-align: center; font-weight: bold">미세먼지</div>
              <div style="color: green; text-align: center">좋음</div>
            </div>
          </div>
          <div v-if="grade.pm10Grade == 3" class="d-flex">
            <img src="/src/assets/detailIcon/mise_sick.png" width="80px" class="me-4" />
            <div style="width: 100%; height: 100%">
              <div style="text-align: center; font-weight: bold">미세먼지</div>
              <div style="color: yellow; text-align: center">나쁨</div>
            </div>
          </div>
          <div v-if="grade.pm10Grade == 4" class="d-flex">
            <img src="/src/assets/detailIcon/mise_verybad.png" width="80px" class="me-4" />
            <div style="width: 100%; height: 100%">
              <div style="text-align: center; font-weight: bold">미세먼지</div>
              <div style="color: red; text-align: center">매우 나쁨</div>
            </div>
          </div>
        </div>
        <div class="mb-3">
          <div v-if="grade.pm25Grade == 1" class="d-flex">
            <img src="/src/assets/detailIcon/spmise_good.png" width="80px" class="me-4" />
            <div style="width: 100%; height: 100%">
              <div style="text-align: center; font-weight: bold">초미세먼지</div>
              <div style="color: blue; text-align: center">매우 좋음</div>
            </div>
          </div>
          <div v-if="grade.pm25Grade == 2" class="d-flex">
            <img src="/src/assets/detailIcon/spmise_soso.png" width="80px" class="me-4" />
            <div style="width: 100%; height: 100%">
              <div style="text-align: center; font-weight: bold">초미세먼지</div>
              <div style="color: green; text-align: center">좋음</div>
            </div>
          </div>
          <div v-if="grade.pm25Grade == 3" class="d-flex">
            <img src="/src/assets/detailIcon/spmise_sick.png" width="80px" class="me-4" />
            <div style="width: 100%; height: 100%">
              <div style="text-align: center; font-weight: bold">초미세먼지</div>
              <div style="color: yellow; text-align: center">나쁨</div>
            </div>
          </div>
          <div v-if="grade.pm25Grade == 4" class="d-flex">
            <img src="/src/assets/detailIcon/supermise_verybad.png" width="80px" class="me-4" />
            <div style="width: 100%; height: 100%">
              <div style="text-align: center; font-weight: bold">초미세먼지</div>
              <div style="color: red; text-align: center">매우 나쁨</div>
            </div>
          </div>
        </div>
        <div>
          <div v-if="grade.khaiGrade == 1" class="d-flex">
            <img src="/src/assets/detailIcon/weather_good.png" width="80px" class="me-4" />
            <div style="width: 100%; height: 100%; text-align: center">
              <div style="text-align: center; font-weight: bold">대기 지수</div>
              <div style="color: blue; text-align: center">매우 좋음</div>
            </div>
          </div>
          <div v-if="grade.khaiGrade == 2" class="d-flex">
            <img src="/src/assets/detailIcon/weather_soso.png" width="80px" class="me-4" />
            <div style="width: 100%; height: 100%">
              <div style="text-align: center; font-weight: bold">대기 지수</div>
              <div style="color: green; text-align: center">좋음</div>
            </div>
          </div>
          <div v-if="grade.khaiGrade == 3" class="d-flex">
            <img src="/src/assets/detailIcon/weather_sick.png" width="80px" class="me-4" />
            <div style="width: 100%; height: 100%">
              <div style="text-align: center; font-weight: bold">대기 지수</div>
              <div style="color: yellow; text-align: center">나쁨</div>
            </div>
          </div>
          <div v-if="grade.khaiGrade == 4" class="d-flex">
            <img src="/src/assets/detailIcon/weather_verybad.png" width="80px" class="me-4" />
            <div style="width: 100%; height: 100%">
              <div style="text-align: center; font-weight: bold">대기 지수</div>
              <div style="color: red; text-align: center">매우 나쁨</div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<style scoped></style>
