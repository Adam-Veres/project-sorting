package com.epam.web;

import com.epam.dto.EcoServiceDto;
import com.epam.mapper.EcoServiceMapper;
import com.epam.security.Authority;
import com.epam.service.ProtectedZoneService;
import java.util.Set;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RequiredArgsConstructor
@RestController
@RequestMapping("/api/protected")
public class ProtectedZoneController {
  private final ProtectedZoneService protectedZoneService;
  private final EcoServiceMapper ecoServiceMapper;

  @GetMapping(value = "/whoami")
  public Set<Object> addService() {
    // HttpServletRequest request
    return protectedZoneService.getUserInformation();
  }

  @PreAuthorize(Authority.HAS_SERVICE_AUTHORITY)
  @DeleteMapping(path = "/manage/{id}")
  public boolean deleteEcoService(@PathVariable long id) {
    return protectedZoneService.deleteEcoServiceAuthorized(id) != 0;
  }

  @PreAuthorize(Authority.HAS_SERVICE_AUTHORITY)
  @PostMapping(path = "/manage")
  public EcoServiceDto createEcoService(@RequestBody @Valid EcoServiceDto ecoServiceDto) {
    return ecoServiceMapper.ecoServiceToEcoServiceDto(
        protectedZoneService.createNewEcoServiceAuthorized(
            ecoServiceMapper.ecoServiceDtoToEcoService(ecoServiceDto)));
  }

  @PreAuthorize(Authority.HAS_SERVICE_AUTHORITY)
  @PutMapping(path = "/manage/{id}")
  public EcoServiceDto updateEcoService(
      @RequestBody @Valid EcoServiceDto ecoServiceDto, @PathVariable long id) {
    return ecoServiceMapper.ecoServiceToEcoServiceDto(
        protectedZoneService.updateEcoServiceAuthorized(
            ecoServiceMapper.ecoServiceDtoToEcoService(ecoServiceDto), id));
  }
}
