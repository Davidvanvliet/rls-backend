import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import nl.rls.Main;
import nl.rls.ci.url.BaseURL;
import nl.rls.composer.controller.TrainController;
import org.flywaydb.test.FlywayTestExecutionListener;
import org.flywaydb.test.annotation.FlywayTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.event.annotation.BeforeTestClass;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;

import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

@SpringBootTest(classes = Main.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class,
        FlywayTestExecutionListener.class})
@FlywayTest
@ExtendWith({SpringExtension.class, GlobalSettings.class})
public class TrainControllerIT {
    @LocalServerPort
    int randomServerPort;

    @BeforeTestClass
    public void insertSetup() {
        String jsonTrain = "{\n" +
                "    \"operationalTrainNumber\": \"123456789\",\n" +
                "    \"trainType\": \"2\",\n" +
                "    \"transferPoint\": \"http://localhost:5000/api/v1/locations/100\",\n" +
                "    \"scheduledTimeAtHandover\": \"2019-12-14T17:41:56.108+0000\",\n" +
                "    \"scheduledDateTimeAtTransfer\": \"2019-12-14T17:41:56.108+0000\"\n" +
                "}";

        String path = RestAssured.baseURI + randomServerPort + BaseURL.BASE_PATH + TrainController.PATH;
        ValidatableResponse response = given().body(jsonTrain).contentType(ContentType.JSON).when().post(path).then();
    }

    @BeforeEach
    public void setup() {
        RestAssured.port = randomServerPort;
    }

    @Test
    public void createTrain() {
        String jsonTrain = "{\n" +
                "    \"operationalTrainNumber\": \"12345678\",\n" +
                "    \"trainType\": \"2\",\n" +
                "    \"transferPoint\": \"http://localhost:5000/api/v1/locations/100\",\n" +
                "    \"scheduledTimeAtHandover\": \"2019-12-14T17:41:56.108+0000\",\n" +
                "    \"scheduledDateTimeAtTransfer\": \"2019-12-14T17:41:56.108+0000\"\n" +
                "}";

        String path = RestAssured.baseURI + RestAssured.port + BaseURL.BASE_PATH + TrainController.PATH;
        ValidatableResponse response = given().body(jsonTrain).contentType(ContentType.JSON).when().post(path).then();
        response.assertThat().body(matchesJsonSchemaInClasspath("createTrain.json"));
    }

    @Test
    public void getTrains() {
        ValidatableResponse response = given().when().get(RestAssured.baseURI + RestAssured.port + BaseURL.BASE_PATH + TrainController.PATH).then();

    }

    @Test
    public void getTrain() {
        ValidatableResponse response = given().when().get(RestAssured.baseURI + RestAssured.port + BaseURL.BASE_PATH + TrainController.PATH + "/1").then();
        response.assertThat().body(matchesJsonSchemaInClasspath("getTrain.json"));
    }
}
