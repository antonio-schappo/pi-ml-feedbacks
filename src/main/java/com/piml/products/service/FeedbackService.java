package com.piml.products.service;

import com.piml.products.entity.Feedback;
import com.piml.products.entity.Product;
import com.piml.products.repository.FeedbackRepository;
import com.piml.products.repository.ProductRepository;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
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
//    criar campos ainda nao criados na entidade ao persistir
//    associar a foreignkey
}
