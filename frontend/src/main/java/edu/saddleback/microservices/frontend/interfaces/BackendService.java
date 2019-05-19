package edu.saddleback.microservices.frontend.interfaces;

import edu.saddleback.microservices.frontend.controller.backendmodels.*;
import edu.saddleback.microservices.frontend.model.Product;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.*;

/**
 * Provides Retrofit API with a interface to let it generate the packing and unpacking of Json objects.
 */
public interface BackendService {

    @POST("/api/v1/users")
    Call<SuccessfulAccountCreatedUser> registerAccount(@Body CreateAccountObject obj);

    @POST("/api/v1/session")
    Call<SuccessfulLoginToken> login(@Body LoginObject obj);

    @GET("/api/v1/products")
    Call<List<Product>> getAllProducts();

    @GET("/api/v1/products/{id}")
    Call<Product> getProduct(@Path("id") String id);

    @GET("/api/v1/cart")
    Call<List<HollowCartObject>> getCart(@Header("Authorization") String token);

    //Above have controllers written

    @PUT("/api/v1/cart")
    Call<List<HollowCartObject>> updateCart(@Header("Authorization") String token, List<HollowCartObject> newCart);

    @DELETE("/api/v1/cart")
    void deleteCart(@Header("Authorization") String token);

}
