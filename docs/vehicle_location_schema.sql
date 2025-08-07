CREATE TABLE vehicle_locations (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  trip_id BIGINT NOT NULL,
  employee_id BIGINT,
  is_driver BOOLEAN NOT NULL,
  latitude DOUBLE NOT NULL,
  longitude DOUBLE NOT NULL,
  timestamp TIMESTAMP NOT NULL,
  CONSTRAINT fk_vehicle_location_trip FOREIGN KEY (trip_id) REFERENCES trips(id),
  CONSTRAINT fk_vehicle_location_employee FOREIGN KEY (employee_id) REFERENCES employees(id)
);
