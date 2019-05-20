package edu.saddleback.microservices.frontend.controller.backendcontrollers;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import edu.saddleback.microservices.frontend.controller.AppController;
import edu.saddleback.microservices.frontend.model.backendmodels.LoginObj;
import edu.saddleback.microservices.frontend.model.backendmodels.SuccessfulLoginToken;
import edu.saddleback.microservices.frontend.observable.Observable;

/**
 * Controls all login attempt logic, handles sending a request via Retrofit API and the responses as well.
 */
public class LoginController implements Callback<SuccessfulLoginToken> {

    static final String BASE_URL = "https://k8s.typokign.com/";
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
        loggedIn.set(false);

    }

    /**
     * Runs the call request, sending the username and password.
     */
    public void start() {

        Call<SuccessfulLoginToken> call = AppController.getBackendService().login(new LoginObj(username, password));
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

        if (response.code() == 201) {

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

        System.out.println("RECEIVED LOGIN FAILURE");
        System.out.println(t.toString());
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
