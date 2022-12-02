package com.epam.service;

import com.epam.mapper.EcoUserMapper;
import com.epam.model.EcoService;
import com.epam.model.EcoUser;
import com.epam.repository.EcoServiceRepository;
import com.epam.repository.EcoUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.transaction.Transactional;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class ProtectedZoneService {

  private final EcoUserRepository ecoUserRepository;
  private final EcoUserMapper ecoUserMapper;
  private final EcoServiceRepository ecoServiceRepository;

  @Transactional
  public Set<Object> getUserInformation() {
    final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    final EcoUser ecoUser = ecoUserRepository.findByUsername(authentication.getName()).get();
    return Set.of(authentication, ecoUserMapper.ecoUserToEcoUserDto(ecoUser));
  }

  /**
	 * If Service exists with given id, will delete it.
	 * @param id of Eco Service
	 * @return true if Service being and deleted. False if Service is not being.
	 */
  @Transactional
  public int deleteEcoServiceAuthorized(final long id) {
    final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    return ecoServiceRepository.deleteByOwner_UsernameAndId(authentication.getName(), id);
  }

  /**
	 * Create new Eco Service
	 * @param ecoService
	 * @return the saved Entity
	 */
  @Transactional
  public EcoService createNewEcoServiceAuthorized(final EcoService ecoService) {
    final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    ecoService.setOwner(ecoUserRepository.findByUsername(authentication.getName()).get());
    return ecoServiceRepository.save(ecoService);
  }

  /**
	 * Update an existing Eco Service
	 * @param ecoService new datas of service, add unmodified fields too
	 * @param id of modified Eco Service
	 * @return the modified entity
	 */
  @Transactional
  public EcoService updateEcoServiceAuthorized(final EcoService es, final long id) {
	  final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
	  if(ecoServiceRepository.existsById(id)) {
			EcoService existingEcoService = ecoServiceRepository.findById(id).get();
			existingEcoService.setDeliveryOptions(es.getDeliveryOptions());
			existingEcoService.setPaymentConditions(es.getPaymentConditions());
			existingEcoService.setTypeOfWastes(es.getTypeOfWastes());
			existingEcoService.setServiceName(es.getServiceName());
			existingEcoService.setDescription(es.getDescription());
			existingEcoService.setCoordinate(es.getCoordinate());
			return ecoServiceRepository.save(existingEcoService);
		}
    throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Eco Service not found with this id!");
  }
  
  /**
	 * Add a rating to the service
	 * @param rating
	 * @param id
	 * @return the upgraded entity
	 */
  @Transactional
	public EcoService addRatingToEcoService(Optional<Double> rating, Optional<Long> id) {
		Double rate = rating.orElseThrow(() -> new IllegalArgumentException("Rate can not be null!"));
		Long identifier = id.orElseThrow(() -> new IllegalArgumentException("ID can not be null!"));
		if(rate < 0 || rate > 5) {
			throw new IllegalArgumentException("Rate should be between 0 and 5!");
		}
		if(ecoServiceRepository.existsById(identifier)) {
			EcoService existingEcoService = ecoServiceRepository.findById(identifier).get();
			existingEcoService.addRating(Optional.of(BigDecimal.valueOf(rate)));
			return ecoServiceRepository.save(existingEcoService);
		}
		return null;
	}
  
}
