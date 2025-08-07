<script setup>
import { ref, reactive, computed, onMounted } from 'vue';
import { FilterMatchMode } from '@primevue/core/api';
import { useToast } from 'primevue/usetoast';
import { getVehicles, getVehicle, createVehicle, updateVehicle, deleteVehicle, deleteVehicles, getVehicleOwners } from '@/service/api';

const toast = useToast();

const dt = ref();
const vehicles = ref([]);
const owners = ref([]);
const selectedVehicles = ref([]);
const vehicleDialog = ref(false);
const loading = ref(false);
const saving = ref(false);
const formMode = ref('create');
let editingId = null;

const filters = ref({
    global: { value: null, matchMode: FilterMatchMode.CONTAINS }
});

const form = reactive({
    registrationNumber: '',
    model: '',
    vehicleType: null,
    capacity: null,
    status: null,
    ownerId: null
});

const statusOptions = [
    { label: 'Active', value: 'ACTIVE' },
    { label: 'Inactive', value: 'INACTIVE' }
];

const vehicleTypes = ['CAR', 'TRUCK', 'VAN', 'BUS'];

const filteredVehicles = computed(() => {
    const keyword = filters.value.global.value?.toLowerCase() || '';
    return vehicles.value.filter((v) => (v.registrationNumber && v.registrationNumber.toLowerCase().includes(keyword)) || (v.model && v.model.toLowerCase().includes(keyword)) || (v.vehicleType && v.vehicleType.toLowerCase().includes(keyword)));
});

function resetForm() {
    form.registrationNumber = '';
    form.model = '';
    form.vehicleType = null;
    form.capacity = null;
    form.status = null;
    form.ownerId = null;
    editingId = null;
    formMode.value = 'create';
}

function openNew() {
    resetForm();
    vehicleDialog.value = true;
}

async function fetchVehicles() {
    loading.value = true;
    try {
        const { data } = await getVehicles();
        vehicles.value = data;
    } catch {
        toast.add({ severity: 'error', summary: 'Error', detail: 'Failed to load vehicles', life: 3000 });
    } finally {
        loading.value = false;
    }
}

async function fetchOwners() {
    try {
        const { data } = await getVehicleOwners();
        owners.value = data;
    } catch {
        toast.add({ severity: 'error', summary: 'Error', detail: 'Failed to load owners', life: 3000 });
    }
}

async function editVehicle(vehicle) {
    try {
        const { data } = await getVehicle(vehicle.id);
        form.registrationNumber = data.registrationNumber;
        form.model = data.model;
        form.vehicleType = data.vehicleType;
        form.capacity = data.capacity;
        form.status = data.status;
        form.ownerId = data.owner?.id ?? data.ownerId;
        editingId = data.id;
        formMode.value = 'edit';
        vehicleDialog.value = true;
    } catch (e) {
        toast.add({ severity: 'error', summary: 'Error', detail: 'Failed to load vehicle', life: 3000 });
    }
}

async function submit() {
    if (!form.registrationNumber || !form.model || !form.vehicleType || form.capacity === null || !form.status || !form.ownerId) {
        toast.add({ severity: 'warn', summary: 'Validation', detail: 'Please fill out all fields', life: 3000 });
        return;
    }
    saving.value = true;
    const payload = {
        registrationNumber: form.registrationNumber,
        model: form.model,
        vehicleType: form.vehicleType,
        capacity: form.capacity,
        status: form.status,
        ownerId: form.ownerId
    };
    try {
        if (formMode.value === 'edit') {
            await updateVehicle(editingId, payload);
            toast.add({ severity: 'success', summary: 'Updated', detail: 'Vehicle updated', life: 3000 });
        } else {
            await createVehicle(payload);
            toast.add({ severity: 'success', summary: 'Created', detail: 'Vehicle created', life: 3000 });
        }
        vehicleDialog.value = false;
        await fetchVehicles();
        resetForm();
    } catch (e) {
        toast.add({ severity: 'error', summary: 'Error', detail: e.response?.data?.message || 'Failed to save vehicle', life: 3000 });
    } finally {
        saving.value = false;
    }
}

async function confirmDelete(id) {
    if (!confirm('Are you sure you want to delete this vehicle?')) {
        return;
    }
    try {
        await deleteVehicle(id);
        toast.add({ severity: 'success', summary: 'Deleted', detail: 'Vehicle deleted', life: 3000 });
        await fetchVehicles();
    } catch {
        toast.add({ severity: 'error', summary: 'Error', detail: 'Failed to delete vehicle', life: 3000 });
    }
}

