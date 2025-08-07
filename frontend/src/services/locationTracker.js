import { sendLocationUpdate } from './locationService.js';

export function startLocationTracking({ tripId, userId, isDriver }) {
    if (!('geolocation' in navigator)) {
        console.error('Geolocation is not supported by this browser.');
        return () => {};
    }

    const watchId = navigator.geolocation.watchPosition(
        (pos) => {
            const { latitude, longitude } = pos.coords;
            sendLocationUpdate({
                tripId,
                employeeId: isDriver ? null : userId,
                isDriver,
                latitude,
                longitude,
                timestamp: new Date().toISOString()
            }).catch((err) => console.error('Location update failed', err));
        },
        (err) => console.error('Geolocation error', err),
        { enableHighAccuracy: true, maximumAge: 10000, timeout: 10000 }
    );

    return () => navigator.geolocation.clearWatch(watchId);
}
