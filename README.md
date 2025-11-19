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
Render URL:`https://sundbok-s69y.onrender.com`
Postgres URL:`host=dpg-d4b1146r433s738kkvmg-a.frankfurt-postgres.render.com port=5432 dbname=sundbok_db user=sundbok_db_user password=L77PObna2NvhqOmPELhmsZbXe1mlLMM0 sslmode=require sslrootcert=none`

### Users API (`/api/users`)

| Method | Endpoint | Description| Request Body | Response|
|--------|----------|------------|--------------|---------|
| GET | `/api/users` | Get all users | - | `Iterable<User>` |
| GET | `/api/users/{id}` | Get user by ID | - | `User` or 404 |
| GET | `/api/users/search?name={name}` | Get user by name | - | `User` or 404 |
| POST | `/api/users` | Create new user | `User` | `User` (201 Created) |
| PUT | `/api/users/{id}` | Update user | `User` | `User` or 404 |
| DELETE | `/api/users/{id}` | Delete user | - | 204 No Content or 404 |
| GET | `/api/users/count` | Get user count | - | `Long`  |
| GET | `/api/users/exists/{id}` | Check if user exists | - | `Boolean` |
| GET | `/api/users/{userId}/favorites"` | Lists favorited facilities | - | `Set<Facility>` |
| GET | `/api/users/{userId}/favorites/{facilityId}` | Adds facility to favorites | - | `Set<Facility>` |
| GET    | `/api/users/{userId}/favorites`                   | List favorited facilities | -                                | `Set<Facility>` or 403 |
| POST   | `/api/users/{userId}/favorites/{facilityId}`      | Add facility to favorites | -                                | `Set<Facility>` or 403 |
| DELETE | `/api/users/{userId}/favorites/{facilityId}`      | Remove facility from favorites | -                                | `Set<Facility>` or 403 |
| GET    | `/api/users/{userId}/friends`                     | List friends    | -                                | `Set<User>` or 403 |
| POST   | `/api/users/{userId}/friends/{otherId}`           | Add mutual friendship | -                                | `Set<User>` or 403 |
| DELETE | `/api/users/{userId}/friends/{otherId}`           | Remove friendship | -                                | 204 No Content or 403 |
| GET    | `/api/users/{userId}/subscriptions`               | List subscriptions | -                                | `Set<Facility>` or 403 |
| POST   | `/api/users/{userId}/subscriptions/{facilityId}`  | Subscribe to facility | -                                | `Set<Facility>` or 403 |
| DELETE | `/api/users/{userId}/subscriptions/{facilityId}`  | Unsubscribe from facility | -                                | `Set<Facility>` or 403 |
| GET    | `/api/users/{id}/profile-picture`                 | Get profile picture (public) | -                                | `byte[]` (image/jpeg) or 404 |
| PUT    | `/api/users/{id}/profile-picture`                 | Upload/replace profile picture | `file` (multipart/form-data)     | 204 No Content or 400 or 403 |
| DELETE | `/api/users/{id}/profile-picture`                 | Delete profile picture | -                                | 204 No Content or 403 |
#### Example User JSON:
```json
{
  "name": "John Doe",
  "email": "john.doe@example.com"
}
```

