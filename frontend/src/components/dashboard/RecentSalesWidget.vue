<script setup>
import { ref, onMounted } from 'vue';
import { getRecentBookings } from '@/service/api';

const bookings = ref([]);

function formatCurrency(value) {
    return `₹${Number(value).toLocaleString('en-IN')}`;
}

onMounted(async () => {
    // Get 5 most recent bookings
    const res = await getRecentBookings(5);
    bookings.value = res.data;
});
</script>

<template>
    <div class="card">
        <div class="font-semibold text-xl mb-4">Recent Bookings</div>
        <DataTable :value="bookings" :rows="5" :paginator="false" responsiveLayout="scroll">
            <Column field="routeName" header="Route" style="width: 20%" />
            <Column field="tripTime" header="Trip Time" style="width: 20%" />
            <Column field="date" header="Date" style="width: 15%" />
            <Column field="userName" header="User" style="width: 20%" />
            <Column field="totalAmount" header="Fare" style="width: 15%">
                <template #body="slotProps">
                    {{ formatCurrency(slotProps.data.totalAmount) }}
                </template>
            </Column>
            <Column style="width: 10%" header="Details">
                <template #body="slotProps">
                    <Button icon="pi pi-search" type="button" class="p-button-text" @click="viewBooking(slotProps.data)" />
                </template>
            </Column>
        </DataTable>
    </div>
</template>

<script>
// Optionally, you can define viewBooking for detail action
function viewBooking(booking) {
    alert(`Booking ID: ${booking.id}`);
}
</script>
