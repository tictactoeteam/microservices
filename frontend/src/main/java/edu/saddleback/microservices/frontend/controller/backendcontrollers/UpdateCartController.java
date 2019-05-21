package edu.saddleback.microservices.frontend.controller.backendcontrollers;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import edu.saddleback.microservices.frontend.controller.AppController;
import edu.saddleback.microservices.frontend.model.Cart;
import edu.saddleback.microservices.frontend.model.CartItem;
import edu.saddleback.microservices.frontend.model.backendmodels.CartObj;
import edu.saddleback.microservices.frontend.model.backendmodels.HollowCartObj;
import edu.saddleback.microservices.frontend.observable.Observable;

/**
 * Controls all update cart attempt logic, handles sending a request via Retrofit API and the responses as well.
 */
public class UpdateCartController implements Callback<CartObj> {

    private String token;
    private List<CartItem> cartItems;
    private Observable<Boolean> cartReceived;
    private List<HollowCartObj> newCart;

    /**
     * Constructor
     *
     * @param token
     * @param cart
     */
    public UpdateCartController(String token, Cart cart) {

        cartReceived = new Observable<>();
        cartReceived.set(false);
        this.token = token;
        newCart = new ArrayList<>();

        for (int i = 0; i < cart.getSize(); i++) {

            newCart.add(new HollowCartObj(cart.getCartItem(i).getProduct().getProductID(),
                    cart.getCartItem(i).getQuantity()));

        }

    }

    /**
     * Sends the https request.
     */
    public void start() {

        Call<CartObj> call = AppController.getBackendService().updateCart(token, new CartObj(newCart));
        call.enqueue(this);

    }

    /**
     * Handles the response, saves the data, translates it from a hollow cart item to a cartItem, and notifies
     * listeners.
     *
     * @param call
     * @param response
     */
    @Override
    public void onResponse(Call<CartObj> call, Response<CartObj> response) {

        System.out.println("RECEIVED updateCart RESPONSE");
        System.out.println(response.toString());
        if (response.code() == 200) {

            CartObj hollowCart = response.body();
            ArrayList<CartItem> fullCart = new ArrayList<>();
            ArrayList<GetProductController> controllerList = new ArrayList<>();
            for (int i = 0; i < hollowCart.cart.size(); i++) {

                GetProductController controller = new GetProductController(hollowCart.cart.get(i).product);
                controller.getProductRecievedBoolean().subscribe((onProductReceived) -> {

                    if (onProductReceived) {

                        fullCart.add(new CartItem(controller.getProduct(),
                                hollowCart.cart.get(controllerList.indexOf(controller)).quantity));

                        if (fullCart.size() == hollowCart.cart.size()) {

                            cartReceived.set(true);

                        }

                    }

                });

                controllerList.add(controller);

            }

        }

    }

    /**
     * Handles the failure response and notifies listeners.
     *
     * @param call
     * @param t
     */
    @Override
    public void onFailure(Call<CartObj> call, Throwable t) {

        System.out.println("RECEIVED updateCart FAILURE");
        cartReceived.set(false);

    }

    //Getters
    public Observable<Boolean> getCartReceivedBoolean() {
        return cartReceived;
    }

    public Cart getCart() {

        Cart tmpCart = new Cart();
        for (int i = 0; i < cartItems.size(); i++) {

            tmpCart.add(cartItems.get(i));

        }

        return tmpCart;

    }

}
