This project is a full-stack task management system built with a Spring Boot backend Which uses Postgre Sql and an Android Studio frontend.
It provides secure user authentication, project management, and team collaboration features.

Backend Rep : https://github.com/Priynshu2341/realtimetaskmanagement

Language: Kotlin
Framework: Android Studio
Architecture: MVVM (Model–View–ViewModel)
Database: SQLite (Room) not used in This Project 
Networking: Retrofit + OkHttp + Gson using Hilt.
UI: Jetpack Compose Material Design Components Follows Paging 3 for all ui like Project Task and Comments.
Backend Integration: RESTful APIs (Spring Boot)
Tools: Android Studio, Git, Gradle, Postman

🚀 Key Features

🔐 Authentication & Authorization
Secure login and registration using JWT tokens from backend.
Automatic refresh token flow handled for seamless user experience.
Role-based UI behavior (Admin vs User).

🧱 Project & Task Management
Admins can create projects, assign users, and manage tasks update task status and Priority.
Users can view assigned projects, and comment on tasks .

💬 Real-Time Collaboration
Comment section for each task for team discussions.
Synchronized data updates with backend via REST API.

⚙️ Local Storage & Caching
Uses Paging 3 Paging Source Factory From improved Performance and Better Results .
Data fetched from backend is cached locally for faster loading.

🎨 Modern UI
Clean Material Design UI with smooth navigation and responsive layouts.
Uses Jetpack Compose For better and Good Ui .

💡 Highlights
Built using clean architecture (MVVM) for scalability and maintainability.
Implements JWT-based secure communication between app and backend.
Optimized for performance with Room caching and Retrofit error handling.
Follows modern Android development best practices.
