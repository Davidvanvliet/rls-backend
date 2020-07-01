package nl.rls.composer.service;

import nl.rls.auth.config.SecurityContext;
import nl.rls.ci.url.DecodePath;
import nl.rls.composer.domain.*;
import nl.rls.composer.domain.code.BrakeType;
import nl.rls.composer.domain.code.StockType;
import nl.rls.composer.repository.DangerGoodsTypeRepository;
import nl.rls.composer.repository.TractionRepository;
import nl.rls.composer.repository.WagonRepository;
import nl.rls.composer.rest.dto.DangerGoodsInWagonPostDto;
import nl.rls.composer.rest.dto.RollingStockPostDto;
import nl.rls.composer.rest.dto.TractionInTrainPostDto;
import nl.rls.composer.rest.dto.WagonInTrainPostDto;
import org.springframework.stereotype.Component;

import javax.persistence.EntityNotFoundException;

@Component
public class RollingStockFactory {
    private final TractionRepository tractionRepository;

    private final WagonRepository wagonRepository;

    private final SecurityContext securityContext;

    private final DangerGoodsTypeRepository dangerGoodsTypeRepository;

    public RollingStockFactory(TractionRepository tractionRepository, WagonRepository wagonRepository, SecurityContext securityContext, DangerGoodsTypeRepository dangerGoodsTypeRepository) {
        this.tractionRepository = tractionRepository;
        this.wagonRepository = wagonRepository;
        this.securityContext = securityContext;
        this.dangerGoodsTypeRepository = dangerGoodsTypeRepository;
    }

    public RollingStock createRollingStock(RollingStockPostDto rollingStockPostDto) {
        switch (StockType.valueOf(rollingStockPostDto.getStockType().toUpperCase())) {
            case TRACTION:
                return createTraction((TractionInTrainPostDto) rollingStockPostDto);
            case WAGON:
                return createWagon((WagonInTrainPostDto) rollingStockPostDto);
            default:
                throw new RuntimeException("Invalid rolling stock type");
        }
    }

    private TractionInTrain createTraction(TractionInTrainPostDto tractionInTrainPostDto) {
        int ownerId = securityContext.getOwnerId();
        Traction traction = tractionRepository.findByLocoTypeNumberAndOwnerId(tractionInTrainPostDto.getStockIdentifier(), ownerId)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Could not find traction %d", tractionInTrainPostDto.getStockIdentifier())));
        TractionInTrain tractionInTrain = new TractionInTrain();
        tractionInTrain.setDriverIndication(tractionInTrainPostDto.isDriverIndication() ? 1 : 0);
        tractionInTrain.setTraction(traction);
        tractionInTrain.setStockType("traction");
        return tractionInTrain;
    }

    private WagonInTrain createWagon(WagonInTrainPostDto wagonInTrainPostDto) {
        int ownerId = securityContext.getOwnerId();
        Wagon wagon = wagonRepository.findByNumberFreightAndOwnerId(String.valueOf(wagonInTrainPostDto.getStockIdentifier()), ownerId)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Could not find wagon %d", wagonInTrainPostDto.getStockIdentifier())));
        WagonInTrain wagonInTrain = new WagonInTrain();
        wagonInTrain.setBrakeType(BrakeType.valueOf(wagonInTrainPostDto.getBrakeType()));
        wagonInTrain.setTotalLoadWeight(wagonInTrainPostDto.getTotalLoadWeight());
        wagonInTrain.setWagon(wagon);
        wagonInTrain.setStockType("wagon");
        if (wagonInTrainPostDto.getDangerGoodsInWagonPostDtos() != null) {
            for (DangerGoodsInWagonPostDto dangerGoodsInWagonPostDto : wagonInTrainPostDto.getDangerGoodsInWagonPostDtos()) {
                int dangerGoodsTypeId = DecodePath.decodeInteger(dangerGoodsInWagonPostDto.getDangerGoodsTypeUrl(), "dangergoodstypes");
                DangerGoodsType dangerGoodsType = dangerGoodsTypeRepository.findById(dangerGoodsTypeId).orElseThrow(() -> new EntityNotFoundException(String.format("Could not find dangerous goods type with id %d", dangerGoodsTypeId)));
                wagonInTrain.addDangerGoodsInWagon(new DangerGoodsInWagon(
                        dangerGoodsType,
                        wagonInTrain,
                        dangerGoodsInWagonPostDto.getDangerousGoodsWeight(),
                        dangerGoodsInWagonPostDto.getDangerousGoodsVolume())
                );
            }
        }
        return wagonInTrain;
    }
}
