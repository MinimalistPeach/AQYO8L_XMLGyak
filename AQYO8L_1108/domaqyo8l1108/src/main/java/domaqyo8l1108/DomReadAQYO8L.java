package domaqyo8l1108;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;
import org.w3c.dom.Node;

public class DomReadAQYO8L {

    public static void main(String[] args) {
        File xml = new File(
                "AQYO8L_1108\\domaqyo8l1108\\src\\main\\java\\domaqyo8l1108\\AQYO8L_kurzusfelvetel.xml");

        try {
            SAXParserFactory saxParserFactory = SAXParserFactory.newInstance();
            SAXParser saxParser = saxParserFactory.newSAXParser();
            DomHandler handler = new DomHandler();
            saxParser.parse(xml, handler);
        } catch (ParserConfigurationException | SAXException | IOException e) {
            e.printStackTrace();
        }
    }
}

class DomHandler extends DefaultHandler {

    private int indent = 0;

    private String formatAttributes(Attributes attributes) {
        int attrLength = attributes.getLength();
        if (attrLength == 0)
            return "";

        StringBuilder sb = new StringBuilder(" ");
        for (int i = 0; i < attrLength; i++) {
            sb.append(attributes.getLocalName(i) + "=" + attributes.getValue(i));
            if (i < attrLength - 1)
                sb.append(" ");
        }
        return sb.toString();

    }

    private void indent() {
        for (int i = 0; i < indent; i++) {
            System.out.print("  ");
        }
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) {
        if (qName != "kurzus") {
            indent++;
            indent();
        }
        System.out.print("<" + qName + formatAttributes(attributes) + ">");
        if (qName == "kurzus" || qName == "AQYO8L_kurzusfelvetel" || qName == "hallgato") {
            System.out.println();
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName) {
        System.out.print("</" + qName + ">\n");
        indent--;
        indent();
    }

    @Override
    public void characters(char[] ch, int start, int length) {
        String chars = new String(ch, start, length).trim();
        if (!chars.isEmpty()) {
            System.out.print(chars);
        }
    }
}
