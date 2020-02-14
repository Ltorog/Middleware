package com.gatblac.test.bsx.demobsx.Middleware;

import java.util.Date;
import java.util.List;

public class Historical {


    public static class HistoricalDetails{
        private Date date;
        private float close;

        public Date getDate(){
            return date;
        }

        public void setDate(Date date){
            this.date = date;
        }

        public float getClose(){
            return close;
        }

        public void setClose(float close){
            this.close = close;
        }
    }

    private String symbol;
    private List<HistoricalDetails> details;

    public String getSymbol(){
        return symbol;
    }

    public void setSymbol(String symbol){
        this.symbol = symbol;
    }

    public List<HistoricalDetails> getDetails(){
        return details;
    }

    public void setDetails(List<HistoricalDetails> details){
        this.details = details;
    }

}

