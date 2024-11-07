package com.alisavran.retrofitjava.service;

import com.alisavran.retrofitjava.model.CryptoModel;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.GET;

public interface CryptoAPI {

    //GET  = Veriyi alma işlemi , POST = veriyi yazdırma işlemi

    //URL Base/Get = www.website.com/price?key=45466546546

    @GET("crypto.json")
    Observable<List<CryptoModel>> getData();
    //Call<List<CryptoModel>> getData();
}
