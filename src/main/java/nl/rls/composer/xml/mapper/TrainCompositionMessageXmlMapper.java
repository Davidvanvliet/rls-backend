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
					.fields("compositIdentifierOperationalType",
										field("transportOperationalIdentifiers").accessible(true))
					.fields("messageStatus", "messageStatus")
					.fields("sender.code", "messageHeader.sender.value")
					.fields("recipient.code", "messageHeader.recipient.value")
					.fields("senderReference", "messageHeader.senderReference")
					.fields("messageIdentifier", "messageHeader.messageReference.messageIdentifier")
					.fields("messageType", "messageHeader.messageReference.messageType")
					.fields("messageTypeVersion", "messageHeader.messageReference.messageTypeVersion")
					.fields("messageDateTime", "messageHeader.messageReference.messageDateTime")
					.fields("train.transfereeIM.code", "transfereeIM")
					.fields("train.journeySections", field("trainCompositionJourneySection").accessible(true))
					.fields("train.operationalTrainNumber", "operationalTrainNumberIdentifier.operationalTrainNumber")
					.fields("train.scheduledTimeAtHandover", "operationalTrainNumberIdentifier.scheduledTimeAtHandover")
					.fields("train.operationalTrainNumber", "operationalTrainNumber");
				
				mapping(nl.rls.composer.domain.CompositIdentifierOperationalType.class,
						info.taf_jsg.schemes.TransportOperationalIdentifiers.class).fields("company.code", "company");

				mapping(nl.rls.composer.domain.Train.class, info.taf_jsg.schemes.OperationalTrainNumberIdentifier.class)
					.fields("operationalTrainNumber", "operationalTrainNumber");

				mapping(nl.rls.composer.domain.TrainCompositionJourneySection.class,
				info.taf_jsg.schemes.TrainCompositionJourneySection.class)
					.fields("exceptionalGaugingInd", field("trainRunningData.exceptionalGaugingInd").accessible(true))
					.fields("dangerousGoodsIndicator", field("trainRunningData.dangerousGoodsIndicator").accessible(true))
					.fields("journeySectionOrigin", field("journeySection.journeySectionOrigin").accessible(true))
					.fields("journeySectionDestination", field("journeySection.journeySectionDestination").accessible(true))
					.fields("responsibilityActualSection", field("journeySection.responsibilityActualSection").accessible(true))
					.fields("responsibilityNextSection", field("journeySection.responsibilityNextSection").accessible(true))
					.fields("activities", field("trainRunningData.activities").accessible(true))
					.fields("tractions", field("locoIdent").accessible(true))
					.fields("wagons", field("wagonData").accessible(true));
				
				mapping(nl.rls.composer.domain.Location.class, info.taf_jsg.schemes.LocationIdent.class)
					.fields("countryCodeIso", "countryCodeISO");

				mapping(nl.rls.composer.domain.WagonInTrain.class, info.taf_jsg.schemes.WagonData.class)
					.fields("position", "wagonTrainPosition")
					.fields("wagonLoad.wagon.numberFreight", "wagonNumberFreight")
					.fields("wagonLoad.wagon.wagonTechData", "wagonTechData")
					.fields("wagonLoad", "wagonOperationalData");

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
//				mapping(nl.rls.composer.domain.code.TrainActivityType.class, info.taf_jsg.schemes.TrainRunningData.Activities.class)
//						.fields("code", "trainActivityType");
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
		return xmlTcm;
	}
}
