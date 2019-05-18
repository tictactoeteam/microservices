package edu.saddleback.microservices.frontend.interfaces;

import edu.saddleback.microservices.frontend.controller.backendmodels.CreateAccountObject;
import edu.saddleback.microservices.frontend.controller.backendmodels.LoginObject;
import edu.saddleback.microservices.frontend.model.SuccessfulAccountCreatedUser;
import edu.saddleback.microservices.frontend.model.SuccessfulLoginToken;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Provides Retrofit API with a interface to let it generate the packing and unpacking of Json objects.
 */
public interface BackendService {

    @POST("/api/v1/users")
    Call<SuccessfulAccountCreatedUser> registerAccount(@Body CreateAccountObject obj);

    @POST("/api/v1/session")
    Call<SuccessfulLoginToken> login(@Body LoginObject obj);

}
