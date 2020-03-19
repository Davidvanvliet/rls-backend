package nl.rls.composer.rest.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
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
@ApiModel(description = "DTO to update or create a Wagon instance.")
public class WagonPostDto {
    @ApiModelProperty(notes = "Identifies uniquely the freight wagon by its number. Identification code of a freight wagon based on the TSI OPE and CEN Recommendations and CIS wagons coded according to OSJD-UIC leaflet 402, which allows the conversion from 8 digits to 12 digits and viceversa.")
    private String numberFreight;
    private String name;
    private String code;
    @Min(1)
    @Max(999999)
    private int lengthOverBuffers;
    @Min(0)
    @Max(999)
    private int wagonNumberOfAxles;
    @Min(1)
    @Max(999999)
    private int wagonWeightEmpty;
    @Min(0)
    @Max(999)
    private int handBrakeBrakedWeight;
}
