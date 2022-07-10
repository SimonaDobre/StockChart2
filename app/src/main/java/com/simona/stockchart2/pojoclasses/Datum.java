package com.simona.stockchart2.pojoclasses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Datum {

    @SerializedName("open")
    @Expose
    private Double open;


    @SerializedName("high")
    @Expose
    private Double high;


    @SerializedName("low")
    @Expose
    private Double low;


    @SerializedName("close")
    @Expose
    private Double close;


    @SerializedName("volume")
    @Expose
    private Integer volume;


    @SerializedName("adj_high")
    @Expose
    private Object adjHigh;


    @SerializedName("adj_low")
    @Expose
    private Object adjLow;


    @SerializedName("adj_close")
    @Expose
    private Double adjClose;


    @SerializedName("adj_open")
    @Expose
    private Object adjOpen;


    @SerializedName("adj_volume")
    @Expose
    private Object adjVolume;


    @SerializedName("split_factor")
    @Expose
    private Integer splitFactor;


    @SerializedName("dividend")
    @Expose
    private Integer dividend;


    @SerializedName("symbol")
    @Expose
    private String symbol;


    @SerializedName("exchange")
    @Expose
    private String exchange;


    @SerializedName("date")
    @Expose
    private String date;


    public Double getOpen() {
        return open;
    }

    public void setOpen(Double open) {
        this.open = open;
    }

    public Double getHigh() {
        return high;
    }

    public void setHigh(Double high) {
        this.high = high;
    }

    public Double getLow() {
        return low;
    }

    public void setLow(Double low) {
        this.low = low;
    }

    public Double getClose() {
        return close;
    }

    public void setClose(Double close) {
        this.close = close;
    }

    public Integer getVolume() {
        return volume;
    }

    public void setVolume(Integer volume) {
        this.volume = volume;
    }

    public Object getAdjHigh() {
        return adjHigh;
    }

    public void setAdjHigh(Object adjHigh) {
        this.adjHigh = adjHigh;
    }

    public Object getAdjLow() {
        return adjLow;
    }

    public void setAdjLow(Object adjLow) {
        this.adjLow = adjLow;
    }

    public Double getAdjClose() {
        return adjClose;
    }

    public void setAdjClose(Double adjClose) {
        this.adjClose = adjClose;
    }

    public Object getAdjOpen() {
        return adjOpen;
    }

    public void setAdjOpen(Object adjOpen) {
        this.adjOpen = adjOpen;
    }

    public Object getAdjVolume() {
        return adjVolume;
    }

    public void setAdjVolume(Object adjVolume) {
        this.adjVolume = adjVolume;
    }

    public Integer getSplitFactor() {
        return splitFactor;
    }

    public void setSplitFactor(Integer splitFactor) {
        this.splitFactor = splitFactor;
    }

    public Integer getDividend() {
        return dividend;
    }

    public void setDividend(Integer dividend) {
        this.dividend = dividend;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getExchange() {
        return exchange;
    }

    public void setExchange(String exchange) {
        this.exchange = exchange;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }


}
