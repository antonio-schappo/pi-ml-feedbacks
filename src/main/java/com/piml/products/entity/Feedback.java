package com.piml.products.entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="feedback")
public class Feedback {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name= "feedback_id")
    private Long id;
    @ManyToOne()
    @JoinColumn(name = "product_id")
    private Product product;
    private Long buyerId;
    private LocalDateTime datePosted;
    private LocalDateTime dateEdited;
    private Long starRating;
    private String comment;
}
