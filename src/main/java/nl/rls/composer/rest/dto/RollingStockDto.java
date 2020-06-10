package nl.rls.composer.rest.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import nl.rls.composer.rest.dto.hateoas.IdentifiableRepresentationModel;

@ToString
@NoArgsConstructor
@Getter
@Setter
public class RollingStockDto extends IdentifiableRepresentationModel<RollingStockDto> {
    private int position;

    private String stockType;

    private Long stockIdentifier;

    private boolean isGaugedExceptional;

    private boolean containsDangerousGoods;

    private int totalWeight;

    private int loadWeight;

    private int length;

    private int maxAxleWeight;

    private int maxSpeed;
}
