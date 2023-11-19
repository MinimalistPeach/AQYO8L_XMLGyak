package hu.domparse.aqyo8l;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
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
import org.xml.sax.SAXException;

public class DomReadAQYO8L {
    public static void start() {
        try {
            // DocumentBuilder inicializálása
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            // Dokumentum beolvasása
            Document document = builder.parse("XMLTaskAQYO8L\\1.feladat\\XMLAQYO8L.xml");

            // Dokumentum kiírása a konzolra
            printXML(document);

            // Dokumentum kiírása fájlba
            writeXMLToFile(document, "XMLTaskAQYO8L\\2.feladat\\domparseaqyo8l\\src\\main\\java\\hu\\domparse\\aqyo8l\\XMLAQYO8L_1.xml");
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

    private static void writeXMLToFile(Document doc, String filePath) {
        try {
            // TransformerFactory és Transformer osztályok példányosítása
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();

            // Behúzás beállítása a transformerben
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            // A DOM forrásának beállítása
            DOMSource source = new DOMSource(doc);

            // Üres fájl létrehozása
            File outputFile = new File(filePath);

            // Fájl írási csatornájának létrehozása
            OutputStream outputStream = new FileOutputStream(outputFile);
            StreamResult fileResult = new StreamResult(outputStream);

            // A transformer segítéségével a DOM forrás kiírása a fájlba
            transformer.transform(source, fileResult);
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}