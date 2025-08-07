<template>
  <div>
    <div class="card">
      <Toolbar class="mb-4">
        <template #start>
          <Button label="New" icon="pi pi-plus" severity="secondary" class="mr-2" @click="openNew" />
        </template>
      </Toolbar>
      <DataTable
        ref="dt"
        :value="owners"
        dataKey="id"
        :paginator="true"
        :rows="10"
        :filters="filters"
        paginatorTemplate="FirstPageLink PrevPageLink PageLinks NextPageLink LastPageLink CurrentPageReport RowsPerPageDropdown"
        :rowsPerPageOptions="[5, 10, 20]"
        currentPageReportTemplate="Showing {first} to {last} of {totalRecords} owners"
      >
        <template #header>
          <div class="flex flex-wrap gap-2 items-center justify-between">
            <h4 class="m-0">Manage Owners</h4>
            <IconField>
              <InputIcon>
                <i class="pi pi-search" />
              </InputIcon>
              <InputText v-model="filters['global'].value" placeholder="Search..." />
            </IconField>
          </div>
        </template>
        <Column field="name" header="Name" sortable></Column>
        <Column field="contactEmail" header="Email"></Column>
        <Column field="contactPhone" header="Phone"></Column>
        <Column field="address" header="Address"></Column>
        <Column field="status" header="Status"></Column>
        <Column :exportable="false" style="min-width:8rem">
          <template #body="slotProps">
            <Button icon="pi pi-pencil" outlined rounded class="mr-2" @click="editOwner(slotProps.data)" />
            <Button icon="pi pi-trash" outlined rounded severity="danger" @click="confirmDeleteOwner(slotProps.data)" />
          </template>
        </Column>
      </DataTable>
    </div>

    <Dialog v-model:visible="ownerDialog" :style="{ width: '450px' }" header="Owner Details" :modal="true" class="p-fluid">
      <div class="flex flex-col gap-4">
        <div>
          <label for="name" class="block font-bold mb-2">Name</label>
          <InputText id="name" v-model="owner.name" required autofocus :invalid="submitted && !owner.name" />
          <small v-if="submitted && !owner.name" class="text-red-500">Name is required.</small>
        </div>
        <div>
          <label for="contactEmail" class="block font-bold mb-2">Contact Email</label>
          <InputText id="contactEmail" v-model="owner.contactEmail" />
        </div>
        <div>
          <label for="contactPhone" class="block font-bold mb-2">Contact Phone</label>
          <InputText id="contactPhone" v-model="owner.contactPhone" />
        </div>
        <div>
          <label for="address" class="block font-bold mb-2">Address</label>
          <InputText id="address" v-model="owner.address" />
        </div>
        <div>
          <label for="status" class="block font-bold mb-2">Status</label>
          <InputText id="status" v-model="owner.status" />
        </div>
      </div>
      <template #footer>
        <Button label="Cancel" icon="pi pi-times" text @click="hideDialog" />
        <Button label="Save" icon="pi pi-check" @click="saveOwner" />
      </template>
    </Dialog>

    <Dialog v-model:visible="deleteOwnerDialog" :style="{ width: '450px' }" header="Confirm" :modal="true">
      <div class="flex items-center gap-4">
        <i class="pi pi-exclamation-triangle !text-3xl" />
        <span v-if="owner">Are you sure you want to delete <b>{{ owner.name }}</b>?</span>
      </div>
      <template #footer>
        <Button label="No" icon="pi pi-times" text @click="deleteOwnerDialog = false" />
        <Button label="Yes" icon="pi pi-check" @click="deleteOwnerFunc" />
      </template>
    </Dialog>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue';
import { FilterMatchMode } from '@primevue/core/api';
import { useToast } from 'primevue/usetoast';
import { getOwners, getOwner, createOwner, updateOwner, deleteOwner } from '@/service/api';

const owners = ref([]);
const ownerDialog = ref(false);
const deleteOwnerDialog = ref(false);
const owner = ref({});
const submitted = ref(false);
const dt = ref();
const toast = useToast();
const filters = ref({
  global: { value: null, matchMode: FilterMatchMode.CONTAINS }
});

const fetchOwners = async () => {
  try {
    const { data } = await getOwners();
    owners.value = data;
  } catch (e) {
    toast.add({ severity: 'error', summary: 'Error', detail: 'Failed to load owners', life: 3000 });
  }
};

onMounted(fetchOwners);

function openNew() {
  owner.value = { name: '', contactEmail: '', contactPhone: '', address: '', status: '' };
  submitted.value = false;
  ownerDialog.value = true;
}

function hideDialog() {
  ownerDialog.value = false;
  submitted.value = false;
}

async function saveOwner() {
  submitted.value = true;
  if (!owner.value.name) return;
  try {
    if (owner.value.id) {
      await updateOwner(owner.value.id, owner.value);
      toast.add({ severity: 'success', summary: 'Successful', detail: 'Owner Updated', life: 3000 });
    } else {
      await createOwner(owner.value);
      toast.add({ severity: 'success', summary: 'Successful', detail: 'Owner Created', life: 3000 });
    }
    ownerDialog.value = false;
    await fetchOwners();
  } catch (e) {
    toast.add({ severity: 'error', summary: 'Error', detail: 'Failed to save owner', life: 3000 });
  }
}

async function editOwner(row) {
  try {
    const { data } = await getOwner(row.id);
    owner.value = { ...data };
    ownerDialog.value = true;
  } catch (e) {
    toast.add({ severity: 'error', summary: 'Error', detail: 'Failed to load owner', life: 3000 });
  }
}

function confirmDeleteOwner(row) {
  owner.value = { ...row };
  deleteOwnerDialog.value = true;
}

async function deleteOwnerFunc() {
  try {
    await deleteOwner(owner.value.id);
    toast.add({ severity: 'success', summary: 'Successful', detail: 'Owner Deleted', life: 3000 });
    deleteOwnerDialog.value = false;
    await fetchOwners();
  } catch (e) {
    toast.add({ severity: 'error', summary: 'Error', detail: 'Failed to delete owner', life: 3000 });
  }
}
</script>
