package edu.saddleback.microservices.frontend.model.backendmodels;

/**
 * Represents a backend cart item object.
 */
public class HollowCartObj {

    public String id;
    public int quantity;

    /**
     * Constructor
     *
     * @param id
     * @param quantity
     */
    public HollowCartObj(String id, long quantity) {

        this.id = id;
        this.quantity = (int) quantity;

    }


}
