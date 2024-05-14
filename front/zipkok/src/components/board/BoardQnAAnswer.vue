<script setup>
import { onMounted, ref } from "vue";

const props = defineProps({ comment: Object, type: String });
const emit = defineEmits(["registEvent", "cancelEvent"]);
const comment = props.comment;
const type = props.type;
const com_content = ref(comment.content);
console.log("코멘ㅌ예용", com_content.value, type);

onMounted(() => {
  resize(document.getElementById("comTextArea"));
});
function resize(target) {
  target.style.height = "1px";
  target.style.height = target.scrollHeight + 10 + "px";
}
</script>

<template>
  <div class="row py-1">
    <div
      style="font-size: 17px; font-weight: bold"
      class="col-2 d-flex justify-content-center align-items-center"
    >
      {{ comment.writer }}
    </div>
    <textarea
      type="text"
      class="col board-content text-secondary p-2 m-2"
      id="comTextArea"
      @input="resize($event.currentTarget)"
      v-model="com_content"
      :disabled="type == 'read'"
    ></textarea>
    <div class="d-flex justify-content-end" v-if="type == 'regist'">
      <button type="button" class="btn reverseButton mb-3 ms-1" @click="emit('cancelEvent')">
        취소
      </button>
      <button
        type="button"
        class="btn lighterButton mb-3 ms-1"
        @click="emit('registEvent', com_content)"
      >
        저장
      </button>
    </div>
    <div class="divider mt-3 mb-3"></div>
  </div>
</template>

<style scoped></style>
