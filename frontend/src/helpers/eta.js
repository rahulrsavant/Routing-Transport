// Simple ETA helpers using straight-line distance
export function haversineDistance(a, b) {
  const R = 6371; // km
  const toRad = deg => (deg * Math.PI) / 180;
  const dLat = toRad(b.latitude - a.latitude);
  const dLng = toRad(b.longitude - a.longitude);
  const lat1 = toRad(a.latitude);
  const lat2 = toRad(b.latitude);

  const h =
    Math.sin(dLat / 2) ** 2 +
    Math.cos(lat1) * Math.cos(lat2) * Math.sin(dLng / 2) ** 2;
  return 2 * R * Math.asin(Math.sqrt(h));
}

export function calculateETA(current, stop, speedKmh = 30) {
  // Returns ETA in minutes based on straight-line distance
  if (!current) return 'N/A';
  const distanceKm = haversineDistance(current, stop);
  const hours = distanceKm / speedKmh;
  const minutes = Math.round(hours * 60);
  return minutes + ' min';
}
