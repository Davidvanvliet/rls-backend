package nl.rls.composer.rest.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class TractionInTrainDto extends RollingStockDto {
    private boolean driverIndication;
    private TractionDto traction;
}
