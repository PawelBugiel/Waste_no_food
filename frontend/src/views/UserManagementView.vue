<template>
  <div class="container mt-4">
    <div class="d-flex justify-content-between align-items-center mb-4">
      <router-link to="/home" class="btn btn-info btn-sm btn-compact d-inline-flex align-items-center">
        <img src="@/assets/images/products_logo.webp" alt="" class="btn-icon-sm">
        <span>Manage products</span>
      </router-link>
      <button @click="logout" class="btn btn-info btn-sm btn-compact btn-logout">Logout</button>
    </div>

    <h2 class="mb-4 d-flex align-items-center justify-content-center">
      <img src="@/assets/images/users_logo.webp" alt="Users icon" class="heading-icon">
      <span>User Management</span>
    </h2>

    <form @submit.prevent="registerUser" class="mb-3">
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
          <button @click="showDeleteModal(selectedUser)" :disabled="!selectedUser" type="button" class="btn btn-sm btn-custom-delete w-100 h-100">
            Delete selected user
          </button>
        </div>
      </div>
    </div>

    <table class="table table-striped" v-if="users.length">
      <thead>
      <tr>
        <th style="width: 50px;">No.</th>
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
    <p v-if="!users.length" class="text-center">No users found.</p>

    <div class="d-flex justify-content-between align-items-center mb-4" v-if="totalPages > 0">
      <button @click="prevPage" :disabled="currentPage === 0" class="btn btn-info btn-compact btn-previous-page">Previous</button>
      <span>Page {{ currentPage + 1 }} of {{ totalPages }}</span>
      <button @click="nextPage" :disabled="currentPage >= totalPages - 1" class="btn btn-info btn-compact btn-next-page">Next</button>
    </div>
    <div class="modal fade modal-custom" id="deleteUserModal" tabindex="-1" aria-labelledby="deleteModalLabel" aria-hidden="true">
      <div class="modal-dialog modal-dialog-centered modal-sm">
        <div class="modal-content">
          <div class="modal-header">
            <h5 class="modal-title" id="deleteModalLabel">Confirm Deletion</h5>
            <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
          </div>
          <div class="modal-body">
            Are you sure you want to delete user:
            <div class="my-2">
              <strong>{{ userToDelete?.email }}</strong>
            </div>
          </div>
          <div class="modal-footer">
            <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Cancel</button>
            <button type="button" class="btn btn-custom-delete" @click="confirmDeleteUser">Delete</button>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted, watch } from 'vue';
import { useRouter } from 'vue-router';
import axios from '@/axios.js';
import { useAuthStore } from '@/stores/authStore';
import { Modal } from 'bootstrap';
import { useToast } from "vue-toastification";
import ValidationErrorToast from "@/components/ValidationErrorToast.vue";

// --- Stan komponentu ---
const users = ref([]);
const newUser = ref({ email: '', password: '', role: 'ENDUSER' });
const selectedUser = ref(null);
const selectedUserId = ref(null);
const userToDelete = ref(null);
const deleteModal = ref(null);

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
const toast = useToast();

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
    if (!searchQuery.value) {
      selectedUser.value = null;
      selectedUserId.value = null;
    }
  } catch (err) {
    const errorMessage = err.response?.data?.exceptionMessage || 'An unexpected error occurred while fetching users.';
    toast.error(errorMessage, { timeout: 7000 });
    users.value = [];
    totalPages.value = 0;
  }
};

const registerUser = async () => {
  if (selectedUser.value) return;
  try {
    const endpoint = newUser.value.role === 'ADMIN' ? '/auth/register-admin' : '/auth/register-enduser';
    await axios.post(endpoint, {
      email: newUser.value.email,
      password: newUser.value.password,
    }, { headers: { Authorization: `Bearer ${authStore.token}` } });

    toast.success(`User "${newUser.value.email}" registered successfully!`, { timeout: 3000 });

    newUser.value = { email: '', password: '', role: 'ENDUSER' };
    currentPage.value = 0;
    await fetchUsers();
  } catch (err) {
    if (err.response && err.response.data) {
      const errorData = err.response.data;
      if (errorData.validationErrors) {
        toast.error({
          component: ValidationErrorToast,
          props: { validationErrors: errorData.validationErrors }
        }, { timeout: 7000 });
      } else {
        toast.error(errorData.exceptionMessage || 'An unexpected error occurred during registration.', { timeout: 7000 });
      }
    } else {
      toast.error('Could not connect to the server.', { timeout: 7000 });
    }
  }
};

const showDeleteModal = (user) => {
  if (user) {
    userToDelete.value = user;
    deleteModal.value.show();
  }
};

const confirmDeleteUser = async () => {
  if (!userToDelete.value) return;
  try {
    const userEmail = userToDelete.value.email;
    await axios.delete(`/auth/user/${userToDelete.value.id}`, { headers: { Authorization: `Bearer ${authStore.token}` } });

    toast.success(`User "${userEmail}" deleted successfully!`, { timeout: 3000 });

    deleteModal.value.hide();
    userToDelete.value = null;
    selectedUser.value = null;
    selectedUserId.value = null;

    if (users.value.length === 1 && currentPage.value > 0) {
      currentPage.value--;
    } else {
      await fetchUsers();
    }
  } catch (err) {
    const errorMessage = err.response?.data?.exceptionMessage || 'An unexpected error occurred while deleting the user.';
    toast.error(errorMessage, { timeout: 7000 });
    deleteModal.value.hide();
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
    selectedUser.value = null;
    selectedUserId.value = null;
  } else {
    selectedUser.value = user;
    selectedUserId.value = user.id;
  }
};

onMounted(() => {
  fetchUsers();
  const modalElement = document.getElementById('deleteUserModal');
  if (modalElement) {
    deleteModal.value = new Modal(modalElement);
  }
});
</script>

<style scoped>
.table-active {
  background-color: #e0f2f1 !important;
  font-weight: bold;
}
</style>