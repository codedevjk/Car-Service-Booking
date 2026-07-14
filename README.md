# Car Service Booking System

A robust, microservices-based application for managing car service appointments. This project features a Spring Boot backend cluster (Eureka Discovery, API Gateway, and four core microservices) and an Angular frontend, designed with strict role-based access control and cross-service validations.

## Architecture Overview

The system is built using a microservices architecture:
*   **Discovery Server (Port 8761):** Netflix Eureka Server for service registration and discovery.
*   **API Gateway (Port 8080):** Spring Cloud Gateway that handles routing and securely passes `X-User-Id` and `X-User-Role` HTTP headers to downstream services.
*   **Auth Service (Port 8081):** Manages user credentials, authenticates logins against the database, and issues session tokens.
*   **User Service (Port 8082):** Manages customer profiles and vehicle registries.
*   **Catalog Service (Port 8083):** Manages service categories and car service packages.
*   **Booking Service (Port 8084):** Manages the core booking state machine and cross-service validations.
*   **Frontend UI (Port 4200):** Angular Single Page Application.

---

## Prerequisites

Before setting up the project, ensure you have the following installed on your machine:
1.  **Java Development Kit (JDK):** Version 21
2.  **Node.js & npm:** Node v18+ and npm v9+ (for Angular frontend)
3.  **Maven:** To build the Java microservices
4.  **MySQL Server:** Version 8.0+ running locally on port `3306`

---

## Setup & Configuration

### 1. Database Setup

You must create the underlying MySQL databases before starting the backend services. Spring Boot's `ddl-auto=update` will automatically generate the tables.

Open your MySQL client or terminal and run the following commands:
```sql
CREATE DATABASE auth_db;
CREATE DATABASE user_db;
CREATE DATABASE catalog_db;
CREATE DATABASE booking_db;
```

*Note: Ensure your MySQL root credentials match the properties in the microservices' `application.properties` (typically `root` / `root` or `root` / `password`). Update the `.properties` files in `src/main/resources` of each microservice if your local MySQL credentials differ.*

### 2. Building and Running the Backend Microservices

The backend consists of multiple Spring Boot applications that must be started in a specific order.

**Step 2.1: Build all modules**
Open a terminal in the root `backend` folder and run:
```bash
mvn clean install -DskipTests
```

**Step 2.2: Start the microservices**
Open a separate terminal window for **each** of the following services and run them in this exact order:

1.  **Discovery Server** (Must start first so others can register)
    ```bash
    cd backend/discovery-server
    mvn spring-boot:run
    ```
2.  **API Gateway**
    ```bash
    cd backend/api-gateway
    mvn spring-boot:run
    ```
3.  **Auth Service**
    ```bash
    cd backend/auth-service
    mvn spring-boot:run
    ```
4.  **User Service**
    ```bash
    cd backend/user-service
    mvn spring-boot:run
    ```
5.  **Catalog Service**
    ```bash
    cd backend/catalog-service
    mvn spring-boot:run
    ```
6.  **Booking Service**
    ```bash
    cd backend/booking-service
    mvn spring-boot:run
    ```

*Verify:* You can check that all services have successfully registered by opening your browser and navigating to the Eureka Dashboard at `http://localhost:8761`.

### 3. Setting Up the Angular Frontend

Once the backend cluster is fully running, you can start the frontend.

1.  Open a new terminal and navigate to the frontend folder:
    ```bash
    cd frontend/angular-app
    ```
2.  Install the Node dependencies:
    ```bash
    npm install
    ```
3.  Start the Angular development server:
    ```bash
    npm start
    ```
    *(or use `ng serve`)*

### 4. Accessing the Application

Open your browser and navigate to:
**http://localhost:4200**

#### Default Admin Credentials
The system comes pre-seeded with a strictly locked Admin account. Do not attempt to register a new admin.
*   **Email:** admin@gmail.com
*   **Password:** admin1234

#### Customer Registration
To test the customer flow, simply click "Register" on the login screen, create a new customer account, and log in to explore the Dashboard, Vehicle Registry, and Booking workflows.

---

## Important Business Rules (System Constraints)
*   **Isolated Data:** Customers can only view and manage their own profiles, vehicles, and bookings. Admins have read-only access to customer data but full write access to the Service Catalog and Booking states.
*   **Safe Deletion:** An Admin cannot delete a Service Category if it contains active services. Furthermore, an Admin cannot deactivate a Service Package if a customer currently has an active booking associated with it.
*   **State Machine:** Bookings strictly follow `PENDING -> CONFIRMED -> IN_SERVICE -> READY_FOR_DELIVERY -> COMPLETED`. Customers may only cancel a booking while it is in the `PENDING` state.
