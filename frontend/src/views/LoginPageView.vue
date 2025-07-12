<template>
  <div class="auth-container">
    <h2>Login Page</h2>

    <form @submit.prevent="handleLogin">
      <div class="mb-3">
        <label for="email" class="form-label">Email:</label>
        <input v-model="formData.email" type="email" id="email" class="form-control form-control-sm" required />
      </div>
      <div class="mb-3">
        <label for="password" class="form-label">Password:</label>
        <input v-model="formData.password" type="password" id="password" class="form-control form-control-sm" required />
      </div>

      <button type="submit" class="btn btn-info btn-compact btn-login">Login</button>
    </form>

    <p v-if="error" class="text-danger mt-2">{{ error }}</p>
  </div>
</template>

<script setup>
import { ref } from 'vue';
import { useRouter } from 'vue-router';
import axios from '@/axios.js';
import { useAuthStore } from '@/stores/authStore';

// --- Zmienne reaktywne (zamiast `data`) ---
const formData = ref({
  email: '',
  password: ''
});
const error = ref('');

// --- Instancje store'u i routera ---
const authStore = useAuthStore();
const router = useRouter();

// --- Metoda (jako funkcja `const`) ---
const handleLogin = async () => {
  try {
    const response = await axios.post('/auth/login', {
      email: formData.value.email,
      password: formData.value.password
    });

    const { token } = response.data;

    // Używamy akcji ze store'a, która powinna od razu zdekodować token i ustawić rolę
    authStore.setToken(token);

    // Przekierowanie w zależności od roli
    if (authStore.role === 'ADMIN') {
      router.push('/admin');
    } else {
      router.push('/home');
    }
  } catch (err) {
    console.error('Login error:', err);
    error.value = 'Login failed. Please check your credentials.';
    if (err.response) {
      error.value += ` (Status: ${err.response.status})`;
    }
  }
};
</script>

<style scoped>
.auth-container {
  max-width: 400px;
  margin: 50px auto;
  padding: 20px;
}
</style>