package nl.rls.util;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class ErrorDto {
    private String field;
    private String message;
    private String stackTrace;
    private List<ErrorDto> errors;

    public void addError(ErrorDto errorDto) {
        if (this.errors == null) {
            errors = new ArrayList<>();
        }
        this.errors.add(errorDto);
    }
}
