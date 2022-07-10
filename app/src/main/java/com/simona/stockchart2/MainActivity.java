package com.simona.stockchart2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.anychart.AnyChart;
import com.anychart.AnyChartView;
import com.anychart.chart.common.dataentry.DataEntry;
import com.anychart.chart.common.dataentry.ValueDataEntry;
import com.anychart.charts.Cartesian;
import com.anychart.core.cartesian.series.Line;
import com.anychart.data.Mapping;
import com.anychart.data.Set;
import com.anychart.enums.Anchor;
import com.anychart.enums.MarkerType;
import com.anychart.enums.TooltipPositionMode;
import com.anychart.graphics.vector.Stroke;
import com.simona.stockchart2.pojoclasses.Datum;
import com.simona.stockchart2.pojoclasses.Example;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    EditText inputED;
    Button searchBtn;
    AnyChartView lineChartWindow;
    String ticker;

    Retrofit myRetrofit;
    APIinterface apIinterface;
    RadioGroup daysRadioGrup;

    String API_KEY = BuildConfig.API_KEY ;
    String BASE_URL = "http://api.marketstack.com/v1/";
    String startDate, endDate;

    List<Datum> listOfDatum = new ArrayList<>();
    List<DataEntry> listOfTickerPrices = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();

    }

    private void getHistoricalPrices() {
        listOfDatum.clear();
        listOfTickerPrices.clear();
        Call<Example> callerExample = apIinterface.getPricesForTicker(
                API_KEY,
                ticker,
                startDate,
                endDate);
        callerExample.enqueue(new Callback<Example>() {
            @Override
            public void onResponse(Call<Example> call, Response<Example> response) {
                Example example = response.body();
                listOfDatum = example.getData();

                Cartesian cartesian = AnyChart.line();
                //        cartesian.animation(true);
                cartesian.padding(10d, 20d, 5d, 20d);
                cartesian.crosshair().enabled(true);
                cartesian.crosshair()
                        .yLabel(true)
                        // TODO ystroke
                        .yStroke((Stroke) null, null, null, (String) null, (String) null);

                cartesian.tooltip().positionMode(TooltipPositionMode.POINT);
                cartesian.yAxis(0).title("Price (USD)");
                cartesian.xAxis(0).labels().padding(5d, 5d, 5d, 5d);

                Set set = Set.instantiate();
                Mapping series1Mapping = set.mapAs("{ x: 'x', value: 'value' }");
                Line graphLine = cartesian.line(series1Mapping);

                graphLine.hovered().markers().enabled(true);
                graphLine.hovered().markers()
                        .type(MarkerType.CIRCLE)
                        .size(4d);
                graphLine.tooltip()
                        .position("right")
                        .anchor(Anchor.LEFT_CENTER)
                        .offsetX(5d)
                        .offsetY(5d);
                cartesian.legend().enabled(true);
                cartesian.legend().fontSize(13d);
                cartesian.legend().padding(0d, 0d, 10d, 0d);

                lineChartWindow.setChart(cartesian);

                int delayMilis1 = 100;
                Handler hand = new Handler();
                Runnable runn = new Runnable() {
                    @Override
                    public void run() {
                        for (int i = listOfDatum.size() - 1; i >= 0; i--) {
                            String currentDate = listOfDatum.get(i).getDate().substring(0, 10);
                            listOfTickerPrices.add(new ValueDataEntry(currentDate, listOfDatum.get(i).getClose()));
                        }
                        graphLine.name(ticker.toUpperCase());
                        graphLine.data(listOfTickerPrices);
                        set.data(listOfTickerPrices);
                        hand.postDelayed(this::run, delayMilis1);
                    }
                };
                hand.postDelayed(runn, delayMilis1);

            }

            @Override
            public void onFailure(Call<Example> call, Throwable t) {

            }
        });


    }

    void hideKb() {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(inputED.getWindowToken(), 0);
    }

    void setStartDate_EndDate() {
        startDate = "";
        endDate = "";
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        endDate = sdf.format(cal.getTime());
        switch (daysRadioGrup.getCheckedRadioButtonId()) {
            case R.id.period1dRadioBtn:
                cal.add(Calendar.DATE, -1);
                break;
            case R.id.period10dRadioBtn:
                cal.add(Calendar.DATE, -10);
                break;
            case R.id.period30dRadioBtn:
                cal.add(Calendar.DATE, -30);
                break;
            case R.id.period90dRadioBtn:
                cal.add(Calendar.DATE, -90);
                break;
            case R.id.period12mRadioBtn:
                cal.add(Calendar.DATE, -364);
                break;
        }
        startDate = sdf.format(cal.getTime());

    }

    void initViews() {
        daysRadioGrup = findViewById(R.id.activity_main_period_radiogroup);

        inputED = findViewById(R.id.inputEditText);
        searchBtn = findViewById(R.id.searchButton);
        lineChartWindow = findViewById(R.id.stockChartAnyChart);
        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ticker = inputED.getText().toString().trim();
                hideKb();
                if (daysRadioGrup.getCheckedRadioButtonId() == -1) {
                    Toast.makeText(MainActivity.this, "Selecteaza o perioda!", Toast.LENGTH_SHORT).show();
                    return;
                }
                setStartDate_EndDate();
                getHistoricalPrices();
            }
        });

        myRetrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        apIinterface = myRetrofit.create(APIinterface.class);
    }

}