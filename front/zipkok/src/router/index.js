import { createRouter, createWebHistory } from "vue-router";
import MainView from "../views/MainView.vue";
import UserView from "@/views/UserView.vue";
import HouseView from "@/views/HouseView.vue";
import { useMemberStore } from "@/stores/member";
import { storeToRefs } from "pinia";

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: "/",
      name: "main",
      component: MainView,
    },
    {
      path: "/user",
      name: "user",
      component: UserView,
      children: [
        {
          path: "create",
          name: "createUser",
          component: () => import("@/components/user/UserCreate.vue"),
          beforeEnter: (to, from) => {
            const store = useMemberStore();
            const { isAuthorized } = storeToRefs(store);
            if (isAuthorized.value) {
              router.push(from);
            }
          },
        },
        {
          path: "modify",
          name: "modifyUser",
          component: () => import("@/components/user/UserModify.vue"),
          beforeEnter: (to, from) => {
            const store = useMemberStore();
            const { isAuthorized } = storeToRefs(store);
            if (!isAuthorized.value) {
              router.push(from);
            }
          },
        },
        {
          path: "detail",
          name: "userDetail",
          component: () => import("@/components/user/UserDetail.vue"),
          beforeEnter: (to, from) => {
            const store = useMemberStore();
            const { isAuthorized } = storeToRefs(store);
            if (!isAuthorized.value) {
              router.push(from);
            }
          },
        },
        {
          path: "login",
          name: "login",
          component: () => import("@/components/user/UserLogin.vue"),
          beforeEnter: (to, from) => {
            const store = useMemberStore();
            const { isAuthorized } = storeToRefs(store);
            if (isAuthorized.value) {
              router.push(from);
            }
          },
        },
        {
          path: "pwFind",
          name: "pwFind",
          component: () => import("@/components/user/UserPwFind.vue"),
          beforeEnter: (to, from) => {
            const store = useMemberStore();
            const { isAuthorized } = storeToRefs(store);
            if (isAuthorized.value) {
              router.push(from);
            }
          },
        },
      ],
    },
    {
      path: "/board",
      name: "board",
      component: () => import("../views/BoardView.vue"),
      redirect: { name: "board-list" },
      children: [
        {
          path: "list",
          name: "board-list",
          component: () => import("@/components/board/BoardList.vue"),
        },
        {
          path: "view/:boardId",
          name: "board-view",
          component: () => import("@/components/board/BoardDetail.vue"),
        },
        {
          path: "write",
          name: "board-write",
          component: () => import("@/components/board/BoardCreate.vue"),
          beforeEnter: (to, from) => {
            const store = useMemberStore();
            const { isAuthorized } = storeToRefs(store);
            if (!isAuthorized.value) {
              router.push(from);
            }
          },
        },
        {
          path: "modify/:boardId",
          name: "board-modify",
          component: () => import("@/components/board/BoardUpdate.vue"),
          beforeEnter: (to, from) => {
            const store = useMemberStore();
            const { isAuthorized } = storeToRefs(store);
            if (!isAuthorized.value) {
              router.push(from);
            }
          },
        },
      ],
    },
    {
      path: "/house",
      name: "house",
      component: HouseView,
    },
    {
      path: "/det",
      name: "det",
      component: () => import("@/components/house/detail/HouseDetail.vue"),
    },
  ],
});

router.beforeEach(() => {
  const store = useMemberStore();
  store.authorizationRequest();
  // console.log("피니아 값: " + store.isAuthorized);
  // console.log("로컬스토리지 값: " + store.isAuthorized());
});

function checkUser(to, from)  {
  const store = useMemberStore();
  const { isAuthorized } = storeToRefs(store);
  if (isAuthorized.value) {
    router.push(from);
  }
}

export default router;
