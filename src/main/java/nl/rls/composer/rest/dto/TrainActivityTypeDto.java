package nl.rls.composer.rest.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.hateoas.RepresentationModel;

import javax.xml.bind.annotation.XmlRootElement;

@ToString
@NoArgsConstructor
@Getter
@Setter
@XmlRootElement(name = "trainActivityType")
public class TrainActivityTypeDto extends RepresentationModel {
    private String code;
    private String description;
}
