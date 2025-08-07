-- Migration to link users to employees, drivers, or vehicle owners
ALTER TABLE users
    ADD COLUMN employee_id BIGINT NULL,
    ADD COLUMN driver_id BIGINT NULL,
    ADD COLUMN owner_id BIGINT NULL,
    ADD CONSTRAINT fk_users_employee FOREIGN KEY (employee_id) REFERENCES employees(id),
    ADD CONSTRAINT fk_users_driver FOREIGN KEY (driver_id) REFERENCES drivers(id),
    ADD CONSTRAINT fk_users_owner FOREIGN KEY (owner_id) REFERENCES vehicle_owners(id);
