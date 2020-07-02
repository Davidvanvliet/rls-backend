package nl.rls.composer.xml.mapper;

import info.taf_jsg.schemes.*;
import nl.rls.composer.domain.JourneySection;
import nl.rls.composer.domain.*;
import org.dozer.DozerBeanMapper;
import org.dozer.loader.api.BeanMappingBuilder;

import java.math.BigDecimal;
import java.math.BigInteger;

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
                        .fields("operationalTrainNumber", "operationalTrainNumber")
                        .exclude("customMessageStatuses");

                mapping(nl.rls.composer.domain.JourneySection.class,
                        info.taf_jsg.schemes.TrainCompositionJourneySection.class)
                        .fields("trainComposition.livestockOrPeopleIndicator", "livestockOrPeopleIndicator")
                        .fields("trainComposition.gaugedExceptional",
                                field("trainRunningData.exceptionalGaugingInd").accessible(true))
                        .fields("trainComposition.containsDangerousGoods",
                                field("trainRunningData.dangerousGoodsIndicator").accessible(true))
                        .fields("trainComposition.maxSpeed",
                                "trainRunningData.trainRunningTechData.trainMaxSpeed")
                        .fields("trainComposition.numberOfAxles",
                                "trainRunningData.trainRunningTechData.numberOfAxles")
                        .fields("trainComposition.numberOfVehicles",
                                "trainRunningData.trainRunningTechData.numberOfVehicles")
                        .fields("trainComposition.length",
                                "trainRunningData.trainRunningTechData.trainLength")
                        .fields("trainComposition.weight",
                                "trainRunningData.trainRunningTechData.trainWeight")
                        .fields("trainComposition.maxAxleWeight",
                                "trainRunningData.trainRunningTechData.maxAxleWeight")
                        .fields("trainComposition.brakeWeight",
                                "trainRunningData.trainRunningTechData.brakeWeight")
                        .fields("train.trainType",
                                "trainRunningData.trainRunningTechData.trainType")

                        .fields("journeySectionOrigin",
                                field("journeySection.journeySectionOrigin").accessible(true))
                        .fields("journeySectionDestination",
                                field("journeySection.journeySectionDestination").accessible(true))
                        .fields("responsibilityActualSection",
                                field("journeySection.responsibilityActualSection").accessible(true))
                        .fields("responsibilityNextSection",
                                field("journeySection.responsibilityNextSection").accessible(true));

                mapping(nl.rls.composer.domain.Location.class, info.taf_jsg.schemes.LocationIdent.class)
                        .fields("countryCodeIso", "countryCodeISO");

                mapping(nl.rls.composer.domain.WagonInTrain.class, info.taf_jsg.schemes.WagonData.class)
                        .fields("position", "wagonTrainPosition").fields("wagon.numberFreight", "wagonNumberFreight")
                        .fields("wagon", "wagonTechData")
                        .fields("totalLoadWeight", "wagonOperationalData.totalLoadWeight.")
                        .fields("wagon.brakeWeightP", "wagonOperationalData.brakeWeight")
                        .fields("brakeType", "wagonOperationalData.brakeType")
                        .fields("wagon.maxSpeed", "wagonOperationalData.wagonMaxSpeed")
                        .fields("dangerGoodsInWagons", field("wagonOperationalData.dangerousGoodsDetails").accessible(true))
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
                // mapping(nl.rls.composer.domain.code.TrainActivityType.class,
                // info.taf_jsg.schemes.TrainRunningData.Activities.class)
                // .fields("code", "trainActivityType");
            }
        };
        mapper.addMapping(mappingBuilder);
        TrainCompositionMessage xmlTcm = mapper.map(tcm, TrainCompositionMessage.class);
        for (JourneySection journeySection : tcm.getTrain().getJourneySections()) {
            TrainCompositionJourneySection trainCompositionJourneySection = mapper.map(journeySection, TrainCompositionJourneySection.class);
            TrainComposition trainComposition = journeySection.getTrainComposition();
            int i = 1;
            for (RollingStock rollingStock : trainComposition.getRollingStock()) {
                if (rollingStock.getStockType().equals("traction")) {
                    TractionInTrain tractionInTrain = (TractionInTrain) rollingStock;
                    TrainCompositionJourneySection.LocoIdent locoIdent = new TrainCompositionJourneySection.LocoIdent()
                            .setDriverIndication(BigInteger.valueOf(tractionInTrain.getDriverIndication()))
                            .setLocoNumber(String.valueOf(tractionInTrain.getStockIdentifier()))
                            .setLocoTypeNumber(String.valueOf(tractionInTrain.getTraction().getLocoTypeNumber()))
                            .setTractionMode(11)
                            .setTractionType(tractionInTrain.getTraction().getTractionType().getCode())
                            .setTractionPositionInTrain(i);
                    trainCompositionJourneySection.getLocoIdent().add(locoIdent);

                } else {
                    WagonInTrain wagonInTrain = (WagonInTrain) rollingStock;
                    WagonOperationalData wagonOperationalData = new WagonOperationalData()
                            .setBrakeType(wagonInTrain.getBrakeType().code())
                            .setBrakeWeight(wagonInTrain.getBrakeWeight())
                            .setTotalLoadWeight(wagonInTrain.getTotalLoadWeight())
                            .setWagonMaxSpeed(wagonInTrain.getMaxSpeed());
                    for (DangerGoodsInWagon dangerGoodsInWagon : wagonInTrain.getDangerGoodsInWagons()) {
                        wagonOperationalData.addDangerGoodsDetails(
                                new WagonOperationalData.DangerousGoodsDetails()
                                        .setWeightOfDangerousGoods(BigDecimal.valueOf(dangerGoodsInWagon.getDangerousGoodsWeight()))
                                        .setDangerousGoodsIndication(
                                                new DanGoodsType()
                                                        .setUnNumber(dangerGoodsInWagon.getDangerGoodsType().getUnNumber())
                                                        .setRidClass(dangerGoodsInWagon.getDangerGoodsType().getRidClass())
                                        )
                        );
                    }
                    WagonData wagonData = new WagonData()
                            .setWagonTrainPosition(i)
                            .setWagonNumberFreight(String.valueOf(wagonInTrain.getStockIdentifier()))
                            .setWagonOperationalData(wagonOperationalData)
                            .setWagonTechData(
                                    new WagonTechData()
                                            .setWagonWeightEmpty(wagonInTrain.getWagon().getWagonWeightEmpty())
                                            .setWagonNumberOfAxles(wagonInTrain.getNumberOfAxles())
                                            .setLengthOverBuffers(wagonInTrain.getLength())
                            );
                    trainCompositionJourneySection.getWagonData().add(wagonData);
                    i++;
                }
            }
            xmlTcm.getTrainCompositionJourneySection().add(trainCompositionJourneySection);
        }
        return xmlTcm;
    }
}
