package com.piml.products.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.piml.products.entity.Feedback;
import lombok.*;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;


@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UpdateFeedbackDto {
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long id;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long productId;
    private Long buyerId;
    @Min(value = 1, message = "Star rating must be between 1 and 5 stars")
    @Max(value = 5, message = "Star rating must be between 1 and 5 stars")
    private Long starRating;
    @Size(min = 5, max = 280, message = "Please write a comment with between 5 and 280 characters")
    private String comment;

    public Feedback map() {
        return Feedback.builder()
                .buyerId(this.buyerId)
                .starRating(this.starRating)
                .comment(this.comment)
                .build();
    }
}
