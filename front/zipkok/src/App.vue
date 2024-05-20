<script setup>
import HeaderNavBar from "./components/layout/HeaderNavBar.vue";
import FooterNavBar from "./components/layout/FooterNavBar.vue";
</script>

<template>
  <body class="d-flex flex-column min-vh-100">
    <HeaderNavBar style="height: 10vh" />
    <RouterView  />
    <FooterNavBar style="height: 5vh; position: sticky" />
  </body>
</template>

<style scoped></style>
