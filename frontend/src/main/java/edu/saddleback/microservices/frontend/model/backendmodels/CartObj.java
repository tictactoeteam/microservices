package edu.saddleback.microservices.frontend.model.backendmodels;

import java.util.List;

public class CartObj {

    public List<HollowCartObj> cart;

    public CartObj(List<HollowCartObj> tmp) {

        cart = tmp;

    }

}
