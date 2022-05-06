package com.piml.products.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.piml.products.entity.Feedback;
import lombok.*;


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
    private Long starRating;
    private String comment;

    public Feedback map() {
        return Feedback.builder()
                .buyerId(this.buyerId)
                .starRating(this.starRating)
                .comment(this.comment)
                .build();
    }
}
