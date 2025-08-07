import axios from 'axios';

// Axios instance used throughout the app
const apiClient = axios.create({
  baseURL: import.meta.env.VITE_API_BASE_URL,
  headers: {
    'Content-Type': 'application/json'
  }
});

// Attach user id from localStorage to every request
apiClient.interceptors.request.use(config => {
  const userStr = localStorage.getItem('user');
  let userId;

  try {
    userId = JSON.parse(userStr)?.id;
  } catch {
    // ignore JSON parse errors
  }

  if (userId) {
    config.headers['X-User-Id'] = userId;
  }

  return config;
});

// Global response error handler
apiClient.interceptors.response.use(
  response => response,
  error => {
    let message = 'An unexpected error occurred.';
    const config = error.config || {};
    const userStr = localStorage.getItem('user');
    let userId;

    try {
      userId = JSON.parse(userStr)?.id;
    } catch {
      // ignore again
    }

    // Check for known error formats
    if (error.response?.data?.message) {
      message = error.response.data.message;
    } else if (error.message && error.message.includes('Network')) {
      message = 'Network error. Please check your connection.';
    }

    // Enhanced logging
    console.error('🚨 Global Error Handler Triggered:', {
      message: error.message,
      stack: error.stack,
      method: config.method,
      url: config.url,
      data: config.data,
      user: userId
    });

    // Emit global UI update event
    window.dispatchEvent(new CustomEvent('api-error', { detail: message }));

    // Propagate error so local handlers can react
    return Promise.reject(error);
  }
);

export default apiClient;
