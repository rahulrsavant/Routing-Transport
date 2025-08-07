<template>
  <div class="card">
    <Toolbar class="mb-4">
      <template #start>
        <Button label="New" icon="pi pi-plus" severity="secondary" class="mr-2" @click="openNew" />
        <Button label="Delete" icon="pi pi-trash" severity="secondary" @click="confirmDeleteSelected" :disabled="!selectedDrivers.length" />
      </template>
      <template #end>
        <Button label="Export" icon="pi pi-upload" severity="secondary" @click="exportCSV" />
      </template>
    </Toolbar>

    <DataTable
      ref="dt"
      v-model:selection="selectedDrivers"
      :value="filteredDrivers"
      dataKey="id"
      :paginator="true"
      :rows="10"
      :loading="loading"
      paginatorTemplate="FirstPageLink PrevPageLink PageLinks NextPageLink LastPageLink CurrentPageReport RowsPerPageDropdown"
      :rowsPerPageOptions="[5, 10, 20]"
      currentPageReportTemplate="Showing {first} to {last} of {totalRecords} drivers"
    >
      <template #header>
        <div class="flex flex-wrap gap-2 items-center justify-between">
          <h4 class="m-0">Manage Drivers</h4>
          <IconField>
            <InputIcon><i class="pi pi-search" /></InputIcon>
            <InputText v-model="filters['global'].value" placeholder="Search..." />
          </IconField>
        </div>
      </template>

      <Column selectionMode="multiple" style="width: 3rem" :exportable="false" />
      <Column field="name" header="Name" sortable />
      <Column field="phone" header="Phone" sortable />
      <Column field="licenseNumber" header="License Number" sortable />
      <Column field="address" header="Address" sortable />
      <Column field="status" header="Status" sortable />
      <Column header="Assigned Vehicle">
        <template #body="slotProps">{{ slotProps.data.assignedVehicle?.registrationNumber }}</template>
      </Column>
      <Column :exportable="false" style="min-width:8rem">
        <template #body="slotProps">
          <Button icon="pi pi-pencil" outlined rounded class="mr-2" @click="editDriver(slotProps.data)" />
          <Button icon="pi pi-trash" outlined rounded severity="danger" @click="confirmDelete(slotProps.data.id)" />
        </template>
      </Column>
    </DataTable>

    <Dialog v-model:visible="driverDialog" :style="{ width: '850px' }" header="Driver Details" :modal="true" class="p-fluid">
      <div class="card flex flex-col gap-4">
        <div class="font-semibold text-xl text-primary mb-3">
          {{ formMode === 'edit' ? 'Edit' : 'Create' }} Driver
        </div>
        <div class="grid grid-cols-1 md:grid-cols-3 gap-4">
          <div class="flex flex-col gap-2">
            <label for="name">Name</label>
            <InputText id="name" v-model="form.name" :disabled="saving" required />
          </div>
          <div class="flex flex-col gap-2">
            <label for="phone">Phone</label>
            <InputText id="phone" v-model="form.phone" :disabled="saving" required />
          </div>
          <div class="flex flex-col gap-2">
            <label for="licenseNumber">License Number</label>
            <InputText id="licenseNumber" v-model="form.licenseNumber" :disabled="saving" required />
          </div>
          <div class="flex flex-col gap-2">
            <label for="address">Address</label>
            <InputText id="address" v-model="form.address" :disabled="saving" required />
          </div>
          <div class="flex flex-col gap-2">
            <label for="assignedVehicleId">Assigned Vehicle</label>
            <Dropdown id="assignedVehicleId" v-model="form.assignedVehicleId" :options="vehicles" optionLabel="registrationNumber" optionValue="id" placeholder="Select" :disabled="saving" />
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
    </Dialog>
  </div>
</template>

<script setup>
import { ref, reactive, computed, onMounted } from 'vue';
import { FilterMatchMode } from '@primevue/core/api';
import { useToast } from 'primevue/usetoast';
import {
  getDrivers,
  getDriver,
  createDriver,
  updateDriver,
  deleteDriver,
  deleteDrivers,
  getVehicles
} from '@/service/api';

const toast = useToast();

const drivers = ref([]);
const vehicles = ref([]);
const selectedDrivers = ref([]);
const dt = ref();
const filters = ref({
  global: { value: null, matchMode: FilterMatchMode.CONTAINS }
});

const form = reactive({
  name: '',
  phone: '',
  licenseNumber: '',
  address: '',
  assignedVehicleId: null,
  status: ''
});

const formMode = ref('create');
const saving = ref(false);
const loading = ref(false);
const driverDialog = ref(false);
const submitted = ref(false);
let editingId = null;

