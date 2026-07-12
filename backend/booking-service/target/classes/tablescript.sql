CREATE DATABASE IF NOT EXISTS booking_db;
USE booking_db;
CREATE TABLE IF NOT EXISTS booking (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    reference_number VARCHAR(100) UNIQUE NOT NULL,
    customer_id BIGINT NOT NULL,
    vehicle_id BIGINT NOT NULL,
    service_id BIGINT NOT NULL,
    appointment_date DATE NOT NULL,
    time_slot VARCHAR(50) NOT NULL,
    problem_description TEXT,
    status VARCHAR(50) NOT NULL
);
INSERT IGNORE INTO booking (id, reference_number, customer_id, vehicle_id, service_id, appointment_date, time_slot, problem_description, status) VALUES (1, 'BKG-12345', 1, 1, 1, '2026-08-01', '09:00 AM', 'Regular service', 'CONFIRMED');
INSERT IGNORE INTO booking (id, reference_number, customer_id, vehicle_id, service_id, appointment_date, time_slot, problem_description, status) VALUES (2, 'BKG-67890', 1, 2, 3, '2026-08-05', '02:00 PM', 'Engine making noise', 'PENDING');
INSERT IGNORE INTO booking (id, reference_number, customer_id, vehicle_id, service_id, appointment_date, time_slot, problem_description, status) VALUES (3, 'BKG-11223', 1, 3, 2, '2026-07-10', '10:00 AM', 'Tires worn out', 'COMPLETED');
INSERT IGNORE INTO booking (id, reference_number, customer_id, vehicle_id, service_id, appointment_date, time_slot, problem_description, status) VALUES (4, 'BKG-99887', 2, 1, 4, '2026-08-15', '11:00 AM', 'Need spark plug change', 'CANCELLED');
