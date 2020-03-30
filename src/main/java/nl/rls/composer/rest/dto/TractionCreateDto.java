package nl.rls.composer.rest.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@NoArgsConstructor
@Getter
@Setter
public class TractionCreateDto {
	@Size(min = 2, max = 2)
    private String tractionType;
    @Min(5)
    @Max(12)
    private String locoTypeNumber;
    @Min(4)
    @Max(12)
    private String locoNumber;
    private String typeName;
    @Min(1)
    @Max(999999)
    private int lengthOverBuffers;
    private int numberOfAxles;
    private int weight;
    @Min(1)
    @Max(99999)
    private int brakeWeight;

}