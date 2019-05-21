package edu.saddleback.microservices.frontend.controller.backendcontrollers;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import edu.saddleback.microservices.frontend.controller.AppController;
import edu.saddleback.microservices.frontend.observable.Observable;

/**
 * Controls all delete cart attempt logic, handles sending a request via Retrofit API and the responses as well.
 */
public class DeleteCartController implements Callback<Void> {

    private String token;
    private Observable<Boolean> cartDeleted;

    /**
     * Constructor
     *
     * @param token
     */
    public DeleteCartController(String token) {

        this.token = token;
        cartDeleted = new Observable<>();
        cartDeleted.set(false);

    }

    /**
     * Sends the https request.
     */
    public void start() {

        Call<Void> call = AppController.getBackendService().deleteCart(token);
        call.enqueue(this);

    }

    /**
     * Handles the response message and notifies listeners.
     *
     * @param call
     * @param response
     */
    @Override
    public void onResponse(Call<Void> call, Response<Void> response) {

        System.out.println("RECEIVED deleteCart RESPONSE");
        System.out.println(response.toString());
        if (response.code() == 204) {
            cartDeleted.set(true);
        }

    }

    /**
     * Handles the failure response message and notifies listeners.
     *
     * @param call
     * @param t
     */
    @Override
    public void onFailure(Call<Void> call, Throwable t) {

        System.out.println("RECEIVED deleteCart FAILURE");
        cartDeleted.set(false);

    }

    //Getter
    public Observable<Boolean> getCartDeletedObservableBoolean() {
        return cartDeleted;
    }

}
