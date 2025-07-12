<template>
  <header class="app-header">
    <div class="container">
      <div class="header-content">
        <h1 class="app-title">Waste no food</h1>
        <nav>
          <button v-if="authStore.token" @click="logout" class="btn btn-outline-secondary btn-sm">Logout</button>
        </nav>
      </div>
    </div>
  </header>
</template>

<script setup>
import { useAuthStore } from '@/stores/authStore';
import { useRouter } from 'vue-router';

const authStore = useAuthStore();
const router = useRouter();

const logout = () => {
  // Zakładam, że clearAuth() czyści token i resetuje stan
  authStore.clearAuth();
  router.push('/'); // Przekieruj na stronę logowania po wylogowaniu
};
</script>

<style scoped>
.app-header {
  background-color: #f8f9fa;
  padding: 10px 0;
  border-bottom: 1px solid #dee2e6;
  margin-bottom: 2rem;
}
.header-content {
  display: flex;
  justify-content: space-between;
  align-items: center;
}
.app-title {
  font-size: 1.5rem;
  margin: 0;
  font-weight: 300;
}
</style>