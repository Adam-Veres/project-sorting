package com.epam.mapper;

import com.epam.repository.ServiceRatingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
@RequiredArgsConstructor
public class CommonMapper {

    private final PasswordEncoder passwordEncoder;

    private final ServiceRatingRepository serviceRatingRepository;

    @PasswordEncoderMapping
    public String encode(String value) {
        return passwordEncoder.encode(value);
    }

    @RatingEncoderMapping
    public Float getRating(long serviceId){
        return serviceRatingRepository.averageServiceRating(serviceId).orElse(0.0F);
    }
}
