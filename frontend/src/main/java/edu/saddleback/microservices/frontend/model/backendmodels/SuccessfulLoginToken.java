package edu.saddleback.microservices.frontend.model.backendmodels;

public class SuccessfulLoginToken {

    private String token;

    public SuccessfulLoginToken(String str) {

        token = str;

    }

    //Getter
    public String getToken() {

        return token;

    }

}
