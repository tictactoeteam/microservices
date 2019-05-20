package edu.saddleback.microservices.order.util;

import java.util.ArrayList;
import java.util.List;

public class Order {
    public String id;
    public String status;
    public List<CartObject> cart;
    public String coin;
    public String address;
    public long price;
    public String timestamp;


    public Order(String id, String status, ArrayList<CartObject> cart, String coin, String address, long price, String timestamp) {

        this.id = id;
        this.status = status;
        this.cart = cart;
        this.coin = coin;
        this.address = address;
        this.price = price;
        this.timestamp = timestamp;
    }
}
