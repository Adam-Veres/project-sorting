package com.epam.web;

import com.epam.dto.JwtAuthRequest;
import com.epam.dto.JwtControllersResponseMessage;
import com.epam.dto.JwtTockenResponse;
import com.epam.service.JwtEcoUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth/")
@RequiredArgsConstructor
public class JwtAuthenticationController {

  private final JwtEcoUserService jwtEcoUserService;

  @RequestMapping(value = "/authenticate", method = RequestMethod.POST)
  public ResponseEntity<?> createAuthenticationToken(@RequestBody final JwtAuthRequest authenticationRequest) {
    return ResponseEntity.ok(
        new JwtTockenResponse(
            jwtEcoUserService.getToken(
                authenticationRequest.getUsername(), authenticationRequest.getPassword())));
  }

	@ExceptionHandler(AuthenticationException.class)
	public ResponseEntity<?> handleAuthenticationExceptionException(final AuthenticationException e) {
		return new ResponseEntity<>(new JwtControllersResponseMessage(e), HttpStatus.UNAUTHORIZED);
	}

	@ExceptionHandler(BindException.class)
	public ResponseEntity<?> handleMethodArgumentNotValidException(final BindException e) {
		return new ResponseEntity<>(new JwtControllersResponseMessage(e), HttpStatus.UNAUTHORIZED);
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<?> handleException(final Exception e) {
		return new ResponseEntity<>(new JwtControllersResponseMessage("COMMON_ERROR"), HttpStatus.UNAUTHORIZED);
	}
}