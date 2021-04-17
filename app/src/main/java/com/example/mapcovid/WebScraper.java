package com.example.mapcovid;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.*;
import javax.xml.parsers.*;
import java.io.*;
import org.w3c.dom.*;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

public class WebScraper {

    public static void main(String[] args) throws IOException {
        int[][] data = getData();
        for (int m = 0; m < 6; m++) {
            for (int n = 0; n < 4; n++) {
                // System.out.print(data[m][n] + " ");
            }
            // System.out.println();
        }
        updateKML(data);
    }

    public static boolean loadWebsite() throws IOException {
        Document doc = Jsoup.connect("http://publichealth.lacounty.gov/media/coronavirus/locations.htm").get();
        return true;
    }

    public static int[][] getData() throws IOException {
        Document doc = Jsoup.connect("http://publichealth.lacounty.gov/media/coronavirus/locations.htm").get();
        Elements table = doc.select("table.table.table-striped.table-bordered.table-sm").next();
        // System.out.println(table.html());
        int[][] data = new int[6][4];
        int i = 0, j = 0;
        for (Element e : table.select("tr")) {
            // adams-normandie, exposition park, harvard heights, jefferson park, pico-union, university park
            if (i == 87 || i == 123 || i == 135 || i == 142 || i == 172 || i == 201) {
                System.out.println(e.text());
                String[] split = e.text().split(" ");
                int[] city;
                int l = 0;
                for (int k = split.length - 4; k < split.length; k++) {
                    data[j][l] = Integer.parseInt(split[k]);
                    // System.out.println(data[j][l]);
                    l++;
                }
                j++;
            } else if (i > 340) break;
            i++;
        }
        return data;
    }

    public static boolean updateKML(int[][] data) {
        try {
            File inputFile = new File("../MapCovid/app/src/main/res/raw/legend.kml");
            // File inputFile = new File(context.getFilesDir(), "legend.kml");
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            org.w3c.dom.Document doc = dBuilder.parse(inputFile);
            ((org.w3c.dom.Document) doc).getDocumentElement().normalize();
            // System.out.println("Root element :" + ((org.w3c.dom.Document) doc).getDocumentElement().getNodeName());
            NodeList values = ((org.w3c.dom.Document) doc).getElementsByTagName("value");
            System.out.println("----------------------------");
            int city = 0;
            for (int i = 0; i < values.getLength(); i++) {
                Node node = values.item(i); // city stats
                // System.out.print(i + " ");
                // System.out.println(node.getTextContent());
                if (node.getNodeType() == Node.ELEMENT_NODE) { // update data here
                    org.w3c.dom.Element e = (org.w3c.dom.Element) node;
                    if (i == 1 || i == 4 || i == 7 || i == 10 || i == 13 || i == 16) {
                        e.setTextContent(Integer.toString(data[city][0]));
                    } else if (i == 2 || i == 5 || i == 8 || i == 11 || i == 14 || i == 17) {
                        e.setTextContent(Integer.toString(data[city][2]));
                        city++;
                    }
                    // System.out.println(node.getTextContent());
                }
            }

            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new File("../MapCovid/app/src/main/res/raw/legend.kml"));
            transformer.transform(source, result);


        } catch (Exception e) {
            e.printStackTrace();
        }

        return true;
    }

}
