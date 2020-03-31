package nl.rls.composer.rest.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@NoArgsConstructor
@Getter
@Setter
public class TractionAddDto {
    private String traction;
    @Min(0)
    @Max(99)
    private int tractionPositionInTrain;
    @Min(0)
    @Max(1)
    private int driverIndication;
	@Size(min = 2, max = 2)
    private String tractionMode;

}