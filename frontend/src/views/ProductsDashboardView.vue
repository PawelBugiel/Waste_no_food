<template>
  <div class="container mt-4">
    <div class="d-flex justify-content-between align-items-center mb-4">
      <div>
        <router-link v-if="authStore.role === 'ADMIN'" to="/users"
                     class="btn btn-info btn-compact btn-sm d-inline-flex align-items-center">
          <img src="@/assets/images/users_logo.webp" alt="" class="btn-icon-sm">
          <span>Manage users</span>
        </router-link>
      </div>
      <div>
        <button @click="logout" class="btn btn-info btn-sm btn-compact btn-logout">Logout</button>
      </div>
    </div>

    <h2 class="mb-4 d-flex align-items-center justify-content-center">
      <img src="@/assets/images/products_logo.webp" alt="Products icon" class="heading-icon">
      <span>Product Dashboard</span>
    </h2>

    <form @submit.prevent="updateProduct()" class="mb-4">
      <div class="row g-3">
        <div class="col-md-4 text-start">
          <label for="product-name" class="form-label">Product name:</label>
          <input v-model="newProduct.name" type="text" class="form-control form-control-sm" id="product-name" required/>
        </div>
        <div class="col-md-4 text-start">
          <label for="product-quantity" class="form-label">Quantity:</label>
          <input v-model.number="newProduct.quantity" type="number" class="form-control form-control-sm"
                 id="product-quantity" required/>
        </div>
        <div class="col-md-4 text-start">
          <label for="product-expiry" class="form-label">Expiry date:</label>
          <input v-model="newProduct.expiryDate" type="date" class="form-control form-control-sm" id="product-expiry"
                 required/>
        </div>

        <div class="col-md-4 text-start">
          <label for="search-by-name" class="form-label">Search by name:</label>
          <input v-model="searchQuery" type="text" id="search-by-name" class="form-control form-control-sm"
                 @input="fetchProducts" :disabled="isEditMode"/>
        </div>

        <div class="col-md-8 text-start align-self-end">
          <div v-if="isEditMode">
            <button type="submit" class="btn btn-custom-edit-update btn-sm">Update</button>
            <button @click="cancelEdit" type="button" class="btn btn-secondary btn-sm ms-2">Cancel</button>
          </div>
        </div>
      </div>

      <div class="error-container">
        <p v-if="addProductError" class="text-danger mt-2 mb-0" v-html="addProductError"></p>
      </div>

    </form>


    <div class="mb-3" style="max-width: 750px;">
      <div class="row g-3">
        <div class="col">
          <button :disabled="isEditMode || selectedProduct" @click="addProduct"
                  class="btn btn-sm btn-custom-add w-100 h-100">
            Add new product
          </button>
        </div>
        <div class="col">
          <button @click="editSelectedProduct()" :disabled="!selectedProduct"
                  class="btn btn-sm btn-custom-edit-update w-100 h-100">
            Edit selected product
          </button>
        </div>
        <div class="col">
          <button @click="showDeleteModal(selectedProduct)" :disabled="!selectedProduct || isEditMode"
                  class="btn btn-sm btn-custom-delete w-100 h-100">
            Delete selected product
          </button>
        </div>
      </div>
    </div>

    <table class="table table-striped">
      <thead>
      <tr>
        <th style="width: 50px;">No.</th>
        <th style="width: 50px;">Select</th>
        <th><a href="#" @click.prevent="sort('name')">Name</a><span
            v-if="sortBy === 'name'">{{ sortDirection === 'asc' ? '↑' : '↓' }}</span></th>
        <th><a href="#" @click.prevent="sort('quantity')">Quantity</a><span
            v-if="sortBy === 'quantity'">{{ sortDirection === 'asc' ? '↑' : '↓' }}</span></th>
        <th><a href="#" @click.prevent="sort('expiryDate')">Expiry date</a><span
            v-if="sortBy === 'expiryDate'">{{ sortDirection === 'asc' ? '↑' : '↓' }}</span></th>
        <th><a href="#" @click.prevent="sort('expiryDate')">Days to expiry</a><span
            v-if="sortBy === 'expiryDate'">{{ sortDirection === 'asc' ? '↑' : '↓' }}</span></th>
      </tr>
      </thead>
      <tbody :class="{ 'locked-for-edit': isEditMode }">
      <tr v-for="(product, index) in productsWithDaysLeft"
          :key="product.id"
          @click="selectProduct(product)"
          :class="{
            'table-active': selectedProduct && selectedProduct.id === product.id,
            'row-expired': product.daysToExpire < 0,
            'row-expiring-soon': product.daysToExpire >= 0 && product.daysToExpire <= 3
          }"
          :style="{ cursor: isEditMode ? 'not-allowed' : 'pointer' }">

        <td>{{ (currentPage * pageSize) + index + 1 }}</td>
        <td><input type="radio" :value="product.id" v-model="selectedProductId" @click.stop="selectProduct(product)"
                   :disabled="isEditMode">
        </td>
        <td>{{ product.name }}</td>
        <td>{{ product.quantity }}</td>
        <td>{{ formatDate(product.expiryDate) }}</td>
        <td>
          <span v-if="product.daysToExpire > 1">{{ product.daysToExpire }} days</span>
          <span v-else-if="product.daysToExpire === 1">1 day</span>
          <span v-else-if="product.daysToExpire === 0">Expires today</span>
          <span v-else>Expired</span>
        </td>
      </tr>
      </tbody>
    </table>
    <div class="d-flex justify-content-between align-items-center mb-4" v-if="totalPages > 0">
      <button @click="prevPage" :disabled="currentPage === 0" class="btn btn-info btn-compact btn-previous-page">
        Previous
      </button>
      <span>Page {{ currentPage + 1 }} of {{ totalPages }}</span>
      <button @click="nextPage" :disabled="currentPage >= totalPages - 1"
              class="btn btn-info btn-compact btn-next-page">Next
      </button>
    </div>

    <div class="modal fade modal-custom" id="deleteModal" tabindex="-1" aria-labelledby="deleteModalLabel" aria-hidden="true">
      <div class="modal-dialog modal-dialog-centered modal-sm">
        <div class="modal-content">
          <div class="modal-header">
            <h5 class="modal-title" id="deleteModalLabel">Confirm Deletion</h5>
            <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
          </div>
          <div class="modal-body">
            Are you sure you want to delete product:
            <div class="my-2">
              <strong>{{ productToDelete?.name }}</strong>
            </div>
          </div>
          <div class="modal-footer">
            <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Cancel</button>
            <button type="button" class="btn btn-custom-delete" @click="deleteProduct">Delete</button>
          </div>
        </div>
      </div>
    </div>

    <div class="modal fade modal-custom" id="updateQuantityModal" tabindex="-1" aria-hidden="true">
      <div class="modal-dialog modal-dialog-centered modal-sm">
        <div class="modal-content">
          <div class="modal-header">
            <h5 class="modal-title">Confirm Quantity Update</h5>
            <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
          </div>
          <div class="modal-body">
            Product <strong>{{ productToUpdateQuantity?.name }}</strong> already exists.
            <div class="my-2">
              Do you want to add <strong>{{ quantityToAdd }}</strong> to the current stock?
            </div>
          </div>
          <div class="modal-footer">
            <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">No</button>
            <button type="button" class="btn btn-custom-edit-update" @click="confirmAddQuantity">Yes, add quantity</button>
          </div>
        </div>
      </div>
    </div>

  </div>
