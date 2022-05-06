package com.piml.products.service;

import com.piml.products.entity.Feedback;
import com.piml.products.entity.Product;
import com.piml.products.exception.UnauthorizedUserException;
import com.piml.products.repository.FeedbackRepository;
import com.piml.products.repository.ProductRepository;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
public class FeedbackService {
    private final FeedbackRepository feedbackRepository;
    private final ProductRepository productRepository;
    public FeedbackService(FeedbackRepository feedbackRepository,
                           ProductRepository productRepository){
        super();
        this.feedbackRepository = feedbackRepository;
        this.productRepository = productRepository;
    }

    /**
     * Create a new feedback.
     * @param feedback receives a request containing a product to create.
     * @param id receives the id of the product that the feedback is about
     * @return feedback saved.
     */

    public Feedback create(Feedback feedback, Long id) {
        Optional<Product> product = this.productRepository.findById(id);
        if(!product.isPresent()) {
            throw new EntityNotFoundException("Product with id ".concat(String.valueOf(id)).concat(" was not found"));
        }
        feedback.setProduct(product.get());
        feedback.setDatePosted(LocalDateTime.now());
        return feedbackRepository.save(feedback);
    }

    /**
     * Search all feedbacks by the id of a product
     * @param productId receives a Long of productId to be searched.
     * @return all feedbacks from the given product
     */

    public List<Feedback> getFeedbacksByProductId(Long productId) {
        Optional<List<Feedback>> feedbackList = this.feedbackRepository.findAllByProductId(productId);
        if(!feedbackList.isPresent() || feedbackList.get().size() == 0) {
            throw new EntityNotFoundException("Product with id ".concat(String.valueOf(productId))
                    .concat(" either does not exist or no feedbacks have been posted for it."));
        }
        return feedbackList.get();
    }

    /**
     * Search all feedbacks by the id of a buyer
     * @param buyerId receives a Long of buyerId to be searched.
     * @return all feedbacks from the given buyer
     */

    public List<Feedback> getFeedbacksByBuyerId(Long buyerId) {
        Optional<List<Feedback>> feedbackList = this.feedbackRepository.findAllByBuyerId(buyerId);
        if(!feedbackList.isPresent() || feedbackList.get().size() == 0) {
            throw new EntityNotFoundException("Buyer with id ".concat(String.valueOf(buyerId))
                    .concat(" either does not exist or has not written any feedback yet."));
        }
        return feedbackList.get();
    }

    /**
     * Search a feedback by its specific id
     * @param aLong receives a Long of an specific feedbackId to be searched.
     * @return a specific feedback
     */

    public Feedback getFeedbackById(Long aLong) {
        Optional<Feedback> feedback = this.feedbackRepository.findById(aLong);
        if(!feedback.isPresent()){
            throw new EntityNotFoundException("No records of feedback with id "
                    .concat(String.valueOf(aLong)).concat(" found."));
        }
        return feedback.get();
    }

    /**
     * Search all feedbacks in the repository
     * @return all feedbacks in the repository
     */
    public Collection<? extends Feedback> getAllFeedbacks() { return this.feedbackRepository.findAll(); }

    /**
     * Update a feedback with new info supplied in the payload of a request
     * @param newInfo receives the new info the client wishes to update in the feedback
     * @param feedbackToUpdate receives the feedback the client wishes to update
     * @return a specific updated feedback
     */
    public Feedback updateFeedback(Feedback newInfo, Feedback feedbackToUpdate) {
        if (!newInfo.getBuyerId().equals(feedbackToUpdate.getBuyerId())) {
            throw new UnauthorizedUserException("The buyer with the buyerId of "
                    .concat(String.valueOf(newInfo.getBuyerId()))
                    .concat(" is not authorized to modify this feedback!"));
        }
        feedbackToUpdate.setComment(newInfo.getComment());
        feedbackToUpdate.setStarRating(newInfo.getStarRating());
        feedbackToUpdate.setDateEdited(LocalDateTime.now());
        return feedbackRepository.save(feedbackToUpdate);
    }

    /**
     * Delete a feedback by its specific id
     * @param feedbackId receives a Long of an specific feedbackId to be deleted.
     */
    public void deleteFeedback(Long feedbackId) {
        try{
            feedbackRepository.deleteById(feedbackId);
        } catch (RuntimeException Ex) {
            throw new EntityNotFoundException("No records of feedback with id "
            .concat(String.valueOf(feedbackId)).concat(" found."));
        }
    }
}
