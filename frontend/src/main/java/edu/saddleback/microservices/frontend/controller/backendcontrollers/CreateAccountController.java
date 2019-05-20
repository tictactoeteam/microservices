package edu.saddleback.microservices.frontend.controller.backendcontrollers;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import edu.saddleback.microservices.frontend.controller.AppController;
import edu.saddleback.microservices.frontend.model.backendmodels.CreateAccountObj;
import edu.saddleback.microservices.frontend.model.backendmodels.SuccessfulAccountCreatedUser;
import edu.saddleback.microservices.frontend.observable.Observable;

/**
 * Controls all create account attempt logic, handles sending a request via Retrofit API and the responses as well.
 */
public class CreateAccountController implements Callback<SuccessfulAccountCreatedUser> {

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
        accountCreated.set(false);

    }

    /**
     * Runs the call request, sending the username, email, and password.
     */
    public void start() {

        Call<SuccessfulAccountCreatedUser> call = AppController.getBackendService()
                .registerAccount(new CreateAccountObj(username, email, password));
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

        System.out.println("RECIEVED CREATE ACCOUNT RESPONSE");
        System.out.println(response.toString());
        if (response.code() == 201) { //If account doesnt already exist
            accountCreated.set(true);
        } else if (response.code() == 400) {
            accountCreated.set(false);
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