</template>

<script setup>
import {ref, computed, watch, onMounted} from 'vue';
import axios from '../axios';
import {Modal} from 'bootstrap';
import {useAuthStore} from '@/stores/authStore';
import {useRouter} from 'vue-router';
import { useToast } from "vue-toastification";

import ValidationErrorToast from "@/components/ValidationErrorToast.vue";

// --- Stan komponentu ---
const products = ref([]);
const newProduct = ref({name: '', quantity: null, expiryDate: ''});
const isEditMode = ref(false);
const currentProduct = ref({id: '', name: '', quantity: null, expiryDate: ''});
const productToDelete = ref(null);
const deleteModal = ref(null);
const searchQuery = ref('');
const selectedProduct = ref(null);
const selectedProductId = ref(null);
const productToUpdateQuantity = ref(null);
const quantityToAdd = ref(null);
const updateQuantityModal = ref(null);

// --- Stan paginacji i sortowania ---
const currentPage = ref(0);
const totalPages = ref(0);
const pageSize = ref(10);
const sortBy = ref('expiryDate');
const sortDirection = ref('asc');

const authStore = useAuthStore();
const router = useRouter();
const toast = useToast();

// --- Właściwości obliczeniowe ---
const productsWithDaysLeft = computed(() => {
  return products.value.map(product => {
    const today = new Date();
    today.setHours(0, 0, 0, 0);
    const expirationDate = new Date(product.expiryDate);
    expirationDate.setHours(0, 0, 0, 0);
    const diffTime = expirationDate.getTime() - today.getTime();
    const diffDays = Math.ceil(diffTime / (1000 * 60 * 60 * 24));
    return {...product, daysToExpire: diffDays};
  });
});

