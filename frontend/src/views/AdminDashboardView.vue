<template>
  <div class="container mt-4">
    <div class="d-flex justify-content-end mb-4">
      <button @click="logout" class="btn btn-info btn-sm btn-compact btn-logout">Logout</button>
    </div>

    <h2>Admin Dashboard</h2>
    <div class="d-flex justify-content-center mb-4">
      <router-link to="/home" class="btn btn-2x-large btn-info mx-1 btn-go-to-products">Go to Products</router-link>
      <router-link to="/users" class="btn btn-2x-large btn-info mx-1 btn-manage-users">Manage Users</router-link>
    </div>
  </div>
</template>

<script>
import { useAuthStore } from '@/stores/authStore';

export default {
  name: 'AdminDashboardView',
  setup() {
    const authStore = useAuthStore();
    return { authStore };
  },
  methods: {
    logout() {
      this.authStore.clearAuth();
      this.$router.push('/');
    }
  }
};
</script>

