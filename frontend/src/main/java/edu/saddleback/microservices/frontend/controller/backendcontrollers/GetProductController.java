package edu.saddleback.microservices.frontend.controller.backendcontrollers;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import edu.saddleback.microservices.frontend.controller.AppController;
import edu.saddleback.microservices.frontend.model.Product;
import edu.saddleback.microservices.frontend.observable.Observable;

/**
 * Controls all get product attempt logic, handles sending a request via Retrofit API and the responses as well.
 */
public class GetProductController implements Callback<Product> {

    private String productID;
    private Product product;
    private Observable<Boolean> productReceived;

    /**
     * Constructor
     *
     * @param productID
     */
    public GetProductController(String productID) {

        this.productID = productID;
        productReceived = new Observable<>();
        productReceived.set(false);

    }

    /**
     * Sends the https request.
     */
    public void start() {

        Call<Product> call = AppController.getBackendService().getProduct(productID);
        call.enqueue(this);

    }

    /**
     * Handles the respojse, saves the data, and notifies listeners.
     *
     * @param call
     * @param response
     */
    @Override
    public void onResponse(Call<Product> call, Response<Product> response) {

        System.out.println("RECEIVED getProducts RESPONSE");
        System.out.println(response.toString());
        if (response.code() == 200) {

            product = response.body();
            productReceived.set(true);

        }

    }

    /**
     * Handles the failure message and notifies listeners.
     *
     * @param call
     * @param t
     */
    @Override
    public void onFailure(Call<Product> call, Throwable t) {

        System.out.println("RECEIVED getProducts FAILURE");
        productReceived.set(false);

    }

    //Getter
    public Observable<Boolean> getProductRecievedBoolean() {
        return productReceived;
    }

    public Product getProduct() {
        return product;
    }

}
