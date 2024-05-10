<script setup></script>

<template>
  <nav class="navbar bg-body-tertiary">
    <div class="container-fluid">
      <a class="navbar-brand"
        ><img src="@/assets/logo.png" alt="Bootstrap" width="80" height="80" /> ZIPKOK</a
      >
      <div>
        <img src="@/assets/navNot.png" />
        <img src="@/assets/navBoard.png" />
        <router-link :to="{ name: 'login' }"><img src="@/assets/navLogin.png" /></router-link>
        <router-link :to="{ name: 'createUser' }"
          ><img src="@/assets/navRegister.png"
        /></router-link>
      </div>
    </div>
  </nav>
</template>

<style scoped></style>
