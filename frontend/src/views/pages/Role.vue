<script setup>
import { createRole, deleteRole, getPermissions, getRole, getRoles, updateRole } from '@/service/api';
import { useToast } from 'primevue/usetoast';
import { onMounted, ref } from 'vue';
import { useRoute } from 'vue-router';

const route = useRoute();
const toast = useToast();

const form = ref({ name: '', description: '', permissionIds: [] });


const roles = ref([]);
const permissions = ref([]);
const formMode = ref('create');
const saving = ref(false);
const loading = ref(false);
let editingId = null;

const fetchRoles = async () => {
    try {
        const { data } = await getRoles();
        roles.value = data;
    } catch (error) {
        toast.add({ severity: 'error', summary: 'Error', detail: 'Failed to load roles', life: 3000 });
    }
};

const fetchPermissions = async () => {
    try {
        const { data } = await getPermissions();
        permissions.value = data;
    } catch (error) {
        toast.add({ severity: 'error', summary: 'Error', detail: 'Failed to load permissions', life: 3000 });
    }
};

const loadRole = async (id) => {


    if (!id) return;
    try {
        loading.value = true;
        const { data } = await getRole(id);
        form.value = {
          name: data.name,
          description: data.description,
          permissionIds: Array.isArray(data.permissions) ? data.permissions.map(p => p.id) : []
        };

        formMode.value = 'edit';
        editingId = id;
    } catch (error) {
        toast.add({ severity: 'error', summary: 'Error', detail: 'Failed to load role', life: 3000 });
    } finally {
        loading.value = false;
    }
};

const submit = async () => {
    saving.value = true;
    try {
        const payload = {
            name: form.value.name,
            description: form.value.description,
            permissionIds: form.value.permissionIds
        };
        if (formMode.value === 'edit') {
            await updateRole(editingId, payload);
            toast.add({ severity: 'success', summary: 'Updated', detail: 'Role updated', life: 3000 });
        } else {
            await createRole(payload);
            toast.add({ severity: 'success', summary: 'Created', detail: 'Role created', life: 3000 });
        }
        await fetchRoles();
        resetForm();
    } catch (error) {
        let message = 'Failed to save role.';
        if (error.response?.data?.message) {
            message = error.response.data.message;
        }
        toast.add({ severity: 'error', summary: 'Error', detail: message, life: 3000 });
    } finally {
        saving.value = false;
    }
};

const editRole = (role) => {
    loadRole(role.id);
};

const confirmDelete = async (id) => {
    try {
        await deleteRole(id);
        toast.add({ severity: 'success', summary: 'Deleted', detail: 'Role deleted successfully', life: 3000 });
        await fetchRoles();
    } catch (e) {
        toast.add({ severity: 'error', summary: 'Error', detail: 'Failed to delete role', life: 3000 });
    }
};

const resetForm = () => {
    form.value = { name: '', description: '', permissionIds: [] };
    formMode.value = 'create';
    editingId = null;
};

onMounted(async () => {
    await Promise.all([fetchRoles(), fetchPermissions()]);
    if (route.params.id) {
        await loadRole(route.params.id);
    }
});
</script>

<template>
    <div class="grid gap-4">
        <!-- FORM CARD -->
        <div class="card flex flex-col gap-4">
            <div class="font-semibold text-xl text-primary mb-3">{{ formMode === 'edit' ? 'Edit' : 'Create' }} Role</div>
            <div class="flex flex-col gap-2">
                <label for="name">Name</label>
                <InputText id="name" v-model="form.name" :disabled="saving" required />
            </div>
            <div class="flex flex-col gap-2">
                <label for="description">Description</label>
                <Textarea id="description" v-model="form.description" rows="5" :disabled="saving" />
            </div>

    <!-- ✅ PERMISSIONS ALIGNED -->
<div class="flex flex-col gap-2">
    <label class="font-semibold text-sm text-primary">Permissions</label>
    <div class="grid grid-cols-2 sm:grid-cols-3 md:grid-cols-4 lg:grid-cols-5 xl:grid-cols-6 gap-2">
        <div
            v-for="perm in permissions"
            :key="perm.id"
            class="flex items-center space-x-2"
        >
            <Checkbox v-model="form.permissionIds" :inputId="`perm-${perm.id}`" :value="perm.id" />
            <label :for="`perm-${perm.id}`" class="text-sm">{{ perm.code }}</label>
        </div>
    </div>
</div>

    
<!-- 
            <div class="flex flex-col gap-2">
                 <label class="font-semibold text-sm text-primary">Permissions</label>
                    <div class="grid grid-cols-2 sm:grid-cols-3 md:grid-cols-4 lg:grid-cols-5 xl:grid-cols-6 gap-2">
        
                <div class="flex flex-wrap gap-3">
                    <div v-for="perm in permissions" 
                    :key="perm.id" 
                    class="flex items-center"
                >
                        <Checkbox v-model="form.permissionIds" :inputId="`perm-${perm.id}`" :value="perm.id" />
                        <label :for="`perm-${perm.id}`" class="text-sm">{{ perm.code }}</label>
                    </div>
                </div>
            </div>
            </div> -->



            <div class="flex gap-2">
                <Button label="Submit" :loading="saving" @click="submit" />
                <Button label="Clear" severity="secondary" outlined @click="resetForm" :disabled="saving" />
            </div>
        </div>

        <!-- LIST CARD -->
        <div class="card">
            <div class="font-semibold text-xl text-primary mb-3">Role List</div>
            <DataTable :value="roles" dataKey="id" :loading="loading" :paginator="true" :rows="5" :rowsPerPageOptions="[5, 10, 20, 50]">
                <Column field="id" header="ID" style="width: 4rem" />
                <Column field="name" header="Name" />
                <Column field="description" header="Description" />
                <Column header="Actions" style="width: 10rem">
                    <template #body="slotProps">
                        <Button icon="pi pi-pencil" outlined rounded class="mr-2" @click="editRole(slotProps.data)" />
                        <Button icon="pi pi-trash" outlined severity="danger" rounded @click="confirmDelete(slotProps.data.id)" />
                    </template>
                </Column>
            </DataTable>
        </div>
    </div>
</template>

<style scoped></style>
