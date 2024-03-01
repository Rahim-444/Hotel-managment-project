# Hotel Management System with Login Functionality

## Overview
This document outlines the design and implementation of a hotel management system with login functionality. The system is developed using Java Swing for the GUI and follows the MVC architecture.

## Components
### Model
- **User**: Represents a user with attributes like username, password, role, etc.

### View
- **LoginView**: GUI window for user login.

### Controller
- **LoginController**: Controller for managing user authentication.

### File Management
- **UserFileHandler**: Handles reading from and writing to a file for storing user credentials.

## Functionality
1. **User Authentication**
   - Validate user credentials during login.

## File Storage
- **user_credentials.txt**: Stores encrypted user credentials.

## Implementation Steps
1. Create a `User` class to represent user entities with attributes like username, password, role, etc.
2. Design a `LoginView` GUI window where users can input their username and password.
3. Implement a `LoginController` to handle user authentication.
4. Write methods in `UserFileHandler` to read and write user credentials to a file.
5. Integrate user authentication logic into your application's main workflow.

## Conclusion
This login system enhances the security of the hotel management system by allowing only authorized users to access the application. It provides a robust authentication mechanism while maintaining simplicity and usability.


