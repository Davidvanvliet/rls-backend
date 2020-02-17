package nl.rls.composer.rest.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@NoArgsConstructor
@Getter
@Setter
public class WagonInTrainAddDto {
    private int position;
    private String brakeType;
    private int totalLoadWeight;
    private int brakeWeight;
    private int wagonMaxSpeed;
    private String wagonUrl;
    private String breakTypeUrl;
}