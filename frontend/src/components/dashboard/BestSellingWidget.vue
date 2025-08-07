<script setup>
import { ref, onMounted } from 'vue'
import { getRouteStats } from '@/service/api' // <-- implement this API call!

const menu = ref(null)

const items = ref([
  { label: 'Add New Route', icon: 'pi pi-fw pi-plus' },
  { label: 'Remove', icon: 'pi pi-fw pi-trash' }
])

const routes = ref([])

onMounted(async () => {
  try {
    const { data } = await getRouteStats()   // GET /api/v1/routes/stats (for example)
    routes.value = data
  } catch (e) {
    routes.value = [
      // fallback demo data
      { routeName: "Telco to Pune Station", utilization: 65, totalTrips: 22, status: "ACTIVE" },
      { routeName: "Pune Station to Telco", utilization: 48, totalTrips: 18, status: "ACTIVE" },
      { routeName: "Dange Chowk to Telco", utilization: 40, totalTrips: 12, status: "INACTIVE" },
      { routeName: "Telco to Dange Chowk", utilization: 88, totalTrips: 31, status: "ACTIVE" }
    ]
  }
})
</script>


<template>
  <div class="card">
    <div class="flex justify-between items-center mb-6">
      <div class="font-semibold text-xl">Top Performing Routes</div>
      <div>
        <Button icon="pi pi-ellipsis-v" class="p-button-text p-button-plain p-button-rounded" @click="$refs.menu.toggle($event)" />
        <Menu ref="menu" popup :model="items" class="!min-w-40" />
      </div>
    </div>
    <ul class="list-none p-0 m-0">
      <li v-for="route in routes" :key="route.routeName" class="flex flex-col md:flex-row md:items-center md:justify-between mb-6">
        <div>
          <span class="text-surface-900 dark:text-surface-0 font-medium mr-2 mb-1 md:mb-0">{{ route.routeName }}</span>
          <div class="mt-1 text-muted-color">Trips: {{ route.totalTrips }} <span v-if="route.status !== 'ACTIVE'" class="ml-2 text-red-400 text-xs">(Inactive)</span></div>
        </div>
        <div class="mt-2 md:mt-0 flex items-center">
          <div class="bg-surface-300 dark:bg-surface-500 rounded-border overflow-hidden w-40 lg:w-24" style="height: 8px">
            <div
              :class="[
                route.utilization >= 75 ? 'bg-green-500' : route.utilization >= 50 ? 'bg-orange-500' : 'bg-cyan-500'
              ]"
              class="h-full"
              :style="{ width: route.utilization + '%' }"
            />
          </div>
          <span
            :class="[
              route.utilization >= 75 ? 'text-green-500' : route.utilization >= 50 ? 'text-orange-500' : 'text-cyan-500',
              'ml-4 font-medium'
            ]"
          >%{{ route.utilization }}</span>
        </div>
      </li>
    </ul>
  </div>
</template>

