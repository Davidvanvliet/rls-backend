package nl.rls.composer.rest.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import java.util.Date;

@NoArgsConstructor
@Getter
@Setter
public class TrainPostDto {
    @NotBlank(message = "operationalTrainNumber is mandatory")
    private String operationalTrainNumber;
    private String transferPoint;
    private Date scheduledTimeAtHandover;
    private Date scheduledDateTimeAtTransfer;
    private Integer trainType;
}
