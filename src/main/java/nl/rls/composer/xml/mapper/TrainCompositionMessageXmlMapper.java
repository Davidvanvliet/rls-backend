package nl.rls.composer.xml.mapper;

import org.dozer.DozerBeanMapper;
import org.dozer.loader.api.BeanMappingBuilder;

import nl.rls.composer.domain.LocationSubsidiaryIdentification;
import nl.rls.xml.tcm.TrainCompositionMessage;

public class TrainCompositionMessageXmlMapper {

	public static TrainCompositionMessage map(nl.rls.composer.domain.TrainCompositionMessage tcm) {
		DozerBeanMapper mapper = new DozerBeanMapper();
		BeanMappingBuilder mappingBuilder = new BeanMappingBuilder() {
			protected void configure() {
				mapping(nl.rls.composer.domain.message.MessageHeader.class, nl.rls.xml.tcm.MessageHeader.class)
						.fields("sender.code", "sender").fields("recipient.code", "recipient");
				mapping(nl.rls.composer.domain.CompositIdentifierOperationalType.class,
						nl.rls.xml.tcm.TransportOperationalIdentifiers.class).fields("company.code", "company");
				mapping(nl.rls.composer.domain.Responsibility.class,
						nl.rls.xml.tcm.ResponsibilityActualSection.class).fields("responsibleRU.code", "responsibleRU")
								.fields("responsibleIM.code", "responsibleIM");
				mapping(nl.rls.composer.domain.Responsibility.class,
						nl.rls.xml.tcm.ResponsibilityNextSection.class).fields("responsibleRU.code", "responsibleRU")
								.fields("responsibleIM.code", "responsibleIM");
				mapping(nl.rls.composer.domain.TrainCompositionMessage.class,
						nl.rls.xml.tcm.TrainCompositionMessage.class)
								.fields("transfereeIM.code", "transfereeIM")
								.fields("trainCompositionJourneySection",
										field("trainCompositionJourneySection").accessible(true))
								.fields("compositIdentifierOperationalType",
										field("transportOperationalIdentifiers").accessible(true))
								.fields("messageStatus", "messageStatus")
								.fields("operationalTrainNumber.value", "operationalTrainNumber");
				mapping(LocationSubsidiaryIdentification.class, nl.rls.xml.tcm.LocationSubsidiaryIdentification.class)
						.fields("locationSubsidiaryCode", "locationSubsidiaryCode.value")
						.fields("allocationCompany.code", "allocationCompany");
				mapping(nl.rls.composer.domain.OperationalTrainNumberIdentifier.class,
						nl.rls.xml.tcm.OperationalTrainNumberIdentifier.class).fields("operationalTrainNumber.value",
								"operationalTrainNumber");
				mapping(nl.rls.composer.domain.LocationIdent.class, nl.rls.xml.tcm.LocationIdent.class)
						.fields("countryIso", "countryCodeISO");
				mapping(nl.rls.composer.domain.Locomotive.class,
						nl.rls.xml.tcm.TrainCompositionJourneySection.LocoIdent.class)
								.fields("tractionType.code", "tractionType")
								.fields("tractionMode.code", "tractionMode");
				mapping(nl.rls.composer.domain.TrainCompositionJourneySection.class,
						nl.rls.xml.tcm.TrainCompositionJourneySection.class)
								.fields("locomotives", field("locoIdent").accessible(true))
								.fields("wagons", field("wagonData").accessible(true));
				mapping(nl.rls.composer.domain.Wagon.class, nl.rls.xml.tcm.WagonData.class)
						.fields("wagonNumberFreight.wagonNumberFreight", "wagonNumberFreight")
						.fields("wagonNumberFreight.wagonTechData", "wagonTechData");

			}
		};
		mapper.addMapping(mappingBuilder);
		TrainCompositionMessage xmlTcm = mapper.map(tcm, TrainCompositionMessage.class);
		return xmlTcm;
	}

}
