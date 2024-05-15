import { createRouter, createWebHistory } from "vue-router";
import MainView from "../views/MainView.vue";
import UserView from "@/views/UserView.vue";
import HouseView from "@/views/HouseView.vue";

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
        },
        {
          path: "modify",
          name: "modifyUser",
          component: () => import("@/components/user/UserModify.vue"),
        },
        {
          path: "detail",
          name: "userDetail",
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
        },
        {
          path: "modify/:boardId",
          name: "board-modify",
          component: () => import("@/components/board/BoardUpdate.vue"),
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

export default router;
