package com.piml.products.service;

import com.piml.products.entity.Feedback;
import com.piml.products.entity.Product;
import com.piml.products.exception.handler.UnauthorizedUserException;
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

    public Feedback create(Feedback feedback, Long id) {
        Optional<Product> product = this.productRepository.findById(id);
        if(!product.isPresent()) {
            throw new EntityNotFoundException("Product with id ".concat(String.valueOf(id)).concat(" was not found"));
        }
        feedback.setProduct(product.get());
        feedback.setDatePosted(LocalDateTime.now());
        return feedbackRepository.save(feedback);
    }

    public List<Feedback> getFeedbacksByProductId(Long productId) {
        Optional<List<Feedback>> feedbackList = this.feedbackRepository.findAllByProductId(productId);
        if(!feedbackList.isPresent() || feedbackList.get().size() == 0) {
            throw new EntityNotFoundException("Product with id ".concat(String.valueOf(productId))
                    .concat(" either does not exist or no feedbacks have been posted for it."));
        }
        return feedbackList.get();
    }

    public List<Feedback> getFeedbacksByBuyerId(Long buyerId) {
        Optional<List<Feedback>> feedbackList = this.feedbackRepository.findAllByBuyerId(buyerId);
        if(!feedbackList.isPresent() || feedbackList.get().size() == 0) {
            throw new EntityNotFoundException("Buyer with id ".concat(String.valueOf(buyerId))
                    .concat(" either does not exist or has not written any feedback yet."));
        }
        return feedbackList.get();
    }

    public Feedback getFeedbackById(Long aLong) { return this.feedbackRepository.getById(aLong); }

    public Collection<? extends Feedback> getAllFeedbacks() { return this.feedbackRepository.findAll(); }

    public Feedback updateFeedback(Feedback newInfo, Feedback feedbackToUpdate) {
        if (!newInfo.getBuyerId().equals(feedbackToUpdate.getBuyerId())) {
            throw new UnauthorizedUserException("The buyer with the buyerId of "
                    .concat(String.valueOf(newInfo.getBuyerId()))
                    .concat(" is not authorized to modify this feedback!"));
        }
        feedbackToUpdate.setComment(newInfo.getComment());
        feedbackToUpdate.setStarRating(newInfo.getStarRating());
        return feedbackRepository.save(feedbackToUpdate);
    }
}
