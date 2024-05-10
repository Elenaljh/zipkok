import { createRouter, createWebHistory } from "vue-router";
import MainView from "../views/MainView.vue";
import UserView from "@/views/UserView.vue";

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
          name: "create-user",
          component: () => import("@/components/user/UserCreate.vue"),
        },
        {
          path: "modify",
          name: "modify-user",
          component: () => import("@/components/user/UserModify.vue"),
        },
        {
          path: "detail/:userid",
          name: "user-detail",
          component: () => import("@/components/user/UserDetail.vue"),
        },
        {
          path: "login",
          name: "login",
          component: () => import("@/components/user/UserLogin.vue"),
        },
        {
          path: "pwFind",
          name: "pwFind",
          component: () => import("@/components/user/UserPwFind.vue"),
        },
      ],
    },
    {
      path: "/board",
      name: "board",
      component: () => import("@/views/BoardView.vue"),
      redirect: { name: "article-list" },
      children: [
        {
          path: "list",
          name: "article-list",
          component: () => import("@/components/board/BoardList.vue"),
        },
        {
          path: "view/:articleno",
          name: "article-view",
          component: () => import("@/components/board/BoardDetail.vue"),
        },
        {
          path: "write",
          name: "article-write",
          component: () => import("@/components/board/BoardCreate.vue"),
        },
        {
          path: "modify/:articleno",
          name: "article-modify",
          component: () => import("@/components/board/BoardUpdate.vue"),
        },
      ],
    },
  ],
});

export default router;
