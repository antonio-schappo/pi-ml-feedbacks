package com.piml.products.controller;

import com.piml.products.dto.FeedbackDto;
import com.piml.products.entity.Feedback;
import com.piml.products.service.FeedbackService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping
public class FeedbackController {

    private final FeedbackService feedbackService;

    public FeedbackController(FeedbackService feedbackService) {
        this.feedbackService = feedbackService;
    }

    @PostMapping("/feedback")
    public ResponseEntity<FeedbackDto> createFeedback(@RequestParam(name = "productId" ) Long id,
                                                      @RequestBody FeedbackDto dto) {
        Feedback feedback = dto.map();
        FeedbackDto createdFeedback = FeedbackDto.map(feedbackService.create(feedback, id));
        return new ResponseEntity<>(createdFeedback, HttpStatus.CREATED);
    }

    @GetMapping("/feedback")
    public ResponseEntity<List<FeedbackDto>> getFeedbacksByProductId(
            @RequestParam(name = "productId") Long productId) {
        List<Feedback> feedbacks = feedbackService.getFeedbacksByProductId(productId);
        return new ResponseEntity<>(feedbacks.stream()
                .map(FeedbackDto::map)
                .collect(Collectors.toList()), HttpStatus.OK);
    }
}
