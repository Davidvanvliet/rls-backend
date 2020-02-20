package nl.rls;

import org.xml.sax.SAXException;

import javax.xml.XMLConstants;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class XmlValidateTest {

    public static void main(String[] args) {
        try {
            File xsdFile = new File("taf_cat_complete_sector.xsd");
            InputStream xsdStream = new FileInputStream(xsdFile);
            System.out.println("xsdStream: " + xsdStream.toString());

            File xmlFile = new File("train_composition_Message.xml");
            InputStream xmlStream = new FileInputStream(xmlFile);
            System.out.println("xmlStream: " + xmlStream.toString());

            validateAgainstXSD(xmlStream, xsdStream);
        } catch (SAXException | IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    static boolean validateAgainstXSD(InputStream xml, InputStream xsd) throws SAXException, IOException {
        System.out.println("Starting validateAgainstXSD");
        SchemaFactory factory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
        System.out.println("factory.newSchema(new StreamSource(xsd))");
        Schema schema = factory.newSchema(new StreamSource(xsd));
        System.out.println("schema.newValidator()");
        Validator validator = schema.newValidator();
        System.out.println("Just before validating");
        validator.validate(new StreamSource(xml));
        return true;
    }

}
