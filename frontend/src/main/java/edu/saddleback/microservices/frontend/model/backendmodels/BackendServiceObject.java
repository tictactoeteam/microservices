package edu.saddleback.microservices.frontend.model.backendmodels;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import edu.saddleback.microservices.frontend.interfaces.BackendService;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class BackendServiceObject {

    private final String baseUrl = "https://k8s.typokign.com/";
    private BackendService service;

    public BackendServiceObject() {

        Gson gson = new GsonBuilder()
                .setLenient()
                .create();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        service = retrofit.create(BackendService.class);

    }

    public BackendService getBackendService() {
        return service;
    }


}
