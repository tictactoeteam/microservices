package edu.saddleback.microservices.frontend.model.backendmodels;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import edu.saddleback.microservices.frontend.interfaces.BackendService;

/**
 * Represents the setup data for all https requests.
 */
public class BackendServiceObject {

    private final String baseUrl = "https://k8s.typokign.com/";
    private BackendService service;

    /**
     * Constructor
     * Builds the retrofit https api classes.
     */
    public BackendServiceObject() {

        Gson gson = new GsonBuilder()
                .setLenient()
                .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                .create();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        service = retrofit.create(BackendService.class);

    }

    //Getters
    public BackendService getBackendService() {
        return service;
    }


}
