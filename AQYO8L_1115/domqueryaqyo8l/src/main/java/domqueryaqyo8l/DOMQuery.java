package domqueryaqyo8l;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

public class DOMQuery {
    public static void main(String[] args) {
        List<String> kurzusList = new ArrayList<>();
        List<String> hallgatoList = new ArrayList<>();
        List<String> oktatoList = new ArrayList<>();
        try {
            File inputFile = new File(
                    "AQYO8L_1115\\domqueryaqyo8l\\src\\main\\java\\domqueryaqyo8l\\AQYO8L_kurzusfelvetel.xml");
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(inputFile);
            doc.getDocumentElement().normalize();
            NodeList kurzusNodeList = doc.getElementsByTagName("kurzusnev");
            System.out.print("Kurzusnév: [");
            for (int i = 0; i < kurzusNodeList.getLength(); i++) {
                Element kurzusElement = (Element) kurzusNodeList.item(i);
                String kurzus = kurzusElement.getTextContent();
                kurzusList.add(kurzus);
                if (i == kurzusNodeList.getLength() - 1) {
                    System.out.print(kurzus + "]");
                } else {
                    System.out.print(kurzus + ", ");
                }
            }

            System.out.println("");
            NodeList hallgatoNodeList = doc.getElementsByTagName("hallgato");

            Document newDoc = dBuilder.newDocument();
            Element rootElement = newDoc.createElement("hallgatok");
            newDoc.appendChild(rootElement);

            for (int i = 0; i < hallgatoNodeList.getLength(); i++) {
                Element hallgatoElement = (Element) hallgatoNodeList.item(i);
                String hallgato = hallgatoElement.getTextContent();
                hallgatoList.add(hallgato);
                Element hallgatoNode = newDoc.createElement("hallgato");
                hallgatoNode.setAttribute("id", hallgatoElement.getAttribute("id"));
                rootElement.appendChild(hallgatoNode);

                System.out.println("<hallgato id=\"" + hallgatoElement.getAttribute("id") + "\">");

                Element hnevNode = newDoc.createElement("hnev");
                hnevNode.appendChild(newDoc.createTextNode(hallgatoElement.getElementsByTagName("hnev").item(0).getTextContent()));
                hallgatoNode.appendChild(hnevNode);

                System.out.println("<hnev>" + hallgatoElement.getElementsByTagName("hnev").item(0).getTextContent() + "</hnev>");

                Element szulevNode = newDoc.createElement("szulev");
                szulevNode.appendChild(newDoc.createTextNode(hallgatoElement.getElementsByTagName("szulev").item(0).getTextContent()));
                hallgatoNode.appendChild(szulevNode);

                System.out.println("<szulev>" + hallgatoElement.getElementsByTagName("szulev").item(0).getTextContent() + "</szulev>");

                Element szakNode = newDoc.createElement("szak");
                szakNode.setAttribute("evf", hallgatoElement.getElementsByTagName("szak").item(0).getAttributes().getNamedItem("evf").getNodeValue());
                szakNode.appendChild(newDoc.createTextNode(hallgatoElement.getElementsByTagName("szak").item(0).getTextContent()));
                hallgatoNode.appendChild(szakNode);

                System.out.println("<szak evf=\"" + hallgatoElement.getElementsByTagName("szak").item(0).getAttributes().getNamedItem("evf").getNodeValue() + "\">" + hallgatoElement.getElementsByTagName("szak").item(0).getTextContent() + "</szak>");
            }

            System.out.println("</hallgato>");

            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(newDoc);
            StreamResult result = new StreamResult(new File("AQYO8L_1115\\domqueryaqyo8l\\src\\main\\java\\domqueryaqyo8l\\AQYO8L_hallgato.xml"));
            transformer.transform(source, result);

            NodeList oktatoNodeList = doc.getElementsByTagName("oktato");
            for (int i = 0; i < oktatoNodeList.getLength(); i++) {
                Element oktatoElement = (Element) oktatoNodeList.item(i);
                String oktato = oktatoElement.getTextContent();
                oktatoList.add(oktato);
                System.out.println(oktato);
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}