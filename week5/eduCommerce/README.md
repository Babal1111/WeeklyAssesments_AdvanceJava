# EduCommerce - Student Performance & Attendance Management System
## Spring Boot Microservices Architecture

---

## Services & Ports

| Service            | Port |
|--------------------|------|
| Config Server      | 8888 |
| Eureka Server      | 8761 |
| API Gateway        | 8080 |
| Student Service    | 8081 |
| Attendance Service | 8082 |
| Result Service     | 8083 |

---

## Startup Order

Start services in this exact order:

1. `config-server`
2. `eureka-server`
3. `api-gateway`
4. `student-service`
5. `attendance-service`
6. `result-service`

---

## Environment Variables (Optional)

Set these before running for production:

```
DB_PASSWORD=yourpassword
JWT_SECRET=your-secret-key-minimum-32-chars
ZIPKIN_URL=http://localhost:9411
```

Defaults work out-of-the-box for local development.

---

## Build & Run

```bash
# Build each service
cd config-server && mvn clean install -DskipTests
cd ../eureka-server && mvn clean install -DskipTests
cd ../api-gateway && mvn clean install -DskipTests
cd ../student-service && mvn clean install -DskipTests
cd ../attendance-service && mvn clean install -DskipTests
cd ../result-service && mvn clean install -DskipTests

# Run each service
mvn spring-boot:run
```

---

## API Endpoints (via Gateway on port 8080)

### Auth (Public - no token required)
```
POST /auth/register   - Register student
POST /auth/login      - Login, returns JWT
```

### Students (JWT required)
```
GET    /students
GET    /students/{id}
PUT    /students/{id}
DELETE /students/{id}
```

### Courses
```
POST   /courses
GET    /courses
PUT    /courses/{id}
DELETE /courses/{id}
```

### Enrollment
```
POST /enroll                      - Body: { studentId, courseId }
GET  /students/{id}/courses
```

### Attendance
```
POST   /attendance                             - Mark attendance
GET    /attendance/student/{studentId}
GET    /attendance/course/{courseId}
PUT    /attendance/{id}
DELETE /attendance/{id}
GET    /attendance/summary/student/{id}/course/{id}
```

### Results
```
POST   /results
GET    /results/student/{studentId}
GET    /results/course/{courseId}
PUT    /results/{id}
DELETE /results/{id}
GET    /results/performance/student/{id}/course/{id}
```

---

## Using the API

### 1. Register
```json
POST /auth/register
{
  "name": "John Doe",
  "email": "john@example.com",
  "password": "password123",
  "department": "Computer Science",
  "semester": 3
}
```

### 2. Login
```json
POST /auth/login
{
  "email": "john@example.com",
  "password": "password123"
}
// Returns: { "token": "eyJ..." }
```

### 3. Use Token
```
Authorization: Bearer eyJ...
```

---

## Distributed Tracing

Run Zipkin locally:
```bash
docker run -d -p 9411:9411 openzipkin/zipkin
```
Open: http://localhost:9411

---

## H2 Console (per service)
- Student:    http://localhost:8081/h2-console  (JDBC: jdbc:h2:mem:studentdb)
- Attendance: http://localhost:8082/h2-console  (JDBC: jdbc:h2:mem:attendancedb)
- Result:     http://localhost:8083/h2-console  (JDBC: jdbc:h2:mem:resultdb)

---

## Architecture

```
Client (Postman)
       |
  API Gateway :8080  (JWT Filter + Load Balancing)
       |
  -----+------------+---------------+
  |                 |               |
Student :8081  Attendance :8082  Result :8083
  |                 |               |
  +--------Eureka Server :8761------+
                    |
             Config Server :8888
                    |
           (classpath configurations)
```
