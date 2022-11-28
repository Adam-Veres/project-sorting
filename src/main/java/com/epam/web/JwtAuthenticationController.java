package com.epam.web;


import com.epam.config.JwtTokenUtil;
import com.epam.dto.EcoUserDTO;
import com.epam.exceptions.UserAlreadyExistsException;
import com.epam.model.JwtRequest;
import com.epam.model.JwtResponse;
import com.epam.service.JwtEcoUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.BindException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collections;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@CrossOrigin
@RequestMapping("/api/auth/")
@RequiredArgsConstructor
public class JwtAuthenticationController {

	protected final Log logger = LogFactory.getLog(this.getClass());

	private final AuthenticationManager authenticationManager;

	private final JwtTokenUtil jwtTokenUtil;

	private final JwtEcoUserDetailsService userDetailsService;

	@RequestMapping(value = "/authenticate", method = RequestMethod.POST)
	public ResponseEntity<?> createAuthenticationToken(@RequestBody final JwtRequest authenticationRequest) throws Exception {

		authenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword());

		final UserDetails userDetails = userDetailsService
				.loadUserByUsername(authenticationRequest.getUsername());

		final String token = jwtTokenUtil.generateToken(userDetails);

		return ResponseEntity.ok(new JwtResponse(token));
	}

	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public ResponseEntity<?> saveUser(@Valid @RequestBody final EcoUserDTO user) throws Exception {
		return ResponseEntity.ok(userDetailsService.save(user));
	}

	private void authenticate(final String username, final String password) throws Exception {
		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
		} catch (DisabledException e) {
			throw new Exception("USER_DISABLED", e);
		} catch (BadCredentialsException e) {
			throw new Exception("INVALID_CREDENTIALS", e);
		}
	}

	@ExceptionHandler(BindException.class)
	public ResponseEntity<Map<String, Set<String>>> handleMethodArgumentNotValidException(final BindException e) {
		return new ResponseEntity<>(Collections.singletonMap("message",e.getAllErrors().stream().map(ObjectError::getDefaultMessage).collect(Collectors.toSet())), HttpStatus.UNAUTHORIZED);
	}

	@ExceptionHandler(UserAlreadyExistsException.class)
	public ResponseEntity<Map<String, Set<String>>> handleUserAlreadyExistsException(final UserAlreadyExistsException e) {
		return new ResponseEntity<>(Collections.singletonMap("message",Collections.singleton(e.getMessage())), HttpStatus.UNAUTHORIZED);
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<Map<String,Set<String>>> handleException(final Exception e) {
		logger.warn("Unexpected exception: "+e.getMessage());
		return new ResponseEntity<>(Collections.singletonMap("message",Collections.singleton("COMMON_ERROR")), HttpStatus.UNAUTHORIZED);
	}
}