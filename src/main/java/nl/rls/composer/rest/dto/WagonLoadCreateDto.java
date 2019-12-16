package nl.rls.composer.rest.dto;

import javax.xml.bind.annotation.XmlRootElement;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@XmlRootElement
@NoArgsConstructor
@Getter @Setter
public class WagonLoadCreateDto {
	private String wagon;
	private String brakeType;
	private int totalLoadWeight;
	private int brakeWeight;
	private int wagonMaxSpeed;
}