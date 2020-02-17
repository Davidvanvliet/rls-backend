package nl.rls.composer.rest.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@NoArgsConstructor
@Getter
@Setter
public class TractionCreateDto {
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