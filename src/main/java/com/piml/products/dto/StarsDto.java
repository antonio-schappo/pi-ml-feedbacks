package com.piml.products.dto;

import com.piml.products.entity.Feedback;
import lombok.*;

import java.util.List;
import java.util.stream.Collectors;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StarsDto {
    private Double starRatingAverage;

    public StarsDto(List<Feedback> feedbacks) { this.starRatingAverage = calculateStarRatingAverage(feedbacks);}

    private Double calculateStarRatingAverage(List<Feedback> feedbacks) {
        return feedbacks.stream().map(Feedback::getStarRating).collect(Collectors.averagingDouble(Long::doubleValue));
    }
}
