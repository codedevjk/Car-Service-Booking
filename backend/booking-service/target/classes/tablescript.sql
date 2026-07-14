CREATE DATABASE IF NOT EXISTS booking_db;
USE booking_db;
CREATE TABLE IF NOT EXISTS booking (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    reference_number VARCHAR(100) UNIQUE NOT NULL,
    customer_id VARCHAR(255) NOT NULL,
    vehicle_id BIGINT NOT NULL,
    service_id BIGINT NOT NULL,
    appointment_date DATE NOT NULL,
    time_slot VARCHAR(50) NOT NULL,
    problem_description TEXT,
    status VARCHAR(50) NOT NULL
);
-- Rahul's bookings
INSERT IGNORE INTO booking (id, reference_number, customer_id, vehicle_id, service_id, appointment_date, time_slot, problem_description, status) VALUES (1, 'BKG-10001', 'U1', 1, 1, '2023-05-10', '09:00 AM - 10:00 AM', 'Regular service', 'COMPLETED');
INSERT IGNORE INTO booking (id, reference_number, customer_id, vehicle_id, service_id, appointment_date, time_slot, problem_description, status) VALUES (2, 'BKG-10002', 'U1', 2, 3, '2023-11-15', '11:00 AM - 12:00 PM', 'Engine making noise', 'COMPLETED');
INSERT IGNORE INTO booking (id, reference_number, customer_id, vehicle_id, service_id, appointment_date, time_slot, problem_description, status) VALUES (3, 'BKG-10003', 'U1', 3, 2, '2026-08-10', '10:00 AM - 11:00 AM', 'Tires worn out', 'CONFIRMED');

-- Priya's bookings
INSERT IGNORE INTO booking (id, reference_number, customer_id, vehicle_id, service_id, appointment_date, time_slot, problem_description, status) VALUES (4, 'BKG-20001', 'U2', 4, 1, '2024-01-20', '02:00 PM - 03:00 PM', 'EV checkup', 'COMPLETED');
INSERT IGNORE INTO booking (id, reference_number, customer_id, vehicle_id, service_id, appointment_date, time_slot, problem_description, status) VALUES (5, 'BKG-20002', 'U2', 5, 4, '2026-08-15', '11:00 AM - 12:00 PM', 'Need spark plug change', 'PENDING');
INSERT IGNORE INTO booking (id, reference_number, customer_id, vehicle_id, service_id, appointment_date, time_slot, problem_description, status) VALUES (6, 'BKG-20003', 'U2', 6, 2, '2026-08-20', '03:00 PM - 04:00 PM', 'Tire rotation and alignment', 'CONFIRMED');

-- Amit's bookings
INSERT IGNORE INTO booking (id, reference_number, customer_id, vehicle_id, service_id, appointment_date, time_slot, problem_description, status) VALUES (7, 'BKG-30001', 'U3', 7, 3, '2026-07-25', '12:00 PM - 01:00 PM', 'Check engine light on', 'CANCELLED');
INSERT IGNORE INTO booking (id, reference_number, customer_id, vehicle_id, service_id, appointment_date, time_slot, problem_description, status) VALUES (8, 'BKG-30002', 'U3', 8, 1, '2026-08-05', '09:00 AM - 10:00 AM', 'Oil change required', 'PENDING');
INSERT IGNORE INTO booking (id, reference_number, customer_id, vehicle_id, service_id, appointment_date, time_slot, problem_description, status) VALUES (9, 'BKG-30003', 'U3', 9, 2, '2026-08-12', '10:00 AM - 11:00 AM', 'Routine checkup', 'CONFIRMED');
