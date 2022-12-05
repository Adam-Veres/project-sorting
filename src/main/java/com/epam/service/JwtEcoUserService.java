package com.epam.service;

import com.epam.config.JwtTokenUtil;
import com.epam.dto.EcoUserDTO;
import com.epam.exceptions.UserAlreadyExistsException;
import com.epam.mapper.EcoUserMapper;
import com.epam.model.EcoUser;
import com.epam.repository.EcoUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class JwtEcoUserService {

    private final AuthenticationManager authenticationManager;

    private final EcoUserMapper ecoUserMapper;

    private final JwtTokenUtil jwtTokenUtil;

    private final EcoUserRepository ecoUserRepository;

    public void authenticate(final String username, final String password) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (LockedException e) {
            throw new LockedException("USER_LOCKED", e);
        } catch (BadCredentialsException e) {
            throw new BadCredentialsException("INVALID_CREDENTIALS", e);
        }
    }

    public EcoUser save(final EcoUserDTO userDto) throws AuthenticationException {
        final EcoUser newUser = ecoUserMapper.ecoUserDtoToEcoUser(userDto);
        try {
            return ecoUserRepository.save(newUser);
        } catch (Exception e) {
            throw new UserAlreadyExistsException("USER_ALREADY_EXISTS", e);
        }
    }

    public String getToken(String userName, String password){
        authenticate(userName, password);
        final Map<String, Object> claims = new HashMap<>();
        ecoUserRepository.findByUsername(userName).ifPresent(
                ecoUser -> claims.put("role",ecoUser.getUserRole())
        );
        return jwtTokenUtil.generateToken(claims,userName);
    }
}
