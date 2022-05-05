package com.piml.products.service;

import com.piml.products.repository.FeedbackRepository;
import com.piml.products.repository.ProductRepository;
import org.springframework.stereotype.Service;

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
}
