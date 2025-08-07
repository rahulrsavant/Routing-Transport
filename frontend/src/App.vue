<script setup>
import { onMounted, onBeforeUnmount } from 'vue';
import { useToast } from 'primevue/usetoast';
import Toast from 'primevue/toast';

const toast = useToast();

function handleError(event) {
  toast.add({
    severity: 'error',
    summary: 'Error',
    detail: event.detail,
    life: 3000
  });
}

onMounted(() => {
  window.addEventListener('api-error', handleError);
});

onBeforeUnmount(() => {
  window.removeEventListener('api-error', handleError);
});
</script>

<template>
  <Toast />
  <router-view />
</template>

<style scoped></style>
