package com.epam.web;

import com.epam.dto.EcoServiceDto;
import com.epam.dto.EcoServiceDtoNarrow;
import com.epam.mapper.EcoServiceMapper;
import com.epam.model.EcoService;
import com.epam.service.EcoServiceService;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/ecoservice")
@RequiredArgsConstructor
public class EcoServiceQueryController {

  private final EcoServiceService ecoServiceService;
  private final EcoServiceMapper ecoServiceMapper;

  /** @return give back all Eco Services from database */
  @GetMapping
  public List<EcoServiceDto> getAllServices() {
    final List<EcoServiceDto> ecoServices =
        ecoServiceMapper.ecoServiceListToEcoServiceListDto(ecoServiceService.getAllEcoService());
    if (!ecoServices.isEmpty()) {
      return ecoServices;
    }
    throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No EcoServices in Database!");
  }

  /**
   * You should get bottom-left (bl) and top-right (tr) coordinate
   *
   * @param blLatitude
   * @param blLongitude
   * @param trLatitude
   * @param trLongitude
   * @return list of Eco Services in the area (DTO)
   */
  @GetMapping(params = {"bl_latitude", "bl_longitude", "tr_latitude", "tr_longitude"})
  public List<EcoServiceDtoNarrow> getServicesFromArea(
      @RequestParam(name = "bl_latitude") final Optional<BigDecimal> blLatitude,
      @RequestParam(name = "bl_longitude") final Optional<BigDecimal> blLongitude,
      @RequestParam(name = "tr_latitude") final Optional<BigDecimal> trLatitude,
      @RequestParam(name = "tr_longitude") final Optional<BigDecimal> trLongitude) {
    final List<EcoServiceDtoNarrow> ecoServices =
        ecoServiceMapper.ecoServiceListToEcoServiceListDtoNarrow(
            ecoServiceService.getServiceFromArea(blLatitude, blLongitude, trLatitude, trLongitude));
    if (!ecoServices.isEmpty()) {
      return ecoServices;
    }
    throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No EcoServices in Database!");
  }

  /**
   * @param id of service
   * @return give back one Eco Service
   */
  @GetMapping(path = "/{id}")
  public EcoServiceDto getServiceById(@PathVariable final Long id) {
    final EcoService es =
        ecoServiceService
            .getServiceById(Optional.of(id))
            .orElseThrow(
                () ->
                    new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "No EcoServices exist with this id: " + id + "  in Database!"));
    return ecoServiceMapper.ecoServiceToEcoServiceDto(es);
  }

  /**
   * @param ecoServiceDto will an example for searching
   * @param distance
   * @return give back Eco Services what satisfied the requirements
   */
  @PostMapping(params = {"bl_latitude", "bl_longitude", "tr_latitude", "tr_longitude"})
  public List<EcoServiceDtoNarrow> getFilteredServicesFromArea(
      @RequestBody final EcoServiceDto ecoServiceDto,
      @RequestParam(name = "bl_latitude") final Optional<BigDecimal> blLatitude,
      @RequestParam(name = "bl_longitude") final Optional<BigDecimal> blLongitude,
      @RequestParam(name = "tr_latitude") final Optional<BigDecimal> trLatitude,
      @RequestParam(name = "tr_longitude") final Optional<BigDecimal> trLongitude) {
    final List<EcoServiceDtoNarrow> ecoServices =
        ecoServiceMapper.ecoServiceListToEcoServiceListDtoNarrow(
            ecoServiceService.getFilteredService(
                ecoServiceMapper.ecoServiceDtoToEcoService(ecoServiceDto),
                blLatitude,
                blLongitude,
                trLatitude,
                trLongitude));
    if (!ecoServices.isEmpty()) {
      return ecoServices;
    }
    throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No EcoServices in Database!");
  }
}
