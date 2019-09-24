package nl.rls.composer.rest.dto;

import javax.xml.bind.annotation.XmlRootElement;

import org.springframework.hateoas.ResourceSupport;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@ToString
@XmlRootElement
@NoArgsConstructor
@Getter
@Setter
public class WagonTechDataDto extends ResourceSupport {
	private String name;
	private String code;
	private int lengthOverBuffers;
	private int wagonNumberOfAxles;
	private int wagonWeightEmpty;
	private int handBrakeBrakedWeight;
}