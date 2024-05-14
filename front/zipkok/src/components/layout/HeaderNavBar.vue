<script setup></script>

<template>
  <nav class="navbar bg-body-tertiary">
    <div class="container-fluid">
      <a class="navbar-brand">
        <router-link :to="{ name: 'main' }">
          <img src="@/assets/logo.png" alt="Bootstrap" width="80" height="80" />
          ZIPKOK</router-link
        ></a
      >

      <div>
        <span class="me-2">
          <router-link :to="{ name: 'board' }"><img src="@/assets/navNot.png" /></router-link>
          <router-link :to="{ name: 'board-list', query: { tn: 1 } }"
            ><img src="@/assets/navDoc.png"
          /></router-link> </span
        ><span>
          <router-link :to="{ name: 'login' }"><img src="@/assets/navLogin.png" /></router-link>
          <router-link :to="{ name: 'createUser' }"
            ><img src="@/assets/navRegister.png"
          /></router-link>
        </span>
      </div>
    </div>
  </nav>
</template>

<style scoped>
a {
  text-decoration: none;
  color: black;
}
</style>
