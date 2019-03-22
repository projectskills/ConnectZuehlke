package ch.zuehlke.fullstack.ConnectZuehlke.common.exceptionHandling;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;

import java.net.UnknownHostException;

import static org.springframework.http.HttpStatus.*;

@ControllerAdvice
public class ExceptionHandling {
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ProblemDto> handleResourceNotFoundException() {

        ProblemDto problem = new ProblemDto(NOT_FOUND.getReasonPhrase(), NOT_FOUND.value(), "The requested resource is not available");

        return ResponseEntity.status(NOT_FOUND).body(problem);
    }

    @ExceptionHandler(HttpClientErrorException.class)
    public ResponseEntity<ProblemDto> handleClientErrorException() {

        ProblemDto problem = new ProblemDto(INTERNAL_SERVER_ERROR.getReasonPhrase(), INTERNAL_SERVER_ERROR.value(),
                "Internal Server Error");

        return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(problem);
    }

    @ExceptionHandler(HttpServerErrorException.class)
    public ResponseEntity<ProblemDto> handleServerErrorException() {

        ProblemDto problem = new ProblemDto(BAD_GATEWAY.getReasonPhrase(), BAD_GATEWAY.value(),
                "External Server Error");

        return ResponseEntity.status(BAD_GATEWAY).body(problem);
    }

    @ExceptionHandler(ApplicationException.class)
    public ResponseEntity<ProblemDto> handleApplicationException(ApplicationException exception) {

        ProblemDto problem = new ProblemDto(BAD_REQUEST.getReasonPhrase(), BAD_REQUEST.value(), exception.getProblem());

        return ResponseEntity.status(BAD_REQUEST).body(problem);
    }

    @ExceptionHandler(UnknownHostException.class)
    public ResponseEntity<ProblemDto> handleUnknownHostException(UnknownHostException exception) {

        ProblemDto problem = new ProblemDto(BAD_REQUEST.getReasonPhrase(), BAD_REQUEST.value(),
                "Cannot resolve external API endpoint: " + exception.getLocalizedMessage());

        return ResponseEntity.status(BAD_REQUEST).body(problem);
    }
}