const filteredDrivers = computed(() => {
  const keyword = filters.value.global.value?.toLowerCase() || '';
  return drivers.value.filter(d =>
    d.name?.toLowerCase().includes(keyword) ||
    d.phone?.toLowerCase().includes(keyword) ||
    d.licenseNumber?.toLowerCase().includes(keyword) ||
    d.address?.toLowerCase().includes(keyword) ||
    d.status?.toLowerCase().includes(keyword) ||
    d.assignedVehicle?.registrationNumber?.toLowerCase().includes(keyword)
  );
});

function openNew() {
  resetForm();
  formMode.value = 'create';
  driverDialog.value = true;
}

function hideDialog() {
  driverDialog.value = false;
  submitted.value = false;
}

const fetchDrivers = async () => {
  loading.value = true;
  try {
    const { data } = await getDrivers();
    drivers.value = data;
  } catch (e) {
    toast.add({ severity: 'error', summary: 'Error', detail: 'Failed to load drivers', life: 3000 });
  } finally {
    loading.value = false;
  }
};

const fetchVehicles = async () => {
  try {
    const { data } = await getVehicles();
    vehicles.value = data;
  } catch (e) {
    toast.add({ severity: 'error', summary: 'Error', detail: 'Failed to load vehicles', life: 3000 });
  }
};

const editDriver = async (row) => {
  try {
    const { data } = await getDriver(row.id);
    form.name = data.name;
    form.phone = data.phone;
    form.licenseNumber = data.licenseNumber;
    form.address = data.address;
    form.status = data.status;
    form.assignedVehicleId = data.assignedVehicle?.id ?? data.assignedVehicleId;

    editingId = data.id;
    formMode.value = 'edit';
    driverDialog.value = true;
  } catch (e) {
    toast.add({ severity: 'error', summary: 'Error', detail: 'Failed to load driver', life: 3000 });
  }
};

const submit = async () => {
  saving.value = true;
  submitted.value = true;
  const payload = {
    name: form.name,
    phone: form.phone,
    licenseNumber: form.licenseNumber,
    address: form.address,
    assignedVehicleId: form.assignedVehicleId,
    status: form.status
  };

  try {
    if (formMode.value === 'edit') {
      await updateDriver(editingId, payload);
      toast.add({ severity: 'success', summary: 'Updated', detail: 'Driver updated', life: 3000 });
    } else {
      await createDriver(payload);
      toast.add({ severity: 'success', summary: 'Created', detail: 'Driver created', life: 3000 });
    }
    driverDialog.value = false;
    await fetchDrivers();
    resetForm();
  } catch (e) {
    const message = e.response?.data?.message || 'Failed to save driver.';
    toast.add({ severity: 'error', summary: 'Error', detail: message, life: 3000 });
  } finally {
    saving.value = false;
  }
};

const confirmDelete = async (id) => {
  try {
    await deleteDriver(id);
    toast.add({ severity: 'success', summary: 'Deleted', detail: 'Driver deleted successfully', life: 3000 });
    await fetchDrivers();
  } catch (e) {
    toast.add({ severity: 'error', summary: 'Error', detail: 'Failed to delete driver', life: 3000 });
  }
};

// const confirmDeleteSelected = async () => {
//   try {
//     const ids = selectedDrivers.value.map(d => d.id);
//     await deleteDrivers(ids);
//     toast.add({ severity: 'success', summary: 'Deleted', detail: 'Drivers deleted successfully', life: 3000 });
//     selectedDrivers.value = [];
//     await fetchDrivers();
//   } catch (e) {
//     const message = e.response?.data?.message || 'Failed to delete drivers';
//     toast.add({ severity: 'error', summary: 'Error', detail: message, life: 3000 });
//   }
// };

const confirmDeleteSelected = async () => {
  try {
    const ids = selectedDrivers.value.map(d => d.id);
    const response = await deleteDrivers(ids);

    const { message, deleted, locked } = response.data;

    toast.add({
      severity: locked.length ? 'warn' : 'success',
      summary: 'Deletion Result',
      detail: message,
      life: 4000,
    });

    selectedDrivers.value = [];
    await fetchDrivers();
  } catch (e) {
    const fallback = e.response?.data?.message || 'Failed to delete drivers';
    toast.add({ severity: 'error', summary: 'Error', detail: fallback, life: 4000 });
  }
};


function resetForm() {
  form.name = '';
  form.phone = '';
  form.licenseNumber = '';
  form.address = '';
  form.assignedVehicleId = null;
  form.status = '';
  formMode.value = 'create';
  editingId = null;
}

const exportCSV = () => {
  dt.value.exportCSV();
};

onMounted(async () => {
  await Promise.all([fetchDrivers(), fetchVehicles()]);
});
</script>

<style scoped></style>
