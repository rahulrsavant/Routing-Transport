# UI Design Overview

This document outlines the proposed UI screens and behavior for the Routing-Transport system. Each screen is kept intentionally simple while providing basic CRUD functionality.

## Login
- Username/email and password input fields
- On submit, authenticate the user and fetch their roles and permissions
- Store the user object (including roles/permissions) in a Pinia store persisted to `localStorage`

## User Management
1. **User List**
   - Table of all users with basic info
   - "New User" button to navigate to the create form
2. **User Create/Edit**
   - Form for username, email, mobile, password, status
   - Assign one or more roles via checkboxes
   - Assign individual permissions if needed
3. **User Detail/Profile**
   - Display user information
   - List their companies and roles

## Role Management
1. **Role List**
   - Show existing roles
   - Button to add new roles
2. **Role Create/Edit**
   - Form for role name
   - Select permissions via checkboxes

## Permission Management
- List all available permissions
- Allow creation of new permissions
- Assign permissions to roles or directly to users

## Company Management
- CRUD screens for companies
- Permissions gate: only users with the appropriate permission see edit/delete buttons

## Vehicle, Driver, Route, Trip and Seat Screens
- Basic CRUD screens for each entity
- Example: an "Add Vehicle" button is only visible if the user has `CREATE_VEHICLE`

```html
<!-- Example permission-based button -->
<button v-if="user.permissions.includes('CREATE_VEHICLE')">Add Vehicle</button>
```

## Reports
- Screen to view or export reports
- Visible only if the user has `VIEW_REPORT` or `EXPORT_REPORT`

## Company as Tenant
The existing Company entity acts as the tenant/organization in this demo.
If multi-tenancy is needed in the future, additional screens can be added,
but there is no separate Tenant entity at this time.

## Notes
- Authentication remains simple using the existing password logic
- Ensure `user_roles` and `role_permissions` join tables are populated when saving
- Pinia store persists roles and permissions so the UI can check them without extra calls

