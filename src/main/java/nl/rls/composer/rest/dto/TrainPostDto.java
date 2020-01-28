package nl.rls.composer.rest.dto;

import java.util.Date;

import javax.validation.constraints.NotBlank;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter @Setter
public class TrainPostDto {
	@NotBlank(message = "operationalTrainNumber is mandatory")
    private String operationalTrainNumber;
	private String transferPoint;
    private Date scheduledTimeAtHandover;
    private Date scheduledDateTimeAtTransfer;
	private Integer trainType;
}
