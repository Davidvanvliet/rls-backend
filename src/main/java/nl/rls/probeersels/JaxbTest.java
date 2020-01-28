package nl.rls.probeersels;

import java.io.StringReader;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import nl.rls.ci.soap.dto.LITechnicalAck;

public class JaxbTest {

	public static void main(String[] args) {
		String xmlString = "<LI_TechnicalAck><ResponseStatus>ACK</ResponseStatus><AckIndentifier>ACKID937cb438-4fb3-429a-bf27-1476818d1742</AckIndentifier><MessageReference><MessageType>TrainRunningInformationMessage</MessageType><MessageTypeVersion>5.1.8</MessageTypeVersion><MessageIdentifier>937cb438-4fb3-429a-bf27-1476818d1742</MessageIdentifier><MessageDateTime>Mon Dec 16 15:11:49 CET 2019</MessageDateTime></MessageReference><Sender>3025</Sender><Recipient>0074</Recipient><RemoteLIName>CCG-LI</RemoteLIName><RemoteLIInstanceNumber>21</RemoteLIInstanceNumber><MessageTransportMechanism>WEBSERVICE</MessageTransportMechanism></LI_TechnicalAck>"; 

		LITechnicalAck technicalAck = unMarshallResponse(xmlString);
		System.out.println(technicalAck.getAckIndentifier());

	}

	private static LITechnicalAck unMarshallResponse(String xmlString) {
		LITechnicalAck technicalAck = null;
		try {
			JAXBContext jaxbContext = JAXBContext.newInstance(LITechnicalAck.class);

			Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
			technicalAck = (LITechnicalAck) jaxbUnmarshaller.unmarshal(new StringReader(xmlString));
			System.out.println(technicalAck.getAckIndentifier());

		} catch (JAXBException e) {
			e.printStackTrace();
		}
		return technicalAck;
	}

}
