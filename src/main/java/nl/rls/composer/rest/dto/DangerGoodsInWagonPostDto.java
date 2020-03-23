package nl.rls.composer.rest.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;

import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;

@NoArgsConstructor
@Getter
@Setter
public class DangerGoodsInWagonPostDto extends RepresentationModel {
    /**
     * The weight of the dangerous goods in Kilograms
     */
    @DecimalMin("0.0")
    @DecimalMax("999999.0")
    private int dangerousGoodsWeight;
    /**
     * The volume of the dangerous goods in cubic meters
     */
    private Float dangerousGoodsVolume;
    private String dangerGoodsTypeUrl;
}
