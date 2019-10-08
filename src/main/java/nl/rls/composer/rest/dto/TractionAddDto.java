package nl.rls.composer.rest.dto;

import javax.xml.bind.annotation.XmlRootElement;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@XmlRootElement
@NoArgsConstructor
@Getter @Setter
public class TractionAddDto {
	private String traction;
	private int tractionPositionInTrain;
	private int driverIndication;

}