//package com.example.registrationlogindemo.util;
//
//import com.fasterxml.jackson.core.type.TypeReference;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import org.json.simple.JSONArray;
//import org.json.simple.JSONObject;
//import org.json.simple.parser.ParseException;
//
//import java.io.IOException;
//import java.io.InputStream;
//import java.net.URL;
//import java.nio.charset.Charset;
//import java.nio.charset.StandardCharsets;
//import java.util.List;
//
//public class JSONParser {
//
//    private static final String TICKERS_URL = "https://api.coinlore.net/api/tickers/";
//
//    public static List<Coin> getCoinsListFromUrl() {
//        List<Coin> coinsList = null;
//        try {
//            String tickersJson = URLReader(new URL(TICKERS_URL), StandardCharsets.UTF_8);
//            // Считываем json
//            Object obj = new org.json.simple.parser.JSONParser().parse(tickersJson);
//            // Кастим obj в JSONObject
//            JSONObject rootJsonObj = (JSONObject) obj;
//
//            JSONArray jsonArray = (JSONArray) rootJsonObj.get("data");
//
//            ObjectMapper mapper = new ObjectMapper();
//
//            coinsList = mapper.readValue(String.valueOf(jsonArray), new TypeReference<>() {
//            });
//
//            return coinsList;
//
//        } catch (IOException | ParseException e) {
//            throw new RuntimeException(e);
//        }
//    }
//
//    public static String URLReader(URL url, Charset encoding) throws IOException {
//        try (InputStream in = url.openStream())
//        {
//            byte[] bytes = in.readAllBytes();
//            return new String(bytes, encoding);
//        }
//    }
//
//
//}
