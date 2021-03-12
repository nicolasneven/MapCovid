package com.example.mapcovid;

import java.net.HttpURLConnection;
import java.net.URLConnection;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.*;
import java.net.URISyntaxException;
import java.util.*;

public class Media {

    public static void main(String args[]) throws IOException, URISyntaxException {
        String bearerToken = System.getenv("BEARER_TOKEN");
        if (null != bearerToken) {
            Map<String, String> rules = new HashMap<>();
            rules.put("covid OR Coronavirus OR Covid-19 place:'los angeles'", "covid news in LA");
            setupRules(bearerToken, rules);
            connectStream(bearerToken);
        }
        else{
            System.out.println("Bearer Token not found");
        }


    }
    //TODO
    private static void connectStream(String bearerToken) throws IOException, URISyntaxException {

    }

    //TODO
    private static void setupRules(String bearerToken, Map<String, String> rules) throws IOException, URISyntaxException {

    }

    //TODO
    private static void createRules(String bearerToken, Map<String, String> rules) throws URISyntaxException, IOException {

    }
    //TODO
    //private static List<String> getRules(String bearerToken) throws URISyntaxException, IOException {
   // }
    private static String getFormattedString(String string, List<String> ids) {
        StringBuilder sb = new StringBuilder();
        if (ids.size() == 1) {
            return String.format(string, "\"" + ids.get(0) + "\"");
        } else {
            for (String id : ids) {
                sb.append("\"" + id + "\"" + ",");
            }
            String result = sb.toString();
            return String.format(string, result.substring(0, result.length() - 1));
        }
    }

    private static String getFormattedString(String string, Map<String, String> rules) {
        StringBuilder sb = new StringBuilder();
        if (rules.size() == 1) {
            String key = rules.keySet().iterator().next();
            return String.format(string, "{\"value\": \"" + key + "\", \"tag\": \"" + rules.get(key) + "\"}");
        } else {
            for (Map.Entry<String, String> entry : rules.entrySet()) {
                String value = entry.getKey();
                String tag = entry.getValue();
                sb.append("{\"value\": \"" + value + "\", \"tag\": \"" + tag + "\"}" + ",");
            }
            String result = sb.toString();
            return String.format(string, result.substring(0, result.length() - 1));
        }
    }

}
