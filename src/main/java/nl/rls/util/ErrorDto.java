package nl.rls.util;

import lombok.Data;

@Data
public class ErrorDto {
    private String message;
    private String stackTrace;
}
