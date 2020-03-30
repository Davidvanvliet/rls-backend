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
        SpringApplication.run(Main.class, args);
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
