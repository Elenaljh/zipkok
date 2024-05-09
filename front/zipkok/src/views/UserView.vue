<script>
import UserCreate from "@/components/user/UserCreate.vue";
import { useRoute } from "vue-router";
import { ref } from "vue";

export default {
  setup() {
    const route = useRoute();
    const page = ref(route.params.page);

    return { page };
  },
  components: {
    UserCreate,
  },
};
</script>
<template>
  <div class="d-flex justify-content-center">
    <UserCreate v-if="page === 'create'" />
  </div>
</template>
<style></style>
