<template>
  <div class="grid gap-4">
    <!-- FORM CARD -->
    <div class="card">
      <div class="font-semibold text-xl text-primary mb-3">
        {{ formMode === 'edit' ? 'Edit' : 'Create' }} Owner
      </div>

      <div class="grid grid-cols-1 md:grid-cols-3 lg:grid-cols-3 gap-4">
        <div class="flex flex-col gap-2">
          <label for="name">Name</label>
          <InputText id="name" v-model="form.name" :disabled="saving" required />
        </div>

        <div class="flex flex-col gap-2">
          <label for="contactEmail">Contact Email</label>
          <InputText id="contactEmail" v-model="form.contactEmail" :disabled="saving" required />
        </div>

        <div class="flex flex-col gap-2">
          <label for="contactPhone">Contact Phone</label>
          <InputText id="contactPhone" v-model="form.contactPhone" :disabled="saving" required />
        </div>

        <div class="flex flex-col gap-2">
          <label for="address">Address</label>
          <InputText id="address" v-model="form.address" :disabled="saving" required />
        </div>

        <div class="flex flex-col gap-2">
          <label for="status">Status</label>
          <InputText id="status" v-model="form.status" :disabled="saving" required />
        </div>
      </div>

      <div class="flex gap-2 mt-4">
        <Button label="Submit" :loading="saving" @click="submit" />
        <Button label="Clear" severity="secondary" outlined @click="resetForm" :disabled="saving" />
      </div>

    </div>

    <!-- LIST CARD -->
    <div class="card">
      <div class="font-semibold text-xl text-primary mb-3">Owner List</div>
      <DataTable
        :value="owners"
        dataKey="id"
        :loading="loading"
        :paginator="true"
        :rows="5"
        :rowsPerPageOptions="[5, 10, 20, 50]"
      >
        <Column field="id" header="ID" style="width: 4rem" />
        <Column field="name" header="Name" />
        <Column field="contactEmail" header="Email" />
        <Column field="contactPhone" header="Phone" />
        <Column field="address" header="Address" />
        <Column field="status" header="Status" />
        <Column header="Actions" style="width: 10rem">
          <template #body="slotProps">
            <Button
              icon="pi pi-pencil"
              outlined
              rounded
              class="mr-2"
              @click="editOwner(slotProps.data)"
            />
            <Button
              icon="pi pi-trash"
              outlined
              severity="danger"
              rounded
              @click="confirmDelete(slotProps.data.id)"
            />
          </template>
        </Column>
      </DataTable>
    </div>
  </div>
</template>

<script setup>
import { createOwner, deleteOwner, getOwners, getOwner, updateOwner } from '@/service/api';
import { useToast } from 'primevue/usetoast';
import { onMounted, ref } from 'vue';
import { useRoute } from 'vue-router';

const route = useRoute();
const toast = useToast();

const form = ref({
  name: '',
  contactEmail: '',
  contactPhone: '',
  address: '',
  status: ''
});

const owners = ref([]);
const formMode = ref('create');
const saving = ref(false);
const loading = ref(false);
let editingId = null;

const fetchOwners = async () => {
  try {
    const { data } = await getOwners();
    owners.value = data;
  } catch (error) {
    toast.add({ severity: 'error', summary: 'Error', detail: 'Failed to load owners', life: 3000 });
  }
};

const loadOwner = async (id) => {
  if (!id) return;
  try {
    loading.value = true;
    const { data } = await getOwner(id);
    form.value = {
      name: data.name,
      contactEmail: data.contactEmail,
      contactPhone: data.contactPhone,
      address: data.address,
      status: data.status
    };
    formMode.value = 'edit';
    editingId = id;
  } catch (error) {
    toast.add({ severity: 'error', summary: 'Error', detail: 'Failed to load owner', life: 3000 });
  } finally {
    loading.value = false;
  }
};

const submit = async () => {
  saving.value = true;
  try {
    const payload = { ...form.value };
    if (formMode.value === 'edit') {
      await updateOwner(editingId, payload);
      toast.add({ severity: 'success', summary: 'Updated', detail: 'Owner updated', life: 3000 });
    } else {
      await createOwner(payload);
      toast.add({ severity: 'success', summary: 'Created', detail: 'Owner created', life: 3000 });
    }
    await fetchOwners();
    resetForm();
  } catch (error) {
    let message = 'Failed to save owner.';
    if (error.response?.data?.message) {
      message = error.response.data.message;
    }
    toast.add({ severity: 'error', summary: 'Error', detail: message, life: 3000 });
  } finally {
    saving.value = false;
  }
};

const editOwner = (owner) => {
  loadOwner(owner.id);
};

const confirmDelete = async (id) => {
  try {
    await deleteOwner(id);
    toast.add({ severity: 'success', summary: 'Deleted', detail: 'Owner deleted successfully', life: 3000 });
    await fetchOwners();
  } catch (e) {
    toast.add({ severity: 'error', summary: 'Error', detail: 'Failed to delete owner', life: 3000 });
  }
};

const resetForm = () => {
  form.value = {
    name: '',
    contactEmail: '',
    contactPhone: '',
    address: '',
    status: ''
  };
  formMode.value = 'create';
  editingId = null;
};

onMounted(async () => {
  await fetchOwners();
  if (route.params.id) {
    await loadOwner(route.params.id);
  }
});
</script>
