package nl.rls.composer.rest.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import nl.rls.composer.rest.dto.hateoas.ResourceSupport;

import javax.xml.bind.annotation.XmlRootElement;

@ToString
@NoArgsConstructor
@Getter
@Setter
@XmlRootElement(name = "trainActivityType")
public class TrainActivityTypeDto extends ResourceSupport {
    private String code;
    private String description;
}
