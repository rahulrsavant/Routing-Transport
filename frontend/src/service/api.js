// ✅ KEEP THIS CODE — NO CHANGE NEEDED
import apiClient from '@/services/api';

// export function loginUser(username, password) {
//     return apiClient.post('/api/v1/users/login', { username, password });
// }

export function loginUser(data) {
    return apiClient.post('/api/v1/users/login', data);
}

export function getPermission(id) {
    return apiClient.get(`/api/v1/permissions/${id}`);
}

export function getPermissions() {
    return apiClient.get('/api/v1/permissions');
}

export function createPermission(data) {
    return apiClient.post('/api/v1/permissions', data); // Make sure data = { code, description }
}

export function updatePermission(id, data) {
    return apiClient.put(`/api/v1/permissions/${id}`, data);
}

export const deletePermission = (id) => {
    return apiClient.delete(`/api/v1/permissions/${id}`);
}
export function getRole(id) {
    return apiClient.get(`/api/v1/roles/${id}`);
}

export function getRoles() {
    return apiClient.get(`/api/v1/roles`);
}

export function createRole(data) {
    return apiClient.post(`/api/v1/roles`, data);
}

export function updateRole(id, data) {
    return apiClient.put(`/api/v1/roles/${id}`, data);
}

export function deleteRole(id) {
    return apiClient.delete(`/api/v1/roles/${id}`);
}

export function getOwner(id) {
    return apiClient.get(`/api/v1/owners/${id}`);
}

export function getOwners() {
    return apiClient.get('/api/v1/owners');
}

export function getVehicleOwners() {
    return apiClient.get('/api/v1/owners');
}

export function createOwner(data) {
    return apiClient.post('/api/v1/owners', data); // Make sure data = { code, description }
}

export function updateOwner(id, data) {
    return apiClient.put(`/api/v1/owners/${id}`, data);
}

export const deleteOwner = (id) => {
    return apiClient.delete(`/api/v1/owners/${id}`);
}

export function getVehicle(id) {
    return apiClient.get(`/api/v1/vehicles/${id}`);
}

export function getVehicles() {
    return apiClient.get('/api/v1/vehicles');
}

export function createVehicle(data) {
    return apiClient.post('/api/v1/vehicles', data);
}

export function updateVehicle(id, data) {
    return apiClient.put(`/api/v1/vehicles/${id}`, data);
}

export const deleteVehicle = (id) => {
    return apiClient.delete(`/api/v1/vehicles/${id}`);
};

export const deleteVehicles = (ids) => {
    return apiClient.post('/api/v1/vehicles/bulk-delete', { ids });
};

export function getDriver(id) {
    return apiClient.get(`/api/v1/drivers/${id}`);
}

export function getDrivers() {
    return apiClient.get('/api/v1/drivers');
}

export function createDriver(data) {
    return apiClient.post('/api/v1/drivers', data);
}

export function updateDriver(id, data) {
    return apiClient.put(`/api/v1/drivers/${id}`, data);
}

export const deleteDriver = (id) => {
    return apiClient.delete(`/api/v1/drivers/${id}`);
};

export const deleteDrivers = (ids) => {
    return apiClient.delete('/api/v1/drivers', { data: ids });
};

export function getEmployees() {
    return apiClient.get('/api/v1/employees');
}

export function getEmployee(id) {
    return apiClient.get(`/api/v1/employees/${id}`);
}

export function createEmployee(data) {
    return apiClient.post('/api/v1/employees', data);
}

export function updateEmployee(id, data) {
    return apiClient.put(`/api/v1/employees/${id}`, data);
}

export const deleteEmployee = (id) => {
    return apiClient.delete(`/api/v1/employees/${id}`);
};

// export function getContract(id) {
//     return apiClient.get(`/api/v1/contracts/${id}`);
// }

export function getContracts() {
    return apiClient.get('/api/v1/contracts');
}

export const getContract = (id) => apiClient.get(`/api/v1/contracts/${id}`);

export function createContract(data) {
    return apiClient.post('/api/v1/contracts', data);
}

export function updateContract(id, data) {
    return apiClient.put(`/api/v1/contracts/${id}`, data);
}

export const deleteContract = (id) => {
    return apiClient.delete(`/api/v1/contracts/${id}`);
};

export function getRoute(id) {
    return apiClient.get(`/api/v1/routes/${id}`);
}

export function getRoutes() {
    return apiClient.get('/api/v1/routes');
}

export function createRoute(data) {
    return apiClient.post('/api/v1/routes', data);
}

export function updateRoute(id, data) {
    return apiClient.put(`/api/v1/routes/${id}`, data);
}

export const deleteRoute = (id) => {
    return apiClient.delete(`/api/v1/routes/${id}`);
};

export const deleteRoutes = (ids) => {
    return apiClient.delete('/api/v1/routes/bulk', { data: ids });
};


export const saveContract = (data) => apiClient.post('/api/v1/contracts', data); // ✅ Fixed


export const getCompanies = () => apiClient.get('/api/v1/companies'); // ✅ Fixed

export const getTrips = () => apiClient.get('/api/v1/trips');
export const getTrip = (id) => apiClient.get(`/api/v1/trips/${id}`);
export const getSeats = (vehicleId) => apiClient.get('/api/v1/seats', { params: { vehicleId } });
export const getSeatAvailability = (tripId, date) =>
    apiClient.get('/api/v1/seats/availability', { params: { trip_id: tripId, date } });
export const getSeatAvailabilityRange = (tripId, startDate, endDate) =>
    apiClient.get('/api/v1/seats/availability-range', {
        params: { trip_id: tripId, start_date: startDate, end_date: endDate }
    });
export const createBooking = (data) => apiClient.post('/api/v1/bookings', data);
export const getBookingsCalendar = (tripId, startDate, endDate) =>
    apiClient.get('/api/v1/bookings/calendar', {
        params: { trip_id: tripId, start_date: startDate, end_date: endDate }
    });

export const getSeatEmployeeList = (tripId) =>
    apiClient.get(`/api/v1/bookings/trip/${tripId}/seat-employee`);

export function getRecentBookings(limit = 5) {
    return apiClient.get(`/api/v1/bookings/recent?limit=${limit}`);
}

export const getRouteStats = () => apiClient.get('/api/v1/routes/stats');





export default {
    loginUser,
    getPermission,
    getPermissions,
    createPermission,
    updatePermission,
    deletePermission,
    getRole,
    getRoles,
    createRole,
    updateRole,
    deleteRole,
    getOwner,
    getOwners,
    createOwner,
    updateOwner,
    deleteOwner,
    getVehicle,
    getVehicles,
    createVehicle,
    updateVehicle,
    deleteVehicle,
    deleteVehicles,
    getDriver,
    getDrivers,
    createDriver,
    updateDriver,
    deleteDriver,
    deleteDrivers,
    getEmployee,
    getEmployees,
    createEmployee,
    updateEmployee,
    deleteEmployee,
    getContract,
    getContracts,
    createContract,
    updateContract,
    deleteContract,
    getRoute,
    getRoutes,
    createRoute,
    updateRoute,
    deleteRoute,
    deleteRoutes,
    saveContract,
    getCompanies,
    getTrips,
    getTrip,
    getSeats,
    getSeatAvailability,
    getSeatAvailabilityRange,
    createBooking,
    getBookingsCalendar,
    getSeatEmployeeList,
    getRecentBookings,
    getRouteStats,

};
