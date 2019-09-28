package nl.rls.composer.rest.dto;

import javax.xml.bind.annotation.XmlRootElement;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@XmlRootElement
@NoArgsConstructor
@Getter @Setter
public class LocomotiveCreateDto {
	private String tractionType;
	private String tractionMode;
	private String locoTypeNumber;
	private String locoNumber;
	private String type;
	private int lengthOverBuffers;
	private int numberOfAxles;
	private int weight;
	private int brakeWeight;

}