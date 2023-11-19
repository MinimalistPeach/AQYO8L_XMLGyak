package hu.domparse.aqyo8l;

import java.io.IOException;
import java.io.StringWriter;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class DomModifyAQYO8L {
    public static void start() {
        try {
            // DocumentBuilder inicializálása
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            // Dokumentum beolvasása
            Document document = builder.parse("XMLTaskAQYO8L\\1.feladat\\XMLAQYO8L.xml");

            // Dokumentum módosítása
            modifyNodes(document);

            // Dokumentum kiírása a konzolra a módosítás után
            printXML(document);

        } catch (ParserConfigurationException | SAXException | IOException e) {
            e.printStackTrace();
        }
    }

    private static void printXML(Document document) {
        try {
            // TransformerFactory és Transformer osztályok példányosítása
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();

            // Behúzás beállítása a transformerben
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");

            // StringWriter osztály példányosítása, amiben eltároljuk a dokumentumot
            StringWriter stringWriter = new StringWriter();

            // Dokumentum string-gé alakítása
            transformer.transform(new DOMSource(document), new StreamResult(stringWriter));

            // Dokumentum kiírása a konzolra
            System.out.println(stringWriter.toString());
        } catch (TransformerException e) {
            e.printStackTrace();
        }
    }

    private static void modifyNodes(Document document) {
        // Lekéri az összes Aruhaz node-ot
        NodeList aruhazNodeList = document.getElementsByTagName("Aruhaz");

        // Végigiterál a node-okon
        for (int i = 0; i < aruhazNodeList.getLength(); i++) {
            Node node = aruhazNodeList.item(i);

            if (node.getNodeType() == Node.ELEMENT_NODE) {
                Element element = (Element) node;

                // Lekéri a Nev node-ot
                Node aruhazNevNode = element.getElementsByTagName("Nev").item(0);

                // Módosítja a node értékét
                aruhazNevNode.setTextContent("Aruhaz" + (i + 1));
            }
        }

         // Lekéri az összes Beszallito node-ot
        NodeList beszallitoNodeList = document.getElementsByTagName("Beszallito");

        // Végigiterál a node-okon
        for (int i = 0; i < beszallitoNodeList.getLength(); i++) {
            Node node = beszallitoNodeList.item(i);

            if (node.getNodeType() == Node.ELEMENT_NODE) {
                Element element = (Element) node;

                // Lekéri a Nev node-ot
                Node beszallitoNevNode = element.getElementsByTagName("Nev").item(0);

                // Módosítja a node értékét
                beszallitoNevNode.setTextContent("Beszallito" + (i + 1));
            }
        }
        
    }
}