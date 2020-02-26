package nl.rls.composer.xml.mapper;

import org.dozer.DozerBeanMapper;
import org.dozer.loader.api.BeanMappingBuilder;

import info.taf_jsg.schemes.TrainCompositionMessage;

public class TrainCompositionMessageXmlMapper {
	public static TrainCompositionMessage map(nl.rls.composer.domain.message.TrainCompositionMessage tcm) {
		DozerBeanMapper mapper = new DozerBeanMapper();
		BeanMappingBuilder mappingBuilder = new BeanMappingBuilder() {
			protected void configure() {
				mapping(nl.rls.composer.domain.message.TrainCompositionMessage.class,
						info.taf_jsg.schemes.TrainCompositionMessage.class)
								.fields("messageStatus", "messageStatus")
								.fields("sender.code", "messageHeader.sender.value")
								.fields("recipient.code", "messageHeader.recipient.value")
								.fields("senderReference", "messageHeader.senderReference")
								.fields("messageIdentifier", "messageHeader.messageReference.messageIdentifier")
								.fields("messageType", "messageHeader.messageReference.messageType")
								.fields("messageTypeVersion", "messageHeader.messageReference.messageTypeVersion")
								.fields("messageDateTime", "messageHeader.messageReference.messageDateTime")
								.fields("train.transfereeIM.code", "transfereeIM")
								.fields("train.journeySections",
										field("trainCompositionJourneySection").accessible(true))
								.fields("train.operationalTrainNumber",
										"operationalTrainNumberIdentifier.operationalTrainNumber")
								.fields("train.scheduledTimeAtHandover",
										"operationalTrainNumberIdentifier.scheduledTimeAtHandover")
								.fields("train.operationalTrainNumber", "operationalTrainNumber");

				mapping(nl.rls.composer.domain.Train.class, info.taf_jsg.schemes.OperationalTrainNumberIdentifier.class)
						.fields("operationalTrainNumber", "operationalTrainNumber");

				mapping(nl.rls.composer.domain.JourneySection.class,
						info.taf_jsg.schemes.TrainCompositionJourneySection.class)
								.fields("trainComposition.livestockOrPeopleIndicator", "livestockOrPeopleIndicator")
								.fields("trainComposition.exceptionalGaugingIndicator",
										field("trainRunningData.exceptionalGaugingInd").accessible(true))
								.fields("trainComposition.dangerousGoodsIndicator",
										field("trainRunningData.dangerousGoodsIndicator").accessible(true))
								.fields("trainComposition.trainMaxSpeed",
										"trainRunningData.trainRunningTechData.trainMaxSpeed")
								.fields("trainComposition.numberOfAxles",
										"trainRunningData.trainRunningTechData.numberOfAxles")
								.fields("trainComposition.numberOfVehicles",
										"trainRunningData.trainRunningTechData.numberOfVehicles")
								.fields("trainComposition.trainLength",
										"trainRunningData.trainRunningTechData.trainLength")
								.fields("trainComposition.trainWeight",
										"trainRunningData.trainRunningTechData.trainWeight")
								.fields("trainComposition.trainWeight",
										"trainRunningData.trainRunningTechData.trainWeight")
								.fields("trainComposition.maxAxleWeight",
										"trainRunningData.trainRunningTechData.maxAxleWeight")
								.fields("trainComposition.brakeWeight",
										"trainRunningData.trainRunningTechData.brakeWeight")
								.fields("trainComposition.journeySection.train.trainType",
										"trainRunningData.trainRunningTechData.trainType")

								.fields("journeySectionOrigin",
										field("journeySection.journeySectionOrigin").accessible(true))
								.fields("journeySectionDestination",
										field("journeySection.journeySectionDestination").accessible(true))
								.fields("responsibilityActualSection",
										field("journeySection.responsibilityActualSection").accessible(true))
								.fields("responsibilityNextSection",
										field("journeySection.responsibilityNextSection").accessible(true))
//								.fields("activities", field("trainRunningData.activities").accessible(true))
								.fields("trainComposition.tractions", field("locoIdent").accessible(true))
								.fields("trainComposition.wagons", field("wagonData").accessible(true));

				mapping(nl.rls.composer.domain.Location.class, info.taf_jsg.schemes.LocationIdent.class)
						.fields("countryCodeIso", "countryCodeISO");

				mapping(nl.rls.composer.domain.WagonInTrain.class, info.taf_jsg.schemes.WagonData.class)
						.fields("position", "wagonTrainPosition").fields("wagon.numberFreight", "wagonNumberFreight")
						.fields("wagon", "wagonTechData")
						.fields("totalLoadWeight", "wagonOperationalData.totalLoadWeight.")
						.fields("brakeWeight", "wagonOperationalData.brakeWeight")
						.fields("brakeType", "wagonOperationalData.brakeType")
						.fields("wagonMaxSpeed", "wagonOperationalData.wagonMaxSpeed")
						.fields("dangerGoodsInWagons",field("wagonOperationalData.dangerousGoodsDetails").accessible(true))
						;

				mapping(nl.rls.composer.domain.DangerGoodsInWagon.class,
						info.taf_jsg.schemes.WagonOperationalData.DangerousGoodsDetails.class)
								.fields("dangerousGoodsWeight", "weightOfDangerousGoods")
								.fields("dangerGoodsType", "dangerousGoodsIndication")
								;
				mapping(nl.rls.composer.domain.DangerGoodsType.class,
						info.taf_jsg.schemes.DanGoodsType.class)
								.fields("unNumber", "unNumber")
								.fields("ridClass", "ridClass")
								;
				mapping(nl.rls.composer.domain.Responsibility.class,
						info.taf_jsg.schemes.ResponsibilityActualSection.class)
								.fields("responsibleRU.code", "responsibleRU")
								.fields("responsibleIM.code", "responsibleIM");
				mapping(nl.rls.composer.domain.Responsibility.class,
						info.taf_jsg.schemes.ResponsibilityNextSection.class)
								.fields("responsibleRU.code", "responsibleRU")
								.fields("responsibleIM.code", "responsibleIM");
				mapping(nl.rls.composer.domain.Traction.class,
						info.taf_jsg.schemes.TrainCompositionJourneySection.LocoIdent.class)
								.fields("tractionType.code", "tractionType")
								.fields("tractionMode.code", "tractionMode");
				// mapping(nl.rls.composer.domain.code.TrainActivityType.class,
				// info.taf_jsg.schemes.TrainRunningData.Activities.class)
				// .fields("code", "trainActivityType");
				mapping(nl.rls.composer.domain.TractionInTrain.class,
						info.taf_jsg.schemes.TrainCompositionJourneySection.LocoIdent.class)
								.fields("driverIndication", "driverIndication")
								.fields("position", "tractionPositionInTrain")
								.fields("traction.tractionType.code", "tractionType")
								.fields("traction.tractionMode.code", "tractionMode")
								.fields("traction.locoTypeNumber", "locoTypeNumber")
								.fields("traction.locoNumber", "locoNumber");
				

			}
		};
		mapper.addMapping(mappingBuilder);
		
		TrainCompositionMessage xmlTcm = mapper.map(tcm, TrainCompositionMessage.class);
		info.taf_jsg.schemes.TransportOperationalIdentifiers toi = TransportOperationalIdentifiersXmlMapper.map(tcm);
		xmlTcm.getTransportOperationalIdentifiers().add(toi);
		return xmlTcm;
	}
}
