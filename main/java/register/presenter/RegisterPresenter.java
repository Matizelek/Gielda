package register.presenter;


public interface RegisterPresenter {

    void onConfirm(String login, String password, String repeatPassword);

    void onLoginTextChanged(String loginText);

}
