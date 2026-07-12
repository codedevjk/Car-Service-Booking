# Car Service Booking System - Starter Kit

## Project Structure
This repository contains the Starter Kit for the Car Service Booking System Capstone Project. It consists of two main folders:
- `backend/`: Contains five independent Spring Boot microservices.
- `frontend/`: Contains the Angular 15 web application.

### Backend Microservices
1. **gateway-service (Port: 8080)** - Handles routing and initial JWT validation.
2. **auth-service (Port: 8081)** - Authentication and Registration (US01).
3. **user-service (Port: 8082)** - Customer Profile and Vehicle Management (US02, US03).
4. **catalog-service (Port: 8083)** - Service Categories and Car Services (US04, US05, US06).
5. **booking-service (Port: 8084)** - Bookings and Dashboard (US07, US08, US09, US10).

## Infrastructure Setup (No Docker Required)

### 1. Spring Cloud Consul
You will need Consul running locally for Service Discovery.
- Download the Consul executable for Windows.
- Run `consul agent -dev` in your command prompt.
- Consul will run on port `8500`.

### 2. MySQL Database
You must have MySQL running locally on port `3306` with the username `root` and password `root` (or update `application.properties`).
To initialize the database for a microservice:
1. Open a command prompt and run `mysql -u root -p`.
2. For each microservice (e.g., `auth-service`, `user-service`), open the `src/main/resources/tablescript.sql` file.
3. Run the script in your MySQL console. This will create the database, tables, and insert default sample data.

### 3. Running the Backend Services
Since these are independent Maven projects, open a terminal in each microservice folder (`backend/auth-service`, etc.) and run:
```bash
mvn spring-boot:run
```
*Note: Start `consul` first, then `gateway-service`, then the other microservices.*

### 4. Running the Frontend Angular App
Navigate to `frontend/angular-app`:
```bash
npm install
ng serve
```
Access the application at `http://localhost:4200`.

## Remaining User Stories for Trainees
Trainees are expected to fully implement the following features. Skeleton controllers and DTOs have been provided with `TODO` comments.
- **US05**: Car Service Management
- **US06**: Browse & Search Services
- **US07**: Car Service Booking
- **US08**: Booking Management
- **US09**: Dashboard & Booking History
- **US10**: Booking Search & Reports

Also complete the corresponding Skeleton Modules in the Angular Frontend.
