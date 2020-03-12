package nl.rls.composer.rest.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.hateoas.RepresentationModel;

import javax.xml.bind.annotation.XmlRootElement;

@ToString
@XmlRootElement
@NoArgsConstructor
@Getter
@Setter
public class WagonTypeDto extends RepresentationModel {
    private String name;
    private String code;
    private int lengthOverBuffers;
    private int wagonNumberOfAxles;
    private int wagonWeightEmpty;
    private int handBrakeBrakedWeight;
}
