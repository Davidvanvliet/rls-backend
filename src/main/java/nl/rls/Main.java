package nl.rls;

import java.io.File;
import java.util.Calendar;
import java.util.Date;
import java.util.Optional;
import java.util.UUID;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.PropertyException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import nl.rls.ci.aa.repository.LicenseRepository;
import nl.rls.ci.aa.repository.OwnerRepository;
import nl.rls.ci.aa.repository.RoleRepository;
import nl.rls.ci.aa.repository.UserRepository;
import nl.rls.composer.domain.Company;
import nl.rls.composer.domain.DangerGoodsInWagon;
import nl.rls.composer.domain.DangerGoodsType;
import nl.rls.composer.domain.GenericMessage;
import nl.rls.composer.domain.JourneySection;
import nl.rls.composer.domain.Location;
import nl.rls.composer.domain.Responsibility;
import nl.rls.composer.domain.Traction;
import nl.rls.composer.domain.TractionInTrain;
import nl.rls.composer.domain.Train;
import nl.rls.composer.domain.TrainComposition;
import nl.rls.composer.domain.Wagon;
import nl.rls.composer.domain.WagonInTrain;
import nl.rls.composer.domain.code.BrakeType;
import nl.rls.composer.domain.code.MessageType;
import nl.rls.composer.domain.code.TractionMode;
import nl.rls.composer.domain.code.TractionType;
import nl.rls.composer.domain.code.TrainActivityType;
import nl.rls.composer.domain.message.MessageStatus;
import nl.rls.composer.domain.message.TrainCompositionMessage;
import nl.rls.composer.repository.CompanyRepository;
import nl.rls.composer.repository.CompositIdentifierOperationalTypeRepository;
import nl.rls.composer.repository.DangerGoodsTypeRepository;
import nl.rls.composer.repository.JourneySectionRepository;
import nl.rls.composer.repository.LocationRepository;
import nl.rls.composer.repository.ResponsibilityRepository;
import nl.rls.composer.repository.TractionModeRepository;
import nl.rls.composer.repository.TractionRepository;
import nl.rls.composer.repository.TractionTypeRepository;
import nl.rls.composer.repository.TrainActivityTypeRepository;
import nl.rls.composer.repository.TrainCompositionMessageRepository;
import nl.rls.composer.repository.TrainRepository;
import nl.rls.composer.repository.WagonRepository;
import nl.rls.composer.xml.mapper.TrainCompositionMessageXmlMapper;

@SpringBootApplication(exclude = {SecurityAutoConfiguration.class})
public class Main {
    private static final Logger log = LoggerFactory.getLogger(Main.class);
    @Autowired
    DangerGoodsTypeRepository dangerGoodsTypeRepository;
    // @Autowired
    // private static final Logger log = LoggerFactory.getLogger(Trains24CI.class);
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private LicenseRepository licenseRepository;
    @Autowired
    private OwnerRepository ownerRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private LocationRepository locationIdentRepository;
    @Autowired
    private CompanyRepository companyRepository;
    @Autowired
    private CompositIdentifierOperationalTypeRepository compositIdentifierOperationalTypeRepository;
    @Autowired
    private TractionRepository tractionRepository;
    @Autowired
    private ResponsibilityRepository responsibilityRepository;
    @Autowired
    private TractionModeRepository tractionModeRepository;
    @Autowired
    private TractionTypeRepository tractionTypeRepository;
    @Autowired
    private TrainActivityTypeRepository trainActivityTypeRepository;
    @Autowired
    private JourneySectionRepository journeySectionRepository;
    @Autowired
    private TrainCompositionMessageRepository trainCompositionMessageRepository;
    @Autowired
    private TrainRepository trainRepository;
    @Autowired
    private WagonRepository wagonRepository;

