<script setup>
import { ref, onMounted, onUnmounted } from 'vue';
import { useRoute } from 'vue-router';
import axios from 'axios';
import StopMarker from '@/components/StopMarker.vue';
import { calculateETA } from '@/helpers/eta';

const route = useRoute();
const tripId = route.params.tripId;

const mapCenter = ref({ lat: 0, lng: 0 });
const routePath = ref([]);
const stops = ref([]);
const startPoint = ref(null);
const endPoint = ref(null);
const busLocation = ref(null);

const startIcon = 'http://maps.google.com/mapfiles/ms/icons/green-dot.png';
const endIcon = 'http://maps.google.com/mapfiles/ms/icons/red-dot.png';
const busIcon = 'http://maps.google.com/mapfiles/ms/icons/yellow-dot.png';

let pollTimer = null;

async function fetchTrip() {
  try {
    const { data } = await axios.get(`/routing_transport/api/trips/${tripId}`);
    startPoint.value = data.start_point;
    endPoint.value = data.end_point;
    stops.value = data.stops || [];
    busLocation.value = data.bus_location || null;
    const pts = [];
    if (startPoint.value) pts.push({ lat: startPoint.value.latitude, lng: startPoint.value.longitude });
    stops.value.forEach(s => pts.push({ lat: s.latitude, lng: s.longitude }));
    if (endPoint.value) pts.push({ lat: endPoint.value.latitude, lng: endPoint.value.longitude });
    routePath.value = pts;
    if (busLocation.value) {
      mapCenter.value = { lat: busLocation.value.latitude, lng: busLocation.value.longitude };
    } else if (startPoint.value) {
      mapCenter.value = { lat: startPoint.value.latitude, lng: startPoint.value.longitude };
    }
  } catch (e) {
    console.error('Failed to load trip', e);
  }
}

async function pollBus() {
  try {
    const { data } = await axios.get(`/routing_transport/api/trips/${tripId}/location`);
    if (!data) return;
    if (busLocation.value) {
      animateBus(busLocation.value, data);
    } else {
      busLocation.value = data;
    }
  } catch (e) {
    console.error('Failed to fetch bus location', e);
  }
}

function animateBus(from, to, duration = 1000) {
  const frames = 20;
  let step = 0;
  const latStep = (to.latitude - from.latitude) / frames;
  const lngStep = (to.longitude - from.longitude) / frames;
  const anim = setInterval(() => {
    step++;
    busLocation.value = {
      latitude: from.latitude + latStep * step,
      longitude: from.longitude + lngStep * step
    };
    if (step >= frames) clearInterval(anim);
  }, duration / frames);
}

function etaFor(stop) {
  return calculateETA(busLocation.value, stop);
}

onMounted(() => {
  fetchTrip();
  pollTimer = setInterval(pollBus, 10000);
});

onUnmounted(() => {
  if (pollTimer) clearInterval(pollTimer);
});
</script>

<template>
  <div class="p-3">
    <GMapMap class="map" :center="mapCenter" :zoom="13" map-type-id="roadmap">
      <GMapPolyline v-if="routePath.length" :path="routePath" />
      <GMapMarker v-if="startPoint" :position="{lat:startPoint.latitude,lng:startPoint.longitude}" :icon="startIcon" />
      <StopMarker v-for="s in stops" :key="s.name" :stop="s" :eta="etaFor(s)" />
      <GMapMarker v-if="endPoint" :position="{lat:endPoint.latitude,lng:endPoint.longitude}" :icon="endIcon" />
      <GMapMarker v-if="busLocation" :position="{lat:busLocation.latitude,lng:busLocation.longitude}" :icon="busIcon" :options="{animation: google.maps.Animation.DROP}" />
    </GMapMap>
  </div>
</template>

<style>
.map {
  width: 100%;
  height: 600px;
}
</style>
