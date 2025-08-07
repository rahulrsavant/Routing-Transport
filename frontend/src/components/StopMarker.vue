<script setup>
import { ref } from 'vue';

const props = defineProps({
  stop: {
    type: Object,
    required: true
  },
  eta: {
    type: String,
    default: 'N/A'
  },
  icon: {
    type: String,
    default: 'http://maps.google.com/mapfiles/ms/icons/blue-dot.png'
  }
});

const infoOpen = ref(false);
const position = { lat: props.stop.latitude, lng: props.stop.longitude };
</script>

<template>
  <GMapMarker :position="position" :icon="icon" @click="infoOpen = true">
    <GMapInfoWindow :opened="infoOpen" @closeclick="infoOpen = false">
      <div class="text-sm">
        <strong>{{ stop.name }}</strong><br />
        ETA: {{ eta }}
      </div>
    </GMapInfoWindow>
  </GMapMarker>
</template>
