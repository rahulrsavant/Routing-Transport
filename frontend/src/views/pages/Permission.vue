<template>
  <div class="grid gap-4">
    <!-- FORM CARD -->
    <div class="card flex flex-col gap-4">
        <!-- <div class="font-semibold text-xl >Create Permission</div> -->
      <div class="font-semibold text-xl text-primary mb-3">{{ formMode === 'edit' ? 'Edit' : 'Create' }} Permission</div>
      <div class="flex flex-col gap-2">
        <label for="code">Code</label>
        <InputText id="code" v-model="form.code" :disabled="saving" required />
      </div>
      <div class="flex flex-col gap-2">
        <label for="description">Description</label>
        <Textarea id="description" v-model="form.description" rows="5" :disabled="saving" />
      </div>
      <div class="flex gap-2">
        <Button label="Submit" :loading="saving" @click="submit" />
        <Button label="Clear" severity="secondary" outlined @click="resetForm" :disabled="saving" />
      </div>
    </div>

<!-- LIST CARD -->
    <div class="card">
      <div class="font-semibold text-xl text-primary mb-3">Permission List</div>
          <DataTable
            :value="permissions"
            dataKey="id"
            :loading="loading"
            :paginator="true"
            :rows="5"
            :rowsPerPageOptions="[5, 10, 20, 50]"
          >
            <Column field="id" header="ID" style="width: 4rem" />
            <Column field="code" header="Code" />
            <Column field="description" header="Description" />
            <Column header="Actions" style="width: 10rem">
              <template #body="slotProps">
                <Button icon="pi pi-pencil" outlined rounded class="mr-2"
                  @click="editPermission(slotProps.data)" />
                <Button icon="pi pi-trash" outlined severity="danger" rounded
                  @click="confirmDelete(slotProps.data.id)" />
              </template>
            </Column>
          </DataTable>
    </div>
  </div>
</template>

<script setup>
import { createPermission, getPermission, getPermissions, updatePermission } from '@/service/api';
import { useToast } from 'primevue/usetoast';
import { onMounted, ref } from 'vue';
import { useRoute } from 'vue-router';

const route = useRoute();
const toast = useToast();

const form = ref({ code: '', description: '' });
const permissions = ref([]);
const formMode = ref('create');
const saving = ref(false);
const loading = ref(false);
let editingId = null;

const fetchPermissions = async () => {
  try {
    const { data } = await getPermissions();
    permissions.value = data;
  } catch (error) {
    toast.add({ severity: 'error', summary: 'Error', detail: 'Failed to load permissions', life: 3000 });
  }
};

const loadPermission = async (id) => {
  if (!id) return;
  try {
    loading.value = true;
    const { data } = await getPermission(id);
    form.value = { code: data.code, description: data.description };
    formMode.value = 'edit';
    editingId = id;
  } catch (error) {
    toast.add({ severity: 'error', summary: 'Error', detail: 'Failed to load permission', life: 3000 });
  } finally {
    loading.value = false;
  }
};

const submit = async () => {
  saving.value = true;
  try {
    if (formMode.value === 'edit') {
      await updatePermission(editingId, form.value);
      toast.add({ severity: 'success', summary: 'Updated', detail: 'Permission updated', life: 3000 });
    } else {
      await createPermission(form.value);
      toast.add({ severity: 'success', summary: 'Created', detail: 'Permission created', life: 3000 });
    }
    await fetchPermissions();
    resetForm();
  } catch (error) {
    let message = 'Failed to save permission.';
    if (error.response?.data?.message) {
      message = error.response.data.message;
    }
    toast.add({ severity: 'error', summary: 'Error', detail: message, life: 3000 });
  } finally {
    saving.value = false;
  }
};

const editPermission = (perm) => {
  loadPermission(perm.id);
};

import { deletePermission } from '@/service/api'; // ✅ Make sure this line exists

const confirmDelete = async (id) => {
  try {
    await deletePermission(id);
    toast.add({
      severity: 'success',
      summary: 'Deleted',
      detail: 'Permission deleted successfully',
      life: 3000
    });
    await fetchPermissions();
  } catch (e) {
    toast.add({
      severity: 'error',
      summary: 'Error',
      detail: 'Failed to delete permission',
      life: 3000
    });
  }
};


const resetForm = () => {
  form.value = { code: '', description: '' };
  formMode.value = 'create';
  editingId = null;
};

onMounted(async () => {
  await fetchPermissions();
  if (route.params.id) {
    await loadPermission(route.params.id);
  }
});
</script>

<style scoped></style>
