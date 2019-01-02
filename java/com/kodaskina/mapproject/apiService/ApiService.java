package com.kodaskina.mapproject.apiService;

import com.kodaskina.mapproject.model.Model;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by EA on 5.7.2017.
 */
public interface ApiService {
    @GET("itucr")
    Call<List<Model>> getUserData();
}
