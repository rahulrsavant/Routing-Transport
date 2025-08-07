// vite.config.js
import { defineConfig } from 'vite'
import vue from '@vitejs/plugin-vue'
import path from 'path' // ← Required for path.resolve

export default defineConfig(({ mode }) => {
  const isProduction = mode === 'production'

  return {
    plugins: [vue()],
    base: isProduction ? '/routing_transport/' : '/',
    resolve: {
      alias: {
        '@': path.resolve(__dirname, './src')  // ← This is the fix
      }
    },
    server: {
      proxy: {
        '/routing_transport/api': {
          target: 'http://localhost:8932',
          changeOrigin: true,
          rewrite: path =>
            path.replace(/^\/routing_transport\/api/, '/routing_transport/api')
        }
      }
    }
  }
})

