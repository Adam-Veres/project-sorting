package com.epam.service;

import com.epam.model.EcoService;
import com.epam.model.ServiceRating;
import com.epam.repository.EcoServiceRepository;
import com.epam.repository.EcoUserRepository;
import com.epam.repository.ServiceRatingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ServiceManagementService {

  private final EcoUserRepository ecoUserRepository;
  private final EcoServiceRepository ecoServiceRepository;
  private final ServiceRatingRepository serviceRatingRepository;

  /**
   * If Service exists with given id, will delete it.
   *
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
   *
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
   *
   * @param ecoService new datas of service, add unmodified fields too
   * @param id of modified Eco Service
   * @return the modified entity
   */
  @Transactional
  public EcoService updateEcoServiceAuthorized(final EcoService ecoService, final long id) {
    final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    final EcoService existingEcoService =
        ecoServiceRepository
            .findByOwner_UsernameAndId(authentication.getName(), id)
            .orElseThrow(
                () ->
                    new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Eco Service not found with this id!"));
    existingEcoService.setDeliveryOptions(ecoService.getDeliveryOptions());
    existingEcoService.setPaymentConditions(ecoService.getPaymentConditions());
    existingEcoService.setTypeOfWastes(ecoService.getTypeOfWastes());
    existingEcoService.setServiceName(ecoService.getServiceName());
    existingEcoService.setDescription(ecoService.getDescription());
    existingEcoService.setCoordinate(ecoService.getCoordinate());
    return ecoServiceRepository.save(existingEcoService);
  }

  /**
   * Add a rating to the service
   *
   * @param ratingValue
   * @param serviceId
   * @return the upgraded entity
   */
  @Transactional
  public EcoService addRatingToEcoService(int ratingValue, long serviceId)
      throws ResponseStatusException {
    final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    final ServiceRating rating =
        serviceRatingRepository
            .findByEcoService_IdAndCreator_Username(serviceId, authentication.getName())
            .orElse(
                new ServiceRating(
                    0L,
                    ratingValue,
                    ecoUserRepository.findByUsername(authentication.getName()).get(),
                    ecoServiceRepository
                        .findById(serviceId)
                        .orElseThrow(
                            () ->
                                new ResponseStatusException(
                                    HttpStatus.NOT_FOUND, "Eco Service not found with this id!"))));
    rating.setRating(ratingValue);
    serviceRatingRepository.save(rating);
    return rating.getEcoService();
  }
}
