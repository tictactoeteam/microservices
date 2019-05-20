package edu.saddleback.microservices.frontend.controller.backendcontrollers;

import edu.saddleback.microservices.frontend.controller.AppController;
import edu.saddleback.microservices.frontend.observable.Observable;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DeleteCartController implements Callback<Void> {

    private String token;
    private Observable<Boolean> cartDeleted;

    public DeleteCartController(String token) {

        this.token = token;
        cartDeleted = new Observable<>();
        cartDeleted.set(false);

    }

    public void start() {

        Call<Void> call = AppController.getBackendService().deleteCart(token);
        call.enqueue(this);

    }

    @Override
    public void onResponse(Call<Void> call, Response<Void> response) {

        System.out.println("RECEIVED deleteCart RESPONSE");
        System.out.println(response.toString());
        if (response.code() == 201) {
            cartDeleted.set(true);
        }

    }

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
