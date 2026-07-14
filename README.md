# Car Service Booking System

## Overview

The Car Service Booking System is a robust microservices-based application built with Spring Boot for the backend and Angular for the frontend. It allows users to book car service packages, manage service categories, and handle user authentication and profiles securely.

## Architecture

The application is composed of the following microservices:

- **api-gateway**: Serves as the entry point for all client requests, routing them to the appropriate microservices. Handles CORS and forwards authentication headers.
- **discovery-server**: Eureka server for service registry and discovery.
- **auth-service**: Manages user registration, login, and token generation.
- **user-service**: Manages customer profiles and vehicle information.
- **catalog-service**: Manages service categories and packages.
- **booking-service**: Handles booking creation, management, and tracking.

The frontend is an Angular 17+ application communicating with the backend via the API Gateway.

## Prerequisites

To run this project locally, ensure you have the following installed:

- **Java**: JDK 21
- **Node.js**: v18.x or higher
- **npm**: v9.x or higher
- **Angular CLI**: v17+ (`npm install -g @angular/cli`)
- **Maven**: 3.8+ (for building backend services)
- **MySQL**: 8.0+

## Database Setup

1. Ensure MySQL is running on `localhost:3306`.
2. Create the necessary databases by running the following SQL commands in your MySQL client:

```sql
CREATE DATABASE auth_db;
CREATE DATABASE user_db;
CREATE DATABASE catalog_db;
CREATE DATABASE booking_db;
```

3. Update the database credentials:
   Each microservice has its own `application.properties` or `application.yml` file located in `src/main/resources/`. You must update the `spring.datasource.username` and `spring.datasource.password` properties in these files to match your local MySQL credentials.

Alternatively, you can provide these as environment variables when running the services.

## Consul Setup

1. Ensure Consul is running locally on `localhost:8500`.
   Download the binary from HashiCorp and run it in development mode:
   ```bash
   consul agent -dev
   ```
2. Configuration properties:
   - The application relies on Consul for distributed configuration (if enabled).
   - Update `spring.cloud.consul.host` and `spring.cloud.consul.port` in the properties file of each microservice if your Consul instance is running on a different host or port.

## Running the Backend

You can start the backend services using Maven. **Order matters!** Start the `discovery-server` first.

1. **Start Discovery Server**:

   ```bash
   cd backend/discovery-server
   mvn spring-boot:run
   ```

   _Wait until it is fully started on port 8761._

2. **Start API Gateway**:

   ```bash
   cd backend/api-gateway
   mvn spring-boot:run
   ```

   _Runs on port 8080._

3. **Start Core Services** (You can start these in any order or in parallel, preferably in separate terminal windows):
   - **Auth Service**:
     ```bash
     cd backend/auth-service
     mvn spring-boot:run
     ```
   - **User Service**:
     ```bash
     cd backend/user-service
     mvn spring-boot:run
     ```
   - **Catalog Service**:
     ```bash
     cd backend/catalog-service
     mvn spring-boot:run
     ```
   - **Booking Service**:
     ```bash
     cd backend/booking-service
     mvn spring-boot:run
     ```

## Running the Frontend

1. Navigate to the frontend directory:
   ```bash
   cd frontend
   ```
2. Install dependencies:
   ```bash
   npm install
   ```
3. Start the Angular development server:
   ```bash
   npm start
   ```
   _The application will be accessible at `http://localhost:4200`._

## System Initialization & Mock Data

When you first run the backend services, Hibernate will automatically create the necessary tables in each database.
Mock data is configured via `data.sql` and `tablescript.sql` scripts in the services to populate default data (like the default Admin user, predefined categories, and packages) for testing.

## Default Credentials

- **Admin**: The system provisions a default admin user. Please check the `data.sql` in `auth-service` for credentials or register a new user and modify their role in the database if testing admin features.
- **Customer**: You can register a new customer via the frontend application.

## API Documentation

The system interacts via REST APIs. The frontend application makes HTTP requests to the API Gateway at `http://localhost:8080`, which then routes the requests to the respective microservices.
