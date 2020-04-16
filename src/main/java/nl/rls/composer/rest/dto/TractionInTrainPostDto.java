package nl.rls.composer.rest.dto;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class TractionInTrainPostDto {
    @Min(0)
    @Max(99)
    private int position;
    @Min(0)
    @Max(1)
    private int driverIndication;
	@Size(min = 2, max = 2)
    private String tractionMode;
    private String tractionUrl;
}
