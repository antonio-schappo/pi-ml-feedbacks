package com.piml.products.repository;

import com.piml.products.entity.Feedback;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FeedbackRepository extends JpaRepository<Feedback, Long> {
    Optional<List<Feedback>> findAllByProductId(Long id);
    Optional<List<Feedback>> findAllByBuyerId(Long id);
}