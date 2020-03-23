package nl.rls.composer.rest.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.hateoas.RepresentationModel;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.xml.bind.annotation.XmlRootElement;

@ToString
@XmlRootElement
@NoArgsConstructor
@Getter
@Setter
public class WagonTypeDto extends RepresentationModel {
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
