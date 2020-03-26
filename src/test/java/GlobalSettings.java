import io.restassured.RestAssured;
import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

public class GlobalSettings implements BeforeAllCallback {

    @Override
    public void beforeAll(ExtensionContext context) throws Exception {
        RestAssured.baseURI = "http://localhost:";
    }
}
