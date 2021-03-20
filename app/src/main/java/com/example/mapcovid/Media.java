package com.example.mapcovid;

import java.net.HttpURLConnection;
import java.net.URL;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.*;
import java.net.URISyntaxException;
import java.util.*;

public class Media {

    public static void main(String args[]) throws IOException, URISyntaxException {
        String bearerToken = "AAAAAAAAAAAAAAAAAAAAAM3iNAEAAAAAClh6pzFnfIy%2BukYn%2FKhdMaB%2Frf4%3DCrZ3YLF7Hd99uGjSQ1xxMa28uPtkXl1SzLVRpgwlzamodY1nBI";
        if (null != bearerToken) {
            Map<String, String> rules = new HashMap<>();
            rules.put("(covid OR Coronavirus OR Covid-19 OR Covid19) AND (Los Angeles)", "covid news in LA");
            setupRules(bearerToken, rules);
            connectStream(bearerToken);
        }
        else {
            System.out.println("Bearer Token not found");
        }

    }
    //This method calls the filtered stream endpoint and streams Tweets from it
    private static void connectStream(String bearerToken) throws IOException, URISyntaxException {
        HttpURLConnection connection = null;
        try {
            URL url = new URL("https://api.twitter.com/2/tweets/search/stream");
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Authorization", String.format("Bearer %s", bearerToken));
            BufferedReader reader = new BufferedReader(new InputStreamReader((connection.getInputStream())));
            String line = reader.readLine();
            while (line != null) {
                System.out.println(line);
            }

        }
        catch(IOException ioe){
            System.out.println(ioe.getMessage());
        }


    }

    //setup rules before streaming starts
    private static void setupRules(String bearerToken, Map<String, String> rules) throws IOException, URISyntaxException {
        List<String> existingRules = getRules(bearerToken);
        if (existingRules.size() > 0) {
            deleteRules(bearerToken, existingRules);
        }
        createRules(bearerToken, rules);
    }

    //creates the rules for filtering
    private static void createRules(String bearerToken, Map<String, String> rules) throws URISyntaxException, IOException {
        HttpURLConnection connection = null;
        try {
            URL url = new URL("https://api.twitter.com/2/tweets/search/stream/rules");
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Authorization", String.format("Bearer %s", bearerToken));
            connection.setRequestProperty("Content-Type", "application/json");
            OutputStreamWriter writer = new OutputStreamWriter(
                    connection.getOutputStream());
            writer.write(getFormattedString("{\"add\": [%s]}", rules));
            writer.close();
        }
        catch(IOException ioe){
            System.out.println(ioe.getMessage());
        }
    }
    //gets the current rules
    private static List<String> getRules(String bearerToken) throws URISyntaxException, IOException {
        List<String> rules = new ArrayList<>();
        HttpURLConnection connection = null;
        try {
            URL url = new URL("https://api.twitter.com/2/tweets/search/stream/rules");
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Authorization", String.format("Bearer %s", bearerToken));
            connection.setRequestProperty("Content-Type", "application/json");
            if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
                    String line;
                    while ((line = bufferedReader.readLine()) != null) {
                        JSONObject json = new JSONObject(line);
                        if (json.length() > 1) {
                            JSONArray array = (JSONArray) json.get("data");
                            for (int i = 0; i < array.length(); i++) {
                                JSONObject jsonObject = (JSONObject) array.get(i);
                                rules.add(jsonObject.getString("id"));
                            }
                        }
                    }
                }
            }
        }
        catch(IOException | JSONException ioe){
            System.out.println(ioe.getMessage());
        }
        return rules;
    }

    //deletes rules
    private static void deleteRules(String bearerToken, List<String> existingRules) throws URISyntaxException, IOException {
        HttpURLConnection connection = null;
        try {
            URL url = new URL("https://api.twitter.com/2/tweets/search/stream/rules");
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Authorization", String.format("Bearer %s", bearerToken));
            connection.setRequestProperty("Content-Type", "application/json");
            OutputStreamWriter writer = new OutputStreamWriter(
                    connection.getOutputStream());
            writer.write(getFormattedString("{ \"delete\": { \"ids\": [%s]}}", existingRules));
            writer.close();
        }
        catch(IOException ioe){
            System.out.println(ioe.getMessage());
        }
    }
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
