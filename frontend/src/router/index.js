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
    // ZMIANA: Dodajemy metadane dla tytułu strony
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

// ZMIANA: Dodajemy logikę, która uruchamia się po każdej zmianie trasy
router.afterEach((to) => {
  // Czekamy na następną klatkę animacji, aby upewnić się, że komponent jest gotowy
  if (to.meta.title) {
    document.title = `${to.meta.title} | Waste no food`;
  } else {
    // Domyślny tytuł, jeśli żaden nie jest zdefiniowany w trasie
    document.title = 'Waste no food';
  }
});

export default router;