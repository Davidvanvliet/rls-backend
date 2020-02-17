package nl.rls.composer.rest.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nl.rls.composer.rest.dto.hateoas.ResourceSupport;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Getter
@Setter
public class WagonInTrainDto extends ResourceSupport {
    private int position;
    private String brakeType;
    private int totalLoadWeight;
    private int brakeWeight;
    private int wagonMaxSpeed;
    private WagonDto wagon;
    private List<DangerGoodsInWagonDto> dangerGoods = new ArrayList<DangerGoodsInWagonDto>();

}
