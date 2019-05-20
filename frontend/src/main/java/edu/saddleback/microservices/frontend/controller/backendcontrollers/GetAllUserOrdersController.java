package edu.saddleback.microservices.frontend.controller.backendcontrollers;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import edu.saddleback.microservices.frontend.controller.AppController;
import edu.saddleback.microservices.frontend.model.backendmodels.OrderTransaction;
import edu.saddleback.microservices.frontend.observable.Observable;

public class GetAllUserOrdersController implements Callback<List<OrderTransaction>> {

    private String token;
    private Observable<Boolean> ordersReceived;
    List<OrderTransaction> orders;

    public GetAllUserOrdersController(String token) {

        this.token = token;
        ordersReceived = new Observable<>();
        ordersReceived.set(false);

    }

    public void start() {

        Call<List<OrderTransaction>> call = AppController.getBackendService().getAllUserOrders(token);
        call.enqueue(this);

    }

    @Override
    public void onResponse(Call<List<OrderTransaction>> call, Response<List<OrderTransaction>> response) {

        System.out.println("RECEIVED getAllUserOrders RESPONSE");
        System.out.println(response.toString());
        if (response.code() == 200) {

            orders = response.body();
            ordersReceived.set(true);
        }

    }

    @Override
    public void onFailure(Call<List<OrderTransaction>> call, Throwable t) {

        System.out.println("RECEIVED getAllUserOrders FAILURE");
        ordersReceived.set(false);

    }

    //Getter
    public Observable<Boolean> getOrdersReceivedObservableBoolean() {
        return ordersReceived;
    }

    public List<OrderTransaction> getOrders() {
        return orders;
    }

    public long getOrdersSum() {

        long sum = 0;
        for (int i = 0; i < orders.size(); i++) {
            sum += orders.get(i).price;
        }

        return sum;

    }

}
