package com.epam.repository;

import com.epam.model.EcoService;
import java.math.BigDecimal;
import java.util.List;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface EcoServiceRepository extends JpaRepository<EcoService, Long> {

  @Query(
      "SELECT es FROM EcoService es WHERE es.coordinate.latitude > :startLatitude AND es.coordinate.latitude < :stopLatitude AND "
          + "es.coordinate.longitude > :startLongitude AND es.coordinate.longitude < :stopLongitude")
  List<EcoService> findAllByCoordinateBetweenBorders(
      final BigDecimal startLatitude,
      final BigDecimal stopLatitude,
      final BigDecimal startLongitude,
      final BigDecimal stopLongitude);

  List<EcoService> findAll(final Specification<EcoService> spec);

  int deleteByOwner_UsernameAndId(final String userName, final long id);
}
