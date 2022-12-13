package com.epam.web;

import com.epam.dto.EcoServiceDto;
import com.epam.mapper.EcoServiceMapper;
import com.epam.security.Authority;
import com.epam.service.ServiceManagementService;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@PreAuthorize(Authority.HAS_SERVICE_AUTHORITY)
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/ecoservice/manage")
public class ServiceManagementController {
  private final ServiceManagementService serviceManagementService;
  private final EcoServiceMapper ecoServiceMapper;

  /**
   * Delete Eco Service from DB
   *
   * @param id of Eco Service for deletion
   */
  @DeleteMapping(path = "/{id}")
  public ResponseEntity<String> deleteEcoService(@PathVariable final long id) {
    if (serviceManagementService.deleteEcoServiceAuthorized(id) != 0) {
      return ResponseEntity.ok("Eco Service deleted!");
    }
    return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Eco Service not found with this id!");
  }

  /**
   * Add new Eco Service to DB
   *
   * @param ecoServiceDto Eco Service what you want to add database
   * @return the DTO representation of saved entity
   */
  @PostMapping
  public EcoServiceDto createEcoService(@RequestBody @Valid final EcoServiceDto ecoServiceDto) {
    return ecoServiceMapper.ecoServiceToEcoServiceDto(
        serviceManagementService.createNewEcoServiceAuthorized(
            ecoServiceMapper.ecoServiceDtoToEcoService(ecoServiceDto)));
  }

  /**
   * Modify existing Eco Service
   *
   * @param ecoServiceDto new datas of service, add unmodified fields too
   * @param id of modified Eco Service
   * @return the DTO representation of modified entity
   */
  @PutMapping(path = "/{id}")
  public EcoServiceDto updateEcoService(
      @RequestBody @Valid final EcoServiceDto ecoServiceDto, @PathVariable final long id) {
    return ecoServiceMapper.ecoServiceToEcoServiceDto(
        serviceManagementService.updateEcoServiceAuthorized(
            ecoServiceMapper.ecoServiceDtoToEcoService(ecoServiceDto), id));
  }
}
