package nl.rls.composer.service;

import org.springframework.beans.factory.annotation.Autowired;

import nl.rls.composer.domain.TrainCompositionJourneySection;
import nl.rls.composer.domain.WagonInTrain;
import nl.rls.composer.repository.TrainCompositionJourneySectionRepository;
import nl.rls.composer.repository.WagonInTrainRepository;

public class TrainCompositionJourneySectionService {
	@Autowired
	private WagonInTrainRepository wagonInTrainRepository;
	@Autowired
	private TrainCompositionJourneySectionRepository trainCompositionJourneySectionRepository;

	public void addWagonToTrain(TrainCompositionJourneySection tcjs, WagonInTrain wit) {
		wagonInTrainRepository.save(wit);
		wit.setTrainCompositionJourneySection(tcjs);
		tcjs.addWagon(wit);
		wagonInTrainRepository.saveAll(tcjs.getWagons());
		trainCompositionJourneySectionRepository.save(tcjs);

	}
}
