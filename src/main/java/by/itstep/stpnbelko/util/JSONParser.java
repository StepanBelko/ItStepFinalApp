package by.itstep.stpnbelko.util;

import by.itstep.stpnbelko.entity.Coin;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.List;

import static by.itstep.stpnbelko.util.ConnectionTest.isReallyOnline;

public class JSONParser {

    private static final String TICKERS_URL = "https://api.coinlore.net/api/tickers/";

    public static List<Coin> getCoinsListFromUrl() {
        if (!isReallyOnline()) {
            return null;
        }
        List<Coin> coinsList = null;
        try {
            String tickersJson = URLReader(new URL(TICKERS_URL), StandardCharsets.UTF_8);
            // Считываем json
            Object obj = new org.json.simple.parser.JSONParser().parse(tickersJson);

            // Кастим obj в JSONObject
            JSONObject rootJsonObj = (JSONObject) obj;

            JSONArray jsonArray = (JSONArray) rootJsonObj.get("data");
            
            ObjectMapper mapper = new ObjectMapper();


            coinsList = mapper.readValue(String.valueOf(jsonArray), new TypeReference<List<Coin>>() {
            });
            

//            coinsList = (List<Coin>) mapper.readValue(String.valueOf(jsonArray), Coin.class);

            return coinsList;

        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        } catch (JsonMappingException e) {
            throw new RuntimeException(e);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public static String URLReader(URL url, Charset encoding) {
        try (InputStream in = url.openStream()) {
            byte[] bytes = in.readAllBytes();
            return new String(bytes, encoding);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {

    }


}
