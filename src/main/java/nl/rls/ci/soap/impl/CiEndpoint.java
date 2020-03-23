package nl.rls.ci.soap.impl;

import java.io.StringWriter;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import nl.rls.ci.soapinterface.ObjectFactory;
import nl.rls.ci.soapinterface.UICMessage;
import nl.rls.ci.soapinterface.UICMessageResponse;
import nl.rls.ci.soapinterface.UICReceiveMessage;

@Endpoint
public class CiEndpoint implements UICReceiveMessage {
	private static final Logger log = LoggerFactory.getLogger(CiEndpoint.class);
	private static final String NAMESPACE_URI = "http://uic.cc.org/UICMessage";

	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "UICMessage")
	@ResponsePayload
	public JAXBElement<UICMessageResponse> getMessage(@RequestPayload JAXBElement<UICMessage> request) {
		JAXBContext jaxbContext;
		try {
			jaxbContext = JAXBContext.newInstance(UICMessage.class);
			Marshaller jaxbMarshaller = jaxbContext.createMarshaller();		// output pretty printed
			jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			StringWriter sw = new StringWriter();
			jaxbMarshaller.marshal(request, sw);
			String xmlString = sw.toString();
			System.out.println(xmlString);

		} catch (JAXBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		ObjectFactory factory = new ObjectFactory();
		JAXBElement<UICMessageResponse> response = factory.createUICMessageResponse(new UICMessageResponse());
		String technicalAck = "           <LI_TechnicalAck>\n" + 
				"               <ResponseStatus>NACK</ResponseStatus>\n" + 
				"               <AckIndentifier>ACKIDSOAP bericht van Berend</AckIndentifier>\n" + 
				"               <MessageReference>\n" + 
				"                  <MessageType>TrainCompositionMessage</MessageType>\n" + 
				"                  <MessageTypeVersion>2.1.6</MessageTypeVersion>\n" + 
				"                  <MessageIdentifier>SOAP bericht van Berend</MessageIdentifier>\n" + 
				"                  <MessageDateTime>2019-12-16T11:17:24.444</MessageDateTime>\n" + 
				"               </MessageReference>\n" + 
				"               <Sender>9001</Sender>\n" + 
				"               <Recipient>84</Recipient>\n" + 
				"               <RemoteLIName>ci-bpr.prorail.nl</RemoteLIName>\n" + 
				"               <RemoteLIInstanceNumber>01</RemoteLIInstanceNumber>\n" + 
				"               <MessageTransportMechanism>WEBSERVICE</MessageTransportMechanism>\n" + 
				"            </LI_TechnicalAck>\n" + 
				"";
		response.getValue().setReturn(technicalAck);
		log.info("getMessage (UICMessageResponse): "+response.toString());
		return response;
	}

	@Override
	public UICMessageResponse uicMessage(UICMessage parameters, String messageIdentifier, String messageLiHost,
			boolean compressed, boolean encrypted, boolean signed) {
		ObjectFactory factory = new ObjectFactory();
		JAXBElement<UICMessageResponse> response = factory.createUICMessageResponse(new UICMessageResponse());
		String technicalAck = "           <LI_TechnicalAck>\n" + 
				"               <ResponseStatus>NACK</ResponseStatus>\n" + 
				"               <AckIndentifier>ACKIDSOAP bericht van Berend</AckIndentifier>\n" + 
				"               <MessageReference>\n" + 
				"                  <MessageType>TrainCompositionMessage</MessageType>\n" + 
				"                  <MessageTypeVersion>2.1.6</MessageTypeVersion>\n" + 
				"                  <MessageIdentifier>SOAP bericht van Berend</MessageIdentifier>\n" + 
				"                  <MessageDateTime>2019-12-16T11:17:24.444</MessageDateTime>\n" + 
				"               </MessageReference>\n" + 
				"               <Sender>9001</Sender>\n" + 
				"               <Recipient>84</Recipient>\n" + 
				"               <RemoteLIName>ci-bpr.prorail.nl</RemoteLIName>\n" + 
				"               <RemoteLIInstanceNumber>01</RemoteLIInstanceNumber>\n" + 
				"               <MessageTransportMechanism>WEBSERVICE</MessageTransportMechanism>\n" + 
				"            </LI_TechnicalAck>\n" + 
				"";
		response.getValue().setReturn(technicalAck);
		log.info("getMessage (UICMessageResponse): "+response.toString());
		return response.getValue();
	}

}
