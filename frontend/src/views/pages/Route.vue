<template>
  <div class="card">
    <Toolbar class="mb-4">
      <template #start>
        <Button label="New" icon="pi pi-plus" severity="secondary" class="mr-2" @click="openNew" />
        <Button label="Delete" icon="pi pi-trash" severity="secondary" :disabled="!selectedRoutes.length" @click="confirmDeleteSelected" />
      </template>
      <template #end>
        <Button label="Export" icon="pi pi-upload" severity="secondary" @click="exportCSV" />
      </template>
    </Toolbar>

    <DataTable
      ref="dt"
      v-model:selection="selectedRoutes"
      :value="filteredRoutes"
      dataKey="id"
      :paginator="true"
      :rows="10"
      :loading="loading"
      paginatorTemplate="FirstPageLink PrevPageLink PageLinks NextPageLink LastPageLink CurrentPageReport RowsPerPageDropdown"
      :rowsPerPageOptions="[5, 10, 20]"
      currentPageReportTemplate="Showing {first} to {last} of {totalRecords} routes"
    >
      <template #header>
        <div class="flex flex-wrap gap-2 items-center justify-between">
          <h4 class="m-0">Manage Routes</h4>
          <IconField>
            <InputIcon><i class="pi pi-search" /></InputIcon>
            <InputText v-model="filters['global'].value" placeholder="Search..." />
          </IconField>
        </div>
      </template>

      <Column selectionMode="multiple" style="width: 3rem" :exportable="false" />
      <Column field="name" header="Name" sortable />
      <Column field="startPoint" header="Start" sortable />
      <Column field="endPoint" header="End" sortable />
      <Column field="estimatedTime" header="ETA" sortable />
      <Column field="status" header="Status" sortable />
      
      <Column header="Stops">
        <template #body="slotProps">
          {{ Array.isArray(slotProps.data.stops) ? slotProps.data.stops.join(', ') : '' }}
        </template>
      </Column>

      <Column header="Contract">
        <template #body="slotProps">
          {{ slotProps.data.contract?.name || 'N/A' }}
        </template>
      </Column>

      <Column :exportable="false" style="min-width:8rem">
        <template #body="slotProps">
          <Button icon="pi pi-pencil" outlined rounded class="mr-2" @click="editRoute(slotProps.data)" />
          <Button icon="pi pi-trash" outlined rounded severity="danger" @click="confirmDelete(slotProps.data.id)" />
        </template>
      </Column>
    </DataTable>

    <Dialog v-model:visible="routeDialog" :style="{ width: '850px' }" header="Route Details" :modal="true" class="p-fluid">
      <div class="card flex flex-col gap-4">
        <div class="font-semibold text-xl text-primary mb-3">{{ formMode === 'edit' ? 'Edit' : 'Create' }} Route</div>
        <div class="grid grid-cols-1 md:grid-cols-3 gap-4">
          <div class="flex flex-col gap-2">
            <label for="name">Name</label>
            <InputText id="name" v-model="form.name" :disabled="saving" required />
          </div>
          <div class="flex flex-col gap-2">
            <label for="startPoint">Start Point</label>
            <InputText id="startPoint" v-model="form.startPoint" :disabled="saving" required />
          </div>
          <div class="flex flex-col gap-2">
            <label for="endPoint">End Point</label>
            <InputText id="endPoint" v-model="form.endPoint" :disabled="saving" required />
          </div>
          <div class="flex flex-col gap-2">
            <label for="estimatedTime">Estimated Time</label>
            <InputText id="estimatedTime" v-model="form.estimatedTime" :disabled="saving" />
          </div>
          <div class="flex flex-col gap-2">
            <label for="stops">Stops (comma separated)</label>
            <InputText id="stops" v-model="stopsText" :disabled="saving" />
          </div>
          <div class="flex flex-col gap-2">
            <label for="status">Status</label>
            <Dropdown
              id="status"
              v-model="form.status"
              :options="['ACTIVE', 'INACTIVE']"
              placeholder="Select Status"
              :disabled="saving"
            />
          </div>
          <div class="flex flex-col gap-2">
            <label for="contractId">Contract ID</label>
            <InputText id="contractId" v-model="form.contractId" :disabled="saving" />
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
import { useToast } from 'primevue/usetoast';
import { FilterMatchMode } from '@primevue/core/api';
import {
  getRoutes,
  getRoute,
  createRoute,
  updateRoute,
  deleteRoute,
  deleteRoutes
} from '@/service/api'; // Create these functions as needed

