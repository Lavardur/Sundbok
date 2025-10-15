package is.hi.hbv501g.sundbok.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import is.hi.hbv501g.sundbok.model.Review;
import is.hi.hbv501g.sundbok.service.ReviewService;

import java.util.List;

@RestController
@RequestMapping("/api/reviews")
public class ReviewController {

    private final ReviewService reviewService;
    public ReviewController(ReviewService reviewService){
        this.reviewService = reviewService;
    }

    // GET /api/reviews - Get all reviews
    @GetMapping
    public ResponseEntity<Iterable<Review>> getAllReviews() {
        return ResponseEntity.ok(reviewService.getAllReviews());
    }

    // GET /api/reviews/{id} - Get review by ID
    @GetMapping("/{id}")
    public ResponseEntity<Review> getReviewById(@PathVariable Long id) {
        return reviewService.getReviewById(id)
        .map(ResponseEntity::ok)
        .orElse(ResponseEntity.notFound().build());
    }

    // GET /api/reviews/facility/{facilityId} - Get reviews for facility
    @GetMapping("/facility/{facilityId}")
    public ResponseEntity<List<Review>> getReviewsByFacility(@PathVariable Long facilityId) {
        List<Review> facilityReviews = reviewService.getReviewsByFacility(facilityId);
        if(facilityReviews.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        else{
            return ResponseEntity.ok(facilityReviews);
        }
    }

    // GET /api/reviews/user/{userId} - Get reviews by user
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Review>> getReviewsByUser(@PathVariable Long userId) {
         List<Review> userReviews = reviewService.getReviewsByUser(userId);
                 if(userReviews.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        else{
            return ResponseEntity.ok(userReviews);
        }
         

    }

    // GET /api/reviews/facility/{facilityId}/average-rating - Get average rating
    @GetMapping("/facility/{facilityId}/average-rating")
    public ResponseEntity<Double> getAverageRating(@PathVariable Long facilityId) {
        double average = reviewService.getAverageFacilityRating(facilityId);
        if (Double.isNaN(average)) {
            return ResponseEntity.noContent().build(); // 204 if no reviews
        } else {
            return ResponseEntity.ok(average);        // 200 OK with average
        }
    }

    // POST /api/reviews - Create new review
    @PostMapping
    public ResponseEntity<Review> createReview(@RequestBody Review review) {
        try{
          Review newReview = reviewService.createReview(review);
          return ResponseEntity.status(HttpStatus.CREATED).body(newReview);

        }
        catch(RuntimeException e){
            return ResponseEntity.badRequest().build();
        }
        
    }

    // PUT /api/reviews/{id} - Update review
    @PutMapping("/{id}")
    public ResponseEntity<Review> updateReview(@PathVariable Long id, @RequestBody Review review) {
        try{
           Review updatedReview = reviewService.updateReview(id, review);
           return ResponseEntity.ok(updatedReview);

        }
        catch(RuntimeException e){
            return ResponseEntity.notFound().build();
        }
    }

    // DELETE /api/reviews/{id} - Delete review
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReview(@PathVariable Long id) {
        try{
            reviewService.deleteReview(id);
            return ResponseEntity.noContent().build();
        }
        catch(RuntimeException e){
            return ResponseEntity.notFound().build();

        }

    }
}
