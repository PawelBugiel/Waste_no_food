<template>
  <div class="container mt-4">
    <div class="d-flex justify-content-between align-items-center mb-4">
      <router-link to="/home" class="btn btn-info btn-sm btn-compact d-inline-flex align-items-center">
        <img src="@/assets/images/products_logo.webp" alt="" class="btn-icon-sm">
        <span>Manage Products</span>
      </router-link>
      <button @click="logout" class="btn btn-info btn-sm btn-compact btn-logout">Logout</button>
    </div>

    <h2 class="mb-4 d-flex align-items-center justify-content-center">
      <img src="@/assets/images/users_logo.webp" alt="Users icon" class="heading-icon">
      <span>User Management</span>
    </h2>

    <form @submit.prevent="registerUser" class="mb-4">
      <div class="row g-3">
        <div class="col-md-4 text-start">
          <label for="register-email" class="form-label">Email:</label>
          <input v-model="newUser.email" type="email" id="register-email" class="form-control form-control-sm" required :disabled="selectedUser"/>
        </div>
        <div class="col-md-4 text-start">
          <label for="register-password" class="form-label">Password:</label>
          <input v-model="newUser.password" type="password" id="register-password" class="form-control form-control-sm" required :disabled="selectedUser"/>
        </div>
        <div class="col-md-4 text-start">
          <label for="register-role" class="form-label">Role:</label>
          <select v-model="newUser.role" id="register-role" class="form-select form-select-sm" :disabled="selectedUser">
            <option value="ENDUSER">End user</option>
            <option value="ADMIN">Administrator</option>
          </select>
        </div>
      </div>
      <p v-if="registerError" class="text-danger mt-2">{{ registerError }}</p>
    </form>

    <div class="row g-3 mb-3">
      <div class="col-md-4 text-start">
        <label for="search-by-email" class="form-label">Search by email:</label>
        <input v-model="searchQuery" type="text" id="search-by-email" class="form-control form-control-sm" @input="fetchUsers" :disabled="selectedUser" />
      </div>
    </div>

    <div class="mb-3" style="max-width: 500px;">
      <div class="row g-2">
        <div class="col">
          <button :disabled="selectedUser" type="submit" @click="registerUser" class="btn btn-custom-register btn-sm w-100 h-100">
            Register new user
          </button>
        </div>
        <div class="col">
          <button @click="deleteSelectedUser" :disabled="!selectedUser" type="button" class="btn btn-sm btn-custom-delete w-100 h-100">
            Delete Selected User
          </button>
        </div>
      </div>
    </div>


    <div v-if="error" class="alert alert-danger">
      {{ error }}
    </div>

    <table class="table table-striped" v-if="users.length">
      <thead>
      <tr>
        <th style="width: 50px;">Lp.</th>
        <th style="width: 50px;">Select</th>
        <th>
          <a href="#" @click.prevent="sort('email')">Email</a>
          <span v-if="sortBy === 'email'">{{ sortDirection === 'asc' ? '↑' : '↓' }}</span>
        </th>
        <th>
          <a href="#" @click.prevent="sort('roles.name')">Role</a>
          <span v-if="sortBy === 'roles.name'">{{ sortDirection === 'asc' ? '↑' : '↓' }}</span>
        </th>
      </tr>
      </thead>
      <tbody>
      <tr v-for="(user, index) in users"
          :key="user.id"
          @click="selectUser(user)"
          :class="{ 'table-active': selectedUser && selectedUser.id === user.id }"
          style="cursor: pointer;">
        <td>{{ (currentPage * pageSize) + index + 1 }}</td>
        <td>
          <input type="radio" :value="user.id" v-model="selectedUserId" @click.stop>
        </td>
        <td>{{ user.email }}</td>
        <td>{{ user.roles && user.roles.length > 0 ? user.roles[0].name.replace("ROLE_", "") : "NO_ROLE" }}</td>
      </tr>
      </tbody>
    </table>
    <p v-if="!users.length && !error" class="text-center">No users found.</p>

    <div class="d-flex justify-content-between align-items-center mb-4" v-if="totalPages > 0">
      <button @click="prevPage" :disabled="currentPage === 0" class="btn btn-info btn-compact btn-previous-page">Previous</button>
      <span>Page {{ currentPage + 1 }} of {{ totalPages }}</span>
      <button @click="nextPage" :disabled="currentPage >= totalPages - 1" class="btn btn-info btn-compact btn-next-page">Next</button>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted, watch } from 'vue';
