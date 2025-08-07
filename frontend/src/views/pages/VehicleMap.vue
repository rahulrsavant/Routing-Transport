

<!-- <script setup>
import { ref, onMounted, onUnmounted } from 'vue';
import axios from 'axios';

const center = ref({ lat: 0, lng: 0 });
const zoom = ref(12);
const markers = ref([]);
let timer;

// Polls the backend for vehicle, driver and passenger coordinates
// and updates the map markers accordingly.
async function pollLocations() {
    try {
        const { data } = await axios.get('/routing_transport/api/locations', {
            headers: { 'X-User-Id': 1 }
        });

        if (!Array.isArray(data)) {
            console.error('Backend did NOT return JSON array! Got:', data);
            return;
        }

        const locations = data
            .map((item) => {
                const lat = Number(item.latitude);
                const lng = Number(item.longitude);
                if (isNaN(lat) || isNaN(lng)) {
                    console.error('Bad coordinates from backend!', item);
                    return null;
                }
                return { position: { lat, lng } };
            })
            .filter(Boolean);

        if (locations.length) {
            markers.value = locations;
            center.value = locations[0].position;
        }
    } catch (error) {
        console.error('Error fetching locations:', error);
    }
}

onMounted(() => {
    pollLocations();
    timer = setInterval(pollLocations, 10000);
});

onUnmounted(() => {
    clearInterval(timer);
});
</script>

<template>
    <div class="p-3">
        <GMapMap class="map" :center="center" :zoom="zoom" map-type-id="roadmap">
            <GMapMarker v-for="(marker, idx) in markers" :key="idx" :position="marker.position" />
        </GMapMap>
    </div>
</template>

<style>
.map {
    width: 100%;
    height: 600px;
}
</style> -->




<script setup>
import { ref, onMounted, onUnmounted } from 'vue';
import axios from 'axios';

const sending = ref(false);
const errorMsg = ref('');
const center = ref({ lat: 0, lng: 0 });
const zoom = ref(12);
const markers = ref([]);

let locationTimer = null;
let pollingTimer = null;

// 📡 Sends current device location to backend
function sendLocation(lat, lng) {
  sending.value = true;
  console.log(`Sending location: ${lat}, ${lng}`);
  fetch('/routing_transport/api/location', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
      'X-User-Id': 1
    },
    body: JSON.stringify({
      userId: 1,
      type: 'driver',
      latitude: lat,
      longitude: lng,
      timestamp: Math.floor(Date.now() / 1000)
    })
  })
  .then(res => res.json())
  .then(() => {
    sending.value = false;
    errorMsg.value = '';
  })
  .catch(e => {
    sending.value = false;
    errorMsg.value = 'Failed to send location: ' + e;
  });
}

// 🎯 Gets current geolocation and triggers send
function fetchAndSendLocation() {
  if (navigator.geolocation) {
    navigator.geolocation.getCurrentPosition(
      pos => {
        const lat = pos.coords.latitude;
        const lng = pos.coords.longitude;
        sendLocation(lat, lng);
      },
      err => {
        errorMsg.value = 'Geolocation error: ' + err.message;
      }
    );
  } else {
    errorMsg.value = 'Geolocation not supported!';
  }
}

// 🗺️ Fetches all markers from backend and updates map
async function pollLocations() {
  try {
    const { data } = await axios.get('/routing_transport/api/locations', {
      headers: { 'X-User-Id': 1 }
    });

    const locations = Array.isArray(data)
      ? data
          .map(item => {
            const lat = Number(item.latitude);
            const lng = Number(item.longitude);
            if (isNaN(lat) || isNaN(lng)) return null;
            return { position: { lat, lng } };
          })
          .filter(Boolean)
      : [];

    if (locations.length) {
      markers.value = locations;
      center.value = locations[0].position;
    }
  } catch (error) {
    console.error('Error fetching locations:', error);
  }
}

onMounted(() => {
  fetchAndSendLocation(); // initial send
  pollLocations();        // initial map update
  locationTimer = setInterval(fetchAndSendLocation, 10000);
  pollingTimer = setInterval(pollLocations, 10000);
});

onUnmounted(() => {
  if (locationTimer) clearInterval(locationTimer);
  if (pollingTimer) clearInterval(pollingTimer);
});
</script>

<template>
  <div class="p-3">
    <p v-if="errorMsg" style="color: red">{{ errorMsg }}</p>
    <p v-else>
      Sending location to backend every 10 seconds...<br>
      <span v-if="sending">Sending now...</span>
    </p>

    <GMapMap class="map" :center="center" :zoom="zoom" map-type-id="roadmap">
      <GMapMarker v-for="(marker, idx) in markers" :key="idx" :position="marker.position" />
    </GMapMap>
  </div>
</template>

<style>
.map {
  width: 100%;
  height: 600px;
}
</style>

