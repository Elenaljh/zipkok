import axios from "axios";
import { ref } from "vue";

const accessToken = ref("");
const tm = ref({});
const stationName = ref("");
const grade = ref({});
const { VITE_STATION_KEY, VITE_AIRGRADE_KEY } = import.meta.env;

const convertCoordinate = async (lng, lat) => {
  console.log("좌표 변환", lng, lat);
  await getauth();
  const response = await axios.get(
    "https://sgisapi.kostat.go.kr/OpenAPI3/transformation/transcoord.json",
    {
      params: {
        accessToken: accessToken.value,
        src: "EPSG:4004",
        dst: "EPSG:5179",
        posX: lng,
        posY: lat,
      },
    }
  );
  console.log("좌표 변환 결과 ", response.data.result);
  tm.value = response.data.result;
};

const getauth = async () => {
  console.log("auth키");
  const response = await axios.get(
    "https://sgisapi.kostat.go.kr/OpenAPI3/auth/authentication.json",
    {
      params: {
        consumer_key: "72e63cde266d42d887e1",
        consumer_secret: "5d0f10493e124444982f",
      },
    }
  );
  console.log(response.data.result.accessToken);
  accessToken.value = response.data.result.accessToken;
};

const getStation = async (lng, lat) => {
  await convertCoordinate(lng, lat);
  console.log("측정소 가져오기2", tm.value.posX, tm.value.posY);
  const response = await axios.get(
    "https://apis.data.go.kr/B552584/MsrstnInfoInqireSvc/getNearbyMsrstnList",
    {
      params: {
        serviceKey: VITE_STATION_KEY,
        returnType: "json",
        tmX: tm.value.posX,
        tmY: tm.value.posY,
        ver: 1.1,
      },
    }
  );
  console.log(response.data.response.body.items[0].stationName);
  stationName.value = response.data.response.body.items[0].stationName;
};

const getGrade = async (lng, lat) => {
  console.log("등급 가져오기");
  await getStation(lng, lat);
  const response = await axios.get(
    "http://apis.data.go.kr/B552584/ArpltnInforInqireSvc/getMsrstnAcctoRltmMesureDnsty",
    {
      params: {
        serviceKey: VITE_AIRGRADE_KEY,
        returnType: "json",
        numOfRows: 1,
        stationName: stationName.value,
        dataTerm: "DAILY",
        ver: 1.3,
      },
    }
  );
  console.log(response.data.response.body.items[0]);
  grade.value = response.data.response.body.items[0];
};

export { convertCoordinate, getStation, getGrade, grade };
