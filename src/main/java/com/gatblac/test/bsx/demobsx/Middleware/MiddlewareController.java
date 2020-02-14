package com.gatblac.test.bsx.demobsx.Middleware;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.List;

@RestController
@CrossOrigin(origins = "*")
public class MiddlewareController {

    @Autowired
    private MiddlewareServices middlewareServices;

    @GetMapping(value = "/list", produces = MediaType.APPLICATION_JSON_VALUE)
    public SymbolsList GetList() throws JSONException {

        SymbolsList list = middlewareServices.GetListSymbol();

        return list;
    }

    @GetMapping(value = "/historical", produces = MediaType.APPLICATION_JSON_VALUE)
    public HistoricalStockList GetHistorical() throws JSONException {

        HistoricalStockList historicalStockList = new HistoricalStockList();
        List<Historical> list = middlewareServices.GetHistorical();

        historicalStockList.setHistoricalStockList(list);

        return historicalStockList;

    }
}
