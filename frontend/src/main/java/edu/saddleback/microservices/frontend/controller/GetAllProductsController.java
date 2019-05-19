package edu.saddleback.microservices.frontend.controller;

import edu.saddleback.microservices.frontend.model.Product;
import edu.saddleback.microservices.frontend.observable.Observable;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GetAllProductsController implements Callback<List<Product>> {

    private List<Product> products;
    private Observable<Boolean> productsRecieved;

    public GetAllProductsController() {


        productsRecieved = new Observable<>();
        productsRecieved.set(false);

    }

    public void start() {

        Call<List<Product>> call = AppController.getBackendService().getAllProducts();
        call.enqueue(this);

    }

    @Override
    public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {

        System.out.println("RECEIVED getProducts RESPONSE");
        System.out.println(response.toString());
        if (response.code() == 200) {

            products = response.body();
            productsRecieved.set(true);

        }

    }

    @Override
    public void onFailure(Call<List<Product>> call, Throwable t) {

        System.out.println("RECEIVED getProducts FAILURE");
        productsRecieved.set(false);

    }

    //Getter
    public Observable<Boolean> getProductsRecievedBoolean() {
        return productsRecieved;
    }

    public List<Product> getProducts() {
        return products;
    }

}
