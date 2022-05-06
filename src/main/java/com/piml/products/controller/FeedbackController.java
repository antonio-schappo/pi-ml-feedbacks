package com.piml.products.controller;

import com.piml.products.dto.FeedbackDto;
import com.piml.products.dto.StarsDto;
import com.piml.products.dto.UpdateFeedbackDto;
import com.piml.products.entity.Feedback;
import com.piml.products.service.FeedbackService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping
public class FeedbackController {

    private final FeedbackService feedbackService;

    public FeedbackController(FeedbackService feedbackService) {
        this.feedbackService = feedbackService;
    }

    @PostMapping("/feedback")
    public ResponseEntity<FeedbackDto> createFeedback(@RequestParam(name = "productId") Long id,
                                                      @RequestBody FeedbackDto dto) {
        Feedback feedback = dto.map();
        FeedbackDto createdFeedback = FeedbackDto.map(feedbackService.create(feedback, id));
        return new ResponseEntity<>(createdFeedback, HttpStatus.CREATED);
    }

    @GetMapping("/feedbacks")
    public ResponseEntity<List<FeedbackDto>> getFeedbacksByProductId(
            @RequestParam(name = "productId", required = false) Optional<Long> productId,
            @RequestParam(name = "buyerId", required = false) Optional<Long> buyerId) {
        List<Feedback> feedbacks = new ArrayList<>();
        if(productId.isPresent()){ feedbacks.addAll(feedbackService.getFeedbacksByProductId(productId.get()));
        } else if(buyerId.isPresent()) { feedbacks.addAll(feedbackService.getFeedbacksByBuyerId(buyerId.get()));
        } else { feedbacks.addAll(feedbackService.getAllFeedbacks()); }
        return new ResponseEntity<>(feedbacks.stream().map(FeedbackDto::map)
                .collect(Collectors.toList()), HttpStatus.OK);
    }

    @GetMapping("/feedback")
    public ResponseEntity<FeedbackDto> getFeedbackById(@RequestParam(name = "feedbackId") Long feedbackId) {
        Feedback feedback = feedbackService.getFeedbackById(feedbackId);
        return new ResponseEntity<>(FeedbackDto.map(feedback), HttpStatus.OK);
    }

    @GetMapping("/feedback/stars")
    public ResponseEntity<StarsDto> getStarAverageById(@RequestParam(name = "productId") Long productId) {
        List<Feedback> feedbacks = feedbackService.getFeedbacksByProductId(productId);
        return new ResponseEntity<>(new StarsDto(feedbacks), HttpStatus.OK);
    }

    @PutMapping("/feedback")
    public ResponseEntity<FeedbackDto> updateFeedback(@RequestParam(name = "feedbackId") Long feedbackId,
                                                      @RequestBody UpdateFeedbackDto dto) {
        Feedback feedbackToUpdate = feedbackService.getFeedbackById(feedbackId);
        Feedback updatedFeedback = feedbackService.updateFeedback(dto.map(), feedbackToUpdate);
        return new ResponseEntity<>(FeedbackDto.map(updatedFeedback),HttpStatus.CREATED);
    }
}
