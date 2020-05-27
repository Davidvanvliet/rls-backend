package nl.rls.composer.rest.dto.mapper;

import nl.rls.composer.domain.RollingStock;
import nl.rls.composer.domain.TractionInTrain;
import nl.rls.composer.domain.WagonInTrain;
import nl.rls.composer.domain.code.StockType;
import nl.rls.composer.rest.dto.RollingStockDto;
import nl.rls.composer.rest.dto.TractionInTrainDto;
import nl.rls.composer.rest.dto.WagonInTrainDto;

public class RollingStockDtoMapper {
    public static RollingStockDto map(RollingStock rollingStock) {
        switch (StockType.valueOf(rollingStock.getStockType().toUpperCase())) {
            case TRACTION:
                return TractionInTrainDtoMapper.map((TractionInTrain) rollingStock);
            case WAGON:
                return WagonInTrainDtoMapper.map((WagonInTrain) rollingStock);
            default:
                throw new RuntimeException("Invalid rolling stock type");
        }
    }

    public static RollingStock map(RollingStockDto rollingStockDTO) {
        switch (StockType.valueOf(rollingStockDTO.getStockType().toUpperCase())) {
            case TRACTION:
                return TractionInTrainDtoMapper.map((TractionInTrainDto) rollingStockDTO);
            case WAGON:
                return WagonInTrainDtoMapper.map((WagonInTrainDto) rollingStockDTO);
            default:
                throw new RuntimeException("Invalid rolling stock type");
        }

    }
}
