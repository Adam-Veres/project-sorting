package com.epam.service;

import com.epam.config.JwtTokenUtil;
import com.epam.dto.EcoUserDTO;
import com.epam.exceptions.UserAlreadyExistsException;
import com.epam.mapper.EcoUserMapper;
import com.epam.model.EcoUser;
import com.epam.repository.EcoUserRepository;
import com.epam.security.SecurityUser;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;



@Service
@RequiredArgsConstructor
public class JwtEcoUserDetailsService implements UserDetailsService {

	private final EcoUserRepository ecoUserRepository;

	@Override
	public UserDetails loadUserByUsername(String s) throws AuthenticationException {
		EcoUser user = ecoUserRepository.findByUsername(s).orElseThrow(
				()->new UsernameNotFoundException("USER_DOES_NOT_EXIST")
		);
		return SecurityUser.fromEcoUser(user);
	}
}