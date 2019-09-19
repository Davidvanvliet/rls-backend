package nl.rls.composer.rest.dto;
import javax.xml.bind.annotation.XmlRootElement;

import org.springframework.hateoas.ResourceSupport;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@ToString
@NoArgsConstructor
@Getter @Setter
@XmlRootElement(name = "trainActivityType")
public class TrainActivityTypeDto extends ResourceSupport {
    private String code;
    private String description;
}
