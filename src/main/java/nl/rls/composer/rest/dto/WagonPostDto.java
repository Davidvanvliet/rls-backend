package nl.rls.composer.rest.dto;

import javax.xml.bind.annotation.XmlRootElement;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@XmlRootElement
@NoArgsConstructor
@Getter @Setter
@ApiModel(description = "DTO to update or create a Wagon instance.")
public class WagonPostDto {
	@ApiModelProperty(notes = "Identifies uniquely the freight wagon by its number. Identification code of a freight wagon based on the TSI OPE and CEN Recommendations and CIS wagons coded according to OSJD-UIC leaflet 402, which allows the conversion from 8 digits to 12 digits and viceversa.")
	private String numberFreight;
    @ApiModelProperty(notes = "wagonType is a valid URL to a resource.")
    private String wagonTypeUrl;
}
