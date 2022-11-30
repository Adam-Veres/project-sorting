package com.epam.web;


import com.epam.config.JwtTokenUtil;
import com.epam.dto.EcoUserDTO;
import com.epam.exceptions.UserAlreadyExistsException;
import com.epam.model.JwtRequest;
import com.epam.model.JwtResponse;
import com.epam.service.JwtEcoUserDetailsService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.*;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.BindException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
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

	private final JwtEcoUserDetailsService jwtEcoUserDetailsService;

	@Getter
	private static final class ResponseMessage {
		private final Set<String> message;
		ResponseMessage(BindException e){
			this.message=e.getAllErrors().stream().map(ObjectError::getDefaultMessage).collect(Collectors.toSet());
		}
		ResponseMessage(Exception e){
			this(e.getMessage());
		}
		ResponseMessage(String message){
			this.message=Collections.singleton(message);
		}
	}

	@RequestMapping(value = "/authenticate", method = RequestMethod.POST)
	public ResponseEntity<?> createAuthenticationToken(@RequestBody final JwtRequest authenticationRequest) throws Exception {

		authenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword());

		final UserDetails userDetails = jwtEcoUserDetailsService
				.loadUserByUsername(authenticationRequest.getUsername());

		final String token = jwtTokenUtil.generateToken(userDetails);

		return ResponseEntity.ok(new JwtResponse(token));
	}

	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public ResponseEntity<?> saveUser(@Valid @RequestBody final EcoUserDTO user) throws Exception {
		jwtEcoUserDetailsService.save(user);
		return ResponseEntity.ok(new ResponseMessage("Ok"));
	}

	private void authenticate(final String username, final String password) throws Exception {
		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
		} catch (LockedException e) {
			throw new LockedException("USER_LOCKED", e);
		} catch (BadCredentialsException e) {
			throw new BadCredentialsException("INVALID_CREDENTIALS", e);
		}
	}

	@ExceptionHandler(AuthenticationException.class)
	public ResponseEntity<?> handleAuthenticationExceptionException(final AuthenticationException e) {
		return new ResponseEntity<>(new ResponseMessage(e), HttpStatus.UNAUTHORIZED);
	}

	@ExceptionHandler(BindException.class)
	public ResponseEntity<?> handleMethodArgumentNotValidException(final BindException e) {
		return new ResponseEntity<>(new ResponseMessage(e), HttpStatus.UNAUTHORIZED);
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<?> handleException(final Exception e) {
		return new ResponseEntity<>(new ResponseMessage("COMMON_ERROR"), HttpStatus.UNAUTHORIZED);
	}
}