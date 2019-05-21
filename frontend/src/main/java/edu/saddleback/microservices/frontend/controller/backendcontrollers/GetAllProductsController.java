package edu.saddleback.microservices.frontend.controller.backendcontrollers;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import edu.saddleback.microservices.frontend.controller.AppController;
import edu.saddleback.microservices.frontend.model.Product;
import edu.saddleback.microservices.frontend.observable.Observable;

/**
 * Controls all get all products attempt logic, handles sending a request via Retrofit API and the responses as well.
 */
public class GetAllProductsController implements Callback<List<Product>> {

    private List<Product> products;
    private Observable<Boolean> productsReceived;

    /**
     * Constructor
     */
    public GetAllProductsController() {


        productsReceived = new Observable<>();
        productsReceived.set(false);

    }

    /**
     * Sends the https request.
     */
    public void start() {

        Call<List<Product>> call = AppController.getBackendService().getAllProducts();
        call.enqueue(this);

    }

    /**
     * Handles the response, saves the data, notifies the listeners.
     *
     * @param call
     * @param response
     */
    @Override
    public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {

        System.out.println("RECEIVED getAllProducts RESPONSE");
        System.out.println(response.toString());
        if (response.code() == 200) {

            products = response.body();
            productsReceived.set(true);

        } else {
            productsReceived.set(false);
        }

    }

    /**
     * Handles the failure response and notifies the listeners.
     *
     * @param call
     * @param t
     */
    @Override
    public void onFailure(Call<List<Product>> call, Throwable t) {

        System.out.println("RECEIVED getAllProducts FAILURE");
        productsReceived.set(false);

    }

    //Getter
    public Observable<Boolean> getProductsRecievedBoolean() {
        return productsReceived;
    }

    public List<Product> getProducts() {
        return products;
    }

}
