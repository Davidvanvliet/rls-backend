import io.restassured.RestAssured;
import nl.rls.Main;
import nl.rls.ci.aa.domain.Owner;
import nl.rls.ci.aa.repository.OwnerRepository;
import org.flywaydb.test.FlywayTestExecutionListener;
import org.flywaydb.test.annotation.FlywayTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;

import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = Main.class)
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class,
        FlywayTestExecutionListener.class})
@FlywayTest
@ExtendWith({SpringExtension.class, GlobalSettings.class})
public class SignupControllerIT {
    @LocalServerPort
    int randomServerPort;
    @Autowired
    private OwnerRepository ownerRepository;


    @BeforeEach
    public void setup() {
        RestAssured.port = randomServerPort;
        ownerRepository.save(new Owner("3502", "RaillinkSystems 123"));
    }

    @Test
    public void signup() {
        String email = "berend@koekoeksplein.nl";
        String firstName = "Berend";
        String jsonString = "{\n" +
                "  \"email\": \"" + email + "\",\n" +
                "  \"firstName\" : \"" + firstName + "\",\n" +
                "  \"lastName\" : \"Wilkens\",\n" +
                "  \"password\": \"password\",\n" +
                "  \"owner\": {\"code\": \"3502\"}\n" +
                "}";
        given().body(jsonString).contentType("application/json").post(RestAssured.baseURI + RestAssured.port + "/signup")
                .then().assertThat().body(matchesJsonSchemaInClasspath("signUp-schema.json"));

    }
}
