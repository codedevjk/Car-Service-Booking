$ErrorActionPreference = "Stop"

$GATEWAY_URL = "http://localhost:8080"
$HEADERS = @{ "Content-Type" = "application/json" }

Write-Host "========================================"
Write-Host "Starting E2E Verification Scenario"
Write-Host "========================================"

# Step 1: Customer Registration
Write-Host "`n[Step 1] Registering new customer (Rahul Sharma)..."
$registerBody = @{
    fullName = "Rahul Sharma"
    email    = "rahul@gmail.com"
    password = "rahul1234"
} | ConvertTo-Json

try {
    $regResponse = Invoke-RestMethod -Method Post -Uri "$GATEWAY_URL/api/auth/register" -Headers $HEADERS -Body $registerBody
    Write-Host "Registration Successful: $($regResponse.message)" -ForegroundColor Green
}
catch {
    Write-Host "Registration failed: $_" -ForegroundColor Red
}

# Negative Check: Duplicate Email
Write-Host "`n[Step 14] Verifying duplicate email rejection..."
try {
    Invoke-RestMethod -Method Post -Uri "$GATEWAY_URL/api/auth/register" -Headers $HEADERS -Body $registerBody
    Write-Host "ERROR: Duplicate email was NOT rejected!" -ForegroundColor Red
}
catch {
    Write-Host "Success: Duplicate email rejected correctly." -ForegroundColor Green
}

# Step 2: Customer Login
Write-Host "`n[Step 2] Customer Login..."
$loginBody = @{
    email    = "rahul@gmail.com"
    password = "rahul1234"
} | ConvertTo-Json

try {
    $loginResponse = Invoke-RestMethod -Method Post -Uri "$GATEWAY_URL/api/auth/login" -Headers $HEADERS -Body $loginBody
    $CUSTOMER_ID = $loginResponse.userId
    Write-Host "Login Successful! Customer ID: $CUSTOMER_ID, Role: $($loginResponse.role)" -ForegroundColor Green
}
catch {
    Write-Host "Login failed: $_" -ForegroundColor Red
    exit
}

# Set customer header
$CUSTOMER_HEADERS = @{ "Content-Type" = "application/json"; "X-User-Id" = $CUSTOMER_ID; "X-User-Role" = "CUSTOMER" }

# Step 3: Profile Verification
Write-Host "`n[Step 3] Fetching Profile..."
try {
    $profile = Invoke-RestMethod -Method Get -Uri "$GATEWAY_URL/api/users/profile/$CUSTOMER_ID" -Headers $CUSTOMER_HEADERS
    Write-Host "Profile fetched: $($profile.fullName) - $($profile.email)" -ForegroundColor Green
}
catch {
    Write-Host "Profile fetch failed: $_" -ForegroundColor Red
}

# Step 4: Vehicle Registration
Write-Host "`n[Step 4] Vehicle Registration..."
$vehicleBody = @{
    registrationNumber = "KA09AB1234"
    manufacturer       = "Hyundai"
    model              = "i20"
    fuelType           = "PETROL"
    manufacturingYear  = 2022
    color              = "White"
} | ConvertTo-Json

try {
    $vehicleResponse = Invoke-RestMethod -Method Post -Uri "$GATEWAY_URL/api/vehicles" -Headers $CUSTOMER_HEADERS -Body $vehicleBody
    $VEHICLE_ID = $vehicleResponse.id
    Write-Host "Vehicle registered with ID: $VEHICLE_ID" -ForegroundColor Green
}
catch {
    Write-Host "Vehicle registration failed: $_" -ForegroundColor Red
}

# Negative Check: Duplicate Vehicle
Write-Host "`n[Step 14] Verifying duplicate vehicle rejection..."
try {
    Invoke-RestMethod -Method Post -Uri "$GATEWAY_URL/api/vehicles" -Headers $CUSTOMER_HEADERS -Body $vehicleBody
    Write-Host "ERROR: Duplicate vehicle was NOT rejected!" -ForegroundColor Red
}
catch {
    Write-Host "Success: Duplicate vehicle rejected correctly." -ForegroundColor Green
}

# Step 5: Admin Login
Write-Host "`n[Step 5] Administrator Login..."
$adminLoginBody = @{
    email    = "admin@gmail.com"
    password = "admin1234"
} | ConvertTo-Json

try {
    $adminResponse = Invoke-RestMethod -Method Post -Uri "$GATEWAY_URL/api/auth/login" -Headers $HEADERS -Body $adminLoginBody
    $ADMIN_ID = $adminResponse.userId
    Write-Host "Admin Login Successful! User ID: $ADMIN_ID, Role: $($adminResponse.role)" -ForegroundColor Green
}
catch {
    Write-Host "Admin Login failed: $_" -ForegroundColor Red
    exit
}

