import { createRouter, createWebHistory } from 'vue-router';
import LoginPageView from '../views/LoginPageView.vue';
import ProductsDashboardView from '../views/ProductsDashboardView.vue';
import UserManagementView from '../views/UserManagementView.vue';
import AdminDashboardView from '../views/AdminDashboardView.vue';

const routes = [
  {
    path: '/',
    name: 'Auth',
    component: LoginPageView,
    meta: { title: 'Login' }
  },
  {
    path: '/home',
    name: 'HomePage',
    component: ProductsDashboardView,
    meta: { title: 'Products' }
  },
  {
    path: '/users',
    name: 'UserManagement',
    component: UserManagementView,
    meta: { title: 'User Management' }
  },
  {
    path: '/admin',
    name: 'AdminDashboardView',
    component: AdminDashboardView,
    meta: { title: 'Admin Dashboard' }
  }
];

const router = createRouter({
  history: createWebHistory(),
  routes,
});

router.afterEach((to) => {
  if (to.meta.title) {
    document.title = `${to.meta.title} | Waste no food`;
  } else {
    document.title = 'Waste no food';
  }
});

export default router;