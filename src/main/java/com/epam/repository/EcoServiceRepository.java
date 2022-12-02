package com.epam.repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.epam.model.EcoService;

public interface EcoServiceRepository extends JpaRepository<EcoService, Long>{

	@Query("SELECT es FROM EcoService es WHERE es.coordinate.latitude > :startLatitude AND es.coordinate.latitude < :stopLatitude AND "
			+ "es.coordinate.longitude > :startLongitude AND es.coordinate.longitude < :stopLongitude")
	public List<EcoService> findAllByCoordinateBetweenBorders(BigDecimal startLatitude, BigDecimal stopLatitude, BigDecimal startLongitude, BigDecimal stopLongitude);

	public List<EcoService> findAll(Specification<EcoService> spec);

	int deleteByOwner_UsernameAndId(String userName, long id);

	Optional<EcoService> findByOwner_UsernameAndId(String userName, Long aLong);
}
