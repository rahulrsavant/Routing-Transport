-- Fetch seat bookings with seat number and employee details
SELECT b.id,
       s.seat_number,
       e.id   AS employee_id,
       e.name AS employee_name
FROM bookings b
JOIN seats s ON b.seat_id = s.id
JOIN users u ON b.user_id = u.id
JOIN employees e ON u.employee_id = e.id
WHERE u.id = ?; -- Replace ? with the user id
