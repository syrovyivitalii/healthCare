ALTER TABLE patients
    ADD CONSTRAINT unique_doctor_name UNIQUE (first_name, last_name);
