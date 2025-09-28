package is.hi.hbv501g.sundbok.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import is.hi.hbv501g.sundbok.model.Review;
import java.util.List;

@RestController
@RequestMapping("/api/reviews")
public class ReviewController {

    // GET /api/reviews - Get all reviews
    @GetMapping
    public ResponseEntity<Iterable<Review>> getAllReviews() {
        // TODO: Implementation
        return null;
    }

    // GET /api/reviews/{id} - Get review by ID
    @GetMapping("/{id}")
    public ResponseEntity<Review> getReviewById(@PathVariable Long id) {
        // TODO: Implementation
        return null;
    }

    // GET /api/reviews/facility/{facilityId} - Get reviews for facility
    @GetMapping("/facility/{facilityId}")
    public ResponseEntity<List<Review>> getReviewsByFacility(@PathVariable Long facilityId) {
        // TODO: Implementation
        return null;
    }

    // GET /api/reviews/user/{userId} - Get reviews by user
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Review>> getReviewsByUser(@PathVariable Long userId) {
        // TODO: Implementation
        return null;
    }

    // GET /api/reviews/facility/{facilityId}/average-rating - Get average rating
    @GetMapping("/facility/{facilityId}/average-rating")
    public ResponseEntity<Double> getAverageRating(@PathVariable Long facilityId) {
        // TODO: Implementation
        return null;
    }

    // POST /api/reviews - Create new review
    @PostMapping
    public ResponseEntity<Review> createReview(@RequestBody Review review) {
        // TODO: Implementation
        return null;
    }

    // PUT /api/reviews/{id} - Update review
    @PutMapping("/{id}")
    public ResponseEntity<Review> updateReview(@PathVariable Long id, @RequestBody Review review) {
        // TODO: Implementation
        return null;
    }

    // DELETE /api/reviews/{id} - Delete review
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReview(@PathVariable Long id) {
        // TODO: Implementation
        return null;
    }
}
