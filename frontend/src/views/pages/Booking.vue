<template>
  <div>
    <!-- Step 1: Route & Trip Selection Dialog -->
    <Dialog v-model:visible="showDialog" modal header="Book Your Seat" :closable="false" :style="{ minWidth: '350px' }">
      <div v-if="loadingRoutes || loadingTrips" class="flex justify-center items-center my-8">
        <ProgressSpinner style="width:40px;height:40px" />
      </div>
      <template v-else>
        <div class="mb-4">
          <label class="font-semibold block mb-1">Select Route</label>
          <Dropdown v-model="selectedRoute" :options="routes" optionLabel="name"
            placeholder="Choose a route" class="w-full" />
        </div>
        <div class="mb-4" v-if="selectedRoute">
          <label class="font-semibold block mb-1">Select Trip</label>
          <Dropdown
            v-model="selectedTrip"
            :options="filteredTrips"
            optionLabel="label"
            placeholder="Choose a trip"
            class="w-full"
            :filter="true"
            :disabled="filteredTrips.length === 0"
          />
          <div v-if="filteredTrips.length === 0" class="text-sm text-red-500 mt-1">
            No trips found for this route.
          </div>
        </div>
        <Button label="Continue" class="w-full mt-2" :disabled="!selectedTrip" @click="onDialogContinue" />
      </template>
    </Dialog>

    <!-- Step 3: Seat Booking UI -->
    <div v-if="ready" class="card flex flex-col md:flex-row gap-6">
      <div class="flex-1">
        <Button
          icon="pi pi-refresh"
          label="Change Route/Trip"
          class="mb-4"
          severity="secondary"
          @click="onChangeTrip"
        />
        <div class="mb-4">
          <label class="block font-semibold mb-2">Select Date(s)</label>
          <Calendar v-model="bookingRange" selectionMode="range" dateFormat="yy-mm-dd" :manualInput="false" class="w-full" />
        </div>
        <div class="mb-4">
          <div><b>Trip:</b> {{ trip?.routeName }}</div>
          <div><b>Start Time:</b> {{ trip?.startTime }}</div>
          <div><b>End Time:</b> {{ trip?.endTime }}</div>
          <div><b>Status:</b> <Tag :value="trip?.status" :severity="trip?.status === 'ACTIVE' ? 'success' : 'warning'" /></div>
<div>
  <b>Stops:</b>
  <div v-if="selectedRouteObj?.stops && selectedRouteObj.stops.length" class="flex items-center gap-2 flex-wrap">
    <span v-for="(stop, i) in selectedRouteObj.stops" :key="stop" class="bg-blue-100 text-blue-800 rounded px-2 py-1 text-sm font-semibold">
      {{ stop }}<span v-if="i < selectedRouteObj.stops.length - 1"> → </span>
    </span>
  </div>
  <span v-else>No stops found</span>
</div>
        </div>
        <div v-if="loadingSeats" class="flex justify-center items-center my-8">
          <ProgressSpinner style="width:40px;height:40px" />
        </div>
        <div v-else class="grid gap-2">
          <div v-for="(row, rIndex) in groupedSeats" :key="rIndex" class="flex gap-2">
                <Button
                  v-for="seat in row"
                  :key="seat.id"
                  :label="getSeatLabel(seat)"
                  class="w-20 h-15 p-0 text-xs"
                  :disabled="!seat.available"
                  :severity="selectedSeats.includes(seat.id) ? 'success' : seat.available ? 'secondary' : 'danger'"
                  @click="toggleSeat(seat)"
                />
          </div>
        </div>
      </div>
      <div class="w-full md:w-64">
        <div class="border rounded p-4">
          <div class="font-semibold mb-2">Booking Summary</div>
          <div v-if="trip" class="text-sm">
            <p><b>Vehicle:</b> {{ vehicle?.registrationNumber }}</p>
            <p><b>Driver:</b> {{ trip.driverName }}</p>
            <p><b>Owner:</b> {{ vehicle?.owner?.name }}</p>
          </div>
          <div class="mt-2 text-sm">
            <div>
              <b>Booked Seats:</b>
              <ul>
                <li v-for="se in seatEmployees" :key="se.seatNumber">
                  {{ se.seatNumber }} | {{ se.employeeId }} - {{ se.employeeName }}
                </li>
              </ul>
            </div>
            <p><b>Selected Seats:</b> {{ selectedSeatLabels.join(', ') }}</p>
            <p><b>Total Price:</b> {{ totalPrice.toFixed(2) }}</p>
          </div>
          <Button label="Book Now" class="mt-3 w-full" :disabled="!canBook" @click="bookNow" />
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, computed, onMounted, watch } from 'vue';
import { useToast } from 'primevue/usetoast';
import Dialog from 'primevue/dialog';
import Dropdown from 'primevue/dropdown';
import Calendar from 'primevue/calendar';
import Button from 'primevue/button';
import Tag from 'primevue/tag';
import ProgressSpinner from 'primevue/progressspinner';

