import { fileURLToPath, URL } from 'node:url';

import { PrimeVueResolver } from '@primevue/auto-import-resolver';
import vue from '@vitejs/plugin-vue';
import Components from 'unplugin-vue-components/vite';
import { defineConfig } from 'vite';

// https://vitejs.dev/config/
export default defineConfig({
    base: '/routing_transport/',  // 👈 ADD THIS LINE

    optimizeDeps: {
        noDiscovery: true
    },
    plugins: [
        vue(),
        Components({
            resolvers: [PrimeVueResolver()]
        })
    ],
    resolve: {
        alias: {
            '@': fileURLToPath(new URL('./src', import.meta.url))
        }
    },
    // ✅ Add this section to enable source maps
    build: {
        sourcemap: true
    },
    // 👇 ADD THIS BLOCK FOR LOCAL DEV PROXY
    server: {
        proxy: {
            '/routing_transport/api': {
                target: 'http://localhost:8932',
                changeOrigin: true,
                secure: false
            }
        }
    }
});

