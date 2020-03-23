import io.restassured.response.ValidatableResponse;
import nl.rls.Main;
import nl.rls.ci.url.BaseURL;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;

import static io.restassured.RestAssured.given;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = Main.class)
public class CiControllerIT {
    @LocalServerPort
    int randomServerPort;

    @Test
    public void getMessages() {
        ValidatableResponse response = given().when().get("http://localhost:" + randomServerPort + BaseURL.BASE_PATH + "/messages").then();
        response.assertThat().statusCode(HttpStatus.OK.value());

    }

    @Test
    public void getMessage() {
        ValidatableResponse response = given().when().get("http://localhost:" + randomServerPort + BaseURL.BASE_PATH + "/messages/1").then();
        response.assertThat().statusCode(HttpStatus.OK.value());
    }

    @Test
    public void createMessage() {
        String xmlMessage = "<TrainCompositionMessage xmlns:ns2=\"http://taf-jsg.info/schemes\">\n" +
                "    <MessageHeader>\n" +
                "        <MessageReference>\n" +
                "            <MessageType>3003</MessageType>\n" +
                "            <MessageTypeVersion>2.1.6</MessageTypeVersion>\n" +
                "            <MessageIdentifier>Vrije tekst?</MessageIdentifier>\n" +
                "            <MessageDateTime>2019-12-14T22:11:26.582+01:00</MessageDateTime>\n" +
                "        </MessageReference>\n" +
                "        <SenderReference>test bericht</SenderReference>\n" +
                "        <Sender>9001</Sender>\n" +
                "        <Recipient>84</Recipient>\n" +
                "    </MessageHeader>\n" +
                "    <MessageStatus>1</MessageStatus>\n" +
                "    <TransportOperationalIdentifiers>\n" +
                "        <ObjectType>TR</ObjectType>\n" +
                "        <Company>3502</Company>\n" +
                "        <Core>041350222700</Core>\n" +
                "        <Variant>00</Variant>\n" +
                "        <TimetableYear>2019</TimetableYear>\n" +
                "        <StartDate>2019-12-14+01:00</StartDate>\n" +
                "    </TransportOperationalIdentifiers>\n" +
                "    <OperationalTrainNumberIdentifier>\n" +
                "        <OperationalTrainNumber>123456789</OperationalTrainNumber>\n" +
                "        <ScheduledTimeAtHandover>2019-12-14T22:11:26.373+01:00</ScheduledTimeAtHandover>\n" +
                "    </OperationalTrainNumberIdentifier>\n" +
                "    <OperationalTrainNumber>123456789</OperationalTrainNumber>\n" +
                "    <TransfereeIM>84</TransfereeIM>\n" +
                "    <TrainCompositionJourneySection>\n" +
                "        <JourneySection>\n" +
                "            <JourneySectionOrigin>\n" +
                "                <CountryCodeISO>NL</CountryCodeISO>\n" +
                "                <LocationPrimaryCode>621</LocationPrimaryCode>\n" +
                "                <PrimaryLocationName>Utrecht Centraal</PrimaryLocationName>\n" +
                "            </JourneySectionOrigin>\n" +
                "            <JourneySectionDestination>\n" +
                "                <CountryCodeISO>NL</CountryCodeISO>\n" +
                "                <LocationPrimaryCode>263</LocationPrimaryCode>\n" +
                "                <PrimaryLocationName>Groningen</PrimaryLocationName>\n" +
                "            </JourneySectionDestination>\n" +
                "            <ResponsibilityActualSection>\n" +
                "                <ResponsibleRU>3502</ResponsibleRU>\n" +
                "                <ResponsibleIM>84</ResponsibleIM>\n" +
                "            </ResponsibilityActualSection>\n" +
                "            <ResponsibilityNextSection>\n" +
                "                <ResponsibleRU>3502</ResponsibleRU>\n" +
                "                <ResponsibleIM>84</ResponsibleIM>\n" +
                "            </ResponsibilityNextSection>\n" +
                "        </JourneySection>\n" +
                "        <TrainRunningData>\n" +
                "            <ExceptionalGaugingInd>false</ExceptionalGaugingInd>\n" +
                "            <DangerousGoodsIndicator>false</DangerousGoodsIndicator>\n" +
                "            <Activities/>\n" +
                "        </TrainRunningData>\n" +
                "        <LocoIdent>\n" +
                "            <TractionType>11</TractionType>\n" +
                "            <LocoTypeNumber>123456</LocoTypeNumber>\n" +
                "            <LocoNumber>928422031023</LocoNumber>\n" +
                "            <TractionMode>11</TractionMode>\n" +
                "            <DriverIndication>1</DriverIndication>\n" +
                "            <TractionPositionInTrain>1</TractionPositionInTrain>\n" +
                "        </LocoIdent>\n" +
                "        <LocoIdent>\n" +
                "            <TractionType>11</TractionType>\n" +
                "            <LocoTypeNumber>123455</LocoTypeNumber>\n" +
                "            <LocoNumber>92842203456</LocoNumber>\n" +
                "            <TractionMode>11</TractionMode>\n" +
                "            <DriverIndication>0</DriverIndication>\n" +
                "            <TractionPositionInTrain>2</TractionPositionInTrain>\n" +
                "        </LocoIdent>\n" +
                "        <LivestockOrPeopleIndicator>0</LivestockOrPeopleIndicator>\n" +
                "        <WagonData>\n" +
                "            <WagonNumberFreight>335249561341</WagonNumberFreight>\n" +
                "            <WagonTrainPosition>1</WagonTrainPosition>\n" +
                "            <WagonOperationalData>\n" +
                "                <BrakeType>G</BrakeType>\n" +
                "                <BrakeWeight>10000</BrakeWeight>\n" +
                "                <WagonMaxSpeed>100</WagonMaxSpeed>\n" +
                "                <TotalLoadWeight>13000</TotalLoadWeight>\n" +
                "            </WagonOperationalData>\n" +
                "            <WagonTechData>\n" +
                "                <LengthOverBuffers>964</LengthOverBuffers>\n" +
                "                <WagonNumberOfAxles>2</WagonNumberOfAxles>\n" +
                "                <HandBrakeBrakedWeight>0</HandBrakeBrakedWeight>\n" +
                "                <WagonWeightEmpty>10340</WagonWeightEmpty>\n" +
                "            </WagonTechData>\n" +
                "        </WagonData>\n" +
                "    </TrainCompositionJourneySection>\n" +
                "</TrainCompositionMessage>";
        ValidatableResponse response = given().body(xmlMessage).when().post("http://localhost:" + randomServerPort + BaseURL.BASE_PATH + "/messages").then();
        response.assertThat().statusCode(HttpStatus.CREATED.value());
    }
}
