package com.alisavran.retrofitjava.view;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alisavran.retrofitjava.R;
import com.alisavran.retrofitjava.adapter.RecyclerViewAdapter;
import com.alisavran.retrofitjava.model.CryptoModel;
import com.alisavran.retrofitjava.service.CryptoAPI;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    ArrayList<CryptoModel> cryptoModels;
    private String BASE_URL = "https://raw.githubusercontent.com/atilsamancioglu/K21-JSONDataSet/master/";
    Retrofit retrofit;
    RecyclerView recyclerView;
    RecyclerViewAdapter recyclerViewAdapter;
    CompositeDisposable compositeDisposable = new CompositeDisposable();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //https://github.com/atilsamancioglu/K21-JSONDataSet/blob/master/crypto.json

        //Retrofit VE Json

        recyclerView = findViewById(R.id.recyclerView);

        Gson gson = new GsonBuilder().setLenient().create();

        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        loadData();
    }

    private  void loadData(){

        final CryptoAPI cryptoAPI = retrofit.create(CryptoAPI.class);

        compositeDisposable .add(cryptoAPI.getData()
                .subscribeOn(Schedulers.io()) // kayıt olma işlemi
                .observeOn(AndroidSchedulers.mainThread())    // alınan veriyi gözlemleme
                .subscribe(this::handleResponse));
        /*
        Call<List<CryptoModel>> call = cryptoAPI.getData();

        call.enqueue(new Callback<List<CryptoModel>>() {
            @Override
            public void onResponse(Call<List<CryptoModel>> call, Response<List<CryptoModel>> response) {
                if (response.isSuccessful()){ // gelen cevap başarılı mı
                    List<CryptoModel> responseList =  response.body();
                    cryptoModels = new ArrayList<>(responseList);

                    //RecyclerView

                    recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
                    recyclerViewAdapter = new RecyclerViewAdapter(cryptoModels);
                    recyclerView.setAdapter(recyclerViewAdapter);

                }
            }

            @Override
            public void onFailure(Call<List<CryptoModel>> call, Throwable t) {

            }
        });*/
    }

    private void handleResponse(List<CryptoModel> cryptoModelList){

        cryptoModels = new ArrayList<>(cryptoModelList);

        //RecyclerView

        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
        recyclerViewAdapter = new RecyclerViewAdapter(cryptoModels);
        recyclerView.setAdapter(recyclerViewAdapter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        compositeDisposable.clear();
    }
}