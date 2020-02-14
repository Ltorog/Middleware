package com.gatblac.test.bsx.demobsx.Middleware;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

import ch.qos.logback.core.net.server.Client;
import com.fasterxml.jackson.core.JsonEncoding;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.util.Date;

@Service
public class MiddlewareServices {

    public SymbolsList GetListSymbol(){

        SymbolsList symbolsList = new SymbolsList();
        String stringJson = "";

        List<Symbol> list = new ArrayList<Symbol>();

        String uri = "https://test-gsg.azurewebsites.net/stock/list";

        try {

            RestTemplate restTemplate = new RestTemplate();
            String result = restTemplate.getForObject(uri, String.class);

            ResponseEntity<String> call = restTemplate.getForEntity(uri, String.class);
            stringJson = call.getBody();

            JSONObject jsonObject = new JSONObject(stringJson);
            JSONArray jsonArray = jsonObject.getJSONArray("symbolsList");

            for (int i=0; i<jsonArray.length(); i++) {
                Symbol symbolElement = new Symbol();

                JSONObject jsonObjectElement = (JSONObject) jsonArray.get(i);

                symbolElement.setSymbol(jsonObjectElement.getString("symbol"));
                symbolElement.setName(jsonObjectElement.getString("name"));
                symbolElement.setPrice(Float.parseFloat(jsonObjectElement.getString("price")));

                list.add(symbolElement);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        symbolsList.setSymbolsList(list);
        return symbolsList;
    }

    public List<Historical> GetHistorical(){
        List<Historical> list = new ArrayList<Historical>();

        String uri = "https://test-gsg.azurewebsites.net/stock/historical";
        String stringJson = "";

        try {

            RestTemplate restTemplate = new RestTemplate();
            String result = restTemplate.getForObject(uri, String.class);

            ResponseEntity<String> call = restTemplate.getForEntity(uri, String.class);
            stringJson = call.getBody();

            JSONObject jsonObject = new JSONObject(stringJson);
            JSONArray jsonArray = jsonObject.getJSONArray("historicalStockList");

            for (int i=0; i<jsonArray.length(); i++) {
                Historical historical = new Historical();

                JSONObject jsonObjectElement = (JSONObject) jsonArray.get(i);

                try {
                    JSONArray jsonArrayDetails = jsonObjectElement.getJSONArray("historical");

                    List<Historical.HistoricalDetails> listDetails = new ArrayList<Historical.HistoricalDetails>();

                    for (int j=0; j<jsonArrayDetails.length(); j++) {
                        JSONObject jsonObjectDetail = (JSONObject) jsonArrayDetails.get(j);

                        Historical.HistoricalDetails details = new Historical.HistoricalDetails();

                        DateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
                        Date date = new SimpleDateFormat("yyyy-MM-dd").parse(jsonObjectDetail.getString("date"));//format.parse(jsonObjectDetail.getString("date"));
                        details.setDate(date);

                        float close = Float.parseFloat(jsonObjectDetail.getString("close"));
                        details.setClose(close);

                        listDetails.add(details);
                    }

                    historical.setDetails(listDetails);

                }
                catch (Exception ex){}

                historical.setSymbol(jsonObjectElement.getString("symbol"));
                //historical.setName(jsonObjectElement.getString("name"));
                //historical.setPrice(Float.parseFloat(jsonObjectElement.getString("price")));

                list.add(historical);

            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }
}
