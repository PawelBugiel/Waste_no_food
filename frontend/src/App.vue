<template>
  <div id="app">
    <TheHeader v-if="shouldShowHeader" />

    <main class="container">
      <router-view />
    </main>
  </div>
</template>

<script>
import TheHeader from '@/components/TheHeader.vue';
import { useAuthStore } from '@/stores/authStore';
import { computed } from 'vue';

export default {
  name: 'App',
  components: {
    TheHeader // Rejestrujemy komponent nagłówka
  },
  setup() {
    const authStore = useAuthStore();

    // Używamy 'computed', aby dynamicznie decydować, czy pokazać nagłówek.
    // Nagłówek będzie widoczny, tylko gdy token autoryzacyjny istnieje (użytkownik jest zalogowany).
    const shouldShowHeader = computed(() => authStore.isLoggedIn);

    return {
      shouldShowHeader
    };
  }
};
</script>

<style>
#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
  margin-top: 60px;
}
</style>
