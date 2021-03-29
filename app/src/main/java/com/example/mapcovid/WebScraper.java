package com.example.mapcovid;

import org.jsoup.Jsoup;
import org.jsoup.nodes.*;
import org.jsoup.select.*;
import java.io.IOException;

public class WebScraper {

    public static void main(String[] args) throws IOException {
        Document doc = Jsoup.connect("http://publichealth.lacounty.gov/media/coronavirus/locations.htm").get();
        Elements table = doc.select("table.table.table-striped.table-bordered.table-sm").next();
        // System.out.println(table.html());
        int i = 0;
        for(Element e : table.select("tr")) {
            System.out.println(e.text());
            if (i > 340) break;
            else i++;
        }
    }

    public static boolean isEqual("Expo Park") {

        return name == _____
    }

}