$ADMIN_HEADERS = @{ "Content-Type" = "application/json"; "X-User-Id" = $ADMIN_ID; "X-User-Role" = "ADMIN" }

# Step 6 & 7: Category and Service Verification
Write-Host "`n[Step 6 & 7] Admin verifies categories and services..."
try {
    $categories = Invoke-RestMethod -Method Get -Uri "$GATEWAY_URL/api/categories" -Headers $ADMIN_HEADERS
    Write-Host "Found $($categories.Count) categories." -ForegroundColor Green
    
    $services = Invoke-RestMethod -Method Get -Uri "$GATEWAY_URL/api/service-packages" -Headers $ADMIN_HEADERS
    $SERVICE_ID = $services.content[0].id
    Write-Host "Found $($services.content.Count) services. Using Service ID $SERVICE_ID for booking." -ForegroundColor Green
}
catch {
    Write-Host "Category/Service fetch failed: $_" -ForegroundColor Red
}

# Step 9: Create Booking
Write-Host "`n[Step 9] Customer Creates Booking..."
$futureDate = (Get-Date).AddDays(10).ToString("yyyy-MM-dd")
$bookingBody = @{
    customerId         = $CUSTOMER_ID
    vehicleId          = $VEHICLE_ID
    serviceId          = $SERVICE_ID
    appointmentDate    = $futureDate
    timeSlot           = "10:00 AM"
    problemDescription = "Engine oil replacement and routine inspection."
} | ConvertTo-Json

try {
    $bookingResponse = Invoke-RestMethod -Method Post -Uri "$GATEWAY_URL/api/bookings" -Headers $CUSTOMER_HEADERS -Body $bookingBody
    $BOOKING_ID = $bookingResponse.id
    Write-Host "Booking created successfully! Booking ID: $BOOKING_ID, Status: $($bookingResponse.status)" -ForegroundColor Green
}
catch {
    Write-Host "Booking failed: $_" -ForegroundColor Red
}

# Negative Check: Duplicate Booking
Write-Host "`n[Step 14] Verifying duplicate booking rejection..."
try {
    Invoke-RestMethod -Method Post -Uri "$GATEWAY_URL/api/bookings" -Headers $CUSTOMER_HEADERS -Body $bookingBody
    Write-Host "ERROR: Duplicate booking was NOT rejected!" -ForegroundColor Red
}
catch {
    Write-Host "Success: Duplicate booking rejected correctly." -ForegroundColor Green
}

# Negative Check: Past Date Booking
Write-Host "`n[Step 14] Verifying past date booking rejection..."
$pastDate = (Get-Date).AddDays(-5).ToString("yyyy-MM-dd")
$pastBookingBody = @{
    customerId         = $CUSTOMER_ID
    vehicleId          = $VEHICLE_ID
    serviceId          = $SERVICE_ID
    appointmentDate    = $pastDate
    timeSlot           = "12:00 PM"
    problemDescription = "Past date test"
} | ConvertTo-Json
try {
    Invoke-RestMethod -Method Post -Uri "$GATEWAY_URL/api/bookings" -Headers $CUSTOMER_HEADERS -Body $pastBookingBody
    Write-Host "ERROR: Past date booking was NOT rejected!" -ForegroundColor Red
}
catch {
    Write-Host "Success: Past date booking rejected correctly." -ForegroundColor Green
}

# Step 10: Admin Processes Booking
Write-Host "`n[Step 10] Admin Processes Booking to COMPLETED..."
try {
    Invoke-RestMethod -Method Patch -Uri "$GATEWAY_URL/api/bookings/$BOOKING_ID/status?status=CONFIRMED" -Headers $ADMIN_HEADERS
    Invoke-RestMethod -Method Patch -Uri "$GATEWAY_URL/api/bookings/$BOOKING_ID/status?status=IN_SERVICE" -Headers $ADMIN_HEADERS
    Invoke-RestMethod -Method Patch -Uri "$GATEWAY_URL/api/bookings/$BOOKING_ID/status?status=READY_FOR_DELIVERY" -Headers $ADMIN_HEADERS
    $completedResponse = Invoke-RestMethod -Method Patch -Uri "$GATEWAY_URL/api/bookings/$BOOKING_ID/status?status=COMPLETED" -Headers $ADMIN_HEADERS
    Write-Host "Booking processed successfully! Final Status: $($completedResponse.status)" -ForegroundColor Green
}
catch {
    Write-Host "Booking processing failed: $_" -ForegroundColor Red
}

Write-Host "`n========================================"
Write-Host "E2E Verification Script Completed!"
Write-Host "========================================"
