package edu.saddleback.microservices.frontend.model.backendmodels;

import java.util.List;

public class OrderTransaction {

    public String id;
    public String status;
    public List<HollowCartObj> cart;
    public String coin;
    public String cryptoAddress;
    public long price;
    public String timeStamp;

    public OrderTransaction(String id, String status, List<HollowCartObj> cart, String coin, String cryptoAddress,
                            long price, String timeStamp) {

        this.id = id;
        this.status = status;
        this.cart = cart;
        this.coin = coin;
        this.cryptoAddress = cryptoAddress;
        this.price = price;
        this.timeStamp = timeStamp;

    }

}
