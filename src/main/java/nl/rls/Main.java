package nl.rls;

import java.io.File;
import java.util.Calendar;
import java.util.Date;
import java.util.Optional;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

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

import nl.rls.ci.aa.domain.AppUser;
import nl.rls.ci.aa.domain.License;
import nl.rls.ci.aa.domain.Owner;
import nl.rls.ci.aa.domain.Role;
import nl.rls.ci.aa.repository.LicenseRepository;
import nl.rls.ci.aa.repository.OwnerRepository;
import nl.rls.ci.aa.repository.RoleRepository;
import nl.rls.ci.aa.repository.UserRepository;
import nl.rls.composer.domain.Company;
import nl.rls.composer.domain.CompositIdentifierOperationalType;
import nl.rls.composer.domain.GenericMessage;
import nl.rls.composer.domain.JourneySection;
import nl.rls.composer.domain.LocationIdent;
import nl.rls.composer.domain.OperationalTrainNumberIdentifier;
import nl.rls.composer.domain.Responsibility;
import nl.rls.composer.domain.Traction;
import nl.rls.composer.domain.TractionInTrain;
import nl.rls.composer.domain.TrainCompositionJourneySection;
import nl.rls.composer.domain.TrainCompositionMessage;
import nl.rls.composer.domain.TrainRunningData;
import nl.rls.composer.domain.Wagon;
import nl.rls.composer.domain.WagonIdent;
import nl.rls.composer.domain.WagonInTrain;
import nl.rls.composer.domain.code.BrakeType;
import nl.rls.composer.domain.code.MessageType;
import nl.rls.composer.domain.code.TractionMode;
import nl.rls.composer.domain.code.TractionType;
import nl.rls.composer.domain.code.TrainActivityType;
import nl.rls.composer.domain.message.MessageStatus;
import nl.rls.composer.repository.CompanyRepository;
import nl.rls.composer.repository.CompositIdentifierOperationalTypeRepository;
import nl.rls.composer.repository.JourneySectionRepository;
import nl.rls.composer.repository.LocationIdentRepository;
import nl.rls.composer.repository.OperationalTrainNumberIdentifierRepository;
import nl.rls.composer.repository.ResponsibilityRepository;
import nl.rls.composer.repository.TractionInTrainRepository;
import nl.rls.composer.repository.TractionModeRepository;
import nl.rls.composer.repository.TractionRepository;
import nl.rls.composer.repository.TractionTypeRepository;
import nl.rls.composer.repository.TrainActivityTypeRepository;
import nl.rls.composer.repository.TrainCompositionJourneySectionRepository;
import nl.rls.composer.repository.TrainCompositionMessageRepository;
import nl.rls.composer.repository.WagonIdentRepository;
import nl.rls.composer.repository.WagonInTrainRepository;
import nl.rls.composer.repository.WagonRepository;
import nl.rls.composer.xml.mapper.TrainCompositionMessageXmlMapper;

@SpringBootApplication(exclude = { SecurityAutoConfiguration.class })
public class Main {
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
	private JourneySectionRepository journeySectionRepository;
	@Autowired
	private LocationIdentRepository locationIdentRepository;
	@Autowired
	private CompanyRepository companyRepository;
	@Autowired
	private CompositIdentifierOperationalTypeRepository compositIdentifierOperationalTypeRepository;
	@Autowired
	private TractionRepository tractionRepository;
	@Autowired
	private TractionInTrainRepository tractionInTrainRepository;
	@Autowired
	private OperationalTrainNumberIdentifierRepository operationalTrainNumberIdentifierRepository;
	@Autowired
	private ResponsibilityRepository responsibilityRepository;
	@Autowired
	private TractionModeRepository tractionModeRepository;
	@Autowired
	private TractionTypeRepository tractionTypeRepository;
	@Autowired
	private TrainActivityTypeRepository trainActivityTypeRepository;
	@Autowired
	private TrainCompositionJourneySectionRepository trainCompositionJourneySectionRepository;
	@Autowired
	private TrainCompositionMessageRepository trainCompositionMessageRepository;
	@Autowired
	private WagonIdentRepository wagonIdentRepository;
	@Autowired
	private WagonRepository wagonRepository;
	@Autowired
	private WagonInTrainRepository wagonInTrainRepository;
	private static final Logger log = LoggerFactory.getLogger(Main.class);

