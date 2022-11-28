package com.epam.service;

import com.epam.dto.EcoUserDTO;
import com.epam.exceptions.UserAlreadyExistsException;
import com.epam.model.EcoUser;
import com.epam.repository.EcoUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class JwtEcoUserDetailsService implements UserDetailsService {

	private final EcoUserRepository ecoUserRepository;

	private final PasswordEncoder bcryptEncoder;

	@Override
	public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException {
		final EcoUser user = ecoUserRepository.findByUsername(username);
		if (user == null) {
			throw new UsernameNotFoundException("User not found with username: " + username);
		}
		return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(),
				new ArrayList<>());
	}
	
	public EcoUser save(EcoUserDTO user) throws Exception {
		final EcoUser newUser = new EcoUser();
		newUser.setUsername(user.getUsername());
		newUser.setPassword(bcryptEncoder.encode(user.getPassword()));
		try {
			return ecoUserRepository.save(newUser);
		} catch (Exception e) {
			throw new UserAlreadyExistsException("USER_ALREADY_EXISTS", e);
		}
	}
}