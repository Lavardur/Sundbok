package is.hi.hbv501g.sundbok.service;

import is.hi.hbv501g.sundbok.model.Facility;
import is.hi.hbv501g.sundbok.model.Review;
import is.hi.hbv501g.sundbok.model.User;
import is.hi.hbv501g.sundbok.repository.FacilityRepository;
import is.hi.hbv501g.sundbok.repository.ReviewRepository;
import is.hi.hbv501g.sundbok.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final UserRepository userRepository;
    private final FacilityRepository facilityRepository;

    public ReviewService(ReviewRepository reviewRepository,
                         UserRepository userRepository,
                         FacilityRepository facilityRepository) {
        this.reviewRepository = reviewRepository;
        this.userRepository = userRepository;
        this.facilityRepository = facilityRepository;
    }

    // CREATE - Add new review, looking up user and facility by ID
    public Review createReview(Long userId, Long facilityId, Review review) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + userId));
        Facility facility = facilityRepository.findById(facilityId)
                .orElseThrow(() -> new RuntimeException("Facility not found with id: " + facilityId));
        review.setUser(user);
        review.setFacility(facility);
        return reviewRepository.save(review);
    }

    // READ - Get review by ID
    public Optional<Review> getReviewById(Long id) {
        return reviewRepository.findById(id);
    }

    // READ - get all Reviews
    public Iterable<Review> getAllReviews() {
        return reviewRepository.findAll();
    }

    // READ - get all Reviews in a facility
    public List<Review> getReviewsByFacility(Long facilityId) {
        return reviewRepository.findByFacilityId(facilityId);
    }

    // READ - get all Reviews from a User
    public List<Review> getReviewsByUser(Long userId) {
        return reviewRepository.findByUserId(userId);
    }

    // READ - get the average review of a facility
    public double getAverageFacilityRating(Long facilityId) {
        List<Review> reviews = reviewRepository.findByFacilityId(facilityId);
        if (reviews.isEmpty()) {
            return Double.NaN;
        }
        double sum = 0;
        for (Review r : reviews) {
            sum += r.getRating();
        }
        return sum / reviews.size();
    }

    // UPDATE - Update a review (only rating and comment, user/facility stay the same)
    public Review updateReview(Long id, Review updatedReview) {
        return reviewRepository.findById(id)
                .map(review -> {
                    review.setRating(updatedReview.getRating());
                    review.setComment(updatedReview.getComment());
                    return reviewRepository.save(review);
                })
                .orElseThrow(() -> new RuntimeException("Review not found with id: " + id));
    }

    // DELETE - Delete review
    public void deleteReview(Long id) {
        if (!reviewRepository.existsById(id)) {
            throw new RuntimeException("Review not found with id: " + id);
        }
        reviewRepository.deleteById(id);
    }

    // EXISTS - Check if a review exists
    public boolean reviewExists(Long id) {
        return reviewRepository.existsById(id);
    }

    // COUNT - count all reviews
    public long getReviewCount() {
        return reviewRepository.count();
    }
}
