package nl.rls.composer.rest.dto;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@NoArgsConstructor
@Getter
@Setter
@ApiModel(description = "Identification code of a freight wagon based on the TSI OPE and CEN Recommendations and CIS wagons coded according to OSJD-UIC leaflet 402, which allows the conversion from 8 digits to 12 digits and viceversa.")
public class WagonDto extends RepresentationModel {
    private String numberFreight;
    private String name;
    private String code;
    private int lengthOverBuffers;
    private int wagonNumberOfAxles;
    private int wagonWeightEmpty;
    private int handBrakeBrakedWeight;
}
