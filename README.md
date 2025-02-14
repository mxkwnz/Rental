Car Rental System (CRS) 🚗✨
Overview
The Car Rental System (CRS) is a Java-based application designed to manage car rentals. It includes functionalities like car management, rental tracking, customer and staff management, overdue fine calculation, and more. The system aims to streamline the workflow for both customers and administrators, ensuring a smooth experience for renting cars and managing rentals.

Features 🌟
1. Car Management 🚗
Add Cars: Admins can add new cars to the system by providing details such as:

Car ID (unique identifier)
Model
Brand
Availability status (Available, Rented)
Rental price per day
Update Cars: Admins and staff can update the car details, such as availability status or rental price.

Delete Cars: Remove cars from the system that are no longer available or in service.

Search Cars by ID: Users and admins can search for cars using their unique IDs for quick access to their details.

2. Customer Management 👥
Add Customers: Admins can register new customers with:
ID (unique identifier)
Full Name
Email Address
Password (for login)
Login/Sign-Up: Customers can sign up and log in to manage their rentals, view available cars, and make reservations.
3. Staff Management 👨‍💼👩‍💼
Add Staff: Admins can add staff members with:

Name
Surname
Password (for login)
Staff Actions: Staff can handle car rental requests, manage car availability, and track rental statuses.

4. Rental Management 🔄
Track Rentals: Admins and staff can view all active rentals, including the customer’s details, car rented, and due dates.

Rent Cars: Customers can rent available cars by selecting a car from the catalog. The system records the rental date, due date, and payment details.

Return Cars: Customers can return cars, and the system automatically updates the car’s availability, tracks rental duration, and calculates overdue fines if applicable.

Due Date Calculation: The system automatically calculates the due date based on the library's policy (e.g., 7 days from the rental date). If a car is returned late, the system applies an overdue fine.

5. Overdue Fines 💰
Late Fees: If a car is returned after the due date, the system calculates overdue fines based on the number of late days and applies it to the customer’s account.
