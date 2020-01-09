package nl.rls.composer.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import nl.rls.composer.domain.TractionInTrain;
import nl.rls.composer.domain.TrainCompositionJourneySection;
import nl.rls.composer.domain.WagonInTrain;
import nl.rls.composer.domain.code.TrainActivityType;
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
	
	public TrainCompositionJourneySection copySection(TrainCompositionJourneySection section) {
		TrainCompositionJourneySection newSection = new TrainCompositionJourneySection();
		newSection.setTrain(section.getTrain());
		newSection.setJourneySectionDestination(section.getJourneySectionDestination());
		newSection.setJourneySectionOrigin(section.getJourneySectionOrigin());
		newSection.setLivestockOrPeopleIndicator(section.getLivestockOrPeopleIndicator());
		newSection.setOwnerId(section.getOwnerId());
		newSection.setResponsibilityActualSection(section.getResponsibilityActualSection());
		newSection.setResponsibilityNextSection(section.getResponsibilityNextSection());
		newSection.setTrainMaxSpeed(section.getTrainMaxSpeed());
		newSection.setTrainType(section.getTrainType());
		for (TrainActivityType activity : section.getActivities()) {
			newSection.addActivity(activity);
		}
		for (TractionInTrain tractionInTrain : section.getTractions()) {
			TractionInTrain newTractionInTrain = new TractionInTrain();
			newTractionInTrain.setDriverIndication(tractionInTrain.getDriverIndication());
			newTractionInTrain.setPosition(tractionInTrain.getPosition());
			newTractionInTrain.setTraction(tractionInTrain.getTraction());
			newTractionInTrain.setTrainCompositionJourneySection(tractionInTrain.getTrainCompositionJourneySection());
			newSection.addTraction(newTractionInTrain);
		}
		for (WagonInTrain wagonInTrain : section.getWagons()) {
			WagonInTrain newWagonInTrain = new WagonInTrain();
			newWagonInTrain.setPosition(wagonInTrain.getPosition());
			newWagonInTrain.setTrainCompositionJourneySection(wagonInTrain.getTrainCompositionJourneySection());
			newWagonInTrain.setWagonLoad(wagonInTrain.getWagonLoad());
			newSection.addWagon(newWagonInTrain);
		}
		return newSection;
	}

}
