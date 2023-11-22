package hu.domparse.aqyo8l;

import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.StringJoiner;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.util.HashMap;

public class DomReadAQYO8L {
    public static void main(String args[]) {
        try {
            // DocumentFactory inicializálása
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

            // DocumentBuilder inicializálása
            DocumentBuilder builder = factory.newDocumentBuilder();

            File file = new File("XMLTaskAQYO8L\\1.feladat\\XMLAQYO8L.xml");

            // Dokumentum betöltése
            Document doc = builder.parse(file);
            doc.getDocumentElement().normalize();

            // XML adatok kiírása
            System.out.println("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
            System.out.println(
                    "<Aruhaz-beszallito_AQYO8L xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:noNamespaceSchemaLocation=\"XMLSchemaAQYO8L.xsd\">\n");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Elem kiíró metódus
    private static void printElement(String elementName, String content) {
        System.out.println("                <" + elementName + ">" + content + "</" + elementName + ">");
    }

    private static void printCim(Element cimElement) {
        System.out.println("                <" + cimElement.getNodeName() + ">");
        System.out
                .println("                    <" + cimElement.getElementsByTagName("Iranyitoszam").item(0).getNodeName()
                        + ">" + cimElement.getElementsByTagName("Iranyitoszam").item(0).getTextContent() + "</"
                        + cimElement.getElementsByTagName("Iranyitoszam").item(0).getNodeName() + ">");
        System.out.println("                    <" + cimElement.getElementsByTagName("Telepules").item(0).getNodeName()
                + ">" + cimElement.getElementsByTagName("Telepules").item(0).getTextContent() + "</"
                + cimElement.getElementsByTagName("Telepules").item(0).getNodeName() + ">");
        System.out.println("                    <" + cimElement.getElementsByTagName("Utca").item(0).getNodeName() + ">"
                + cimElement.getElementsByTagName("Utca").item(0).getTextContent() + "</"
                + cimElement.getElementsByTagName("Utca").item(0).getNodeName() + ">");
        System.out.println("                    <" + cimElement.getElementsByTagName("Hazszam").item(0).getNodeName()
                + ">" + cimElement.getElementsByTagName("Hazszam").item(0).getTextContent() + "</"
                + cimElement.getElementsByTagName("Hazszam").item(0).getNodeName() + ">");
        System.out.println("                </" + cimElement.getNodeName() + ">");
    }

    // Áruházakat beolvasó metódus
    private static void readAruhaz(Document document) {
        NodeList aruhazList = document.getElementsByTagName("Aruhaz");
        System.out.println("  <Aruhazak>");
        for (int temp = 0; temp < aruhazList.getLength(); temp++) {
            Node node = aruhazList.item(temp);
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                Element aruhazElement = (Element) node;
                String nev = aruhazElement.getElementsByTagName("Nev").item(0).getTextContent();
                String aruhazId = aruhazElement.getAttribute("aruhazid");
                Element cimElement = (Element) aruhazElement.getElementsByTagName("Cim").item(0);

                System.out.println("        <Aruhaz aruhazid=\"" + aruhazId + "\">");
                printElement("Nev", nev);
                printCim(cimElement);
                System.out.println("        </Aruhaz>");
            }

            System.out.println("    </Aruhazak>");
        }
    }

    // Beszállítókat beolvasó metódus
    private static void readBeszallito(Document document) {
        NodeList beszallitoList = document.getElementsByTagName("Beszallito");
        System.out.println("  <Beszallitok>");
        for (int temp = 0; temp < beszallitoList.getLength(); temp++) {
            Node node = beszallitoList.item(temp);
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                Element beszallitoElement = (Element) node;
                String nev = beszallitoElement.getElementsByTagName("Nev").item(0).getTextContent();
                String beszallitoid = beszallitoElement.getAttribute("beszallitoid");
                String termekKategoria = beszallitoElement.getElementsByTagName("Termek_kategoria").item(0)
                        .getTextContent();
                Element atlagosSzallitasiIdoElement = (Element) beszallitoElement
                        .getElementsByTagName("Atlagos_szallitasi_ido").item(0);
                String atlagosSzallitasiIdoString = atlagosSzallitasiIdoElement.getTextContent();
                String atlagosSzallitasiIdoMertekegyseg = atlagosSzallitasiIdoElement.getAttribute("mertekegyseg");

                System.out.println("        <Beszallito beszallitoid=\"" + beszallitoid + "\">");
                printElement("Nev", nev);
                printElement("Termek_kategoria", termekKategoria);
                System.out.println(
                        "            <Atlagos_szallitasi_ido mertekegyseg=\"" + atlagosSzallitasiIdoMertekegyseg
                                + "\">" + atlagosSzallitasiIdoString + "</Atlagos_szallitasi_ido>");
                System.out.println("        </Beszallito>");
            }

            System.out.println("    </Beszallitok>");
        }
    }

    // Áruház-Beszállító kapcsolatokat beolvasó metódus
    private static void readAruhazBeszallito(Document document) {
        NodeList aruhazBeszallitoList = document.getElementsByTagName("Aruhaz-Beszallito");
        for (int temp = 0; temp < aruhazBeszallitoList.getLength(); temp++) {
            Node node = aruhazBeszallitoList.item(temp);
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                Element aruhazBeszallitoElement = (Element) node;
                String atlagos_Rendelt_Arumennyiseg = aruhazBeszallitoElement
                        .getElementsByTagName("Atlagos_Rendelt_Arumennyiseg").item(0).getTextContent();
                String beszallitoid = aruhazBeszallitoElement.getAttribute("beszallitoid");
                String aruhazid = aruhazBeszallitoElement.getAttribute("aruhazid");

                System.out.println("        <Aruhaz-Beszallito aruhazid=\"" + aruhazid + "\" beszallitoid=\""
                        + beszallitoid + "\">");
                printElement("Atlagos_Rendelt_Arumennyiseg", atlagos_Rendelt_Arumennyiseg);
                System.out.println("       </Aruhaz-Beszallito>");
            }
        }
    }

    // Raktárakat beolvasó metódus
    private static void readRaktarak(Document document) {
        NodeList aruhazRaktarTermekList = document.getElementsByTagName("Aruhaz_Raktar_Termek");
        NodeList beszallitoRaktarTermekList = document.getElementsByTagName("Beszallito_Raktar_Termek");

        for (int temp = 0; temp < aruhazBeszallitoList.getLength(); temp++) {
            Node node = aruhazBeszallitoList.item(temp);
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                Element aruhazBeszallitoElement = (Element) node;
                String atlagos_Rendelt_Arumennyiseg = aruhazBeszallitoElement
                        .getElementsByTagName("Atlagos_Rendelt_Arumennyiseg").item(0).getTextContent();
                String beszallitoid = aruhazBeszallitoElement.getAttribute("beszallitoid");
                String aruhazid = aruhazBeszallitoElement.getAttribute("aruhazid");

                System.out.println("        <Aruhaz-Beszallito aruhazid=\"" + aruhazid + "\" beszallitoid=\""
                        + beszallitoid + "\">");
                printElement("Atlagos_Rendelt_Arumennyiseg", atlagos_Rendelt_Arumennyiseg);
                System.out.println("       </Aruhaz-Beszallito>");
            }
        }
    }
}