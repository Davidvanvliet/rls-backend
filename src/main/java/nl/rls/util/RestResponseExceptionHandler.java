package nl.rls.util;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.persistence.EntityNotFoundException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.List;

@ControllerAdvice
public class RestResponseExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = {
            EntityNotFoundException.class
    })
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<Object> entityNotFound(EntityNotFoundException e, WebRequest request) {
        return convertToResponseEntity(ResponseBuilder.notFound().error(toErrorDto(e)).build());
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        BindingResult bindingResult = ex.getBindingResult();
        List<FieldError> fieldErrors = bindingResult.getFieldErrors();
        ErrorDto errorDto = new ErrorDto();
        errorDto.setMessage("Validation failed");
        for (FieldError fieldError : fieldErrors) {
            ErrorDto fieldErrorDto = new ErrorDto();
            fieldErrorDto.setMessage(fieldError.getDefaultMessage());
            fieldErrorDto.setField(fieldError.getField());
            errorDto.addError(fieldErrorDto);
        }
        return convertToResponseEntity(ResponseBuilder.badRequest().error(errorDto).build());
    }

    private ResponseEntity<Object> convertToResponseEntity(Response<?> response) {
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
