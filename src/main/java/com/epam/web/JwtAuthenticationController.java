package com.epam.web;

import com.epam.config.JwtTokenUtil;
import com.epam.dto.JwtAuthRequest;
import com.epam.dto.JwtControllersResponseMessage;
import com.epam.dto.JwtTockenResponse;
import com.epam.service.JwtEcoUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth/")
@RequiredArgsConstructor
public class JwtAuthenticationController {

  private final AuthenticationManager authenticationManager;

  private final JwtTokenUtil jwtTokenUtil;

  private final JwtEcoUserDetailsService jwtEcoUserDetailsService;

  @RequestMapping(value = "/authenticate", method = RequestMethod.POST)
  public ResponseEntity<?> createAuthenticationToken(
      @RequestBody final JwtAuthRequest authenticationRequest) throws Exception {

    authenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword());

    final UserDetails userDetails =
        jwtEcoUserDetailsService.loadUserByUsername(authenticationRequest.getUsername());

    final String token = jwtTokenUtil.generateToken(userDetails);

    return ResponseEntity.ok(new JwtTockenResponse(token));
  }

  private void authenticate(final String username, final String password) throws Exception {
    try {
      authenticationManager.authenticate(
          new UsernamePasswordAuthenticationToken(username, password));
    } catch (LockedException e) {
      throw new LockedException("USER_LOCKED", e);
    } catch (BadCredentialsException e) {
      throw new BadCredentialsException("INVALID_CREDENTIALS", e);
    }
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
    return new ResponseEntity<>(
        new JwtControllersResponseMessage("COMMON_ERROR"), HttpStatus.UNAUTHORIZED);
  }
}