import {
  getRoutes,
  getTrips,
  getTrip,
  getVehicles,
  getSeats,
  getSeatAvailability,
  getSeatAvailabilityRange,
  createBooking,
  getSeatEmployeeList,
} from '@/service/api';

const toast = useToast();

// Step control
const showDialog = ref(true);
const ready = ref(false);

// Loading states
const loadingRoutes = ref(false);
const loadingTrips = ref(false);
const loadingSeats = ref(false);

// Route and Trip selection
const routes = ref([]);
const selectedRoute = ref(null);
const trips = ref([]);
const selectedTrip = ref(null);

const filteredTrips = computed(() =>
  trips.value
    .filter(t => t.routeId === selectedRoute.value?.id)
    .map(t => ({
      ...t,
      label: `${t.startTime} - ${t.endTime} via ${t.stops || t.routeName}`,
    }))
);

// Data for booking UI
const trip = ref(null);
const vehicle = ref(null);
const seats = ref([]);
const availability = ref([]);
const seatEmployees = ref([]);
const selectedSeats = ref([]);
const bookingRange = ref(null);

// Booking calculations
const startDate = computed(() => {
  if (!bookingRange.value) return null;
  if (Array.isArray(bookingRange.value)) return bookingRange.value[0];
  return bookingRange.value;
});
const endDate = computed(() => {
  if (!bookingRange.value) return null;
  if (Array.isArray(bookingRange.value)) return bookingRange.value[1] || bookingRange.value[0];
  return bookingRange.value;
});
const groupedSeats = computed(() => {
  const map = {};
  seats.value.forEach(seat => {
    const key = seat.rowNumber || 0;
    if (!map[key]) map[key] = [];
    const a = availability.value.find(av => av.seatId === seat.id);
    seat.available = a ? a.available : true;
    map[key].push(seat);
  });
  return Object.values(map);
});
const selectedSeatLabels = computed(() =>
  selectedSeats.value.map(id => seats.value.find(s => s.id === id)?.seatNumber || id)
);
const totalPrice = computed(() =>
  selectedSeats.value.reduce((sum, id) => {
    const seat = seats.value.find(s => s.id === id);
    return sum + (seat?.price || 0);
  }, 0)
);
const canBook = computed(() => selectedSeats.value.length > 0 && startDate.value);


const selectedRouteObj = computed(() => {
  if (!trip.value) return null;
  return routes.value.find(r => r.id === trip.value.routeId);
});

// Step 1/2: Load routes and trips for selection
onMounted(async () => {
  loadingRoutes.value = true;
  loadingTrips.value = true;
  try {
    routes.value = (await getRoutes()).data;
    loadingRoutes.value = false;
    trips.value = (await getTrips()).data;
    loadingTrips.value = false;
  } catch (e) {
    loadingRoutes.value = false;
    loadingTrips.value = false;
    toast.add({ severity: 'error', summary: 'Error', detail: 'Failed to load routes/trips', life: 3000 });
  }
});

function getSeatLabel(seat) {
  const found = seatEmployees.value.find(se => se.seatNumber === seat.seatNumber);
  if (found && found.employeeName) {
    // Show seat number and employee name on separate lines, id after name
    return `${seat.seatNumber}\n${found.employeeName}${found.employeeId ? ' - ' + found.employeeId : ''}`;
  }
  return seat.seatNumber;
}



