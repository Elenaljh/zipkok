<script setup>
import HeaderNavBar from "./components/layout/HeaderNavBar.vue";
import FooterNavBar from "./components/layout/FooterNavBar.vue";
</script>

<template>
  <body class="d-flex flex-column min-vh-100">
    <HeaderNavBar />
    <RouterView />
    <FooterNavBar class="mt-auto" />
  </body>
</template>

<style scoped></style>
