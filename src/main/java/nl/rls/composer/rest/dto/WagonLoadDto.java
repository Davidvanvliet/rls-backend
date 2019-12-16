package nl.rls.composer.rest.dto;

import javax.xml.bind.annotation.XmlRootElement;

import org.springframework.hateoas.ResourceSupport;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@XmlRootElement
@NoArgsConstructor
@Getter @Setter
public class WagonLoadDto extends ResourceSupport {
	private WagonDto wagon;
	private String brakeType;
	private int totalLoadWeight;
	private int brakeWeight;
	private int wagonMaxSpeed;

}
