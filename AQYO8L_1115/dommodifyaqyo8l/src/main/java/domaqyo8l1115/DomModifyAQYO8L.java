package AQYO8L_1115.dommodifyaqyo8l.src.main.java.domaqyo8l1115;

import java.io.File;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.TransformerException;

public class DomModifyAQYO8L {
    public static void main(String[] args) {
        try {
            File inputFile = new File(
                    "AQYO8L_1115\\dommodifyaqyo8l\\src\\main\\java\\domaqyo8l1115\\AQYO8L_kurzusfelvetel.xml");
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(inputFile);
            doc.getDocumentElement().normalize();
            NodeList kurzusNodeList = doc.getElementsByTagName("kurzus");
            for (int i = 0; i < kurzusNodeList.getLength(); i++) {
                Element kurzusNode = (Element) kurzusNodeList.item(i);
                Element oraadoNode = (Element) kurzusNode.getElementsByTagName("oraado").item(0);
                String nyelvAttr = kurzusNode.getAttribute("nyelv");

                if (oraadoNode == null) {
                    oraadoNode = doc.createElement("oraado");
                    oraadoNode.appendChild(doc.createTextNode("Dr. Kovács László"));
                    kurzusNode.appendChild(oraadoNode);
                }

                if (nyelvAttr.equals("angol")) {
                    kurzusNode.setAttribute("nyelv", "német");
                }
            }

            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            DOMSource source = new DOMSource(doc);
            StreamResult consoleResult = new StreamResult(System.out);
            transformer.transform(source, consoleResult);

            File outputFile = new File(
                    "AQYO8L_1115\\dommodifyaqyo8l\\src\\main\\java\\domaqyo8l1115\\kurzusfelvetelModify1AQYO8L.xml");
            OutputStream outputStream = new FileOutputStream(outputFile);
            StreamResult fileResult = new StreamResult(outputStream);
            transformer.transform(source, fileResult);
            outputStream.close();

        } catch (ParserConfigurationException | IOException | TransformerException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}