<template>
  <div class="card">
    <Toolbar class="mb-4">
      <template #start>
        <Button label="New" icon="pi pi-plus" class="mr-2" @click="openNew" />
        <Button label="Delete" icon="pi pi-trash" :disabled="!selectedEmployees.length" @click="confirmDeleteSelected" />
      </template>
    </Toolbar>

    <DataTable
      ref="dt"
      v-model:selection="selectedEmployees"
      :value="filteredEmployees"
      dataKey="id"
      selectionMode="multiple"
      :paginator="true"
      :rows="10"
      :loading="loading"
      :rowsPerPageOptions="[5,10,20]"
      currentPageReportTemplate="Showing {first} to {last} of {totalRecords} employees"
    >
      <template #header>
        <div class="flex justify-between items-center">
          <h4>Manage Employees</h4>
          <IconField>
            <InputIcon><i class="pi pi-search" /></InputIcon>
            <InputText v-model="filters['global'].value" placeholder="Search..." />
          </IconField>
        </div>
      </template>

      <Column selectionMode="multiple" headerStyle="width:3rem" />
      <Column field="name" header="Name" sortable />
      <Column field="email" header="Email" sortable />
      <Column field="phone" header="Phone" sortable />
      <Column field="address" header="Address" sortable />
      <Column field="companyName" header="Company" sortable />
      <Column field="status" header="Status" sortable>
        <template #body="slotProps">
          <Tag :value="slotProps.data.status" :severity="slotProps.data.status === 'ACTIVE' ? 'success' : 'danger'" />
        </template>
      </Column>
      <Column header="Actions">
        <template #body="slotProps">
          <Button icon="pi pi-pencil" class="mr-2" @click="editEmployee(slotProps.data)" />
          <Button icon="pi pi-trash" severity="danger" @click="confirmDelete(slotProps.data.id)" />
        </template>
      </Column>
    </DataTable>

    <Dialog v-model:visible="employeeDialog" :style="{ width: '450px' }" header="Employee Details" modal class="p-fluid">
      <div class="field mb-3">
        <label>Name</label>
        <InputText v-model="form.name" :invalid="submitted && !form.name" />
        <small v-if="submitted && !form.name" class="text-red-500">Name is required.</small>
      </div>
      <div class="field mb-3">
        <label>Email</label>
        <InputText v-model="form.email" :invalid="submitted && !form.email" />
        <small v-if="submitted && !form.email" class="text-red-500">Email is required.</small>
      </div>
      <div class="field mb-3">
        <label>Phone</label>
        <InputText v-model="form.phone" :invalid="submitted && !form.phone" />
        <small v-if="submitted && !form.phone" class="text-red-500">Phone is required.</small>
      </div>
      <div class="field mb-3">
        <label>Address</label>
        <InputText v-model="form.address" :invalid="submitted && !form.address" />
        <small v-if="submitted && !form.address" class="text-red-500">Address is required.</small>
      </div>
      <div class="field mb-3">
        <label>Company</label>
        <Dropdown v-model="form.companyId" :options="companies" optionLabel="name" optionValue="id" placeholder="Select Company" :invalid="submitted && !form.companyId" />
        <small v-if="submitted && !form.companyId" class="text-red-500">Company is required.</small>
      </div>
      <div class="field mb-3">
        <label>Status</label>
        <Dropdown v-model="form.status" :options="['ACTIVE','INACTIVE']" placeholder="Select Status" :invalid="submitted && !form.status" />
        <small v-if="submitted && !form.status" class="text-red-500">Status is required.</small>
      </div>
      <template #footer>
        <Button label="Cancel" icon="pi pi-times" @click="hideDialog" />
        <Button label="Submit" icon="pi pi-check" @click="submit" :loading="saving" />
      </template>
    </Dialog>
  </div>
</template>

<script setup>
import { ref, reactive, computed, onMounted } from 'vue';
import { useToast } from 'primevue/usetoast';
import { FilterMatchMode } from '@primevue/core/api';
import {
  getEmployees,
  getEmployee,
  createEmployee,
  updateEmployee,
  deleteEmployee,
  getCompanies
} from '@/service/api';

const toast = useToast();
const dt = ref();
const employees = ref([]);
const companies = ref([]);
const selectedEmployees = ref([]);
const employeeDialog = ref(false);
const form = reactive({ name: '', email: '', phone: '', address: '', status: '', companyId: null });
const filters = ref({ global: { value: null, matchMode: FilterMatchMode.CONTAINS } });

const loading = ref(false);
const saving = ref(false);
const submitted = ref(false);
const formMode = ref('create');
let editingId = null;

const filteredEmployees = computed(() => {
  const keyword = filters.value.global.value?.toLowerCase() || '';
  return employees.value.filter(e => e.name?.toLowerCase().includes(keyword));
});

const fetchEmployees = async () => {
  loading.value = true;
  try {
    const { data } = await getEmployees();
    employees.value = data;
  } finally {
    loading.value = false;
  }
};

const fetchCompanies = async () => {
  const { data } = await getCompanies();
  companies.value = data;
};

function openNew() {
  resetForm();
  formMode.value = 'create';
  employeeDialog.value = true;
}

function editEmployee(row) {
  getEmployee(row.id).then(({ data }) => {
    form.name = data.name;
    form.email = data.email;
    form.phone = data.phone;
    form.address = data.address;
    form.status = data.status;
    form.companyId = data.companyId;
    editingId = row.id;
    formMode.value = 'edit';
    employeeDialog.value = true;
  });
}

function resetForm() {
  form.name = '';
  form.email = '';
  form.phone = '';
  form.address = '';
  form.status = '';
  form.companyId = null;
  formMode.value = 'create';
  editingId = null;
}

function hideDialog() {
  employeeDialog.value = false;
  submitted.value = false;
  resetForm();
}

// ✅ NEW FUNCTION TO HANDLE SAVE
async function submit() {
  submitted.value = true;

  if (!form.name || !form.email || !form.phone || !form.status) return;

  saving.value = true;
  const payload = { ...form };

  try {
    if (formMode.value === 'edit') {
      await updateEmployee(editingId, payload);
      toast.add({ severity: 'success', summary: 'Updated', detail: 'Employee updated', life: 3000 });
    } else {
      await createEmployee(payload);
      toast.add({ severity: 'success', summary: 'Created', detail: 'Employee created', life: 3000 });
    }

    employeeDialog.value = false;
    await fetchEmployees();
    resetForm();
  } catch (e) {
    toast.add({ severity: 'error', summary: 'Error', detail: 'Failed to save employee', life: 3000 });
  } finally {
    saving.value = false;
  }
}



function confirmDelete(id) {
  deleteEmployee(id).then(() => {
    toast.add({ severity: 'success', summary: 'Deleted', detail: 'Employee deleted', life: 3000 });
    fetchEmployees();
  });
}

async function confirmDeleteSelected() {
  try {
    for (const emp of selectedEmployees.value) {
      await deleteEmployee(emp.id);
    }
    toast.add({ severity: 'success', summary: 'Deleted', detail: 'Employees deleted', life: 3000 });
    selectedEmployees.value = [];
    await fetchEmployees();
  } catch (e) {
    toast.add({ severity: 'error', summary: 'Error', detail: 'Failed to delete selected employees', life: 3000 });
  }
}

onMounted(async () => {
  await fetchEmployees();
  await fetchCompanies();
});
</script>

<style scoped></style>
