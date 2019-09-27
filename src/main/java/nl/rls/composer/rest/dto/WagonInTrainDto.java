package nl.rls.composer.rest.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class WagonInTrainDto {
    private int wagonTrainPosition;
    private WagonDto wagon;
}
