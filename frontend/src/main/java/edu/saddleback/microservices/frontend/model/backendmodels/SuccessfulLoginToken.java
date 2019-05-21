package edu.saddleback.microservices.frontend.model.backendmodels;

/**
 * Represents a login https return data object.
 */
public class SuccessfulLoginToken {

    private String token;

    /**
     * Constructor
     * @param str
     */
    public SuccessfulLoginToken(String str) {

        token = str;

    }

    //Getter
    public String getToken() {

        return token;

    }

}
