package edu.saddleback.microservices.frontend.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import edu.saddleback.microservices.frontend.controller.backendModels.CreateAccountObject;
import edu.saddleback.microservices.frontend.interfaces.BackendService;
import edu.saddleback.microservices.frontend.model.SuccessfulAccountCreatedUser;
import edu.saddleback.microservices.frontend.observable.Observable;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Controls all create account attempt logic, handles sending a request via Retrofit API and the responses as well.
 */
public class CreateAccountController implements Callback<SuccessfulAccountCreatedUser> {

    static final String BASE_URL = "http://k8s.typokign.com/";
    private String username;
    private String email;
    private String password;
    private Observable<Boolean> accountCreated;

    /**
     * Constructor
     *
     * @param username
     * @param email
     * @param password
     */
    public CreateAccountController(String username, String email, String password) {

        this.username = username;
        this.email = email;
        this.password = password;
        accountCreated = new Observable<>();
        accountCreated.softSet(false);

    }

    /**
     * Runs the call request, sending the username, email, and password.
     */
    public void start() {

        Gson gson = new GsonBuilder()
                .setLenient()
                .create();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        BackendService service = retrofit.create(BackendService.class);
        Call<SuccessfulAccountCreatedUser> call = service.registerAccount(new CreateAccountObject(username, email,
                password));
        call.enqueue(this);

    }

    /**
     * Runs when a response was sent, either create account successful or not.
     *
     * @param call
     * @param response
     */
    @Override
    public void onResponse(Call<SuccessfulAccountCreatedUser> call, Response<SuccessfulAccountCreatedUser> response) {

        if (response.code() != 400) { //If account doesnt already exist
            accountCreated.set(true);
        }

    }

    /**
     * Handles critical message failures.
     *
     * @param call
     * @param t
     */
    @Override
    public void onFailure(Call<SuccessfulAccountCreatedUser> call, Throwable t) {
        System.out.println("RECIEVED CREATE ACCOUNT FAILURE");
        t.printStackTrace();
        accountCreated.set(false);
    }

    //Getter
    public Observable<Boolean> getAccountCreatedObservableBoolean() {
        return accountCreated;
    }
}
