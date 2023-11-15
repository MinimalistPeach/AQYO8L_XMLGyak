package domaqyo8l1108;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;
import org.w3c.dom.Node;

public class DomReadAQYO8L {

    public static void main(String[] args) {
        try {
            File inputFile = new File("AQYO8L_1108\\\\domaqyo8l1108\\\\src\\\\main\\\\java\\\\domaqyo8l1108\\\\AQYO8L_kurzusfelvetel.xml");
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(inputFile);
            doc.getDocumentElement().normalize();

            System.out.println(doc.getDocumentElement().getNodeName());

            NodeList seminarList = doc.getElementsByTagName("kurzus");

            for (int temp = 0; temp < seminarList.getLength(); temp++) {
                Node seminarNode = seminarList.item(temp);

                if (seminarNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element seminarElement = (Element) seminarNode;

                    String id = seminarElement.getAttribute("id");
                    String lang = seminarElement.getAttribute("nyelv");

                    System.out.println("<"+seminarElement.getNodeName()+" id=\""+id+"\" nyelv=\""+lang+"\">");

                    System.out.println("<"+seminarElement.getNodeName()+">"+seminarElement.getTextContent()+"</"+seminarElement.getNodeName()+">");

                    System.out.println(
                            "Kurzusnév: " + seminarElement.getElementsByTagName("kurzusnev").item(0).getTextContent());
                    System.out.println(
                            "Kredit: " + seminarElement.getElementsByTagName("kredit").item(0).getTextContent());

                    System.out.println("Hely: " + seminarElement.getElementsByTagName("hely").item(0).getTextContent());
                    System.out.println(
                            "Időpont: " + seminarElement.getElementsByTagName("idopont").item(0).getTextContent());
                    System.out.println(
                            "Oktató: " + seminarElement.getElementsByTagName("oktato").item(0).getTextContent());

                    if (seminarElement.hasAttribute("jovahagyas"))
                        System.out.println("Jóváhagyás: " + seminarElement.getAttribute("jovahagyas"));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
