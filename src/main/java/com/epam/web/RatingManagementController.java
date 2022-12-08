package com.epam.web;

import com.epam.dto.EcoServiceDto;
import com.epam.mapper.EcoServiceMapper;
import com.epam.security.Authority;
import com.epam.service.ServiceManagementService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

@PreAuthorize(Authority.HAS_USER_AUTHORITY)
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/ecoservice/manage")
public class RatingManagementController {
    private final ServiceManagementService serviceManagementService;
    private final EcoServiceMapper ecoServiceMapper;

    /**
     * Add a rating to the service
     * @param rating
     * @param id
     * @return the whole service with updated rating
     */
    @PutMapping(path = "/{rating}/{id}")
    public EcoServiceDto addRatingToEcoService(@PathVariable @Valid @Min(1) @Max(5) final int rating, @PathVariable final long id) {
        return ecoServiceMapper.ecoServiceToEcoServiceDto(serviceManagementService.addRatingToEcoService(rating, id));
    }
}
