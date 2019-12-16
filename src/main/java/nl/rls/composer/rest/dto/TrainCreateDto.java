package nl.rls.composer.rest.dto;

import java.util.Date;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter @Setter
public class TrainCreateDto {
    private String operationalTrainNumber;
	private String transferPoint;
    private Date scheduledTimeAtHandover;
    private Date scheduledDateTimeAtTransfer;
}
