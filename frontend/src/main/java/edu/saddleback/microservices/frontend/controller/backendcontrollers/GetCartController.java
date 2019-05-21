package edu.saddleback.microservices.frontend.controller.backendcontrollers;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import edu.saddleback.microservices.frontend.controller.AppController;
import edu.saddleback.microservices.frontend.model.Cart;
import edu.saddleback.microservices.frontend.model.CartItem;
import edu.saddleback.microservices.frontend.model.backendmodels.HollowCartObj;
import edu.saddleback.microservices.frontend.observable.Observable;

/**
 * Controls all get cart attempt logic, handles sending a request via Retrofit API and the responses as well.
 */
public class GetCartController implements Callback<List<HollowCartObj>> {

    private String token;
    private List<CartItem> cartItems;
    private Observable<Boolean> cartReceived;

    /**
     * Constructor
     *
     * @param token
     */
    public GetCartController(String token) {

        cartReceived = new Observable<>();
        cartReceived.set(false);
        this.token = token;

    }

    /**
     * Sends the https request.
     */
    public void start() {

        Call<List<HollowCartObj>> call = AppController.getBackendService().getCart(token);
        call.enqueue(this);

    }

    /**
     * Handles the response, saves and translates the hollow cart items into cartItems, and notifies listeners.
     *
     * @param call
     * @param response
     */
    @Override
    public void onResponse(Call<List<HollowCartObj>> call, Response<List<HollowCartObj>> response) {

        System.out.println("RECEIVED getCart RESPONSE");
        System.out.println(response.toString());
        if (response.code() == 200) {

            List<HollowCartObj> hollowCart = response.body();
            ArrayList<CartItem> fullCart = new ArrayList<>();
            ArrayList<GetProductController> controllerList = new ArrayList<>();
            for (int i = 0; i < hollowCart.size(); i++) {

                GetProductController controller = new GetProductController(hollowCart.get(i).id);
                controller.getProductRecievedBoolean().subscribe((onProductReceived) -> {

                    if (onProductReceived) {

                        fullCart.add(new CartItem(controller.getProduct(),
                                hollowCart.get(controllerList.indexOf(controller)).quantity));

                        if (fullCart.size() == hollowCart.size()) {

                            cartReceived.set(true);

                        }

                    }

                });

                controllerList.add(controller);

            }

        }

    }

    /**
     * Handles the failure message and notifies listeners
     *
     * @param call
     * @param t
     */
    @Override
    public void onFailure(Call<List<HollowCartObj>> call, Throwable t) {

        System.out.println("RECEIVED getCart FAILURE");
        cartReceived.set(false);

    }

    //Getters
    public Observable<Boolean> getCartReceivedBoolean() {
        return cartReceived;
    }

    public List<CartItem> getCartItems() {
        return cartItems;
    }
    
    public Cart getCart() {

        Cart tmpCart = new Cart();
        for (int i = 0; i < cartItems.size(); i++) {

            tmpCart.add(cartItems.get(i));

        }

        return tmpCart;

    }

}
