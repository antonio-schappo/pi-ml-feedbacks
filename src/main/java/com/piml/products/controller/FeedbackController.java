package com.piml.products.controller;

import com.piml.products.dto.FeedbackDto;
import com.piml.products.dto.StarsDto;
import com.piml.products.dto.UpdateFeedbackDto;
import com.piml.products.entity.Feedback;
import com.piml.products.service.FeedbackService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Api(value = "Feedback")
@RestController
@RequestMapping
public class FeedbackController {

    private final FeedbackService feedbackService;

    public FeedbackController(FeedbackService feedbackService) {
        this.feedbackService = feedbackService;
    }
    /**
     * POST method used to create a new feedback
     * @param id @RequestParam productId receives the id of the product the client wishes to post a feedback of
     * @param dto @RequestBody dto receives the payload of a request containing a feedback to be created
     * @return the response containing the dto of a newly created feedback
     */

    @ApiOperation(value = "Register new Feedback to a product")
    @PostMapping("/feedback")
    public ResponseEntity<FeedbackDto> createFeedback(@RequestParam(name = "productId") Long id,
                                                      @RequestBody FeedbackDto dto) {
        Feedback feedback = dto.map();
        FeedbackDto createdFeedback = FeedbackDto.map(feedbackService.create(feedback, id));
        return new ResponseEntity<>(createdFeedback, HttpStatus.CREATED);
    }


    /**
     * GET method to find all the feedbacks based on possible different @RequestParams
     * @RequestParams are optional and the possible ones are buyerId or productId,
     *  and in case none of them are offered the method will return a list of all feedbacks
     * @return the list of dtos of the feedbacks found
     */
    @ApiOperation(value = "Find feedbacks based on different QueryParams(ProductId, BuyerId)," +
            "or list all feedbacks if none is provided.")
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

    /**
     * GET method to find a specific feedback by ID
     * @RequestParam feedbackId receives the corresponding id of the feedback to be found
     * @return the feedback with the corresponding ID
     */

    @ApiOperation(value = "Get specific feedback from feedbackId")
    @GetMapping("/feedback")
    public ResponseEntity<FeedbackDto> getFeedbackById(@RequestParam(name = "feedbackId") Long feedbackId) {
        Feedback feedback = feedbackService.getFeedbackById(feedbackId);
        return new ResponseEntity<>(FeedbackDto.map(feedback), HttpStatus.OK);
    }

    /**
     * GET method to find the average star rating from the feedbacks of a specific product
     * @RequestParam feedbackId receives the corresponding id of the product whose average
     *      star rating the client wants to get
     * @return the average number(double) of star calculated from all the feedbacks of a product
     */
    @ApiOperation(value = "Get the average feedback star rating for a product")
    @GetMapping("/feedback/stars")
    public ResponseEntity<StarsDto> getStarAverageById(@RequestParam(name = "productId") Long productId) {
        List<Feedback> feedbacks = feedbackService.getFeedbacksByProductId(productId);
        return new ResponseEntity<>(new StarsDto(feedbacks), HttpStatus.OK);
    }

    /**
     * PUT method used to update the information of a specific feedback
     * @RequestParam feedbackId of the feedback the client wishes to update
     * @param dto receives the payload with the information the client wishes to change
     * @return the updated feedback with the new information changed
     */

    @ApiOperation(value = "Update a feedback information of a specific feedback")
    @PutMapping("/feedback")
    public ResponseEntity<FeedbackDto> updateFeedback(@RequestParam(name = "feedbackId") Long feedbackId,
                                                      @RequestBody UpdateFeedbackDto dto) {
        Feedback feedbackToUpdate = feedbackService.getFeedbackById(feedbackId);
        Feedback updatedFeedback = feedbackService.updateFeedback(dto.map(), feedbackToUpdate);
        return new ResponseEntity<>(FeedbackDto.map(updatedFeedback),HttpStatus.CREATED);
    }

    /**
     * DELETE method used to delete a specific feedback from the repository
     * @PathVariable feedbackId of the feedback the client wishes to delete
     * @return a success message with the id of the recently deleted feedback
     */

    @ApiOperation(value = "Delete a specific feedback")
    @DeleteMapping("/feedback/{feedbackId}")
    public ResponseEntity<String> deleteFeedback(@PathVariable Long feedbackId) {
        feedbackService.deleteFeedback(feedbackId);
        return ResponseEntity.ok("The feedback with the feedbackId of "
                .concat(String.valueOf(feedbackId))
        .concat(" was successfully deleted."));
    }
}
