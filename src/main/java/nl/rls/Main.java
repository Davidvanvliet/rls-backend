package nl.rls;

import nl.rls.composer.repository.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication(exclude = {SecurityAutoConfiguration.class})
public class Main {
    private static final Logger log = LoggerFactory.getLogger(Main.class);
    @Autowired
    DangerGoodsTypeRepository dangerGoodsTypeRepository;
    // @Autowired
    // private static final Logger log = LoggerFactory.getLogger(Trains24CI.class);
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
