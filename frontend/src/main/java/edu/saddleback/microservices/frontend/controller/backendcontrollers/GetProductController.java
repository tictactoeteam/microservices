package edu.saddleback.microservices.frontend.controller.backendcontrollers;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import edu.saddleback.microservices.frontend.controller.AppController;
import edu.saddleback.microservices.frontend.model.Product;
import edu.saddleback.microservices.frontend.observable.Observable;

public class GetProductController implements Callback<Product> {

    private String productID;
    private Product product;
    private Observable<Boolean> productRecieved;

    public GetProductController(String productID) {

        this.productID = productID;
        productRecieved = new Observable<>();
        productRecieved.set(false);

    }

    public void start() {

        Call<Product> call = AppController.getBackendService().getProduct(productID);
        call.enqueue(this);

    }

    @Override
    public void onResponse(Call<Product> call, Response<Product> response) {

        System.out.println("RECEIVED getProducts RESPONSE");
        System.out.println(response.toString());
        if (response.code() == 200) {

            product = response.body();
            productRecieved.set(true);

        }

    }

    @Override
    public void onFailure(Call<Product> call, Throwable t) {

        System.out.println("RECEIVED getProducts FAILURE");
        productRecieved.set(false);

    }

    //Getter
    public Observable<Boolean> getProductRecievedBoolean() {
        return productRecieved;
    }

    public Product getProduct() {
        return product;
    }

}
