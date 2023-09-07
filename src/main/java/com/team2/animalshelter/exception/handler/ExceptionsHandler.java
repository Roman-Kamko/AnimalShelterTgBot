package com.team2.animalshelter.exception.handler;

import com.team2.animalshelter.exception.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@Slf4j
@RestControllerAdvice
public class ExceptionsHandler {

    @ExceptionHandler(
            {
                    AdaptationNotFoundException.class,
                    AnimalNotFoundException.class,
                    OwnerNotFoundException.class,
                    ReportNotFoundException.class,
                    ShelterNotFoundException.class,
                    UserNotFoundException.class,
                    VolunteerNotFoundException.class
            }
    )
    public ResponseEntity<?> handleNotFoundExceptions(RuntimeException exception) {
        log.error(exception.getMessage(), exception);
        return ResponseEntity.status(NOT_FOUND)
                .body(exception.getMessage());
    }

}
