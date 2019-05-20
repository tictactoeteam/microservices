package edu.saddleback.microservices.frontend.interfaces;

import edu.saddleback.microservices.frontend.model.Product;
import edu.saddleback.microservices.frontend.model.backendmodels.CreateAccountObj;
import edu.saddleback.microservices.frontend.model.backendmodels.HollowCartObj;
import edu.saddleback.microservices.frontend.model.backendmodels.LoginObj;
import edu.saddleback.microservices.frontend.model.backendmodels.OrderTransaction;
import edu.saddleback.microservices.frontend.model.backendmodels.SuccessfulAccountCreatedUser;
import edu.saddleback.microservices.frontend.model.backendmodels.SuccessfulLoginToken;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

import edu.saddleback.microservices.frontend.controller.backendmodels.CreateAccountObject;
import edu.saddleback.microservices.frontend.controller.backendmodels.HollowCartObject;
import edu.saddleback.microservices.frontend.controller.backendmodels.LoginObject;
import edu.saddleback.microservices.frontend.controller.backendmodels.SuccessfulAccountCreatedUser;
import edu.saddleback.microservices.frontend.controller.backendmodels.SuccessfulLoginToken;
import edu.saddleback.microservices.frontend.model.Product;

/**
 * Provides Retrofit API with a interface to let it generate the packing and unpacking of Json objects.
 */
public interface BackendService {

    //AUTH SERVICE
    @POST("/api/v1/users")
    Call<SuccessfulAccountCreatedUser> registerAccount(@Body CreateAccountObj obj);

    @POST("/api/v1/session")
    Call<SuccessfulLoginToken> login(@Body LoginObj obj);

    //PRODUCT SERVICE
    @GET("/api/v1/products")
    Call<List<Product>> getAllProducts();

    @GET("/api/v1/products/{id}")
    Call<Product> getProduct(@Path("id") String id);

    //CART SERVICE
    @GET("/api/v1/cart")
    Call<List<HollowCartObj>> getCart(@Header("Authorization") String token);

    @PUT("/api/v1/cart")
    Call<List<HollowCartObj>> updateCart(@Header("Authorization") String token, List<HollowCartObj> newCart);

    @DELETE("/api/v1/cart")
    Call<Void> deleteCart(@Header("Authorization") String token);

    //ORDER SERVICE
    @GET("/api/v1/orders/")
    Call<List<OrderTransaction>> getAllUserOrders(@Header("Authorization") String token);

    @POST("/api/v1/orders")
    Call<OrderTransaction> makeOrder(@Header("Authorization") String token, List<HollowCartObj> cart, String coin);

    //Controllers are finished for these above
    @GET("/api/v1/orders/{id}")
    Call<OrderTransaction> getOrder(@Path("id") String id);

}
