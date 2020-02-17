package nl.rls.probeersels;

import org.xml.sax.SAXException;

import javax.xml.XMLConstants;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import java.io.File;
import java.io.IOException;

public class XMLValidation {

    public static void main(String[] args) {

//      System.out.println("EmployeeRequest.xml validates against Employee.xsd? "+validateXMLSchema("src/main/java/nl/rls/probeersels/Employee.xsd", "src/main/java/nl/rls/probeersels/EmployeeRequest.xml"));
//      System.out.println("EmployeeResponse.xml validates against Employee.xsd? "+validateXMLSchema("src/main/java/nl/rls/probeersels/Employee.xsd", "src/main/java/nl/rls/probeersels/EmployeeResponse.xml"));
//      System.out.println("employee.xml validates against Employee.xsd? "+validateXMLSchema("src/main/java/nl/rls/probeersels/Employee.xsd", "src/main/java/nl/rls/probeersels/employee.xml"));
        System.out.println("train_composition_message.xml validates against taf_cat_complete_sector.xsd? " + validateXMLSchema("src/main/resources/taf_cat_complete_sector.xsd", "train_composition_message.xml"));
        System.out.println("TCM_voorbeeld_9001.xml validates against taf_cat_complete_sector.xsd? " + validateXMLSchema("src/main/resources/taf_cat_complete_sector.xsd", "TCM_voorbeeld_9001.xml"));

    }

    public static boolean validateXMLSchema(String xsdPath, String xmlPath) {

        try {
            SchemaFactory factory =
                    SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
            Schema schema = factory.newSchema(new File(xsdPath));
            Validator validator = schema.newValidator();
            validator.validate(new StreamSource(new File(xmlPath)));
        } catch (IOException | SAXException e) {
            System.out.println("Exception: " + e.getMessage());
            return false;
        }
        return true;
    }
}