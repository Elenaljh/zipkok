<script setup></script>

<template>
  <div class="body d-flex justify-content-center">
    <div class="container m-5 p-5">
      <router-view></router-view>
    </div>
  </div>
</template>

<style>
.container {
  background-color: white;
  border-radius: 20px;
}
.col-auto {
  display: flex;
  align-items: center;
}
</style>
