package edu.saddleback.microservices.frontend.controller.backendcontrollers;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import edu.saddleback.microservices.frontend.controller.AppController;
import edu.saddleback.microservices.frontend.model.Cart;
import edu.saddleback.microservices.frontend.model.backendmodels.HollowCartObj;
import edu.saddleback.microservices.frontend.model.backendmodels.OrderTransaction;
import edu.saddleback.microservices.frontend.observable.Observable;

public class MakeOrderController implements Callback<OrderTransaction> {

    private String token;
    private Observable<Boolean> ordersMade;
    private List<HollowCartObj> newCart;
    private String chosenCoin;
    private OrderTransaction order;

    public MakeOrderController(String token, Cart cart, String coin) {

        this.token = token;

        for (int i = 0; i < cart.getSize(); i++) {

            newCart.add(new HollowCartObj(cart.getCartItem(i).getProduct().getProductID(),
                    cart.getCartItem(i).getQuantity()));

        }

        chosenCoin = coin;

    }

    public void start() {

        Call<OrderTransaction> call = AppController.getBackendService().makeOrder(token, newCart, chosenCoin);
        call.enqueue(this);

    }

    @Override
    public void onResponse(Call<OrderTransaction> call, Response<OrderTransaction> response) {

        System.out.println("RECEIVED makeOrder RESPONSE");
        System.out.println(response.toString());
        if (response.code() == 201) {

            order = response.body();
            ordersMade.set(true);
        }

    }

    @Override
    public void onFailure(Call<OrderTransaction> call, Throwable t) {

        System.out.println("RECEIVED makeOrder FAILURE");
        ordersMade.set(false);

    }

    //Getter
    public Observable<Boolean> getOrdersMadeObservableBoolean() {
        return ordersMade;
    }

    public OrderTransaction getOrder() {
        return order;
    }

}