async function confirmDeleteSelected() {
    if (!selectedVehicles.value.length) return;
    if (!confirm('Are you sure you want to delete the selected vehicles?')) {
        return;
    }
    try {
        const ids = selectedVehicles.value.map((v) => v.id);
        const { data } = await deleteVehicles(ids);
        toast.add({
            severity: data.locked?.length ? 'warn' : 'success',
            summary: 'Deletion Result',
            detail: data.message,
            life: 4000
        });
        selectedVehicles.value = [];
        await fetchVehicles();
    } catch (e) {
        toast.add({ severity: 'error', summary: 'Error', detail: e.response?.data?.message || 'Failed to delete vehicles', life: 4000 });
    }
}

function exportCSV() {
    dt.value.exportCSV();
}

onMounted(async () => {
    await Promise.all([fetchVehicles(), fetchOwners()]);
});
</script>

<template>
    <div class="card">
        <Toolbar class="mb-4">
            <template #start>
                <Button label="New Vehicle" icon="pi pi-plus" severity="secondary" class="mr-2" @click="openNew" />
                <Button label="Delete" icon="pi pi-trash" severity="secondary" @click="confirmDeleteSelected" :disabled="!selectedVehicles.length" />
            </template>
            <template #end>
                <Button label="Export" icon="pi pi-upload" severity="secondary" @click="exportCSV" />
            </template>
        </Toolbar>

        <DataTable
            ref="dt"
            v-model:selection="selectedVehicles"
            :value="filteredVehicles"
            dataKey="id"
            :paginator="true"
            :rows="10"
            :loading="loading"
            paginatorTemplate="FirstPageLink PrevPageLink PageLinks NextPageLink LastPageLink CurrentPageReport RowsPerPageDropdown"
            :rowsPerPageOptions="[5, 10, 20]"
            currentPageReportTemplate="Showing {first} to {last} of {totalRecords} vehicles"
        >
            <template #header>
                <div class="flex flex-wrap gap-2 items-center justify-between">
                    <h4 class="m-0">Manage Vehicles</h4>
                    <IconField>
                        <InputIcon><i class="pi pi-search" /></InputIcon>
                        <InputText v-model="filters['global'].value" placeholder="Search..." />
                    </IconField>
                </div>
            </template>

            <Column selectionMode="multiple" style="width: 3rem" :exportable="false" />
            <Column field="registrationNumber" header="Registration Number" sortable />
            <Column field="model" header="Model" sortable />
            <Column field="vehicleType" header="Type" sortable />
            <Column field="capacity" header="Capacity" sortable />
            <Column field="status" header="Status" sortable />
            <Column header="Owner">
                <template #body="slotProps">{{ slotProps.data.owner?.name }}</template>
            </Column>
            <Column :exportable="false" style="min-width: 8rem">
                <template #body="slotProps">
                    <Button icon="pi pi-pencil" outlined rounded class="mr-2" @click="editVehicle(slotProps.data)" />
                    <Button icon="pi pi-trash" outlined rounded severity="danger" @click="confirmDelete(slotProps.data.id)" />
                </template>
            </Column>
        </DataTable>

        <Dialog v-model:visible="vehicleDialog" :style="{ width: '650px' }" header="Vehicle Details" :modal="true" class="p-fluid">
            <div class="card flex flex-col gap-4">
                <div class="font-semibold text-xl text-primary mb-3">{{ formMode === 'edit' ? 'Edit' : 'Create' }} Vehicle</div>
                <div class="grid grid-cols-1 md:grid-cols-2 gap-4">
                    <div class="flex flex-col gap-2">
                        <label for="registrationNumber">Registration Number</label>
                        <InputText id="registrationNumber" v-model="form.registrationNumber" :disabled="saving" required />
                    </div>
                    <div class="flex flex-col gap-2">
                        <label for="model">Model</label>
                        <InputText id="model" v-model="form.model" :disabled="saving" required />
                    </div>
                    <div class="flex flex-col gap-2">
                        <label for="vehicleType">Type</label>
                        <Dropdown id="vehicleType" v-model="form.vehicleType" :options="vehicleTypes" placeholder="Select" :disabled="saving" />
                    </div>
                    <div class="flex flex-col gap-2">
                        <label for="capacity">Capacity</label>
                        <InputNumber id="capacity" v-model="form.capacity" :disabled="saving" />
                    </div>
                    <div class="flex flex-col gap-2">
                        <label for="status">Status</label>
                        <Dropdown id="status" v-model="form.status" :options="statusOptions" optionLabel="label" optionValue="value" placeholder="Select" :disabled="saving" />
                    </div>
                    <div class="flex flex-col gap-2">
                        <label for="ownerId">Owner</label>
                        <Dropdown id="ownerId" v-model="form.ownerId" :options="owners" optionLabel="name" optionValue="id" placeholder="Select" :disabled="saving" />
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

<style scoped></style>