	public static void main(String[] args) {
		String sqlURL = System.getenv("JDBC_DATABASE_URL");
		log.info("=== database url === " + sqlURL);
		SpringApplication.run(Main.class, args);
	}

	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	@Transactional
	public CommandLineRunner demo() {
		return (args) -> {
			log.info("Start CommandLineRunner");
			roleRepository.save(new Role("ROLE_USER"));
			roleRepository.save(new Role("ROLE_SUPER_USER"));
			roleRepository.save(new Role("ROLE_ADMIN"));
			Role role = roleRepository.findByName("ROLE_ADMIN");
			AppUser user = new AppUser("Berend", "Wilkens", "berend.wilkens@hu.nl");
			userRepository.save(user);
			user = userRepository.findById(user.getId()).get();
			user.getRoles().add(role);
			user.setPassword(bCryptPasswordEncoder().encode("qwerty"));
			Owner owner = new Owner();
			owner.setCode("3502");
			owner.setName("RLS - ADMIN");
			Calendar now = Calendar.getInstance();
			Calendar now3 = Calendar.getInstance();
			now3.add(Calendar.MONTH, 3);
			user.setOwner(owner);
			log.info("Start saving ...");
			ownerRepository.save(owner);
			licenseRepository.save(new License("LICENSE_ADMIN", now.getTime(), now3.getTime(), owner));
			userRepository.save(user);

			owner = new Owner();
			owner.setCode("3502");
			owner.setName("Spitzke Spoorbouw BV");
			now = Calendar.getInstance();
			now3 = Calendar.getInstance();
			now3.add(Calendar.MONTH, 3);
			log.info("Start saving ...");
			ownerRepository.save(owner);
			licenseRepository.save(new License("LICENSE_FREE", now.getTime(), now3.getTime(), owner));

			// Calendar now = Calendar.getInstance();
			// now.add(Calendar.MONTH, -3);
			// Calendar now3 = Calendar.getInstance();
			// now3.add(Calendar.MONTH, -1);
			// log.info("Start saving ...");
			// ownerRepository.save(owner);
			// licenseRepository.save(new License("FREE", now.getTime(), now3.getTime(),
			// owner));

			log.info("End CommandLineRunner");
			Integer ownerId = 1;
			System.out.println("trainCompositionMessage 1");
			TrainCompositionMessage trainCompositionMessage = new TrainCompositionMessage(ownerId);
			addMessageHeader(trainCompositionMessage);
			System.out.println("MessageStatus.creation " + MessageStatus.creation);
			trainCompositionMessage.setMessageStatus(MessageStatus.creation.getValue());
			trainCompositionMessage.setOwnerId(ownerId);
			System.out.println("trainCompositionMessage 1.1");
			System.out.println("trainCompositionMessage 1.2");
			OperationalTrainNumberIdentifier operationalTrainNumberIdentifier = new OperationalTrainNumberIdentifier(
					"41350", new Date(), new Date());
			operationalTrainNumberIdentifier.setOwnerId(ownerId);
			operationalTrainNumberIdentifierRepository.save(operationalTrainNumberIdentifier);
			trainCompositionMessage.setOperationalTrainNumberIdentifier(operationalTrainNumberIdentifier);

			System.out.println("trainCompositionMessage 1.3");
			trainCompositionMessage.setTransfereeIM(trainCompositionMessage.getRecipient());
			System.out.println("trainCompositionMessage 1.3.0");
			Optional<LocationIdent> transferPoint = locationIdentRepository.findById(621);
			System.out.println("trainCompositionMessage 1.3.1");
			if (transferPoint.isPresent()) {
				System.out.println("transferPoint.isPresent()");
				trainCompositionMessage.setTransferPoint(transferPoint.get());
			}
			System.out.println("trainCompositionMessage 1.3.2");
			trainCompositionMessageRepository.save(trainCompositionMessage);
			/*
			 * CompositIdentifierOperationalType
			 */
			System.out.println("trainCompositionMessage 1.3.3");
			CompositIdentifierOperationalType compositIdentifierOperationalType = new CompositIdentifierOperationalType();
			compositIdentifierOperationalType.setOwnerId(ownerId);
			compositIdentifierOperationalType.setCompany(companyRepository.findByCode("3502").get(0));
			compositIdentifierOperationalType.setObjectType("TR");
			compositIdentifierOperationalType.setCore("041350222700");
			compositIdentifierOperationalType.setStartDate(new Date());
			compositIdentifierOperationalType.setTimetableYear(2019);
			compositIdentifierOperationalType.setVariant("00");
			System.out.println("trainCompositionMessage 1.3.4");
			compositIdentifierOperationalTypeRepository.save(compositIdentifierOperationalType);
			trainCompositionMessage.getCompositIdentifierOperationalType().add(compositIdentifierOperationalType);
			System.out.println("trainCompositionMessage 1.3.5");
			compositIdentifierOperationalTypeRepository.save(compositIdentifierOperationalType);
			/*
			 * JourneySection
			 */
			System.out.println("trainCompositionMessage 1.4");
			JourneySection journeySection = new JourneySection(ownerId);
			Optional<LocationIdent> journeySectionOrigin = locationIdentRepository.findById(621);
			if (journeySectionOrigin.isPresent()) {
				journeySection.setJourneySectionOrigin(journeySectionOrigin.get());
			}
			Optional<LocationIdent> journeySectionDestination = locationIdentRepository.findById(263);
			if (journeySectionDestination.isPresent()) {
				journeySection.setJourneySectionDestination(journeySectionDestination.get());
			}

			System.out.println("trainCompositionMessage 1.5");
			Responsibility responsibility = new Responsibility(ownerId);
			Company responsibleIM = companyRepository.findByCode("0084").get(0);
			responsibility.setResponsibleIM(responsibleIM);
			Company responsibleRU = companyRepository.findByCode("3502").get(0);
			responsibility.setResponsibleRU(responsibleRU);
			responsibilityRepository.save(responsibility);
			journeySection.setResponsibilityActualSection(responsibility);
			journeySection.setResponsibilityNextSection(responsibility);
			journeySection.setOwnerId(ownerId);
			journeySectionRepository.save(journeySection);
			System.out.println(journeySection);
			/*
			 * TrainRunningTechData
			 */

			/*
			 * TrainRunningData
			 */
			System.out.println("trainCompositionMessage 1.7");
			TrainRunningData trainRunningData = new TrainRunningData(ownerId);
			trainRunningData.setDangerousGoodsIndicator(false);
			trainRunningData.setExceptionalGaugingInd(false);;
			trainRunningData.setTrainType(2);
			trainRunningData.setTrainMaxSpeed(100);

			/*
			 * LocoIdent Traction
			 */
			System.out.println("trainCompositionMessage 1.8");
			Traction traction1 = new Traction(ownerId);
			Optional<TractionType> tractionType = tractionTypeRepository.findByCode("11");
			traction1.setTractionType(tractionType.get());
			traction1.setLocoNumber("928422031023");
			traction1.setLocoTypeNumber("123456");
			Optional<TractionMode> tractionMode = tractionModeRepository.findByCode("11");
			traction1.setTractionMode(tractionMode.get());
			traction1.setLengthOverBuffers(3000);
			traction1.setWeight(55000);
			traction1.setNumberOfAxles(6);
			tractionRepository.save(traction1);
			TractionInTrain tractionInTrain1 = new TractionInTrain();
			tractionInTrain1.setDriverIndication(1);
			tractionInTrain1.setTractionPositionInTrain(1);
			tractionInTrain1.setTraction(traction1);
			tractionInTrainRepository.save(tractionInTrain1);

			Traction traction2 = new Traction(ownerId);
			tractionType = tractionTypeRepository.findByCode("11");
			traction2.setTractionType(tractionType.get());
			traction2.setLocoNumber("92842203456");
			traction2.setLocoTypeNumber("123455");
			tractionMode = tractionModeRepository.findByCode("11");
			traction2.setTractionMode(tractionMode.get());
			traction2.setLengthOverBuffers(3000);
			traction2.setWeight(55000);
			traction2.setNumberOfAxles(6);
			tractionRepository.save(traction2);
			TractionInTrain tractionInTrain2 = new TractionInTrain();
			tractionInTrain2.setDriverIndication(0);
			tractionInTrain2.setTractionPositionInTrain(2);
			tractionInTrain2.setTraction(traction2);
			tractionInTrainRepository.save(tractionInTrain2);
/*
			 * WagonData
			 */
			System.out.println("trainCompositionMessage 1.9");
			Wagon wagon = new Wagon(ownerId);
			wagon.setBrakeType(BrakeType.G);
			wagon.setBrakeWeight(10000);
			wagon.setTotalLoadWeight(13000);
			wagon.setWagonMaxSpeed(100);
			wagon.setOwnerId(ownerId);
			Optional<WagonIdent> wagonIdent = wagonIdentRepository.findByOwnerIdAndWagonNumberFreight(ownerId, "335249561341");
			if (wagonIdent.isPresent()) {
				wagon.setWagonNumberFreight(wagonIdent.get());				
			}
			wagonRepository.save(wagon);
			WagonInTrain wagonInTrain = new WagonInTrain();
			wagonInTrain.setWagon(wagon);
			wagonInTrain.setWagonTrainPosition(1);
			wagonInTrainRepository.save(wagonInTrain);

			/*
			 * TrainCompositionJourneySection
			 */
			System.out.println("trainCompositionMessage 1.10");
			TrainCompositionJourneySection trainCompositionJourneySection = new TrainCompositionJourneySection(ownerId);
			trainCompositionJourneySection.setLivestockOrPeopleIndicator(0);
			trainCompositionJourneySection.setJourneySection(journeySection);

			trainCompositionJourneySection.setTrainRunningData(trainRunningData);
			trainCompositionJourneySection.getTractions().add(tractionInTrain1);
			trainCompositionJourneySection.getTractions().add(tractionInTrain2);
			trainCompositionJourneySection.getWagons().add(wagonInTrain);
			Optional<TrainActivityType> trainActivityType = trainActivityTypeRepository.findByCode("0003");
			if (!trainCompositionJourneySection.addActivity(trainActivityType.get())) {
				System.out.println("trainActivityType already added to journey: "+trainActivityType.get().getCode());
			};
			System.out.println("trainCompositionJourneySection.save(): "+trainCompositionJourneySection);
			trainCompositionJourneySectionRepository.save(trainCompositionJourneySection);
			trainCompositionMessage.getTrainCompositionJourneySection().add(trainCompositionJourneySection);

			trainCompositionMessageRepository.save(trainCompositionMessage);
			System.out.println("trainCompositionMessage 2");
			info.taf_jsg.schemes.TrainCompositionMessage tcm = TrainCompositionMessageXmlMapper
					.map(trainCompositionMessage);
			try {

				File file = new File("train_composition_message.xml");
				JAXBContext jaxbContext = JAXBContext.newInstance(info.taf_jsg.schemes.TrainCompositionMessage.class);
				Marshaller jaxbMarshaller = jaxbContext.createMarshaller();

				// output pretty printed
				jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
				//
				jaxbMarshaller.marshal(tcm, file);
				jaxbMarshaller.marshal(tcm, System.out);

			} catch (JAXBException e) {
				e.printStackTrace();
				//
			}
			log.info("RLS - Application started");
		};

	}

	private void addMessageHeader(GenericMessage message) {
		message.setMessageDateTime(new Date());
		message.setMessageIdentifier("Vrije tekst?");
		message.setMessageType(MessageType.TRAIN_COMPOSITION_MESSAGE.code());
		message.setMessageTypeVersion(MessageType.TRAIN_COMPOSITION_MESSAGE.version());
		message.setSenderReference("test bericht");
		Company recipient = companyRepository.findByCode("0084").get(0);
		Company sender = companyRepository.findByCode("3502").get(0);
		message.setRecipient(recipient);
		message.setSender(sender);
		System.out.println("message(header attributes): " + message.toString());
	}

}
