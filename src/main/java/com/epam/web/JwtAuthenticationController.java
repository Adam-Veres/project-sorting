package com.epam.web;

import com.epam.dto.EcoUserDTO;
import com.epam.model.JwtRequest;
import com.epam.model.JwtResponse;
import com.epam.service.JwtEcoUserDetailsService;
import com.epam.service.JwtEcoUserService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.BindException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@CrossOrigin
@RequestMapping("/api/auth/")
@RequiredArgsConstructor
public class JwtAuthenticationController {

  private final JwtEcoUserService jwtEcoUserService;

  @Getter
  private static final class ResponseMessage {
    private final Set<String> message;

    ResponseMessage(BindException e) {
      this.message =
          e.getAllErrors().stream().map(ObjectError::getDefaultMessage).collect(Collectors.toSet());
    }

    ResponseMessage(Exception e) {
      this(e.getMessage());
    }

    ResponseMessage(String message) {
      this.message = Collections.singleton(message);
    }
  }

  @RequestMapping(value = "/authenticate", method = RequestMethod.POST)
  public ResponseEntity<?> createAuthenticationToken(@RequestBody final JwtRequest authenticationRequest) {
    return ResponseEntity.ok(
        new JwtResponse(
            jwtEcoUserService.getToken(
                authenticationRequest.getUsername(), authenticationRequest.getPassword())));
  }

  @RequestMapping(value = "/register", method = RequestMethod.POST)
  public ResponseEntity<?> saveUser(@Valid @RequestBody final EcoUserDTO user) {
    jwtEcoUserService.save(user);
    return ResponseEntity.ok(
            new JwtResponse(
                    jwtEcoUserService.getToken(
                            user.getUsername(), user.getPassword())));
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
    final Log logger = LogFactory.getLog(this.getClass());
    logger.error(e);
    return new ResponseEntity<>(new ResponseMessage("COMMON_ERROR"), HttpStatus.UNAUTHORIZED);
  }
}
