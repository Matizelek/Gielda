package register.view;

import user.User;

public interface RegisterView {


    void showLoading();

    void showError(String errorMessage);

    void onRegisterSuccess(User user);

    void showEmailIsCorrect();

    void showEmailIsIncorrect();

}
