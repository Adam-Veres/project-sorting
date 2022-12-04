package com.epam.service;

import com.epam.model.EcoUser;
import com.epam.repository.EcoUserRepository;
import com.epam.security.SecurityUser;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;



@Service
@RequiredArgsConstructor
public class JwtEcoUserDetailsService implements UserDetailsService {

	private final EcoUserRepository ecoUserRepository;

	@Override
	public UserDetails loadUserByUsername(final String username) throws AuthenticationException {
		final EcoUser user = ecoUserRepository.findByUsername(username).orElseThrow(
				()->new UsernameNotFoundException("USER_DOES_NOT_EXIST")
		);
		return SecurityUser.fromEcoUser(user);
	}
}