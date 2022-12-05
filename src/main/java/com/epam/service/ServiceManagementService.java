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

@Service
@RequiredArgsConstructor
public class ServiceManagementService {

  private final EcoUserRepository ecoUserRepository;
  private final EcoServiceRepository ecoServiceRepository;

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
	  final EcoService existingEcoService = ecoServiceRepository.findByOwner_UsernameAndId(authentication.getName(), id)
			  .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Eco Service not found with this id!"));
			existingEcoService.setDeliveryOptions(es.getDeliveryOptions());
			existingEcoService.setPaymentConditions(es.getPaymentConditions());
			existingEcoService.setTypeOfWastes(es.getTypeOfWastes());
			existingEcoService.setServiceName(es.getServiceName());
			existingEcoService.setDescription(es.getDescription());
			existingEcoService.setCoordinate(es.getCoordinate());
			return ecoServiceRepository.save(existingEcoService);
  }
  
  /**
	 * Add a rating to the service
	 * @param rating
	 * @param id
	 * @return the upgraded entity
	 */
  @Transactional
	public EcoService addRatingToEcoService(double rating, long id) throws ResponseStatusException {
	  final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
	  EcoService existingEcoService = ecoServiceRepository.
			  findByOwner_UsernameAndId(authentication.getName(),id).orElseThrow(
					  () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Eco Service not found with this id!")
			  );
			existingEcoService.addRating(rating);
			return ecoServiceRepository.save(existingEcoService);
	}
  
}
