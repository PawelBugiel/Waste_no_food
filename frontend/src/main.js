import { createApp } from 'vue';
import { createPinia } from 'pinia';
import App from './App.vue';
import router from './router';
import BootstrapVueNext from 'bootstrap-vue-next';
import 'bootstrap/dist/css/bootstrap.min.css';
import 'bootstrap-vue-next/dist/bootstrap-vue-next.css';
import './styles/global.css';

const app = createApp(App);
const pinia = createPinia();

// Rejestrujemy w aplikacji Pinia, Router i BootstrapVue
app.use(pinia);
app.use(router);
app.use(BootstrapVueNext);

app.mount('#app');