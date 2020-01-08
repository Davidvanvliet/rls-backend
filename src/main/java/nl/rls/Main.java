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
import nl.rls.composer.domain.Location;
import nl.rls.composer.domain.Responsibility;
import nl.rls.composer.domain.Traction;
import nl.rls.composer.domain.TractionInTrain;
import nl.rls.composer.domain.Train;
import nl.rls.composer.domain.TrainCompositionJourneySection;
import nl.rls.composer.domain.Wagon;
import nl.rls.composer.domain.WagonInTrain;
import nl.rls.composer.domain.WagonLoad;
import nl.rls.composer.domain.code.BrakeType;
import nl.rls.composer.domain.code.MessageType;
import nl.rls.composer.domain.code.TractionMode;
import nl.rls.composer.domain.code.TractionType;
import nl.rls.composer.domain.code.TrainActivityType;
import nl.rls.composer.domain.message.MessageStatus;
import nl.rls.composer.domain.message.TrainCompositionMessage;
import nl.rls.composer.repository.CompanyRepository;
import nl.rls.composer.repository.CompositIdentifierOperationalTypeRepository;
import nl.rls.composer.repository.LocationIdentRepository;
import nl.rls.composer.repository.ResponsibilityRepository;
import nl.rls.composer.repository.TractionModeRepository;
import nl.rls.composer.repository.TractionRepository;
import nl.rls.composer.repository.TractionTypeRepository;
import nl.rls.composer.repository.TrainActivityTypeRepository;
import nl.rls.composer.repository.TrainCompositionJourneySectionRepository;
import nl.rls.composer.repository.TrainCompositionMessageRepository;
import nl.rls.composer.repository.TrainRepository;
import nl.rls.composer.repository.WagonLoadRepository;
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
	private LocationIdentRepository locationIdentRepository;
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
	private TrainCompositionJourneySectionRepository trainCompositionJourneySectionRepository;
	@Autowired
	private TrainCompositionMessageRepository trainCompositionMessageRepository;
	@Autowired
	private TrainRepository trainRepository;
	@Autowired
	private WagonLoadRepository wagonLoadRepository;
	@Autowired
	private WagonRepository wagonRepository;
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

			log.info("Start TCM");
			Integer ownerId = 1;
			
			Train train = new Train();
			train.setOwnerId(ownerId);
			train.setOperationalTrainNumber("123456789");
			train.setScheduledDateTimeAtTransfer(new Date());
			train.setScheduledTimeAtHandover(new Date());
			train.setTransfereeIM(companyRepository.findByCode("0084").get(0));
			Optional<Location> transferPoint = locationIdentRepository.findById(621);
			System.out.println("trainCompositionMessage 1.3.1");
			if (transferPoint.isPresent()) {
				System.out.println("transferPoint.isPresent()");
				train.setTransferPoint(transferPoint.get());
			}
			train = trainRepository.save(train);
			/*
			 * JourneySection
			 */
			System.out.println("trainCompositionMessage 1.4");
			TrainCompositionJourneySection journeySection = new TrainCompositionJourneySection(ownerId);
			Optional<Location> journeySectionOrigin = locationIdentRepository.findById(621);
			if (journeySectionOrigin.isPresent()) {
				journeySection.setJourneySectionOrigin(journeySectionOrigin.get());
			}
			Optional<Location> journeySectionDestination = locationIdentRepository.findById(263);
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
			/*
			 * TrainRunningTechData
			 */

			/*
			 * TrainRunningData
			 */
			System.out.println("trainCompositionMessage 1.7");
			journeySection.setDangerousGoodsIndicator(false);
			journeySection.setExceptionalGaugingInd(false);;
			journeySection.setTrainType(2);
			journeySection.setTrainMaxSpeed(100);
			journeySection.setLivestockOrPeopleIndicator(0);

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
			tractionInTrain1.setPosition(1);
			tractionInTrain1.setTraction(traction1);
			journeySection.addTraction(tractionInTrain1);
//			tractionInTrainRepository.save(tractionInTrain1);

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
			tractionInTrain2.setPosition(2);
			tractionInTrain2.setTraction(traction2);
			journeySection.addTraction(tractionInTrain2);
//			tractionInTrainRepository.save(tractionInTrain2);
/*
			 * WagonData
			 */
			System.out.println("trainCompositionMessage 1.9");
			WagonLoad wagonLoad = new WagonLoad(ownerId);
			wagonLoad.setBrakeType(BrakeType.G);
			wagonLoad.setBrakeWeight(10000);
			wagonLoad.setTotalLoadWeight(13000);
			wagonLoad.setWagonMaxSpeed(100);
			wagonLoad.setOwnerId(ownerId);
			Optional<Wagon> wagon = wagonRepository.findByOwnerIdAndNumberFreight(ownerId, "335249561341");
			if (wagon.isPresent()) {
				wagonLoad.setWagon(wagon.get());				
			}
			wagonLoadRepository.save(wagonLoad);
			WagonInTrain wagonInTrain = new WagonInTrain();
			wagonInTrain.setWagonLoad(wagonLoad);
			wagonInTrain.setPosition(1);
//			wagonInTrainRepository.save(wagonInTrain);

			/*
			 * TrainCompositionJourneySection
			 */
			System.out.println("trainCompositionMessage 1.10");

			journeySection.getWagons().add(wagonInTrain);
			Optional<TrainActivityType> trainActivityType = trainActivityTypeRepository.findByCode("0003");
			if (!journeySection.addActivity(trainActivityType.get())) {
				System.out.println("trainActivityType already added to journey: "+trainActivityType.get().getCode());
			};
			System.out.println("trainCompositionJourneySection.save(): "+journeySection);
			trainCompositionJourneySectionRepository.save(journeySection);
			train.addJourneySection(journeySection);

			System.out.println("trainCompositionMessage 1");
			TrainCompositionMessage trainCompositionMessage = new TrainCompositionMessage(ownerId);
			addMessageHeader(trainCompositionMessage);
			System.out.println("MessageStatus.creation " + MessageStatus.creation);
			trainCompositionMessage.setMessageStatus(MessageStatus.creation.getValue());
			trainCompositionMessage.setOwnerId(ownerId);
			System.out.println("trainCompositionMessage 1.1");
			trainCompositionMessageRepository.save(trainCompositionMessage);			
			System.out.println("trainCompositionMessage 1.2");
			trainCompositionMessage.setTrain(train);
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
			       try {
			    	   jaxbMarshaller.setProperty("com.sun.xml.internal.bind.namespacePrefixMapper", new MyNamespaceMapper());
			            //m.setProperty("com.sun.xml.bind.namespacePrefixMapper", new MyNamespaceMapper());
			        } catch(PropertyException e) {
			            // In case another JAXB implementation is used
			        }

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
		message.setMessageIdentifier(UUID.randomUUID().toString());
		message.setMessageType(MessageType.TRAIN_COMPOSITION_MESSAGE.code());
		message.setMessageTypeVersion(MessageType.TRAIN_COMPOSITION_MESSAGE.version());
		message.setSenderReference(UUID.randomUUID().toString());
		Company recipient = companyRepository.findByCode("0084").get(0);
		Company sender = companyRepository.findByCode("9001").get(0);
		message.setRecipient(recipient);
		message.setSender(sender);
		System.out.println("message(header attributes): " + message.toString());
	}

}
