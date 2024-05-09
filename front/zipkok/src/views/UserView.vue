<script>
import UserCreate from "@/components/user/UserCreate.vue";
import UserModify from "@/components/user/UserModify.vue";
import UserDetail from "@/components/user/UserDetail.vue";
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
    UserModify,
    UserDetail,
  },
};
</script>
<template>
  <div class="d-flex justify-content-center">
    <UserCreate v-if="page === 'create'" />
    <UserModify v-if="page === 'modify'" />
    <UserDetail v-if="page === 'detail'" />
  </div>
</template>
<style></style>
