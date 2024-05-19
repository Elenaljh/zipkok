<script setup>
import { ref } from "vue";

const dong = ref("진평동");
const population = ref({ age: 20, sex: "남성", pop: "1,000", ratio: 10.5 });
const office = ref({
  name: "진평경찰서",
  type: "치안시설",
  address: "경상북도 구미시 진평동",
});
const school = ref({
  name: "진평초등학교",
  type: "초등학교",
  address: "경상북도 구미시",
});
const restaurant = ref({
  name: "진평순두부",
  type: "한식",
  address: "경상북도 구미시 어쩌고",
});
const NumOfcctv = ref(5);
</script>

<template>
  <div class="me-2">
    <!--인구정보-->
    <div>
      <p style="font-size: large; font-weight: bold">
        <span style="color: #00b3d6">{{ dong }}</span
        ><span>의 인구구조</span>
      </p>
      <div class="table-responsive card" style="max-height: 300px; overflow-y: auto">
        <table class="table mb-0">
          <thead class="table-light">
            <tr>
              <th scope="col" style="padding-left: 20px">연령대</th>
              <th scope="col">성별</th>
              <th scope="col">인구 수</th>
              <th scope="col">비율</th>
            </tr>
          </thead>
          <tbody>
            <tr v-for="item in 4" :key="item">
              <td style="padding-left: 20px">{{ population.age }}</td>
              <td>{{ population.sex }}</td>
              <td>{{ population.pop }}명</td>
              <td>{{ population.ratio }}%</td>
            </tr>
          </tbody>
        </table>
      </div>
    </div>
    <!--인구정보 끝-->
    <hr style="background: #00b3d6; border: 0; height: 1.5px" />
    <!--주요시설-->
    <div>
      <p style="font-size: large; font-weight: bold">
        <span style="color: #00b3d6">{{ dong }}</span
        ><span>의 주요시설</span>
      </p>
      <div class="table-responsive card" style="max-height: 300px; overflow-y: auto">
        <table class="table mb-0">
          <tbody>
            <tr v-for="item in 10" :key="item">
              <td style="padding-left: 20px">{{ office.name }}</td>
              <td>{{ office.type }}</td>
              <td>{{ office.address }}</td>
            </tr>
          </tbody>
        </table>
      </div>
    </div>
    <!--주요시설 끝-->
    <hr style="background: #00b3d6; border: 0; height: 1.5px" />
    <!--학교-->
    <div>
      <p style="font-size: large; font-weight: bold">
        <span style="color: #00b3d6">{{ dong }}</span
        ><span>의 학교</span>
      </p>
      <div class="table-responsive card" style="max-height: 300px; overflow-y: auto">
        <table class="table mb-0">
          <tbody>
            <tr v-for="item in 10" :key="item">
              <td style="padding-left: 20px">{{ school.name }}</td>
              <td>{{ school.type }}</td>
              <td>{{ school.address }}</td>
            </tr>
          </tbody>
        </table>
      </div>
    </div>
    <!--학교 끝-->
    <hr style="background: #00b3d6; border: 0; height: 1.5px" />
    <!--맛집-->
    <div>
      <p style="font-size: large; font-weight: bold">
        <span style="color: #00b3d6">{{ dong }}</span
        ><span>의 맛집</span>
      </p>
      <div class="table-responsive card" style="max-height: 300px; overflow-y: auto">
        <table class="table mb-0">
          <tbody>
            <tr v-for="item in 20" :key="item">
              <td style="padding-left: 20px">{{ restaurant.name }}</td>
              <td>{{ restaurant.type }}</td>
              <td>{{ restaurant.address }}</td>
            </tr>
          </tbody>
        </table>
      </div>
    </div>
    <!--맛집 끝-->
    <hr style="background: #00b3d6; border: 0; height: 1.5px" />
    <!--CCTV-->
    <div>
      <p style="font-size: large; font-weight: bold">
        <span style="color: #00b3d6">{{ dong }}</span
        ><span>의 CCTV</span>
      </p>
      <div>
        <p style="font-size: large; font-weight: bold">
          <span>총 </span><span style="color: #00b3d6">{{ NumOfcctv }}</span
          ><span>대 설치되어 있어요</span>
        </p>
      </div>
    </div>
    <!--CCTV 끝-->
  </div>
</template>

<style scoped>
.table-responsive.card::-webkit-scrollbar {
  width: 10px;
}
.table-responsive.card::-webkit-scrollbar-thumb {
  background: #f3f3f3;
  border-radius: 10px;
}
</style>
