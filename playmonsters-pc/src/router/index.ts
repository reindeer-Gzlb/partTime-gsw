import { createRouter, createWebHashHistory,createWebHistory, RouteRecordRaw } from 'vue-router';
import LoginRouter from '@/router/login'

const routes: Array<RouteRecordRaw> = [
  {
    path: '/login',
    name: 'login',
    component: () => import(/* webpackChunkName: "Home" */ '@/views/login/login.vue')
  },
  {
    path: '/register',
    name: 'register',
    component: () => import(/* webpackChunkName: "Home" */ '@/views/login/register.vue')
  },
  {
    path: '/yinsi',
    name: 'yinsi',
    component: () => import(/* webpackChunkName: "Home" */ '@/views/webLink/yinsi.vue')
  },
  ...LoginRouter
];

const router = createRouter({
  mode: 'history',
  history: createWebHistory(),
  routes
});

export default router;
