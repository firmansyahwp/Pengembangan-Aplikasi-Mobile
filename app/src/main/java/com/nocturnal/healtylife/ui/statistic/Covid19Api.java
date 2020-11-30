package com.nocturnal.healtylife.ui.statistic;

import retrofit2.Call;
import retrofit2.http.GET;

public interface Covid19Api {
    @GET(".")
    Call<Attributes> getAttributes();
}
