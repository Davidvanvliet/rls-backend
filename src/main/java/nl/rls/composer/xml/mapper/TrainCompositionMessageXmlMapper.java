package nl.rls.composer.xml.mapper;

import org.dozer.DozerBeanMapper;
import org.dozer.loader.api.BeanMappingBuilder;
import nl.rls.composer.domain.LocationSubsidiaryIdentification;
import info.taf_jsg.schemes.TrainCompositionMessage;

public class TrainCompositionMessageXmlMapper {
	public static TrainCompositionMessage map(nl.rls.composer.domain.TrainCompositionMessage tcm) {
		DozerBeanMapper mapper = new DozerBeanMapper();
		BeanMappingBuilder mappingBuilder = new BeanMappingBuilder() {
			protected void configure() {
				mapping(nl.rls.composer.domain.message.MessageHeader.class, info.taf_jsg.schemes.MessageHeader.class)
						.fields("sender.code", "sender").fields("recipient.code", "recipient");
				mapping(nl.rls.composer.domain.CompositIdentifierOperationalType.class,
						info.taf_jsg.schemes.TransportOperationalIdentifiers.class).fields("company.code", "company");
				mapping(nl.rls.composer.domain.Responsibility.class,
						info.taf_jsg.schemes.ResponsibilityActualSection.class)
								.fields("responsibleRU.code", "responsibleRU")
								.fields("responsibleIM.code", "responsibleIM");
				mapping(nl.rls.composer.domain.Responsibility.class,
						info.taf_jsg.schemes.ResponsibilityNextSection.class)
								.fields("responsibleRU.code", "responsibleRU")
								.fields("responsibleIM.code", "responsibleIM");
				mapping(nl.rls.composer.domain.TrainCompositionMessage.class,
						info.taf_jsg.schemes.TrainCompositionMessage.class)
								.fields("transfereeIM.code", "transfereeIM")
								.fields("trainCompositionJourneySection",
										field("trainCompositionJourneySection").accessible(true))
								.fields("compositIdentifierOperationalType",
										field("transportOperationalIdentifiers").accessible(true))
								.fields("messageStatus", "messageStatus")
								.fields("operationalTrainNumberIdentifier.operationalTrainNumber",
										"operationalTrainNumber");
				mapping(LocationSubsidiaryIdentification.class,
						info.taf_jsg.schemes.LocationSubsidiaryIdentification.class)
								.fields("locationSubsidiaryCode", "locationSubsidiaryCode.value")
								.fields("allocationCompany.code", "allocationCompany");
				mapping(nl.rls.composer.domain.OperationalTrainNumberIdentifier.class,
						info.taf_jsg.schemes.OperationalTrainNumberIdentifier.class).fields("operationalTrainNumber",
								"operationalTrainNumber");
				mapping(nl.rls.composer.domain.LocationIdent.class, info.taf_jsg.schemes.LocationIdent.class)
						.fields("countryIso", "countryCodeISO");
				// mapping(nl.rls.composer.domain.Traction.class,
				// info.taf_jsg.schemes.TrainCompositionJourneySection.LocoIdent.class)
				// .fields("tractionType.code", "tractionType")
				// .fields("tractionMode.code", "tractionMode");
				mapping(nl.rls.composer.domain.TrainCompositionJourneySection.class,
						info.taf_jsg.schemes.TrainCompositionJourneySection.class)
								.fields("activities", field("trainRunningData.activities").accessible(true))
								.fields("tractions", field("locoIdent").accessible(true))
								.fields("wagons", field("wagonData").accessible(true));
				mapping(nl.rls.composer.domain.code.TrainActivityType.class, info.taf_jsg.schemes.TrainRunningData.Activities.class)
						.fields("code", "trainActivityType");
				mapping(nl.rls.composer.domain.WagonInTrain.class, info.taf_jsg.schemes.WagonData.class)
						.fields("wagonTrainPosition", "wagonTrainPosition")
						.fields("wagon.wagonNumberFreight.wagonNumberFreight", "wagonNumberFreight")
						.fields("wagon.wagonNumberFreight.wagonTechData", "wagonTechData")
						.fields("wagon", "wagonOperationalData");
				mapping(nl.rls.composer.domain.TractionInTrain.class,
						info.taf_jsg.schemes.TrainCompositionJourneySection.LocoIdent.class)
								.fields("driverIndication", "driverIndication")
								.fields("tractionPositionInTrain", "tractionPositionInTrain")
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
