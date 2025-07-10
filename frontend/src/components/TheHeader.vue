<template>
  <header class="app-header">
    <div class="container">
      <div class="header-content">
        <h1 class="app-title">Waste no food</h1>
        <nav>
          <button v-if="authStore.isLoggedIn" @click="logout" class="btn btn-outline-secondary btn-sm">Logout</button>
        </nav>
      </div>
    </div>
  </header>
</template>

<script>
import { useAuthStore } from '@/stores/authStore';
import { useRouter } from 'vue-router';

export default {
  name: 'TheHeader',
  setup() {
    const authStore = useAuthStore();
    const router = useRouter();

    const logout = () => {
      authStore.clearToken();
      router.push('/'); // Przekieruj na stronę logowania po wylogowaniu
    };

    return {
      authStore,
      logout
    };
  }
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