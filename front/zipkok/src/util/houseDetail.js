import { ref } from "vue";
import axios from "axios";

const offices = ref([]);
const schools = ref([]);

const getOffices = async (lng, lat) => {
  const result = await axios.get("http://localhost:8080/apt/office", {
    params: {
      lng: lng,
      lat: lat,
    },
  });
  offices.value = result.data;
};

const getSchools = async (lng, lat) => {
  const result = await axios.get("http://localhost:8080/apt/school", {
    params: {
      lng: lng,
      lat: lat,
    },
  });
  schools.value = result.data;
};

export { offices, schools, getOffices, getSchools };
