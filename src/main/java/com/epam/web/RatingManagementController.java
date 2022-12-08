package com.epam.web;

import com.epam.dto.EcoServiceDto;
import com.epam.dto.JwtControllersResponseMessage;
import com.epam.mapper.EcoServiceMapper;
import com.epam.security.Authority;
import com.epam.service.ServiceManagementService;
import lombok.RequiredArgsConstructor;

import javax.validation.ConstraintViolationException;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@PreAuthorize(Authority.HAS_USER_AUTHORITY)
@Validated
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
    public EcoServiceDto addRatingToEcoService(@PathVariable @Min(1) @Max(5) final int rating, @PathVariable final long id) {
        return ecoServiceMapper.ecoServiceToEcoServiceDto(serviceManagementService.addRatingToEcoService(rating, id));
    }
    
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<?> handleException() {
        return new ResponseEntity<>(new JwtControllersResponseMessage("Rating should be an integer between 1 and 5!"), HttpStatus.BAD_REQUEST);
    }

}
