package edu.saddleback.microservices.cart.util;

public class InvalidRequest extends ErrorResponse {
    public InvalidRequest() {
        super(400, "Invalid request", "INVALID_REQUEST");
    }

    public InvalidRequest(String parameterName) {
        super(400, "Missing " + parameterName, "INVALID_REQUEST");
    }
}
