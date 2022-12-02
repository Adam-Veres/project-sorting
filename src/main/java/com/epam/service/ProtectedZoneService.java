package com.epam.service;

import com.epam.mapper.EcoUserMapper;
import com.epam.model.EcoService;
import com.epam.model.EcoUser;
import com.epam.repository.CoordinateRepository;
import com.epam.repository.EcoServiceRepository;
import com.epam.repository.EcoUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.transaction.Transactional;
import java.util.Set;

// ************************************************************
// WE DON'T NEED IT. IT JUST FOR TEMPORARY AND FOR SOME TESTS AND EXAMPLES
// ************************************************************

@Service
@RequiredArgsConstructor
public class ProtectedZoneService {

  private final EcoUserRepository ecoUserRepository;
  private final EcoUserMapper ecoUserMapper;
  private final EcoServiceRepository ecoServiceRepository;
  private final CoordinateRepository coordinateRepository;

  @Transactional
  public Set<Object> getUserInformation() {
    final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    final EcoUser ecoUser = ecoUserRepository.findByUsername(authentication.getName()).get();
    return Set.of(authentication, ecoUserMapper.ecoUserToEcoUserDto(ecoUser));
  }

  @Transactional
  public int deleteEcoServiceAuthorized(final long id) {
    final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    return ecoServiceRepository.deleteByOwner_UsernameAndId(authentication.getName(), id);
  }

  @Transactional
  public EcoService createNewEcoServiceAuthorized(final EcoService ecoService) {
    final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    ecoService.setOwner(ecoUserRepository.findByUsername(authentication.getName()).get());
    return ecoServiceRepository.save(ecoService);
  }

  @Transactional
  public EcoService updateEcoServiceAuthorized(final EcoService newEcoService, final long id) {
    if(deleteEcoServiceAuthorized(id) != 0){
      return createNewEcoServiceAuthorized(newEcoService);
    }
    throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Eco Service not found with this id!");
  }
}