// --- Obserwatory ---
watch([currentPage, sortBy, sortDirection], () => fetchProducts());

watch(selectedProductId, (newId) => {
  if (newId === null) {
    selectedProduct.value = null;
  } else {
    selectedProduct.value = products.value.find(p => p.id === newId);
  }
});

watch(searchQuery, (newValue, oldValue) => {
  if (newValue !== oldValue) {
    currentPage.value = 0;
  }
});

// --- Metody ---
const formatDate = (dateString) => {
  if (!dateString) return '';
  const [year, month, day] = dateString.split('-');
  return `${day}.${month}.${year}`;
};

const fetchProducts = async () => {
  try {
    const params = {
      page: currentPage.value,
      size: pageSize.value,
      sort: `${sortBy.value},${sortDirection.value}`
    };
    if (searchQuery.value) {
      params.partialName = searchQuery.value;
    }
    const response = await axios.get(searchQuery.value ? '/products/search' : '/products', {
      params,
      headers: {Authorization: `Bearer ${authStore.token}`}
    });
    products.value = response.data.content;
    totalPages.value = response.data.totalPages;
    if (!isEditMode.value) {
      selectedProduct.value = null;
      selectedProductId.value = null;
    }
  } catch (err) {
    const errorMessage = err.response?.data?.exceptionMessage || 'Failed to load products. Check if the server is running.';
    toast.error(errorMessage, { timeout: 7000 });
    totalPages.value = 0;
    products.value = [];
  }
};

const addProduct = async () => {
  try {
    await axios.post('/products', newProduct.value, { headers: { Authorization: `Bearer ${authStore.token}` } });

    toast.success(`Product "${newProduct.value.name}" added successfully!`, { timeout: 3000 });

    newProduct.value = { name: '', quantity: null, expiryDate: '' };
    currentPage.value = 0;
    await fetchProducts();
  } catch (err) {
    if (err.response && err.response.status === 409) {
      try {
        const existingProductResponse = await axios.get('/products/search', {
          params: { partialName: newProduct.value.name, size: 100 },
          headers: { Authorization: `Bearer ${authStore.token}` }
        });
        const existingProduct = existingProductResponse.data.content.find(p => p.name === newProduct.value.name && p.expiryDate === newProduct.value.expiryDate);

        if (existingProduct) {
          productToUpdateQuantity.value = existingProduct;
          quantityToAdd.value = newProduct.value.quantity;
          updateQuantityModal.value.show();
        } else {
          toast.error('A conflict occurred, but the existing product could not be found.', { timeout: 7000 });
        }
      } catch (searchErr) {
        toast.error('A conflict occurred, but failed to search for the existing product.', { timeout: 7000 });
      }
    } else if (err.response && err.response.data) {
      const errorData = err.response.data;
      if (errorData.validationErrors) {
        toast.error({
          component: ValidationErrorToast,
          props: { validationErrors: errorData.validationErrors }
        }, { timeout: 7000 });
      } else {
        toast.error(errorData.exceptionMessage || 'An unexpected error occurred.', { timeout: 7000 });
      }
    } else {
      toast.error('Could not connect to the server.', { timeout: 7000 });
    }
  }
};

