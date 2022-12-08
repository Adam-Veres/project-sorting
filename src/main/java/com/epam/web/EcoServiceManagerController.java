package com.epam.web;

import com.epam.dto.EcoServiceDto;
import com.epam.mapper.EcoServiceMapper;
import com.epam.service.EcoServiceService;
import java.util.Optional;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/ecoservice/manage")
@RequiredArgsConstructor
public class EcoServiceManagerController {

  private final EcoServiceService ecoServiceService;
  private final EcoServiceMapper ecoServiceMapper;

  /**
   * Delete Eco Service from DB
   *
   * @param id of Eco Service for deletion
   */
  @DeleteMapping(path = "/{id}")
  public void deleteEcoService(@PathVariable final Long id) {
    if (ecoServiceService.deleteEcoService(Optional.of(id))) {
      throw new ResponseStatusException(HttpStatus.OK, "Eco Service deleted!");
    }
    throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Eco Service not found with this id!");
  }

  /**
   * Add new Eco Service to DB
   *
   * @param ecoServiceDto Eco Service what you want to add database
   * @return the DTO representation of saved entity
   */
  @PostMapping
  public EcoServiceDto createEcoService(@RequestBody @Valid final EcoServiceDto ecoServiceDto) {
    final EcoServiceDto ecoService =
        ecoServiceMapper.ecoServiceToEcoServiceDto(
            ecoServiceService.createNewEcoService(
                Optional.of(ecoServiceMapper.ecoServiceDtoToEcoService(ecoServiceDto))));
    if (ecoService != null) {
      return ecoService;
    }
    throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Eco Service not found with this id!");
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
      @RequestBody @Valid final EcoServiceDto ecoServiceDto, @PathVariable final Long id) {
    final EcoServiceDto ecoService =
        ecoServiceMapper.ecoServiceToEcoServiceDto(
            ecoServiceService.updateEcoService(
                Optional.of(ecoServiceMapper.ecoServiceDtoToEcoService(ecoServiceDto)),
                Optional.of(id)));
    if (ecoService != null) {
      return ecoService;
    }
    throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Eco Service not found with this id!");
  }

  /**
   * Add a rating to the service
   *
   * @param rating
   * @param id
   * @return the whole service with updated rating
   */
  @PutMapping(path = "/{rating}/{id}")
  public EcoServiceDto addRatingToEcoService(
      @PathVariable final Double rating, @PathVariable final Long id) {
    final EcoServiceDto ecoService =
        ecoServiceMapper.ecoServiceToEcoServiceDto(
            ecoServiceService.addRatingToEcoService(Optional.of(rating), Optional.of(id)));
    if (ecoService != null) {
      return ecoService;
    }
    throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Eco Service not found with this id!");
  }
}
