<script setup>
import { ref } from "vue";

const dong = ref("진평동");
const population = ref({ age: 20, sex: "남성", pop: "1,000", ratio: 10.5 });
</script>

<template>
  <div class="me-2">
    <!--인구정보-->
    <div>
      <p style="font-size: large; font-weight: bold">
        <span style="color: #00b3d6">{{ dong }}</span
        ><span>의 인구구조</span>
      </p>
      <div
        class="table-responsive card"
        style="max-height: 300px; overflow-y: auto"
      >
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