const confirmAddQuantity = async () => {
  updateQuantityModal.value.hide();
  try {
    const requestBody = { quantityToAdd: quantityToAdd.value };
    await axios.patch(`/products/${productToUpdateQuantity.value.id}/quantity`, requestBody, {
      headers: { Authorization: `Bearer ${authStore.token}` }
    });
    toast.success(`Quantity for "${productToUpdateQuantity.value.name}" updated successfully!`);
    newProduct.value = { name: '', quantity: null, expiryDate: '' };
    await fetchProducts();
  } catch (err) {
    const errorMessage = err.response?.data?.exceptionMessage || 'An unexpected error occurred while updating quantity.';
    toast.error(errorMessage);
  }
};

const updateProduct = async () => {
  try {
    const productIdToUpdate = currentProduct.value.id;
    await axios.put(`/products/${productIdToUpdate}`, newProduct.value, {headers: {Authorization: `Bearer ${authStore.token}`}});

    toast.success(`Product "${newProduct.value.name}" updated successfully!`, { timeout: 3000 });

    isEditMode.value = false;
    currentProduct.value = {id: '', name: '', quantity: null, expiryDate: ''};
    newProduct.value = {name: '', quantity: null, expiryDate: ''};
    await fetchProducts();
  } catch (err) {
    if (err.response && err.response.data) {
      const errorData = err.response.data;
      if (errorData.validationErrors) {
        toast.error({
          component: ValidationErrorToast,
          props: { validationErrors: errorData.validationErrors }
        }, { timeout: 7000 });
      } else {
        toast.error(errorData.exceptionMessage || 'An unexpected error occurred while updating the product.', { timeout: 7000 });
      }
    } else {
      toast.error('Could not connect to the server.', { timeout: 7000 });
    }
  }
};

const editSelectedProduct = () => {
  if (selectedProduct.value) {
    isEditMode.value = true;
    currentProduct.value = {...selectedProduct.value};
    newProduct.value = {...selectedProduct.value};
  }
};

const cancelEdit = () => {
  isEditMode.value = false;
  currentProduct.value = {id: '', name: '', quantity: null, expiryDate: ''};
  newProduct.value = {name: '', quantity: null, expiryDate: ''};
  selectedProduct.value = null;
  selectedProductId.value = null;
};

const showDeleteModal = (product) => {
  if (product) {
    productToDelete.value = product;
    deleteModal.value.show();
  }
};

const deleteProduct = async () => {
  try {
    const productName = productToDelete.value.name;
    await axios.delete(`/products/${productToDelete.value.id}`, {headers: {Authorization: `Bearer ${authStore.token}`}});

    toast.success(`Product "${productName}" deleted successfully!`, { timeout: 3000 });

    deleteModal.value.hide();
    productToDelete.value = null;
    selectedProduct.value = null;
    selectedProductId.value = null;
    if (products.value.length === 1 && currentPage.value > 0) {
      currentPage.value--;
    } else {
      await fetchProducts();
    }
  } catch (err) {
    const errorMessage = err.response?.data?.exceptionMessage || 'An unexpected error occurred while deleting the product.';
    toast.error(errorMessage, { timeout: 7000 });
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

const logout = () => {
  authStore.clearAuth();
  router.push('/');
};

const selectProduct = (product) => {
  if (isEditMode.value) return;

  if (selectedProduct.value && selectedProduct.value.id === product.id) {
    selectedProduct.value = null;
    selectedProductId.value = null;
  } else {
    selectedProduct.value = product;
    selectedProductId.value = product.id;
  }
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

onMounted(() => {
  fetchProducts();

  const deleteModalElement = document.getElementById('deleteModal');
  if (deleteModalElement) {
    deleteModal.value = new Modal(deleteModalElement);
  }

  const updateQuantityModalElement = document.getElementById('updateQuantityModal');
  if (updateQuantityModalElement) {
    updateQuantityModal.value = new Modal(updateQuantityModalElement);
  }
});
</script>

<style scoped>
.table-active {
  background-color: #e0f2f1 !important;
  font-weight: bold;
}

.row-expired td {
  color: #dc3545;
}

.row-expiring-soon td {
  color: #BC5A0E;
  font-weight: bold;
}

.locked-for-edit tr {
  opacity: 0.6;
}

.locked-for-edit tr.table-active {
  opacity: 1;
}
</style>