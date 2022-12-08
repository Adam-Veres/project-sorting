package com.epam.web;

import com.epam.dto.EcoUserDTO;
import com.epam.dto.JwtControllersResponseMessage;
import com.epam.service.JwtEcoUserDetailsService;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/auth/")
@RequiredArgsConstructor
public class JwtRegistrationController {

  private final JwtEcoUserDetailsService jwtEcoUserDetailsService;

  @RequestMapping(value = "/register", method = RequestMethod.POST)
  public ResponseEntity<?> saveUser(@Valid @RequestBody final EcoUserDTO user) throws Exception {
    jwtEcoUserDetailsService.save(user);
    return ResponseEntity.ok(new JwtControllersResponseMessage("Ok"));
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
  public ResponseEntity<?> handleException() {
    return new ResponseEntity<>(
        new JwtControllersResponseMessage("COMMON_ERROR"), HttpStatus.UNAUTHORIZED);
  }
}
