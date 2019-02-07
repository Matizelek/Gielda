package register.presenter;

import register.view.RegisterView;

public interface RegisterPresenter {

    void onConfirm(String login, String password, String repeatPassword);

    void onLoginTextChanged(String loginText);

}