import { useRouter } from 'vue-router';
import axios from '@/axios.js';
import { useAuthStore } from '@/stores/authStore';

// --- Stan komponentu ---
const users = ref([]);
const newUser = ref({ email: '', password: '', role: 'ENDUSER' });
const error = ref('');
const registerError = ref('');
const selectedUser = ref(null);
const selectedUserId = ref(null);

// --- Stan paginacji i sortowania ---
const currentPage = ref(0);
const totalPages = ref(0);
const pageSize = ref(10);
const sortBy = ref('email');
const sortDirection = ref('asc');

// --- Stan wyszukiwarki ---
const searchQuery = ref('');

const authStore = useAuthStore();
const router = useRouter();

// --- Obserwatory ---
watch([currentPage, sortBy, sortDirection], () => fetchUsers());

watch(selectedUserId, (newId) => {
  if (newId) {
    selectedUser.value = users.value.find(user => user.id === newId);
  } else {
    selectedUser.value = null;
  }
});

watch(searchQuery, (newValue, oldValue) => {
  if (newValue !== oldValue) {
    currentPage.value = 0;
  }
});


// --- Metody ---
const fetchUsers = async () => {
  try {
    let endpoint = '/auth/users';
    const params = {
      page: currentPage.value,
      size: pageSize.value,
      sort: `${sortBy.value},${sortDirection.value}`
    };

    if (searchQuery.value) {
      endpoint = '/auth/search';
      params.email = searchQuery.value;
    }

    const response = await axios.get(endpoint, {
      params,
      headers: { Authorization: `Bearer ${authStore.token}` }
    });
    users.value = response.data.content;
    totalPages.value = response.data.totalPages;
    error.value = '';

    // Nie czyść zaznaczenia, jeśli jesteśmy w trakcie wyszukiwania
    if (!searchQuery.value) {
      selectedUser.value = null;
      selectedUserId.value = null;
    }
  } catch (err) {
    error.value = 'Failed to load users. Please check your authentication.';
    users.value = [];
    totalPages.value = 0;
  }
};

const registerUser = async () => {
  if (selectedUser.value) return; // Dodatkowe zabezpieczenie
  try {
    const endpoint = newUser.value.role === 'ADMIN' ? '/auth/register-admin' : '/auth/register-enduser';
    await axios.post(endpoint, {
      email: newUser.value.email,
      password: newUser.value.password,
      role: newUser.value.role
    }, { headers: { Authorization: `Bearer ${authStore.token}` } });
    registerError.value = '';
    newUser.value = { email: '', password: '', role: 'ENDUSER' };
    currentPage.value = 0;
    await fetchUsers();
  } catch (err) {
    registerError.value = 'Registration failed. Check your permissions or input.';
  }
};

const deleteSelectedUser = async () => {
  if (!selectedUser.value) return;

  if (confirm(`Are you sure you want to delete user ${selectedUser.value.email}?`)) {
    try {
      await axios.delete(`/auth/user/${selectedUser.value.id}`, { headers: { Authorization: `Bearer ${authStore.token}` } });
      if (users.value.length === 1 && currentPage.value > 0) {
        currentPage.value--;
      } else {
        await fetchUsers();
      }
    } catch (err) {
      error.value = 'Failed to delete user. Check your permissions.';
    }
  }
};

const sort = (field) => {
  if (sortBy.value === field) {
    sortDirection.value = sortDirection.value === 'asc' ? 'desc' : 'asc';
  } else {
    sortBy.value = field;
    sortDirection.value = 'asc';
  }
  currentPage.value = 0;
};

const nextPage = () => {
  if (currentPage.value < totalPages.value - 1) {
    currentPage.value++;
  }
};

const prevPage = () => {
  if (currentPage.value > 0) {
    currentPage.value--;
  }
};

const logout = () => {
  authStore.clearAuth();
  router.push('/');
};

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
.table-active {
  background-color: #e0f2f1 !important;
  font-weight: bold;
}
</style>