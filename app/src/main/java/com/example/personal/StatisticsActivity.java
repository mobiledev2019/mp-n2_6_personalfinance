package com.example.personal;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

public class StatisticsActivity extends AppCompatActivity {
    DatabaseHandler databaseHandler;
    PieChart pieChart;
    Spinner spTime;
    static List<String> selects = new ArrayList<String>(Arrays.asList("Hôm nay", "Tháng nay", "Năm nay"));
    @Override
    protected void onCreate(Bundle savingInstanceState) {
        super.onCreate(savingInstanceState);
        setContentView(R.layout.activity_statistics);

        pieChart= (PieChart) findViewById(R.id.piechart);
        pieChart.setRotationEnabled(true);
//        pieChart.setDescription(new Description());
        pieChart.setHoleRadius(35f);
        pieChart.setTransparentCircleAlpha(0);
        pieChart.setCenterText("Thu Chi");
        pieChart.setCenterTextSize(10);
        pieChart.setDescription("Biểu đồ thu chi");
//        pieChart.setDrawEntryLabels(true);

        spTime = (Spinner) findViewById(R.id.spTimeStatistics);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, selects);
        spTime.setAdapter(adapter);



        spTime.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Calendar calendar = Calendar.getInstance();
                int date = calendar.get(Calendar.DATE);
                int month = calendar.get(Calendar.MONTH) + 1;
                int year = calendar.get(Calendar.YEAR);

                String sDate="", sMonth="", sYear="";
                if(date < 10)  sDate = "0" + date;
                if(month < 10) sMonth = "0" + month;
                sYear = year + "";
                String time = "";

                String selectItem = spTime.getSelectedItem().toString();
                if(selectItem.equalsIgnoreCase("Hôm nay")) {
                    time = sDate + "/" + sMonth + "/"+sYear;
                } else if(selectItem.equalsIgnoreCase("Tháng nay")) {
                    time = "/" + sMonth + "/"+sYear;
                } else if(selectItem.equalsIgnoreCase("Năm nay")) {
                    time = "/"+sYear;
                }
                addDataSet(pieChart, time);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


//        pieChart.setOn
    }

    private void addDataSet(PieChart pieChart, String time) {
        databaseHandler = new DatabaseHandler(this);
        databaseHandler.open();
        int amountReceipt  = Integer.parseInt(databaseHandler.getAmountByTypeAndTime("Khoản thu", time));
        int amountPayment  = Integer.parseInt(databaseHandler.getAmountByTypeAndTime("Khoản chi", time));
        amountPayment = -amountPayment;
        ArrayList<Entry> entries = new ArrayList<Entry>();
        ArrayList<String>  list = new ArrayList<String>();
        entries.add(new Entry(amountReceipt, 1));
        entries.add(new Entry(amountPayment, 2));
        list.add("Khoản thu");
        list.add("Khoản chi");
        PieDataSet pieDataSet = new PieDataSet(entries, "Thu chi");

        pieDataSet.setSliceSpace(2);
        pieDataSet.setValueTextSize(12);
        ArrayList<Integer> colors = new ArrayList<>();
//        colors.add(Color.GRAY);
        colors.add(Color.BLUE);
        colors.add(Color.RED);
        pieDataSet.setColors(colors);

        Legend legend = pieChart.getLegend();
        legend.setForm(Legend.LegendForm.CIRCLE);
//        legend.setPosition(Legend.LegendPosition.LEFT_OF_CHART);
//        legend.set
        PieData pieData = new PieData(list, pieDataSet);
        pieChart.setData(pieData);

        pieChart.invalidate();
    }



}
