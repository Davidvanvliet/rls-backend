package nl.rls.composer.service;

import nl.rls.ci.aa.security.SecurityContext;
import nl.rls.composer.domain.RollingStock;
import nl.rls.composer.domain.TrainComposition;
import nl.rls.composer.repository.RollingStockRepository;
import nl.rls.composer.repository.TractionInTrainRepository;
import nl.rls.composer.repository.TrainCompositionRepository;
import nl.rls.composer.repository.WagonInTrainRepository;
import nl.rls.composer.rest.dto.RollingStockDTO;
import nl.rls.composer.rest.dto.mapper.RollingStockDtoMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TrainCompositionService {
    private final WagonInTrainRepository wagonInTrainRepository;
    private final TractionInTrainRepository tractionInTrainRepository;
    private final TrainCompositionRepository trainCompositionRepository;

    private final RollingStockRepository rollingStockRepository;

    private final SecurityContext securityContext;

    public TrainCompositionService(WagonInTrainRepository wagonInTrainRepository, TractionInTrainRepository tractionInTrainRepository, TrainCompositionRepository trainCompositionRepository, RollingStockRepository rollingStockRepository, SecurityContext securityContext) {
        this.wagonInTrainRepository = wagonInTrainRepository;
        this.tractionInTrainRepository = tractionInTrainRepository;
        this.trainCompositionRepository = trainCompositionRepository;
        this.rollingStockRepository = rollingStockRepository;
        this.securityContext = securityContext;
    }

    public List<RollingStockDTO> getRollingStockByTrainCompositionId(Integer trainCompositionId) {
        Integer ownerId = securityContext.getOwnerId();
        List<RollingStock> rollingStock = rollingStockRepository.findAllByTrainCompositionIdAndTrainCompositionOwnerId(trainCompositionId, ownerId);
        return rollingStock.stream()
                .map(RollingStockDtoMapper::map)
                .collect(Collectors.toList());
    }

//    public void addWagonToTrain(TrainComposition trainComposition, WagonInTrain wit) {
//        wagonInTrainRepository.save(wit);
//        wit.setTrainComposition(trainComposition);
//        trainComposition.addWagon(wit);
//        wagonInTrainRepository.saveAll(trainComposition.getWagons());
//        trainCompositionRepository.save(trainComposition);
//    }
//
//    public void moveWagonById(TrainComposition trainComposition, int wagonInTrainId, int position) {
//        trainComposition.moveWagonById(wagonInTrainId, position);
//        wagonInTrainRepository.saveAll(trainComposition.getWagons());
//        trainCompositionRepository.save(trainComposition);
//    }
//
//    public void addTractionToTrain(TrainComposition trainComposition, TractionInTrain tit) {
//        tractionInTrainRepository.save(tit);
//        tit.setTrainComposition(trainComposition);
//        trainComposition.addTraction(tit);
//        tractionInTrainRepository.saveAll(trainComposition.getTractions());
//        trainCompositionRepository.save(trainComposition);
//    }
//
//    public void moveTractionById(TrainComposition trainComposition, int tractionInTrainId, int position) {
//        trainComposition.moveWagonById(tractionInTrainId, position);
//        tractionInTrainRepository.saveAll(trainComposition.getTractions());
//        trainCompositionRepository.save(trainComposition);
//    }

    public TrainComposition copyTrainComposition(TrainComposition trainComposition) {
        TrainComposition newTrainComposition = new TrainComposition();
        newTrainComposition.setLivestockOrPeopleIndicator(trainComposition.getLivestockOrPeopleIndicator());
        newTrainComposition.setBrakeType(trainComposition.getBrakeType());
        newTrainComposition.setOwnerId(trainComposition.getOwnerId());
        newTrainComposition.setJourneySection(trainComposition.getJourneySection());
//        for (TractionInTrain tractionInTrain : trainComposition.getTractions()) {
//            TractionInTrain newTractionInTrain = new TractionInTrain();
//            newTractionInTrain.setDriverIndication(tractionInTrain.getDriverIndication());
//            newTractionInTrain.setPosition(tractionInTrain.getPosition());
//            newTractionInTrain.setTraction(tractionInTrain.getTraction());
//            newTractionInTrain.setTractionMode(tractionInTrain.getTractionMode());
//            newTractionInTrain.setTrainComposition(tractionInTrain.getTrainComposition());
//            newTrainComposition.addTraction(newTractionInTrain);
//        }
//        for (WagonInTrain wagonInTrain : trainComposition.getWagons()) {
//            WagonInTrain newWagonInTrain = new WagonInTrain();
//            newWagonInTrain.setOwnerId(wagonInTrain.getOwnerId());
//            newWagonInTrain.setPosition(wagonInTrain.getPosition());
//            newWagonInTrain.setBrakeType(wagonInTrain.getBrakeType());
//            newWagonInTrain.setTotalLoadWeight(wagonInTrain.getTotalLoadWeight());
//            newWagonInTrain.setTrainComposition(wagonInTrain.getTrainComposition());
//            newWagonInTrain.setWagon(wagonInTrain.getWagon());
//            newTrainComposition.addWagon(newWagonInTrain);
//        }
        return newTrainComposition;
    }
}