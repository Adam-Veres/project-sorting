package com.epam.repository;

import com.epam.model.ServiceRating;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Optional;

@Repository
public interface ServiceRatingRepository  extends JpaRepository<ServiceRating, Long> {
    @Transactional
    @Query(value = "SELECT avg(rating) FROM ServiceRating WHERE ecoService.id=:id")
    Optional<Float> averageServiceRating(Long id);

    Optional<ServiceRating> findByEcoService_IdAndCreator_Username(Long serviceId, String username);

}
