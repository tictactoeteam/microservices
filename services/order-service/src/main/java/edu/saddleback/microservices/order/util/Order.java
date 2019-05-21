package edu.saddleback.microservices.order.util;

import java.util.ArrayList;
import java.util.Date;

public class Order {
    private String id;
    private String customerId;
    private Status status;
    private ArrayList<CartObject> cart;
    private String coin;
    private String address;
    private long price;
    private Date timestamp;

    public Order() {
        this("", "", Status.UNPAID, new ArrayList<>(), "", "", 0, new Date());
    }

    public Order(String id, String customerId, Status status, ArrayList<CartObject> cart, String coin, String address, long price, Date timestamp) {

        this.id = id;
        this.customerId = customerId;
        this.status = status;
        this.cart = cart;
        this.coin = coin;
        this.address = address;
        this.price = price;
        this.timestamp = timestamp;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public ArrayList<CartObject> getCart() {
        return cart;
    }

    public void setCart(ArrayList<CartObject> cart) {
        this.cart = cart;
    }

    public String getCoin() {
        return coin;
    }

    public void setCoin(String coin) {
        this.coin = coin;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public long getPrice() {
        return price;
    }

    public void setPrice(long price) {
        this.price = price;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }
}
