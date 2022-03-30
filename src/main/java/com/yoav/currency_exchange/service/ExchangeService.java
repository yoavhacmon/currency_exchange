package com.yoav.currency_exchange.service;

import com.yoav.currency_exchange.beans.Exchange;
//import com.yoav.currency_exchange.repository.ExchangeRepo;
import lombok.RequiredArgsConstructor;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;

import static org.hibernate.internal.util.collections.ArrayHelper.toList;

@Service
@RequiredArgsConstructor
public class ExchangeService {
//    private ExchangeRepo exchangeRepo;
    private static final String EXCHANGE_URL = "https://api.exchangerate-api.com/v4/latest/";
    private final RestTemplate restTemplate;

//    private static String getUrlData(String url){
//        StringBuilder stringBuilder = new StringBuilder();
//        HttpURLConnection httpURLConnection = null;
//        try {
//            httpURLConnection = (HttpURLConnection) new URL(url).openConnection();
//            var buf = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
//            String line;
//            while ((line=buf.readLine())!=null){
//                stringBuilder.append(line);
//            }
//        } catch (IOException e) {
//            System.out.println(e.getMessage());;
//        } finally {
//            httpURLConnection.disconnect();
//        }
//        return stringBuilder.toString();
//    }

    public Exchange readJason(String fromCurrency, String toCurrency, double amount){
        try {
            String data = restTemplate.getForObject(EXCHANGE_URL+fromCurrency,String.class);
            JSONObject rates = new JSONObject(data).getJSONObject("rates");
            double value = rates.getDouble(toCurrency);
            return Exchange.builder()
                    .fromCurrency(fromCurrency)
                    .toCurrency(toCurrency)
                    .amount(amount)
                    .result(amount*value)
                    .build();
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Map<String, Object> toMap(JSONObject object) throws JSONException {
        Map<String, Object> map = new HashMap<String, Object>();

        Iterator<String> keysItr = object.keys();
        while(keysItr.hasNext()) {
            String key = keysItr.next();
            Object value = object.get(key);

            if(value instanceof JSONArray) {
                value = toList((JSONArray) value);
            }

            else if(value instanceof JSONObject) {
                value = toMap((JSONObject) value);
            }
            map.put(key, value);
        }
        return map;
    }
}
