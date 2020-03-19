package nl.rls.util;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.persistence.EntityNotFoundException;
import java.io.PrintWriter;
import java.io.StringWriter;

@ControllerAdvice
public class RestResponseExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = {
            EntityNotFoundException.class
    })
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<?> entityNotFound(EntityNotFoundException e, WebRequest request) {
        return convertToResponseEntity(ResponseBuilder.notFound().error(toErrorDto(e)).build());
    }

    private ResponseEntity<Response<?>> convertToResponseEntity(Response<?> response) {
        return ResponseEntity.status(response.getStatus())
                .body(response);
    }

    private ErrorDto toErrorDto(RuntimeException e) {
        ErrorDto errorDto = new ErrorDto();
        errorDto.setMessage(e.getMessage());
        StringWriter sw = new StringWriter();
        e.printStackTrace(new PrintWriter(sw));
        errorDto.setStackTrace(sw.toString());
        return errorDto;
    }
}
