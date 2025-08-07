import apiClient from './api.js';

export function sendLocationUpdate(payload) {
    return apiClient.post('/api/v1/location/update', payload);
}

export function fetchCurrentLocation(tripId) {
    return apiClient.get('/api/v1/location/current', { params: { tripId } });
}
