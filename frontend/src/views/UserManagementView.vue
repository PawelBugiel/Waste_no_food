<template>
  <div class="container mt-4">
    <div class="d-flex justify-content-between align-items-center mb-4">
      <router-link to="/home" class="btn btn-info btn-sm btn-compact btn-go-to-products">Go to Products</router-link>
      <button @click="logout" class="btn btn-info btn-sm btn-compact btn-logout">Logout</button>
    </div>

    <h2 class="mb-4 user-management-heading">User Management</h2>

    <div class="p-2 mb-3 col-md-6 col-sm-12 mx-auto">
      <form @submit.prevent="registerUser">
        <div class="mb-2">
          <label for="register-email" class="form-label">Email:</label>
          <input v-model="newUser.email" type="email" id="register-email" class="form-control form-control-sm" required/>
        </div>
        <div class="mb-2">
          <label for="register-password" class="form-label">Password:</label>
          <input v-model="newUser.password" type="password" id="register-password" class="form-control form-control-sm" required/>
        </div>
        <div class="mb-2">
          <label for="register-role" class="form-label">Role:</label>
          <select v-model="newUser.role" id="register-role" class="form-select form-select-sm">
            <option value="ENDUSER">End user</option>
            <option value="ADMIN">Administrator</option>
          </select>
        </div>

        <div>
          <button type="submit" class="btn btn-custom-register btn-sm">Register new user</button>
          <button @click="deleteSelectedUser" :disabled="!selectedUser" type="button" class="btn btn-sm btn-custom-delete ms-2">
            Delete Selected User
          </button>
        </div>

      </form>
      <p v-if="registerError" class="text-danger mt-2">{{ registerError }}</p>
    </div>

    <div v-if="error" class="alert alert-danger">
      {{ error }}
    </div>

    <table class="table table-striped" v-if="users.length">
      <thead>
      <tr>
        <th style="width: 50px;">Select</th>
        <th>Email</th>
        <th>Role</th>
      </tr>
      </thead>
      <tbody>
      <tr v-for="user in users"
          :key="user.id"
          @click="selectUser(user)"
          :class="{ 'table-active': selectedUser && selectedUser.id === user.id }"
          style="cursor: pointer;">
        <td>
          <input type="radio" :value="user.id" v-model="selectedUserId" @click.stop>
        </td>
        <td>{{ user.email }}</td>
        <td>{{ user.roles && user.roles.length > 0 ? user.roles[0].name.replace("ROLE_", "") : "NO_ROLE" }}</td>
      </tr>
      </tbody>
    </table>
    <p v-if="!users.length && !error" class="text-center">No users found.</p>
  </div>
</template>

<script setup>
import { ref, onMounted, watch } from 'vue';
import { useRouter } from 'vue-router';
import axios from '@/axios.js';
import { useAuthStore } from '@/stores/authStore';

// --- Zmienne reaktywne ---
const users = ref([]);
const newUser = ref({ email: '', password: '', role: 'ENDUSER' });
const error = ref('');
const registerError = ref('');

// NOWOŚĆ: Zmienne do obsługi zaznaczania użytkownika
const selectedUser = ref(null);
const selectedUserId = ref(null);

const authStore = useAuthStore();
const router = useRouter();

// NOWOŚĆ: Obserwator do synchronizacji zaznaczenia
watch(selectedUserId, (newId) => {
  if (newId) {
    selectedUser.value = users.value.find(user => user.id === newId);
  } else {
    selectedUser.value = null;
  }
});

// --- Metody ---
const fetchUsers = async () => {
  try {
    const response = await axios.get('/auth/users', { headers: { Authorization: `Bearer ${authStore.token}` } });
    users.value = response.data;
    // Resetuj zaznaczenie po każdym odświeżeniu listy
    selectedUser.value = null;
    selectedUserId.value = null;
  } catch (err) {
    error.value = 'Failed to load users. Please check your authentication.';
  }
};

const registerUser = async () => {
  try {
    const endpoint = newUser.value.role === 'ADMIN' ? '/auth/register-admin' : '/auth/register-enduser';
    await axios.post(endpoint, {
      email: newUser.value.email,
      password: newUser.value.password,
      role: newUser.value.role
    }, { headers: { Authorization: `Bearer ${authStore.token}` } });
    registerError.value = '';
    newUser.value = { email: '', password: '', role: 'ENDUSER' };
    await fetchUsers();
  } catch (err) {
    registerError.value = 'Registration failed. Check your permissions or input.';
  }
};

// ZMIANA: Metoda do usuwania bazuje teraz na zaznaczonym użytkowniku
const deleteSelectedUser = async () => {
  if (!selectedUser.value) return; // Zabezpieczenie

  if (confirm(`Are you sure you want to delete user ${selectedUser.value.email}?`)) {
    try {
      await axios.delete(`/auth/user/${selectedUser.value.id}`, { headers: { Authorization: `Bearer ${authStore.token}` } });
      await fetchUsers(); // Odśwież listę, co automatycznie wyczyści zaznaczenie
    } catch (err) {
      error.value = 'Failed to delete user. Check your permissions.';
    }
  }
};

const logout = () => {
  authStore.clearAuth();
  router.push('/');
};

// NOWOŚĆ: Metoda do zaznaczania/odznaczania użytkownika
const selectUser = (user) => {
  if (selectedUser.value && selectedUser.value.id === user.id) {
    selectedUserId.value = null;
  } else {
    selectedUserId.value = user.id;
  }
};

onMounted(() => {
  fetchUsers();
});
</script>

<style scoped>
/* Te style pochodzą z widoku produktów i zapewniają spójność */
.table-active {
  background-color: #e0f2f1 !important;
  font-weight: bold;
}
</style>