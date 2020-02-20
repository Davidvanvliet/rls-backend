package nl.rls.composer.service;

import nl.rls.composer.domain.TractionInTrain;
import nl.rls.composer.domain.TrainComposition;
import nl.rls.composer.domain.WagonInTrain;
import nl.rls.composer.repository.TractionInTrainRepository;
import nl.rls.composer.repository.TrainCompositionRepository;
import nl.rls.composer.repository.WagonInTrainRepository;
import org.springframework.stereotype.Service;

@Service
public class TrainCompositionService {
    private final WagonInTrainRepository wagonInTrainRepository;
    private final TractionInTrainRepository tractionInTrainRepository;
    private final TrainCompositionRepository trainCompositionRepository;

    public TrainCompositionService(WagonInTrainRepository wagonInTrainRepository, TractionInTrainRepository tractionInTrainRepository, TrainCompositionRepository trainCompositionRepository) {
        this.wagonInTrainRepository = wagonInTrainRepository;
        this.tractionInTrainRepository = tractionInTrainRepository;
        this.trainCompositionRepository = trainCompositionRepository;
    }

    public void addWagonToTrain(TrainComposition trainComposition, WagonInTrain wit) {
        wagonInTrainRepository.save(wit);
        wit.setTrainComposition(trainComposition);
        trainComposition.addWagon(wit);
        wagonInTrainRepository.saveAll(trainComposition.getWagons());
        trainCompositionRepository.save(trainComposition);
    }

    public void moveWagonById(TrainComposition trainComposition, int wagonInTrainId, int position) {
        trainComposition.moveWagonById(wagonInTrainId, position);
        wagonInTrainRepository.saveAll(trainComposition.getWagons());
        trainCompositionRepository.save(trainComposition);
    }

    public void addTractionToTrain(TrainComposition trainComposition, TractionInTrain tit) {
        tractionInTrainRepository.save(tit);
        tit.setTrainComposition(trainComposition);
        trainComposition.addTraction(tit);
        tractionInTrainRepository.saveAll(trainComposition.getTractions());
        trainCompositionRepository.save(trainComposition);
    }

    public void moveTractionById(TrainComposition trainComposition, int tractionInTrainId, int position) {
        trainComposition.moveWagonById(tractionInTrainId, position);
        tractionInTrainRepository.saveAll(trainComposition.getTractions());
        trainCompositionRepository.save(trainComposition);
    }

    public TrainComposition copyTrainComposition(TrainComposition trainComposition) {
        TrainComposition newTrainComposition = new TrainComposition();
        newTrainComposition.setLivestockOrPeopleIndicator(trainComposition.getLivestockOrPeopleIndicator());
        newTrainComposition.setOwnerId(trainComposition.getOwnerId());
        newTrainComposition.setTrainMaxSpeed(trainComposition.getTrainMaxSpeed());
        newTrainComposition.setJourneySection(trainComposition.getJourneySection());
        for (TractionInTrain tractionInTrain : trainComposition.getTractions()) {
            TractionInTrain newTractionInTrain = new TractionInTrain();
            newTractionInTrain.setDriverIndication(tractionInTrain.getDriverIndication());
            newTractionInTrain.setPosition(tractionInTrain.getPosition());
            newTractionInTrain.setTraction(tractionInTrain.getTraction());
            newTractionInTrain.setTrainComposition(tractionInTrain.getTrainComposition());
            newTrainComposition.addTraction(newTractionInTrain);
        }
        for (WagonInTrain wagonInTrain : trainComposition.getWagons()) {
            WagonInTrain newWagonInTrain = new WagonInTrain();
            newWagonInTrain.setPosition(wagonInTrain.getPosition());
            newWagonInTrain.setBrakeType(wagonInTrain.getBrakeType());
            newWagonInTrain.setBrakeWeight(wagonInTrain.getBrakeWeight());
            newWagonInTrain.setTotalLoadWeight(wagonInTrain.getTotalLoadWeight());
            newWagonInTrain.setWagonMaxSpeed(wagonInTrain.getWagonMaxSpeed());
            newWagonInTrain.setTrainComposition(wagonInTrain.getTrainComposition());
            newWagonInTrain.setWagon(wagonInTrain.getWagon());
            newTrainComposition.addWagon(newWagonInTrain);
        }
        return newTrainComposition;
    }
}