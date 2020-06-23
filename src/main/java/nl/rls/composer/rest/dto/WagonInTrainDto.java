package nl.rls.composer.rest.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Getter
@Setter
public class WagonInTrainDto extends RollingStockDto {
    private String brakeType;
    @Min(0)
    @Max(999999)
    private int totalLoadWeight;
    private WagonDto wagon;
    private List<DangerGoodsInWagonDto> dangerGoods = new ArrayList<>();

}
