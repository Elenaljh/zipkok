import { createRouter, createWebHistory } from "vue-router";
import MainView from "../views/MainView.vue";
import UserView from "@/views/UserView.vue";
import HouseView from "@/views/HouseView.vue";
import { useMemberStore } from "@/stores/member";
import { storeToRefs } from "pinia";
import Swal from "sweetalert2";
import { checkAuth } from "@/api/board";
import { callSwal } from "@/util/util";

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
      redirect: { name: "board-list", query: { tn: 0 } },
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
          beforeEnter: (to, from, next) => {
            const store = useMemberStore();
            const { isAuthorized } = storeToRefs(store);
            if (!isAuthorized.value) {
              router.push(from);
              Swal.fire({
                icon: 'info',
                title: '로그인 후 이용 가능합니다.',
                text: '로그인 하시겠습니까?',
                showCancelButton: true,
                confirmButtonText: '예', 
                cancelButtonText: '아니오',
                confirmButtonColor: '#00b4d8',
                cancelButtonColor: 'lightgray',
              }).then(result => {
                if (result.isConfirmed) {
                  router.push({name: "login"});
                } else if (result.isDismissed) {
                  next(false);
                }
            })

            } else {
              next();
            }
          },
        },
        {
          path: "modify/:boardId",
          name: "board-modify",
          component: () => import("@/components/board/BoardUpdate.vue"),
          beforeEnter: (to, from, next) => {
            const store = useMemberStore();
            const { isAuthorized } = storeToRefs(store);
            if (!isAuthorized.value) {
              console.log("no")
              //guardLogin(to, from, next);
            } else {
              checkUserBoard(to.params.boardId, (res) => {
                if (!res) {
                  console.log("사용자 다름");
                  next(false);  
                } else {
                  console.log("ok go");
                  next();
                }
              });
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
  // store.authorizationRequest();
  // console.log("피니아 값: " + store.isAuthorized);
  // console.log("로컬스토리지 값: " + store.isAuthorized());
});

function guardLogin(to, from, next){
  Swal.fire({
    icon: 'info',
    title: '로그인 후 이용 가능합니다.',
    text: '로그인 하시겠습니까?',
    showCancelButton: true,
    confirmButtonText: '예', 
    cancelButtonText: '아니오',
    confirmButtonColor: '#00b4d8',
    cancelButtonColor: 'lightgray',
  }).then(result => {
    if (result.isConfirmed) {
      router.push({name: "login"});
    } else if (result.isDismissed) {
      next(false);
    }
})
}

function checkUserBoard(boardId, callback) {
  checkAuth(
    boardId,
    (response) => {
      if (response.status == 200){
        console.log("true")
        callback(true);
      } else {
        console.log("여기", response)
        callSwal({
          icon: "error",
          title: "권한 없음",
          text: response.data,
        });
        callback(false);
      }
      
    },
    (error) => {
      console.log("여기", error);
      callSwal({
        icon: "error",
        title: "권한 없음",
        text: error.response.data,
      });
      callback(false);
    }
  );
}

export default router;
