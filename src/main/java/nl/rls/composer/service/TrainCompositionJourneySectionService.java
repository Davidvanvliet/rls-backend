package nl.rls.composer.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import nl.rls.composer.domain.TractionInTrain;
import nl.rls.composer.domain.TrainCompositionJourneySection;
import nl.rls.composer.domain.WagonInTrain;
import nl.rls.composer.repository.TractionInTrainRepository;
import nl.rls.composer.repository.TrainCompositionJourneySectionRepository;
import nl.rls.composer.repository.WagonInTrainRepository;
@Service
public class TrainCompositionJourneySectionService {
	@Autowired
	private WagonInTrainRepository wagonInTrainRepository;
	@Autowired
	private TractionInTrainRepository tractionInTrainRepository;
	@Autowired
	private TrainCompositionJourneySectionRepository trainCompositionJourneySectionRepository;

	public void addWagonToTrain(TrainCompositionJourneySection tcjs, WagonInTrain wit) {
		wagonInTrainRepository.save(wit);
		wit.setTrainCompositionJourneySection(tcjs);
		tcjs.addWagon(wit);
		wagonInTrainRepository.saveAll(tcjs.getWagons());
		trainCompositionJourneySectionRepository.save(tcjs);
	}

	public void moveWagonById(TrainCompositionJourneySection tcjs, int wagonInTrainId, int position) {
		tcjs.moveWagonById(wagonInTrainId, position);
		wagonInTrainRepository.saveAll(tcjs.getWagons());
		trainCompositionJourneySectionRepository.save(tcjs);
	}

	public void addTractionToTrain(TrainCompositionJourneySection tcjs, TractionInTrain tit) {
		tractionInTrainRepository.save(tit);
		tit.setTrainCompositionJourneySection(tcjs);
		tcjs.addTraction(tit);
		tractionInTrainRepository.saveAll(tcjs.getTractions());
		trainCompositionJourneySectionRepository.save(tcjs);
	}

	public void moveTractionById(TrainCompositionJourneySection tcjs, int tractionInTrainId, int position) {
		tcjs.moveWagonById(tractionInTrainId, position);
		tractionInTrainRepository.saveAll(tcjs.getTractions());
		trainCompositionJourneySectionRepository.save(tcjs);
	}

}
