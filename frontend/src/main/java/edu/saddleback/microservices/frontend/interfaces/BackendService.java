package edu.saddleback.microservices.frontend.interfaces;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

import edu.saddleback.microservices.frontend.model.Product;
import edu.saddleback.microservices.frontend.model.backendmodels.CartObj;
import edu.saddleback.microservices.frontend.model.backendmodels.CreateAccountObj;
import edu.saddleback.microservices.frontend.model.backendmodels.LoginObj;
import edu.saddleback.microservices.frontend.model.backendmodels.MakeOrderObj;
import edu.saddleback.microservices.frontend.model.backendmodels.OrderTransaction;
import edu.saddleback.microservices.frontend.model.backendmodels.SuccessfulAccountCreatedUser;
import edu.saddleback.microservices.frontend.model.backendmodels.SuccessfulLoginToken;

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
    Call<CartObj> getCart(@Header("Authorization") String token);

    @PUT("/api/v1/cart")
    Call<CartObj> updateCart(@Header("Authorization") String token, @Body CartObj cart);

    @DELETE("/api/v1/cart")
    Call<Void> deleteCart(@Header("Authorization") String token);

    //ORDER SERVICE
    @GET("/api/v1/orders/")
    Call<List<OrderTransaction>> getAllUserOrders(@Header("Authorization") String token);

    @POST("/api/v1/orders")
    Call<OrderTransaction> makeOrder(@Header("Authorization") String token, @Body MakeOrderObj obj);

    //Controllers are finished for these above
    @GET("/api/v1/orders/{id}")
    Call<OrderTransaction> getOrder(@Path("id") String id);

}