### Facilities API (`/api/facilities`)
| Method | Endpoint| Description| Request Body| Response|
|--------|---------|------------|--------------|---------|
| GET    | `/api/facilities` | Get all facilities / search / nearby | -            | `List<Facility>` |
| GET    | `/api/facilities/{id}` | Get facility by ID | -            | `Facility` or 404 |
| POST   | `/api/facilities` | Create new facility | `Facility`   | `Facility` (201 Created) |
| PUT    | `/api/facilities/{id}` | Update facility | `Facility`   | `Facility` or 404 |
| DELETE | `/api/facilities/{id}` | Delete facility | -            | 204 No Content |
| GET    | `/api/facilities/count` | Get facility count | -            | `Long`  |
| GET    | `/api/facilities/exists/{id}` | Check if facility exists | -            | `Boolean` |
| GET    | `/api/facilities/{id}/availability` | Get pool availability info | -            | `{ poolId, name, fjoldi, updatedAt }` or 404 |
| POST   | `/api/facilities/admin/refresh-fjoldi` | Manually refresh availability (admin only) | -            | `{ "ok": true }` |
| GET    | `/api/facilities/{facilityId}/schedule` | Get facility schedule | -            | `List<ScheduleRow>` |
| PUT    | `/api/facilities/{facilityId}/schedule` | Replace schedule | `List<ScheduleRow>` | `List<ScheduleRow>` |
| GET    | `/api/facilities/{id}/images` | List image slot indices | -            | `List<Integer>` or 404 |
| GET    | `/api/facilities/{facilityId}/images/{index}` | Get facility image (JPEG bytes) | -            | `byte[]` (image/jpeg) or 404 or 400 |
| POST   | `/api/facilities/{facilityId}/images` | Add new facility image (first free slot) | `file` (multipart/form-data) | 201 Created or 400 |
| PUT    | `/api/facilities/{facilityId}/images/{index}` | Replace image at given index | `file` (multipart/form-data) | 204 No Content or 400 |
| DELETE | `/api/facilities/{facilityId}/images/{index}` | Delete image at given index | -            | 204 No Content or 400 or 404 |


#### Example Facility JSON:
```json
{
  "name": "Laugardalslaug",
  "address": "Sundlaugavegur 30, 105 Reykjavík"
}
```

### Reviews API (`/api/reviews`)
| Method | Endpoint | Description | Request Body | Response             |
|--------|----------|-------------|--------------|----------------------|
| GET    | `/api/reviews` | Get all reviews | -            | `Iterable<Review>`   |
| GET    | `/api/reviews/{id}` | Get review by ID | -            | `Review` or 404      |
| GET    | `/api/reviews/facility/{facilityId}` | Get reviews for a facility | -            | `List<Review>` or 204 No Content |
| GET    | `/api/reviews/user/{userId}` | Get reviews created by a user | -            | `List<Review>` or 204 No Content |
| GET    | `/api/reviews/facility/{facilityId}/average-rating` | Get average rating for a facility | -            | `Double` or 204 No Content |
| POST   | `/api/reviews` | Create new review | `Review`     | `Review` (201 Created) or 400 |
| PUT    | `/api/reviews/{id}` | Update review | `Review`     | `Review` or 404      |
| DELETE | `/api/reviews/{id}` | Delete review | -            | 204 No Content or 404 |


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
| Method | Endpoint                                   | Description                         | Request Body                        | Response                                  |
|--------|---------------------------------------------|-------------------------------------|--------------------------------------|--------------------------------------------|
| GET    | `/api/checkins`                             | Get all check-ins                   | -                                    | `Iterable<CheckIn>`                        |
| GET    | `/api/checkins/{id}`                        | Get check-in by ID                  | -                                    | `CheckIn` or 404                           |
| GET    | `/api/checkins/user/{userId}`               | Get all check-ins by user           | -                                    | `List<CheckIn>`                            |
| GET    | `/api/checkins/facility/{facilityId}`       | Get all check-ins for facility      | -                                    | `List<CheckIn>`                            |
| POST   | `/api/checkins`                             | Create new check-in                 | `userId`, `facilityId` (query params)| `CheckIn` (201 Created) or 400             |
| DELETE | `/api/checkins/{id}`                        | Delete check-in                     | -                                    | 204 No Content                             |
| GET    | `/api/checkins/visited?userId=x&facilityId=y` | Check if user has visited facility | -                                    | `Boolean`                                  |


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
| Method | Endpoint                                      | Description                     | Request Body | Response                          |
|--------|-----------------------------------------------|---------------------------------|--------------|------------------------------------|
| GET    | `/api/amenities`                              | Get all amenities               | -            | `Iterable<Amenity>`                |
| GET    | `/api/amenities/{id}`                         | Get amenity by ID               | -            | `Amenity` or 404                   |
| GET    | `/api/amenities/facility/{facilityId}`        | Get amenities for facility      | -            | `Iterable<Amenity>`                |
| POST   | `/api/amenities`                              | Create new amenity              | `Amenity`    | `Amenity` (201 Created)            |
| PUT    | `/api/amenities/{id}`                         | Update amenity                  | `Amenity`    | `Amenity` or 404                   |
| DELETE | `/api/amenities/{id}`                         | Delete amenity                  | -            | 204 No Content                     |


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
