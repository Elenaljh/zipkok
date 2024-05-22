import { createApp } from "vue";
import App from "./App.vue";
import router from "./router";
import "bootstrap/dist/css/bootstrap.min.css";
import "bootstrap";
import "@/assets/css/main.css";
import { useKakao } from "vue3-kakao-maps/@utils";
import { createPinia } from "pinia";
import piniaPluginPersistedstate from "pinia-plugin-persistedstate";
import VueSweetalert2 from "vue-sweetalert2";
import { QuillEditor } from '@vueup/vue-quill'
import '@vueup/vue-quill/dist/vue-quill.snow.css';

const pinia = createPinia();
pinia.use(piniaPluginPersistedstate);

const { VITE_KAKAO_MAP_API_KEY } = import.meta.env;

useKakao(VITE_KAKAO_MAP_API_KEY, ['clusterer', 'services', 'drawing']);
console.log(VITE_KAKAO_MAP_API_KEY);
const app = createApp(App);
app.use(VueSweetalert2);
app.component('QuillEditor', QuillEditor);
app.use(pinia);
app.use(router);
app.mount("#app");
