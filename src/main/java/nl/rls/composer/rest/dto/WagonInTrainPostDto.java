package nl.rls.composer.rest.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement
@NoArgsConstructor
@Getter
@Setter
public class WagonInTrainPostDto extends RollingStockPostDto {
    private String brakeType;
    @Min(0)
    @Max(999999)
    private int totalLoadWeight;
    private String breakTypeUrl;
    private List<DangerGoodsInWagonPostDto> dangerGoodsInWagonPostDtos;
}
