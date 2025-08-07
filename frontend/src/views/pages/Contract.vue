<template>
  <div class="card">
    <Toolbar class="mb-4">
      <template #start>
        <Button label="New" icon="pi pi-plus" severity="secondary" class="mr-2" @click="openNew" />
      </template>
    </Toolbar>

<DataTable
  :value="contracts"
  v-model:selection="selectedContracts"
  dataKey="id"
  selectionMode="multiple"
  paginator
  :rows="10"
  :loading="loading"
  tableStyle="min-width: 75rem"
>
  <Column selectionMode="multiple" headerStyle="width: 3rem" />
  <Column field="id" header="ID" sortable />
  <Column field="companyName" header="Company" sortable />
  <Column field="ownerName" header="Owner" sortable />
  <Column field="vehicleNumber" header="Vehicle" sortable />
  <Column field="contractType" header="Type" sortable />
  <Column field="amount" header="Amount" sortable />
  <Column field="startDate" header="Start Date" sortable />
  <Column field="endDate" header="End Date" sortable />
  <Column header="Status">
    <template #body="slotProps">
      <Tag :value="slotProps.data.status" :severity="slotProps.data.status === 'ACTIVE' ? 'success' : 'warning'" />
    </template>
  </Column>
  <Column header="Actions" :exportable="false" style="min-width:8rem">
    <template #body="slotProps">
      <Button icon="pi pi-pencil" severity="secondary" rounded class="mr-2" @click="editContract(slotProps.data)" />
      <Button icon="pi pi-trash" severity="danger" rounded @click="confirmDeleteContract(slotProps.data)" />
    </template>
  </Column>
</DataTable>


    <Dialog v-model:visible="contractDialog" :style="{ width: '600px' }" header="Contract Details" modal>
      <div class="field mb-3">
        <label for="company">Company</label>
        <Dropdown v-model="contract.companyId" :options="companies" optionLabel="name" optionValue="id" placeholder="Select Company" />
      </div>
      <div class="field mb-3">
        <label for="owner">Owner</label>
        <Dropdown v-model="contract.ownerId" :options="owners" optionLabel="name" optionValue="id" placeholder="Select Owner" />
      </div>
      <div class="field mb-3">
        <label for="vehicle">Vehicle</label>
        <Dropdown v-model="contract.vehicleId" :options="vehicles" optionLabel="registrationNumber" optionValue="id" placeholder="Select Vehicle" />
      </div>
      <div class="field mb-3">
        <label for="contractType">Contract Type</label>
        <InputText v-model="contract.contractType" />
      </div>
      <div class="field mb-3">
        <label for="amount">Amount</label>
        <InputNumber v-model="contract.amount" mode="currency" currency="INR" locale="en-IN" />
      </div>
      <div class="field mb-3">
        <label for="startDate">Start Date</label>
        <Calendar v-model="contract.startDate" dateFormat="yy-mm-dd" />
      </div>
      <div class="field mb-3">
        <label for="endDate">End Date</label>
        <Calendar v-model="contract.endDate" dateFormat="yy-mm-dd" />
      </div>
      <div class="field">
        <label for="status">Status</label>
        <Dropdown v-model="contract.status" :options="['ACTIVE', 'INACTIVE']" placeholder="Select Status" />
      </div>

      <template #footer>
        <Button label="Cancel" icon="pi pi-times" severity="secondary" @click="hideDialog" />
        <Button label="Save" icon="pi pi-check" severity="success" @click="saveContract" />
      </template>
    </Dialog>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue';
import Tag from 'primevue/tag';
import { useToast } from 'primevue/usetoast';

import {
  getContracts,
  getContract,
  createContract,
  updateContract,
  deleteContract,
  getCompanies,
  getVehicles,
  getOwners
} from '@/service/api';

const toast = useToast();
const contracts = ref([]);
const selectedContracts = ref([]);
const contractDialog = ref(false);
const contract = ref({});
const loading = ref(false);

const companies = ref([]);
const owners = ref([]);
const vehicles = ref([]);

const openNew = () => {
  contract.value = {};
  contractDialog.value = true;
};

const hideDialog = () => {
  contractDialog.value = false;
};

const editContract = async (row) => {
  try {
    await fetchDropdowns(); // ensure dropdowns are available
    const { data } = await getContract(row.id);

    contract.value = {
      ...data,
      companyId: data.company?.id ?? data.companyId,
      ownerId: data.owner?.id ?? data.ownerId,
      vehicleId: data.vehicle?.id ?? data.vehicleId,
    };

    contractDialog.value = true;
  } catch (error) {
    toast.add({ severity: 'error', summary: 'Error', detail: 'Failed to load contract details', life: 3000 });
  }
};

const saveContract = async () => {
  const payload = { ...contract.value };

  if (!payload.companyId || !payload.ownerId || !payload.vehicleId || !payload.contractType || !payload.startDate || !payload.endDate || !payload.status) {
    toast.add({ severity: 'warn', summary: 'Validation Failed', detail: 'All fields are required', life: 3000 });
    return;
  }

  try {
    if (payload.id) {
      await updateContract(payload.id, payload);
      toast.add({ severity: 'success', summary: 'Updated', detail: 'Contract updated successfully', life: 3000 });
    } else {
      await createContract(payload);
      toast.add({ severity: 'success', summary: 'Created', detail: 'Contract created successfully', life: 3000 });
    }
    fetchContracts();
    hideDialog();
  } catch (error) {
    toast.add({ severity: 'error', summary: 'Error', detail: 'Failed to save contract', life: 3000 });
  }
};

const confirmDeleteContract = async (row) => {
  if (confirm(`Delete contract for ${row.companyName}?`))
  {
    try {
      await deleteContract(row.id);
      toast.add({ severity: 'success', summary: 'Deleted', detail: 'Contract deleted', life: 3000 });
      fetchContracts();
    } catch (error) {
      toast.add({ severity: 'error', summary: 'Error', detail: 'Delete failed', life: 3000 });
    }
  }
};

const fetchContracts = async () => {
  loading.value = true;
  try {
    const { data } = await getContracts();
    contracts.value = Array.isArray(data) ? data : data.data || [];
  } catch (error) {
    toast.add({ severity: 'error', summary: 'Error', detail: 'Failed to load contracts', life: 3000 });
  } finally {
    loading.value = false;
  }
};

const fetchDropdowns = async () => {
  const [compRes, ownerRes, vehicleRes] = await Promise.all([
    getCompanies(),
    getOwners(),
    getVehicles()
  ]);
  companies.value = compRes.data || [];
  owners.value = ownerRes.data || [];
  vehicles.value = vehicleRes.data || [];
  console.log('Dropdowns loaded:', { companies: companies.value, owners: owners.value, vehicles: vehicles.value });
};

onMounted(() => {
  fetchContracts();
  fetchDropdowns();
});
</script>
