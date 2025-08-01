<template>
  <div class="validation-error-list">
    <div v-for="(message, field) in validationErrors" :key="field">
      <u>{{ field }}:</u> {{ message }}
    </div>
  </div>
</template>

<script setup>
defineProps({
  validationErrors: {
    type: Object,
    required: true
  }
});
</script>

<style scoped>
.validation-error-list {
  text-align: left;
}
</style>