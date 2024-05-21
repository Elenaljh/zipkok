<script setup>
import { dateFormat } from "@/util/util";

defineProps({ board: Object });


</script>

<template>
  <tr id="boardListItem" class="text-center m-2">
    <th scope="row">{{ board.boardId }}</th>
    <td class="text-start">
      <router-link
        :to="{ name: 'board-view', params: { boardId: board.boardId } }"
        class="board-title link-dark"
        @click="console.log('click')"
      >
        {{ board.title }}
      </router-link>
    </td>
    <td>{{ board.writer }}</td>
    <td>{{ board.hit }}</td>
    <td>{{ board.boom }}</td>
    <td>{{ dateFormat(board.createdAt) }}</td>
  </tr>
</template>

<style scoped>
a {
  text-decoration: none;
}
</style>
