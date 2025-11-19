package is.hi.hbv501g.sundbok.service;

import is.hi.hbv501g.sundbok.model.Facility;
import is.hi.hbv501g.sundbok.model.Notification;
import is.hi.hbv501g.sundbok.model.Review;
import is.hi.hbv501g.sundbok.model.User;
import is.hi.hbv501g.sundbok.repository.ReviewRepository;
import is.hi.hbv501g.sundbok.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;


@Service
public class ReviewService {
    private final ReviewRepository reviewRepository;
    private final UserRepository userRepository;
    private final NotificationService notificationService;

    public ReviewService(ReviewRepository reviewRepository, UserRepository userRepository, NotificationService notificationService){
        this.reviewRepository=reviewRepository;
        this.userRepository = userRepository;
        this.notificationService = notificationService;

    }

       // CREATE - Add new review
       // CREATE - Add new review
       public Review createReview(Review review ){
           Review saved = reviewRepository.save(review);

           User author = saved.getUser();
           Facility facility = saved.getFacility();
           if (author != null && facility != null) {
               var followers = new HashSet<>(userRepository.findFollowersOf(author.getId()));
               if (!followers.isEmpty()) {
                   String msg = author.getName() + " reviewed " + facility.getName();
                   notificationService.createForUsers(
                           followers,
                           Notification.Type.FRIEND_REVIEW,
                           msg,
                           facility.getId(),
                           author.getId()
                   );
               }
           }

           return saved;
       }


    // READ - Get review by ID
       public Optional <Review> getReviewById(Long id){
        return reviewRepository.findById(id);

       }

       // READ - get all Reviews
       public Iterable<Review> getAllReviews(){
        return reviewRepository.findAll();
       }

       // READ - get all Reviews in a facility
       public List<Review> getReviewsByFacility(Long facilityId){
        return reviewRepository.findByFacilityId(facilityId);
       }

       // READ - get all Reviews from a User

       public List<Review> getReviewsByUser(Long userId){
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

       // Update a review

       public Review updateReview(Long id, Review updatedReview){
                return reviewRepository.findById(id)
                .map(review -> {
                    review.setRating(updatedReview.getRating());
                    review.setComment(updatedReview.getComment());
                    return reviewRepository.save(review);
                })
                .orElseThrow(() -> new RuntimeException("Review not found with id: " + id));

        }

       // DELETE - Delete review
       public void deleteReview(Long id){
         if (!reviewRepository.existsById(id)) {
            throw new RuntimeException("Review not found with id: " + id);
            }
            reviewRepository.deleteById(id);
        }
        // EXISTS - Check if a review exists

        public boolean reviewExists(Long id){
            return reviewRepository.existsById(id);
        }

       // COUNT - count all reviews
       public long getReviewCount() {
           return reviewRepository.count();
        }






}
