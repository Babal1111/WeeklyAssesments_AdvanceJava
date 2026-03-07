# Online Course Management System

Spring Boot REST API — Case Study Project

---

## Prerequisites

- Java 17+
- Maven 3.8+
- PostgreSQL 14+
- IntelliJ IDEA

---

## Setup Steps

### 1. Create PostgreSQL Database

```sql
CREATE DATABASE cms_db;
```

### 2. Update application.properties

Edit `src/main/resources/application.properties`:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/cms_db
spring.datasource.username=postgres
spring.datasource.password=YOUR_PASSWORD_HERE
```

### 3. Open in IntelliJ

- File → Open → select the `course-management-system` folder
- IntelliJ will auto-detect Maven — let it download dependencies
- Wait for indexing to complete

### 4. Run the Application

- Open `CourseManagementSystemApplication.java`
- Click the green ▶ Run button
- App starts at: http://localhost:8080

---

## Swagger UI

Visit: http://localhost:8080/swagger-ui/index.html

---

## API Endpoints

### Auth / Users
| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | /api/auth/register | Register new user |
| POST | /api/auth/login | Login |
| GET | /api/users/{id} | Get user profile |
| POST | /api/users/{id}/profile-picture | Upload profile pic |

### Courses
| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | /api/courses | Create course |
| PUT | /api/courses/{id} | Update course |
| DELETE | /api/courses/{id} | Delete course |
| GET | /api/courses | List all (paginated) |
| GET | /api/courses/{id} | Get by ID |

Pagination: `GET /api/courses?page=0&size=10&sort=title&direction=asc`

### Enrollments
| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | /api/enrollments | Enroll student |
| GET | /api/enrollments/student/{id} | Student's enrollments |
| GET | /api/enrollments/course/{id} | Course's enrollments |
| PATCH | /api/enrollments/{id}/progress?progress=50 | Update progress |

### Course Materials
| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | /api/materials/upload | Upload file |
| GET | /api/materials/{id}/download | Download file |
| GET | /api/materials/course/{courseId} | List course materials |

---

## Sample Requests (Postman / Swagger)

### Register User
```json
POST /api/auth/register
{
  "fullName": "Rahul Sharma",
  "email": "rahul@example.com",
  "password": "pass123",
  "role": "INSTRUCTOR"
}
```

### Create Course
```json
POST /api/courses
{
  "title": "Spring Boot Masterclass",
  "description": "Learn Spring Boot from scratch",
  "price": 999.0,
  "duration": 40,
  "level": "Beginner",
  "instructorId": 1
}
```

### Enroll Student
```json
POST /api/enrollments
{
  "courseId": 1,
  "studentId": 2
}
```

---

## Project Structure

```
com.learning.cms
├── config/          → Swagger config
├── controllers/     → REST controllers
├── dto/
│   ├── request/     → Input DTOs
│   └── response/    → Output DTOs
├── entity/          → JPA entities
│   └── enums/       → Role, EnrollmentStatus
├── exception/       → Custom exceptions + GlobalExceptionHandler
├── repository/      → JPA repositories
├── service/         → Service interfaces
│   └── impl/        → Service implementations
└── util/            → FileStorageUtil
```

---

## Notes

- Files are uploaded to the `uploads/` folder (auto-created)
- Password encoding is plain-text for now (add BCrypt for production)
- Caching is enabled on `GET /api/courses`
