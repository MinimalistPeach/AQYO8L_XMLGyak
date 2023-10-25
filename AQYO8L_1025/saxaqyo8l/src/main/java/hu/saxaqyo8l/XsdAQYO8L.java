package hu.saxaqyo8l;

import java.io.File;
import java.io.IOException;

import javax.xml.XMLConstants;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;

import org.xml.sax.SAXException;

public class XsdAQYO8L {
    public static void main(String[] args) {

        try {
            File xml = new File("AQYO8L_1025\\saxaqyo8l\\src\\main\\java\\hu\\saxaqyo8l\\AQYO8L_kurzusfelvetel.xml");
            File xsd = new File("AQYO8L_1025\\saxaqyo8l\\src\\main\\java\\hu\\saxaqyo8l\\AQYO8L_kurzusfelvetel.xsd");

            SchemaFactory factory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
            Schema schema = factory.newSchema(xsd);
            Validator validator = schema.newValidator();
            validator.validate(new StreamSource(xml));
            System.out.println("XSD Validation successful");

        } catch (IOException | SAXException e) {
            System.out.println("Unsuccessful validation");
            System.out.println(e.getMessage());
        }

    }
}
