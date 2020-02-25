import nl.rls.Main;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;

import java.util.List;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = Main.class)
public class CiControllerIT {
    @LocalServerPort
    int randomServerPort;
    @Autowired
    private TestRestTemplate testRestTemplate;

    @Test
    public void getMessages() {
        ResponseEntity<List> ciDtos = testRestTemplate.getForEntity("http://localhost:" + randomServerPort + "/api/v1/messages", List.class);
        System.out.println(ciDtos);

    }
}
