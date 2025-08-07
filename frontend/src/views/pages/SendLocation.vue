<script setup>
import { ref, onMounted, onUnmounted } from 'vue';

const sending = ref(false);
const errorMsg = ref('');
let intervalId = null;

function sendLocation(lat, lng) {
  sending.value = true;
  console.log(`Sending location: ${lat}, ${lng}`);
  fetch('/routing_transport/api/location', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
      'X-User-Id': 1 // 🛠️ Fixed: Ensure user
    },
    body: JSON.stringify({
      userId: 1,
      type: 'driver',
      latitude: lat,
      longitude: lng,
      timestamp: Math.floor(Date.now() / 1000)
    }) // 🛠️ Fixed: this closing brace was misaligned
  })
  .then(res => res.json())
  .then(data => {
    errorMsg.value = '';
    sending.value = false;
  })
  .catch(e => {
    errorMsg.value = 'Failed to send location: ' + e;
    sending.value = false;
  });
}

function fetchAndSendLocation() {
  console.log('Fetching and sending location...');
  if (navigator.geolocation) {
    navigator.geolocation.getCurrentPosition(
      pos => {
        const lat = pos.coords.latitude;
        const lng = pos.coords.longitude;
        console.log(`Current position: ${lat}, ${lng}`);
        sendLocation(lat, lng);
      },
      err => {
        errorMsg.value = 'Geolocation error: ' + err.message;
        console.error('Geolocation error:', err);
      }
    );
  } else {
    errorMsg.value = 'Geolocation not supported!';
    console.error('Geolocation not supported!');
  }
  console.log('Location fetch and send completed.');
}

onMounted(() => {
  fetchAndSendLocation();
  intervalId = setInterval(fetchAndSendLocation, 10000);
});

onUnmounted(() => {
  if (intervalId) clearInterval(intervalId);
});
</script>

<template>
  <div>
    <p v-if="errorMsg" style="color: red">{{ errorMsg }}</p>
    <p v-else>
      Sending location to backend every 10 seconds...<br>
      <span v-if="sending">Sending now...</span>
    </p>
  </div>
</template>
