package com.epam.service;

import com.epam.dto.EcoUserDTO;
import com.epam.exceptions.UserAlreadyExistsException;
import com.epam.mapper.EcoUserMapper;
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

	private final EcoUserMapper ecoUserMapper;

	@Override
	public UserDetails loadUserByUsername(final String username) throws AuthenticationException {
		final EcoUser user = ecoUserRepository.findByUsername(username).orElseThrow(
				()->new UsernameNotFoundException("USER_DOES_NOT_EXIST")
		);
		return SecurityUser.fromEcoUser(user);
	}
	
	public EcoUser save(EcoUserDTO userDto) throws AuthenticationException {
		final EcoUser newUser = ecoUserMapper.ecoUserDtoToEcoUser(userDto);
		try {
			return ecoUserRepository.save(newUser);
		} catch (Exception e) {
			throw new UserAlreadyExistsException("USER_ALREADY_EXISTS", e);
		}
	}
}