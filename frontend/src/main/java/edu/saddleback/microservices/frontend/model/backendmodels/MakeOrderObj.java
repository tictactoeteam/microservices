package edu.saddleback.microservices.frontend.model.backendmodels;

import java.util.List;

public class MakeOrderObj {

    public List<HollowCartObj> cart;
    public String coin;

    public MakeOrderObj(List<HollowCartObj> list, String str) {

        cart = list;
        coin = str;

    }

}
