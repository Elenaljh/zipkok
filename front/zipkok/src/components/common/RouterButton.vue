<script setup>
const { buttonFunc, buttonText, buttonIcon } = defineProps([
  "buttonFunc",
  "buttonText",
  "buttonIcon",
]);
</script>

<template>
  <button type="button" class="btn lighterButton d-flex align-items-center" @click="buttonFunc">
    <img :src="buttonIcon" class="me-1" /> {{ buttonText }}
  </button>
</template>

<style scoped></style>
