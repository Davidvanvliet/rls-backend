package nl.rls.composer.rest.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@NoArgsConstructor
@Getter
@Setter
public class WagonInTrainAddDto {
    private int position;
    private String brakeType;
    @Min(0)
    @Max(999999)
    private int totalLoadWeight;
    @Min(1)
    @Max(99999)
    private int brakeWeight;
    @Min(0)
    @Max(999)
    private int wagonMaxSpeed;
    private String wagonUrl;
    private String breakTypeUrl;
}