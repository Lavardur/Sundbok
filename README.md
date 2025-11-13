# Sundbok - Swimming Pool Review Application

## Project Plan & Schedule

| **Week**          | **Use Cases**                   | **Expected Hours** | **P.O**                         | **Sprint** | **Consultation**                        |
|--------------------|---------------------------------|--------------------|----------------------------------|-------------|------------------------------------------|
| 1 (Sept. 7–14)     | UC1, UC2, Post                 | 30 | MSK | 1 | A1 Presentation |
| 2 (Sept. 14–21)    | UC1, UC2, Post                 | 30 | SAB | 1 | Model drafts |
| 3 (Sept. 21–28)    | UC2, UC3, Get                 | 25 | ANB — Turn in for A2 (SAB) | 2 | A2 Turn in & Final touches |
| 4 (Sept. 28–Oct. 5)| UC3, Get                       | 25 | ANB | 2 | A2 Presentation |
| 5 (Oct. 5–12)      | BUC 4,5&6, Patch                      | 25 | ANB | 3 | DEV support |
| 6 (Oct. 12–19)     | BUC 7,8&9, Patch                      | 25 | AES (TURN IN ANB) | 3 | A3 Turn in & Final touches |
| 7 (Oct. 19–26)     | BUC 10,11,12&13                       | 30 | AES (PRESENTATION ANB) | 3 | A3 Presentation |
| 8 (Oct. 26–Nov. 2) | Leftover BUC             | 25 | AES | 4 | Dev support |
| 9 (Nov. 2–9)       | Leftover BUC                   | 35 | MSK — Turn in (AES) | 4 | A4 Turn in & Final touches |
| 10 (Nov. 9–16)     | Leftover work                  | 25 | MSK — Presentation (AES) | 4 | A4 Presentation |
| 11 (Nov. 16–23)    | Leftover work until presentation | 25 | MSK — Presentation (MSK), Turn in (MSK) | 5 | A5 Turn in & Presentation |

## Requirements
- Java 24
- Maven
- PostgreSQL Database

## To Run
Til að keyra þarf að nota spring boot run

```bash
./mvnw spring-boot:run
```

## API Endpoints

Base URL: `http://localhost:8080`

### Users API (`/api/users`)

| Method | Endpoint | Description | Request Body | Response |
|--------|----------|-------------|--------------|----------|
| GET | `/api/users` | Get all users | - | `Iterable<User>` |
| GET | `/api/users/{id}` | Get user by ID | - | `User` or 404 |
| GET | `/api/users/search?name={name}` | Get user by name | - | `User` or 404 |
| POST | `/api/users` | Create new user | `User` | `User` (201 Created) |
| PUT | `/api/users/{id}` | Update user | `User` | `User` or 404 |
| DELETE | `/api/users/{id}` | Delete user | - | 204 No Content or 404 |
| GET | `/api/users/count` | Get user count | - | `Long` |
| GET | `/api/users/exists/{id}` | Check if user exists | - | `Boolean` |

#### Example User JSON:
```json
{
  "name": "John Doe",
  "email": "john.doe@example.com"
}
```

### Facilities API (`/api/facilities`)

| Method | Endpoint | Description | Request Body | Response |
|--------|----------|-------------|--------------|----------|
| GET | `/api/facilities` | Get all facilities | - | `Iterable<Facility>` |
| GET | `/api/facilities/{id}` | Get facility by ID | - | `Facility` or 404 |
| POST | `/api/facilities` | Create new facility | `Facility` | `Facility` (201 Created) |
| PUT | `/api/facilities/{id}` | Update facility | `Facility` | `Facility` or 404 |
| DELETE | `/api/facilities/{id}` | Delete facility | - | 204 No Content or 404 |

#### Example Facility JSON:
```json
{
  "name": "Laugardalslaug",
  "address": "Sundlaugavegur 30, 105 Reykjavík"
}
```

### Reviews API (`/api/reviews`)

| Method | Endpoint | Description | Request Body | Response |
|--------|----------|-------------|--------------|----------|
| GET | `/api/reviews` | Get all reviews | - | `Iterable<Review>` |
| GET | `/api/reviews/{id}` | Get review by ID | - | `Review` or 404 |
| GET | `/api/reviews/facility/{facilityId}` | Get reviews for facility | - | `Iterable<Review>` |
| POST | `/api/reviews` | Create new review | `Review` | `Review` (201 Created) |
| PUT | `/api/reviews/{id}` | Update review | `Review` | `Review` or 404 |
| DELETE | `/api/reviews/{id}` | Delete review | - | 204 No Content or 404 |

#### Example Review JSON:
```json
{
  "user": {
    "id": 1
  },
  "facility": {
    "id": 1
  },
  "rating": 5,
  "comment": "Excellent swimming pool with great facilities!"
}
```

### Check-ins API (`/api/checkins`)

| Method | Endpoint | Description | Request Body | Response |
|--------|----------|-------------|--------------|----------|
| GET | `/api/checkins` | Get all check-ins | - | `Iterable<CheckIn>` |
| GET | `/api/checkins/{id}` | Get check-in by ID | - | `CheckIn` or 404 |
| GET | `/api/checkins/user/{userId}` | Get user's check-ins | - | `Iterable<CheckIn>` |
| POST | `/api/checkins` | Create new check-in | `CheckIn` | `CheckIn` (201 Created) |
| DELETE | `/api/checkins/{id}` | Delete check-in | - | 204 No Content or 404 |

#### Example Check-in JSON:
```json
{
  "user": {
    "id": 1
  },
  "facility": {
    "id": 1
  }
}
```

### Amenities API (`/api/amenities`)

| Method | Endpoint | Description | Request Body | Response |
|--------|----------|-------------|--------------|----------|
| GET | `/api/amenities` | Get all amenities | - | `Iterable<Amenity>` |
| GET | `/api/amenities/{id}` | Get amenity by ID | - | `Amenity` or 404 |
| GET | `/api/amenities/facility/{facilityId}` | Get amenities for facility | - | `Iterable<Amenity>` |
| POST | `/api/amenities` | Create new amenity | `Amenity` | `Amenity` (201 Created) |
| PUT | `/api/amenities/{id}` | Update amenity | `Amenity` | `Amenity` or 404 |
| DELETE | `/api/amenities/{id}` | Delete amenity | - | 204 No Content or 404 |

#### Example Amenity JSON:
```json
{
  "facility": {
    "id": 1
  },
  "name": "Olympic Pool",
  "type": "POOL"
}
```

## HTTP Status Codes

- `200 OK` - Successful GET/PUT request
- `201 Created` - Successful POST request
- `204 No Content` - Successful DELETE request
- `400 Bad Request` - Invalid request data
- `404 Not Found` - Resource not found
- `500 Internal Server Error` - Server error

## Database Configuration

The application uses PostgreSQL database. Configure your database connection in `application.yml`:

```yaml
spring:
  datasource:
    url: jdbc:${DATABASE_URL}
    username: ${PGUSER}
    password: ${PGPASSWORD}
    driver-class-name: org.postgresql.Driver
```

## Testing with Postman

Import the API endpoints into Postman and test with the example JSON bodies provided above.