    public static void main(String[] args) {
        String sqlURL = System.getenv("JDBC_DATABASE_URL");
        log.info("=== database url === " + sqlURL);
        System.getProperties().put("http.proxyHost", "127.0.0.1");
        System.getProperties().put("http.proxyPort", "8083");
        SpringApplication.run(Main.class, args);
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

//    @Bean
//    @Transactional
//    public CommandLineRunner demo() {
//        return (args) -> {
//            log.info("RLS - Application started");
//            log.info("Start TCM");
//            Integer ownerId = 1;
//            addWagonsToDB();
//
//            Train train = new Train();
//            train.setOwnerId(ownerId);
//            train.setTrainType(2);
//            train.setOperationalTrainNumber("12345678");
//            train.setScheduledDateTimeAtTransfer(new Date());
//            train.setScheduledTimeAtHandover(new Date());
//            train.setTransfereeIM(companyRepository.findByCode("0084").get(0));
//            Optional<Location> transferPoint = locationIdentRepository.findById(621);
//            System.out.println("train 1.1");
//            if (transferPoint.isPresent()) {
//                System.out.println("transferPoint.isPresent()");
//                train.setTransferPoint(transferPoint.get());
//            }
//            train = trainRepository.save(train);
//            /*
//             * JourneySection
//             */
//            System.out.println("journeySection 2.1");
//            JourneySection journeySection = new JourneySection(ownerId);
//            Optional<Location> journeySectionOrigin = locationIdentRepository.findById(621);
//            if (journeySectionOrigin.isPresent()) {
//                journeySection.setJourneySectionOrigin(journeySectionOrigin.get());
//            }
//            Optional<Location> journeySectionDestination = locationIdentRepository.findById(263);
//            if (journeySectionDestination.isPresent()) {
//                journeySection.setJourneySectionDestination(journeySectionDestination.get());
//            }
//
//            System.out.println("journeySection 2.2");
//            Responsibility responsibility = new Responsibility(ownerId);
//            Company responsibleIM = companyRepository.findByCode("0084").get(0);
//            responsibility.setResponsibleIM(responsibleIM);
//            Company responsibleRU = companyRepository.findByCode("3502").get(0);
//            responsibility.setResponsibleRU(responsibleRU);
//            responsibilityRepository.save(responsibility);
//            journeySection.setResponsibilityActualSection(responsibility);
//            journeySection.setResponsibilityNextSection(responsibility);
//            System.out.println("journeySection 2.3");
//            journeySection.setTrain(train);
//            /*
//             * TrainComposition
//             */
//            System.out.println("trainComposition 3.1");
//            TrainComposition trainComposition = new TrainComposition();
//            trainComposition.setOwnerId(ownerId);
//            trainComposition.setJourneySection(journeySection);
//            trainComposition.setLivestockOrPeopleIndicator(0);
//            trainComposition.setTrainMaxSpeed(100);
//            trainComposition.setMaxAxleWeight(30);
//            journeySection.setTrainComposition(trainComposition);
//
//            /*
//             * LocoIdent Traction
//             */
//            System.out.println("traction 4.1");
//            Traction traction1 = new Traction();
//            Optional<TractionType> tractionType = tractionTypeRepository.findByCode("11");
//            traction1.setTractionType(tractionType.get());
//            traction1.setLocoNumber("928422031023");
//            traction1.setLocoTypeNumber("123456");
//            traction1.setLengthOverBuffers(3000);
//            traction1.setWeight(55000);
//            traction1.setNumberOfAxles(6);
//            traction1.setOwnerId(ownerId);
//            tractionRepository.save(traction1);
//            TractionInTrain tractionInTrain1 = new TractionInTrain();
//            tractionInTrain1.setDriverIndication(1);
//            tractionInTrain1.setPosition(1);
//            tractionInTrain1.setTraction(traction1);
//            trainComposition.addTraction(tractionInTrain1);
//            Optional<TractionMode> tractionMode = tractionModeRepository.findByCode("11");
//            tractionInTrain1.setTractionMode(tractionMode.get());
////			tractionInTrainRepository.save(tractionInTrain1);
//
//            Traction traction2 = new Traction(ownerId);
//            tractionType = tractionTypeRepository.findByCode("11");
//            traction2.setTractionType(tractionType.get());
//            traction2.setLocoNumber("92842203456");
//            traction2.setLocoTypeNumber("123455");
//            tractionMode = tractionModeRepository.findByCode("11");
//            traction2.setLengthOverBuffers(3000);
//            traction2.setWeight(55000);
//            traction2.setNumberOfAxles(6);
//            tractionRepository.save(traction2);
//            TractionInTrain tractionInTrain2 = new TractionInTrain();
//            tractionInTrain2.setDriverIndication(0);
//            tractionInTrain2.setPosition(2);
//            tractionInTrain2.setTraction(traction2);
//            tractionInTrain1.setTractionMode(tractionMode.get());
//            trainComposition.addTraction(tractionInTrain2);
////			tractionInTrainRepository.save(tractionInTrain2);
//            /*
//             * Wagon
//             */
//            System.out.println("wagonInTrain 5.1");
//            WagonInTrain wagonInTrain = new WagonInTrain();
//            wagonInTrain.setOwnerId(ownerId);
//            wagonInTrain.setPosition(1);
//            wagonInTrain.setBrakeType(BrakeType.G);
//            wagonInTrain.setBrakeWeight(10000);
//            wagonInTrain.setTotalLoadWeight(13000);
//            wagonInTrain.setWagonMaxSpeed(100);
//            Optional<Wagon> wagon = wagonRepository.findByNumberFreightAndOwnerId("900100000004", ownerId);
//            if (wagon.isPresent()) {
//                wagonInTrain.setWagon(wagon.get());
//            }
//            trainComposition.getWagons().add(wagonInTrain);
//            Optional<DangerGoodsType> dangerGoodsType = dangerGoodsTypeRepository.findById(1);
//            DangerGoodsInWagon dangerGoodsInWagon = new DangerGoodsInWagon();
//            dangerGoodsInWagon.setDangerGoodsType(dangerGoodsType.get());
//            dangerGoodsInWagon.setDangerousGoodsWeight(1000);
//            dangerGoodsInWagon.setWagonInTrain(wagonInTrain);
//            wagonInTrain.addDangerGoodsInWagon(dangerGoodsInWagon);
//
////			wagonInTrainRepository.save(wagonInTrain);
//
//            /*
//             * TrainCompositionJourneySection
//             */
//            System.out.println("journeySection 6.1");
//
//            Optional<TrainActivityType> trainActivityType = trainActivityTypeRepository.findByCode("0003");
//            if (!journeySection.addActivity(trainActivityType.get())) {
//                System.out.println("trainActivityType already added to journey: " + trainActivityType.get().getCode());
//            }
//            ;
//            System.out.println("journeySection.save(): " + journeySection);
//            journeySectionRepository.save(journeySection);
//            train.addJourneySection(journeySection);
//
//            System.out.println("trainCompositionMessage 1");
//            TrainCompositionMessage trainCompositionMessage = new TrainCompositionMessage(ownerId);
//            addMessageHeader(trainCompositionMessage);
//            System.out.println("MessageStatus.creation " + MessageStatus.creation);
//            trainCompositionMessage.setMessageStatus(MessageStatus.creation.getValue());
//            trainCompositionMessage.setOwnerId(ownerId);
//            System.out.println("trainCompositionMessage 1.1");
//            trainCompositionMessageRepository.save(trainCompositionMessage);
//            System.out.println("trainCompositionMessage 1.2");
//            trainCompositionMessage.setTrain(train);
//
//            /*
//             * CompositIdentifierOperationalType
//             */
//            trainCompositionMessage.setCompany(companyRepository.findByCode("3502").get(0));
//            Date date = new Date();
//            trainCompositionMessage.setStartDate(date);
//            trainCompositionMessage.setObjectType("TR");
//            trainCompositionMessage.setCore("041350222700");
//            trainCompositionMessage.setTimetableYear(Calendar.getInstance().get(Calendar.YEAR));
//            trainCompositionMessage.setVariant("00");
//
//            trainCompositionMessageRepository.save(trainCompositionMessage);
//            System.out.println("trainCompositionMessage 2");
//            System.out.println("JourneySection: " + train.getJourneySections().get(0));
//            System.out.println("numberOfAxles: " + train.getJourneySections().get(0).getTrainComposition().getNumberOfAxles());
//
//            info.taf_jsg.schemes.TrainCompositionMessage tcm = TrainCompositionMessageXmlMapper
//                    .map(trainCompositionMessage);
////            try {
////
////                File file = new File("train_composition_message.xml");
////                JAXBContext jaxbContext = JAXBContext.newInstance(info.taf_jsg.schemes.TrainCompositionMessage.class);
////                Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
////
////                // output pretty printed
////                jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
////                //
////                try {
////                    jaxbMarshaller.setProperty("com.sun.xml.internal.bind.namespacePrefixMapper", new MyNamespaceMapper());
////                    //m.setProperty("com.sun.xml.bind.namespacePrefixMapper", new MyNamespaceMapper());
////                } catch (PropertyException e) {
////                    // In case another JAXB implementation is used
////                }
////
////                jaxbMarshaller.marshal(tcm, file);
////                jaxbMarshaller.marshal(tcm, System.out);
////
////            } catch (JAXBException e) {
////                e.printStackTrace();
////                //
////            }
//            log.info("RLS - Application started");
//        };
//    }
//
//    private void addMessageHeader(GenericMessage message) {
//        message.setMessageDateTime(new Date());
//        message.setMessageIdentifier(UUID.randomUUID().toString());
//        message.setMessageType(MessageType.TRAIN_COMPOSITION_MESSAGE.code());
//        message.setMessageTypeVersion(MessageType.TRAIN_COMPOSITION_MESSAGE.version());
//        message.setSenderReference(UUID.randomUUID().toString());
//        Company recipient = companyRepository.findByCode("0084").get(0);
//        Company sender = companyRepository.findByCode("9001").get(0);
//        message.setRecipient(recipient);
//        message.setSender(sender);
//        System.out.println("message(header attributes): " + message.toString());
//    }
//
//    private void addWagonsToDB() {
//        Wagon wagon = new Wagon();
//        wagon.setOwnerId(1);
//        wagon.setCode("Fccpps");
//        wagon.setHandBrakeBrakedWeight(0);
//        wagon.setLengthOverBuffers(964);
//        wagon.setNumberFreight("900100000001");
//        wagon.setTypeName("Fccpps");
//        wagon.setWagonNumberOfAxles(2);
//        wagon.setWagonWeightEmpty(10340);
//        wagonRepository.save(wagon);
//        Wagon wagon1 = new Wagon();
//        wagon1.setOwnerId(1);
//        wagon1.setCode("Fccpps");
//        wagon1.setHandBrakeBrakedWeight(0);
//        wagon1.setLengthOverBuffers(964);
//        wagon1.setNumberFreight("900100000002");
//        wagon1.setTypeName("Fccpps");
//        wagon1.setWagonNumberOfAxles(2);
//        wagon1.setWagonWeightEmpty(10340);
//        wagonRepository.save(wagon1);
//        Wagon wagon2 = new Wagon();
//        wagon2.setOwnerId(1);
//        wagon2.setCode("Fccpps");
//        wagon2.setHandBrakeBrakedWeight(0);
//        wagon2.setLengthOverBuffers(964);
//        wagon2.setNumberFreight("900100000003");
//        wagon2.setTypeName("Fccpps");
//        wagon2.setWagonNumberOfAxles(2);
//        wagon2.setWagonWeightEmpty(10340);
//        wagonRepository.save(wagon2);
//        Wagon wagon3 = new Wagon();
//        wagon3.setOwnerId(1);
//        wagon3.setCode("Klmos");
//        wagon3.setHandBrakeBrakedWeight(0);
//        wagon3.setLengthOverBuffers(1174);
//        wagon3.setNumberFreight("900100000004");
//        wagon3.setTypeName("Klmos");
//        wagon3.setWagonNumberOfAxles(2);
//        wagon3.setWagonWeightEmpty(12500);
//        wagonRepository.save(wagon3);
//    }

}
