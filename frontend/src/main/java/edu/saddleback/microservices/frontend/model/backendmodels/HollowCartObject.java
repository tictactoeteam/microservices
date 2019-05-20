package edu.saddleback.microservices.frontend.model.backendmodels;

public class HollowCartObject {

    public String id;
    public int quantity;

    public HollowCartObject(String id, long quantity) {

        this.id = id;
        this.quantity = (int) quantity;

    }


}