// Allow user to change route/trip selection
function onChangeTrip() {
  // Reset selection but keep already loaded routes/trips for speed
  selectedRoute.value = null;
  selectedTrip.value = null;
  showDialog.value = true;
  ready.value = false;
  // Also reset seats and trip info for safety
  seats.value = [];
  trip.value = null;
  vehicle.value = null;
  bookingRange.value = null;
  selectedSeats.value = [];
}

// After user completes dialog
async function onDialogContinue() {
  if (!selectedTrip.value) return;
  showDialog.value = false;
  ready.value = false;
  try {
    await fetchTripData();
    await fetchSeats();
    await fetchSeatEmployees();
    ready.value = true;
  } catch (e) {
    toast.add({ severity: 'error', summary: 'Error', detail: 'Failed to load booking info', life: 3000 });
  }
}

// Booking UI logic
async function fetchTripData() {
  try {
    const { data } = await getTrip(selectedTrip.value.id);
    trip.value = data;
    // Find the vehicle for this trip (if registration mapping exists)
    const vehiclesRes = await getVehicles();
    vehicle.value = vehiclesRes.data.find(
      v => v.registrationNumber === data.vehicleRegistration || v.id === data.vehicleId
    );
  } catch (e) {
    toast.add({ severity: 'error', summary: 'Error', detail: 'Failed to load trip information', life: 3000 });
  }
}

async function fetchSeats() {
  if (!vehicle.value) return;
  loadingSeats.value = true;
  try {
    const { data } = await getSeats(vehicle.value.id);
    seats.value = data;
    await fetchAvailability();
  } catch (e) {
    toast.add({ severity: 'error', summary: 'Error', detail: 'Failed to load seats', life: 3000 });
  } finally {
    loadingSeats.value = false;
  }
}

async function fetchSeatEmployees() {
  if (!trip.value) return;
  try {
    const { data } = await getSeatEmployeeList(trip.value.id);
    seatEmployees.value = data;
  } catch (e) {
    toast.add({ severity: 'error', summary: 'Error', detail: 'Failed to load booked seats', life: 3000 });
  }
}

async function fetchAvailability() {
  if (!trip.value || !startDate.value) return;
  try {
    let res;
    const start = formatDate(startDate.value);
    const end = formatDate(endDate.value);
    if (end && start && end !== start) {
      res = await getSeatAvailabilityRange(trip.value.id, start, end);
    } else {
      res = await getSeatAvailability(trip.value.id, start);
    }
    availability.value = res.data;
    // Remove seats from selection if they are now unavailable
    selectedSeats.value = selectedSeats.value.filter(id => {
      const a = availability.value.find(av => av.seatId === id);
      return a && a.available;
    });
  } catch (e) {
    toast.add({ severity: 'error', summary: 'Error', detail: 'Failed to load seat availability', life: 3000 });
  }
}

function toggleSeat(seat) {
  if (!seat.available) return;
  const idx = selectedSeats.value.indexOf(seat.id);
  if (idx > -1) {
    selectedSeats.value.splice(idx, 1);
  } else {
    selectedSeats.value.push(seat.id);
  }
}

async function bookNow() {
  if (!canBook.value) return;
  const user = JSON.parse(localStorage.getItem('user'));
  const payload = {
    userId: user?.id,
    tripId: trip.value.id,
    seatIds: selectedSeats.value,
    startDate: formatDate(startDate.value),
    endDate: formatDate(endDate.value),
  };
  try {
    await createBooking(payload);
    toast.add({ severity: 'success', summary: 'Booked', detail: 'Booking successful', life: 3000 });
    selectedSeats.value = [];
    await fetchAvailability();
    await fetchSeatEmployees();
  } catch (e) {
    toast.add({ severity: 'error', summary: 'Error', detail: e.response?.data?.message || 'Booking failed', life: 3000 });
  }
}

watch(bookingRange, () => {
  if (bookingRange.value) {
    fetchAvailability();
  }
});

function formatDate(date) {
  if (!date) return null;
  return new Date(date).toISOString().split('T')[0];
}
</script>

<style scoped>
.p-button-label {
  white-space: pre-line; /* Allow line breaks in button labels */
  font-size: 11px;
}
</style>

