package nl.rls.composer.rest.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Getter
@Setter
public class RollingStockMoveDto {
    @NotNull
    @Positive
    private int position;
}
