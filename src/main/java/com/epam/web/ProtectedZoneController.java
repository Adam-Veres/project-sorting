package com.epam.web;

import com.epam.dto.EcoServiceDto;
import com.epam.mapper.EcoServiceMapper;
import com.epam.security.Authority;
import com.epam.service.ProtectedZoneService;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

import javax.validation.Valid;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/ecoservice/manage")
public class ProtectedZoneController {
    private final ProtectedZoneService protectedZoneService;
    private final EcoServiceMapper ecoServiceMapper;

    /**
	 * Delete Eco Service from DB
	 * @param id of Eco Service for deletion
	 */
    @PreAuthorize(Authority.HAS_SERVICE_AUTHORITY)
    @DeleteMapping(path = "/{id}")
    public ResponseEntity<String> deleteEcoService(@PathVariable long id) {
    	if(protectedZoneService.deleteEcoServiceAuthorized(id) != 0) {
    		return ResponseEntity.ok("Eco Service deleted!");
    	}
    	return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Eco Service not found with this id!");
    }

    /**
	 * Add new Eco Service to DB
	 * @param ecoServiceDto Eco Service what you want to add database
	 * @return the DTO representation of saved entity
	 */
    @PreAuthorize(Authority.HAS_SERVICE_AUTHORITY)
    @PostMapping
    public EcoServiceDto createEcoService(@RequestBody @Valid EcoServiceDto ecoServiceDto) {
      return ecoServiceMapper.ecoServiceToEcoServiceDto(
                protectedZoneService.createNewEcoServiceAuthorized(
                    ecoServiceMapper.ecoServiceDtoToEcoService(ecoServiceDto)));
    }

    /**
	 * Modify existing Eco Service
	 * @param ecoServiceDto new datas of service, add unmodified fields too
	 * @param id of modified Eco Service
	 * @return the DTO representation of modified entity
	 */
    @PreAuthorize(Authority.HAS_SERVICE_AUTHORITY)
    @PutMapping(path = "/{id}")
    public EcoServiceDto updateEcoService( @RequestBody @Valid EcoServiceDto ecoServiceDto, @PathVariable long id) {
      return ecoServiceMapper.ecoServiceToEcoServiceDto(
                protectedZoneService.updateEcoServiceAuthorized(
                        ecoServiceMapper.ecoServiceDtoToEcoService(ecoServiceDto), id));

    }
    
    /**
	 * Add a rating to the service
	 * @param rating
	 * @param id
	 * @return the whole service with updated rating
	 */
	@PutMapping(path = "/{rating}/{id}")
	public EcoServiceDto addRatingToEcoService(@PathVariable Double rating, @PathVariable Long id) {
		EcoServiceDto ecoService = ecoServiceMapper.ecoServiceToEcoServiceDto(protectedZoneService.addRatingToEcoService(Optional.of(rating), Optional.of(id)));
		if(ecoService != null) {
			return ecoService;
		}
		throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Eco Service not found with this id!");
	}
}
