package com.epam.repository;

import com.epam.model.ServiceRating;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ServiceRatingRepository extends JpaRepository<ServiceRating, Long> {
  @Transactional
  @Query(value = "SELECT avg(rating) FROM ServiceRating WHERE ecoService.id=:id")
  Optional<Float> averageServiceRating(final Long id);

  Optional<ServiceRating> findByEcoService_IdAndCreator_Username(
      final Long serviceId, final String username);
}
