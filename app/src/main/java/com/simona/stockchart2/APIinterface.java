package com.simona.stockchart2;

import com.simona.stockchart2.pojoclasses.Example;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface APIinterface {

    @GET("eod")
    Call<Example> getPricesForTicker(
            @Query("access_key") String access_key,
            @Query("symbols") String symbols,
            @Query("date_from") String date_from,
            @Query("date_to") String date_to
            );

}

