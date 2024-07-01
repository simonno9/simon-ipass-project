// vite.config.js
import { resolve } from 'path';
import { defineConfig } from 'vite';

export default defineConfig({
  build: {
    rollupOptions: {
      input: {
        main: resolve(__dirname, 'index.html'),
        showroom: resolve(__dirname, 'nested/showroom.html'),
        project: resolve(__dirname, 'nested/project.html'),
        // Add other pages as needed
      },
    },
  },
});