const toast = useToast();
const routes = ref([]);
const selectedRoutes = ref([]);
const dt = ref();
const filters = ref({
  global: { value: null, matchMode: FilterMatchMode.CONTAINS }
});
const loading = ref(false);
const saving = ref(false);
const formMode = ref('create');
const routeDialog = ref(false);
let editingId = null;

const form = reactive({
  name: '',
  startPoint: '',
  endPoint: '',
  estimatedTime: '',
  stops: [],
  status: '',
  contractId: ''
});
const stopsText = ref('');

const filteredRoutes = computed(() => {
  const keyword = filters.value.global.value?.toLowerCase() || '';
  return routes.value.filter(r =>
    r.name?.toLowerCase().includes(keyword) ||
    r.startPoint?.toLowerCase().includes(keyword) ||
    r.endPoint?.toLowerCase().includes(keyword) ||
    r.status?.toLowerCase().includes(keyword) ||
    r.stops?.join(', ')?.toLowerCase().includes(keyword)
  );
});

function openNew() {
  resetForm();
  routeDialog.value = true;
}

function resetForm() {
  Object.assign(form, {
    name: '',
    startPoint: '',
    endPoint: '',
    estimatedTime: '',
    stops: [],
    status: '',
    contractId: ''
  });
  stopsText.value = '';
  formMode.value = 'create';
  editingId = null;
}

const fetchRoutes = async () => {
  loading.value = true;
  try {
    const { data } = await getRoutes();

                    routes.value = (Array.isArray(data) ? data : data?.data || []).map(route => {
                  // Ensure fallback if only contractId is present
                  if (!route.contract && route.contractId) {
                    route.contract = { id: route.contractId, name: 'N/A' };
                  }
                  return route;
                });

    // if response is wrapped like { data: [...] }
    routes.value = Array.isArray(data) ? data : data?.data || [];
  } catch (e) {
    toast.add({ severity: 'error', summary: 'Error', detail: 'Failed to load routes', life: 3000 });
  } finally {
    loading.value = false;
  }
};


const editRoute = async (route) => {
  try {
    const { data } = await getRoute(route.id);
    Object.assign(form, data);
    stopsText.value = data.stops?.join(', ') || '';
    editingId = data.id;
    formMode.value = 'edit';
    routeDialog.value = true;
  } catch (e) {
    toast.add({ severity: 'error', summary: 'Error', detail: 'Failed to load route', life: 3000 });
  }
};

const submit = async () => {
  saving.value = true;
  const payload = {
    ...form,
    stops: stopsText.value.split(',').map(s => s.trim()).filter(Boolean)
  };
  try {
    if (formMode.value === 'edit') {
      await updateRoute(editingId, payload);
      toast.add({ severity: 'success', summary: 'Updated', detail: 'Route updated', life: 3000 });
    } else {
      await createRoute(payload);
      toast.add({ severity: 'success', summary: 'Created', detail: 'Route created', life: 3000 });
    }
    routeDialog.value = false;
    await fetchRoutes();
    resetForm();
  } catch (e) {
    toast.add({ severity: 'error', summary: 'Error', detail: 'Failed to save route', life: 3000 });
  } finally {
    saving.value = false;
  }
};

const confirmDelete = async (id) => {
  try {
    await deleteRoute(id);
    toast.add({ severity: 'success', summary: 'Deleted', detail: 'Route deleted', life: 3000 });
    await fetchRoutes();
  } catch (e) {
    toast.add({ severity: 'error', summary: 'Error', detail: 'Delete failed', life: 3000 });
  }
};

const confirmDeleteSelected = async () => {
  try {
    const ids = selectedRoutes.value.map(r => r.id);
    await deleteRoutes(ids);
    toast.add({ severity: 'success', summary: 'Deleted', detail: 'Routes deleted', life: 3000 });
    selectedRoutes.value = [];
    await fetchRoutes();
  } catch (e) {
    toast.add({ severity: 'error', summary: 'Error', detail: 'Bulk delete failed', life: 3000 });
  }
};

const exportCSV = () => {
  dt.value.exportCSV();
};

onMounted(fetchRoutes);
</script>

<style scoped></style>
