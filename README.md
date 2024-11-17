# ProjectManagementSystem

This project is a **Project Management System** built with **Java Spring Boot**. It features user authentication, role-based authorization, and various functionalities for managing projects, tasks, and comments. The system is backed by a PostgreSQL database and offers a secure API with JWT authentication. It also includes Swagger UI for API documentation.

## Features

- **User Authentication**: Users can register and log in with secure password hashing. JWT tokens are used for authentication.
- **Role-Based Access Control**:
  - **Normal Users** can comment on tasks assigned to them and change their status.
  - **Managers** can create, manage projects, tasks, and assign users to tasks.
  - **Admins** can manage all projects, tasks, and comments.
- **Projects**: Users can create and manage projects, including associating tasks with projects.
- **Tasks**: Tasks are linked to projects and users, and have properties like priority and state. Tasks support a many-to-many relationship with users.
- **Comments**: Users can comment on tasks they are assigned to, and managers or admins can manage all comments.
- **CRUD Operations**: Fully supports Create, Read, Update, and Delete operations for all entities (User, Project, Task, Comment).
- **Swagger UI**: Provides an interactive interface for the API documentation.
- **Spring Security**: Handles authentication and authorization, ensuring proper role access.

## Technologies Used

- **Spring Boot** (with Spring Security, Spring Data JPA)
- **JWT** for token-based authentication
- **PostgreSQL** as the database
- **Swagger UI** for API documentation
- **JUnit** for unit testing
- **Hibernate** for ORM
- **Validation** with Spring's `@Valid` annotation
- **Spring Actuator** for application monitoring

## System Requirements

- Java 17 or higher
- PostgreSQL database
- Maven or Gradle for dependency management

## Getting Started

### 1. Clone the repository

Clone the repository and navigate into the project folder:

- Clone the repository from GitHub.
- Navigate to the `ProjectManagementSystem` directory.

### 2. Set up PostgreSQL database with Docker Compose

In the root directory of the project, there is a `docker-compose.yml` file that sets up a PostgreSQL database. This is the easiest way to get started with the database.
```bash
docker compose up -d
```
This will create a PostgreSQL instance with the default settings.
### 3. Build and run the project

You can build the project using either Maven or Gradle.

- **For Maven**:
  - Build the project with `mvn clean install` to compile the application and dependencies.
  - Run the project with `mvn spring-boot:run` to start the application.

### 4. Access the API

Once the application is running, you can access the API at:

```
http://localhost:8080
```

You can access the Swagger UI at:

```
http://localhost:8080/swagger-ui/index.html
```

### 5. User Roles

- **Normal User**: Can create and manage their own comments, and view tasks assigned to them.
- **Manager**: Can create and manage projects, tasks, and assign users to tasks. Can also browse comments.
- **Admin**: Has full control over all projects, tasks, and comments.

### 6. Authentication and Authorization

- Users can register and log in to obtain a JWT token.
- Use the JWT token in the Authorization header for secure API calls:
  ```
  Authorization: Bearer <your_jwt_token>
  ```

### 7. Endpoints

Here are some of the key API endpoints:

- **POST /auth/register**: Register a new user.
- **POST /auth/login**: Login and get a JWT token.
- **POST /projects**: Create a new project (Manager/Admin).
- **GET /projects**: Get all projects.
- **GET /projects/{id}**: Get a specific project by ID.
- **POST /tasks**: Create a new task (Manager/Admin).
- **POST /tasks/{taskId}/users**: Assign users to a task (Manager/Admin).
- **POST /tasks/{taskId}/comments**: Add a comment to a task.
- **GET /tasks/{taskId}/comments**: Get all comments on a task.
- **PUT /tasks/{taskId}/status**: Update task status (Manager/Admin).
- **DELETE /comments/{commentId}**: Delete a comment (Owner/Admin).

### 8. Testing

The project includes unit tests for key functionalities. You can run the tests using Maven or Gradle.

### 9. API Documentation

Swagger UI is available at:

```
http://localhost:8080/swagger-ui/index.html
```

It provides interactive documentation of all available endpoints.
