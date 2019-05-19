package edu.saddleback.microservices.frontend.interfaces;

import edu.saddleback.microservices.frontend.controller.backendmodels.CreateAccountObject;
import edu.saddleback.microservices.frontend.controller.backendmodels.HollowCartObject;
import edu.saddleback.microservices.frontend.controller.backendmodels.LoginObject;
import edu.saddleback.microservices.frontend.controller.backendmodels.SuccessfulAccountCreatedUser;
import edu.saddleback.microservices.frontend.controller.backendmodels.SuccessfulLoginToken;
import edu.saddleback.microservices.frontend.model.Product;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

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

    @PUT("/api/v1/cart")
    Call<List<HollowCartObject>> updateCart(@Header("Authorization") String token, List<HollowCartObject> newCart);

    @DELETE("/api/v1/cart")
    Call<Void> deleteCart(@Header("Authorization") String token);

}
