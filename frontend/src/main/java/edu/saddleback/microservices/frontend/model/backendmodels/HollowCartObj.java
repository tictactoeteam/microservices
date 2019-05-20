package edu.saddleback.microservices.frontend.model.backendmodels;

public class HollowCartObj {

    public String id;
    public int quantity;

    public HollowCartObj(String id, long quantity) {

        this.id = id;
        this.quantity = (int) quantity;

    }


}
