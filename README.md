# 🏥 Health Care Visit Tracking

**A system for tracking doctor visits by patients, managing appointments efficiently.**

## ⚙️ Getting Started
### Prerequisites
* Docker installed

### Installation
* Clone the Repository:

`git clone https://github.com/syrovyivitalii/healthCare.git`

`cd .\health-care\`

### Run the Application

`docker-compose up --build`

## 📄 Usage

### Swagger
* Access the REST API documentation using [Swagger](http://localhost:8080/swagger-ui/index.html#/)

 Example endpoints:

- Visits
    - **POST:** `/api/v1/visits`: Create a visit
- Patients
    - **GET:** `/api/v1/patients/pageable`: Fetch paginated patient list with filters
    - **POST:** `/api/v1/patients`: Add a patient
- Doctors
    - **GET:** `/api/v1/doctors/pageable`:  Fetch paginated list of doctors with filters
    - **POST:** `/api/v1/doctors`: Add a doctor
    - **PATCH:** `/api/v1/doctors/{doctorId}`: Update doctor details

## Visit Creation Payload
    `{
        "start": "YYYY-MM-DDTHH:mm:ss",
        "end": "YYYY-MM-DDTHH:mm:ss",
        "patientId": "UUID",
        "doctorId": "UUID"
    }`

## 🛠 Technologies Used

* Java 17
* Maven
* Spring Framework 3
* Hibernate
* Liquibase
* MySQL
* Docker
* REST
* MapStruct
* Lombok
* Pageable
* search Specification
* GlobalExceptionHandler
* Database loader
