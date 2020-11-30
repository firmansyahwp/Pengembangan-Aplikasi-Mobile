package com.nocturnal.healtylife.ui.statistic;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.nocturnal.healtylife.R;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class StatisticFragment extends Fragment {
    private final String BASE_URL = "https://covid19.bandarlampungkota.go.id/api/";

    private StatisticViewModel mViewModel;

    private View view;
    private TextView text_jml_pdp;
    private TextView text_jml_odp;
    private TextView text_jml_otg;
    private TextView text_jml_sembuh;
    private TextView text_jml_negatif;
    private TextView text_jml_positif;
    private TextView text_jml_meninggal;
    private PieChart pieChart;

    public static StatisticFragment newInstance() {
        return new StatisticFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.statistic_fragment, container, false);
        initialize();
        loadData();
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(StatisticViewModel.class);
        // TODO: Use the ViewModel
    }

    private void initialize() {
        text_jml_pdp = view.findViewById(R.id.text_jml_pdp);
        text_jml_odp = view.findViewById(R.id.text_jml_odp);
        text_jml_otg = view.findViewById(R.id.text_jml_otg);
        text_jml_sembuh = view.findViewById(R.id.text_jml_sembuh);
        text_jml_negatif = view.findViewById(R.id.text_jml_negatif);
        text_jml_positif = view.findViewById(R.id.text_jml_positif);
        text_jml_meninggal = view.findViewById(R.id.text_jml_meninggal);
        pieChart = view.findViewById(R.id.pie_chart);

        setPieChart();
    }

    private void loadData() {
        OkHttpClient okHttpClient = new OkHttpClient.Builder().build();
        Gson gson = new GsonBuilder().setLenient().create();
        Retrofit retrofit = new Retrofit.Builder().baseUrl(BASE_URL).client(okHttpClient).addConverterFactory(GsonConverterFactory.create(gson)).build();

        Covid19Api covid19Api = retrofit.create(Covid19Api.class);

        Call<Attributes> call = covid19Api.getAttributes();

        call.enqueue(new Callback<Attributes>() {
            @Override
            public void onResponse(Call<Attributes> call, Response<Attributes> response) {
                if (!response.isSuccessful()) {
                    Log.e("GET DATA NOT SUCCESS", String.valueOf(response.code()));
                    return;
                }

                Attributes attributes = response.body();
                List<Statistic> statistics = attributes.getAttributes();
                Statistic statistic = statistics.get(0);

                setData(statistic);
                setPieData(statistic);

                Log.d("GET DATA SUCCESS", String.valueOf(statistic.getJml_odp()));
            }

            @Override
            public void onFailure(Call<Attributes> call, Throwable t) {
                Log.e("GET DATA ERROR", t.getMessage());
            }
        });
    }

    private void setData(Statistic statistic) {
        text_jml_pdp.setText(String.valueOf(statistic.getJml_pdp()));
        text_jml_odp.setText(String.valueOf(statistic.getJml_odp()));
        text_jml_otg.setText(String.valueOf(statistic.getJml_otg()));
        text_jml_sembuh.setText(String.valueOf(statistic.getJml_sembuh()));
        text_jml_negatif.setText(String.valueOf(statistic.getJml_negatif()));
        text_jml_positif.setText(String.valueOf(statistic.getJml_positif()));
        text_jml_meninggal.setText(String.valueOf(statistic.getJml_meninggal()));
    }

    private void setPieChart() {
        pieChart.setUsePercentValues(true);
        pieChart.animateY(1000, Easing.EaseInOutCubic);
        pieChart.getDescription().setEnabled(false);
    }

    private void setPieData(Statistic statistic) {
        int sum = statistic.getJml_pdp() + statistic.getJml_odp() + statistic.getJml_otg() + statistic.getJml_sembuh() + statistic.getJml_negatif() + statistic.getJml_positif() + statistic.getJml_meninggal();
        float fsum = Float.valueOf(sum);

        float percent_pdp = (statistic.getJml_pdp()/fsum)*100;
        float percent_odp = (statistic.getJml_odp()/fsum)*100;
        float percent_otg = (statistic.getJml_otg()/fsum)*100;
        float percent_sembuh = (statistic.getJml_sembuh()/fsum)*100;
        float percent_negatif = (statistic.getJml_negatif()/fsum)*100;
        float percent_positif = (statistic.getJml_positif()/fsum)*100;
        float percent_meninggal = (statistic.getJml_meninggal()/fsum)*100;

        List<PieEntry> entries = new ArrayList<>();
        entries.add(new PieEntry(percent_pdp, "PDP"));
        entries.add(new PieEntry(percent_odp, "ODP"));
        entries.add(new PieEntry(percent_otg, "OTG"));
        entries.add(new PieEntry(percent_sembuh, "SEMBUH"));
        entries.add(new PieEntry(percent_negatif, "NEGATIF"));
        entries.add(new PieEntry(percent_positif, "POSITIF"));
        entries.add(new PieEntry(percent_meninggal, "MENINGGAL"));

        PieDataSet pieDataSet = new PieDataSet(entries, "Statistik Covid-19 Bandar Lampung");
        pieDataSet.setColors(ColorTemplate.JOYFUL_COLORS);
        pieDataSet.setValueTextSize(16f);
        pieDataSet.setValueFormatter(new PercentFormatter(pieChart));

        PieData pieData = new PieData(pieDataSet);

        pieChart.setData(pieData);
        pieChart.invalidate();
    }
}
