package nl.rls.ci.domain.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigInteger;

@Getter
@Setter
@NoArgsConstructor
public class ErrorMessageDto {
    private Integer id;
    private String messageStatus;
    private BigInteger typeOfError;
    private BigInteger severity;
    private int errorCode;
    private String freeTextField;
}
