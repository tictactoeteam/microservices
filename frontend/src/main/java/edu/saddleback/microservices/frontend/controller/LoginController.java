package edu.saddleback.microservices.frontend.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import edu.saddleback.microservices.frontend.controller.backendModels.LoginObject;
import edu.saddleback.microservices.frontend.interfaces.BackendService;
import edu.saddleback.microservices.frontend.model.SuccessfulLoginToken;
import edu.saddleback.microservices.frontend.observable.Observable;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Controls all login attempt logic, handles sending a request via Retrofit API and the responses as well.
 */
public class LoginController implements Callback<SuccessfulLoginToken> {

    static final String BASE_URL = "http://k8s.typokign.com/";
    private String username;
    private String password;
    private String token;
    private Observable<Boolean> loggedIn;

    /**
     * Constructor
     *
     * @param username
     * @param password
     */
    public LoginController(String username, String password) {

        this.username = username;
        this.password = password;
        loggedIn = new Observable<>();
        loggedIn.softSet(false);

    }

    /**
     * Runs the call request, sending the username and password.
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
        Call<SuccessfulLoginToken> call = service.login(new LoginObject(username, password));
        call.enqueue(this);

    }

    /**
     * Runs when a response was sent, either login successful or not.
     *
     * @param call
     * @param response
     */
    @Override
    public void onResponse(Call<SuccessfulLoginToken> call, Response<SuccessfulLoginToken> response) {

        System.out.println("RECIEVED LOGIN RESPONSE");
        System.out.println(response.toString());

        if (response.code() != 400 && response.code() != 429) {

            token = response.body().getToken();
            loggedIn.set(true);
            System.out.println("TOKEN: " + token);

        }

    }

    /**
     * Handles critical message failures.
     *
     * @param call
     * @param t
     */
    @Override
    public void onFailure(Call<SuccessfulLoginToken> call, Throwable t) {
        System.out.println("RECIEVED LOGIN FAILURE");
        t.printStackTrace();
        loggedIn.set(false);

    }

    //Getter
    public Observable<Boolean> getLoggedInObservableBoolean() {
        return loggedIn;
    }

    public String getToken() {
        return token;
    }
}
