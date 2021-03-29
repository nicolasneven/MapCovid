package com.example.mapcovid;

import org.jsoup.Jsoup;
import org.jsoup.nodes.*;
import org.jsoup.select.*;
import java.io.IOException;

public class WebScraper {

    private static boolean successful = false;

    public static void main(String[] args) throws IOException {
        printData();
    }

    public static boolean loadWebsite() throws IOException {
        Document doc = Jsoup.connect("http://publichealth.lacounty.gov/media/coronavirus/locations.htm").get();
        return true;
    }

    public static boolean printData() throws IOException {
        Document doc = Jsoup.connect("http://publichealth.lacounty.gov/media/coronavirus/locations.htm").get();
        Elements table = doc.select("table.table.table-striped.table-bordered.table-sm").next();
        // System.out.println(table.html());
        int i = 0;
        for(Element e : table.select("tr")) {
            System.out.println(e.text());
            if (i > 340) break;
            else i++;
        }
        return true;
    }

}
