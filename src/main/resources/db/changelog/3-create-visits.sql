CREATE TABLE visits (
    id BINARY(16) NOT NULL PRIMARY KEY,
    start_date_time TIMESTAMP NOT NULL,
    end_date_time TIMESTAMP NOT NULL,
    patient_id BINARY(16) NOT NULL,
    doctor_id BINARY(16) NOT NULL,
    FOREIGN KEY (patient_id) REFERENCES patients(id),
    FOREIGN KEY (doctor_id) REFERENCES doctors(id),
    CONSTRAINT unique_visit UNIQUE (doctor_id, start_date_time)
);