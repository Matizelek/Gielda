package register.presenter;

import email.EmailValidator;
import hasher.SHAPasswordHasher;
import register.BadDataException;
import register.model.RegisterModel;
import register.resources.StringResources;
import register.view.RegisterView;
import user.User;
import user.repository.UserRepository;

public class RegisterPresenterImpl implements RegisterPresenter {


    final private RegisterView registerView;
    final private RegisterModel model;
    final private EmailValidator emailValidator;


    public RegisterPresenterImpl(RegisterView registerView, UserRepository repository) {
        this.registerView = registerView;
        model = new RegisterModel(new SHAPasswordHasher(), repository);
        emailValidator = new EmailValidator();
    }


    @Override
    public void onConfirm(String login, String password, String repeatPassword) {


        if (emailValidator.validate(login)) {

            try {
                User user = model.register(password, repeatPassword, login);
                registerView.onRegisterSuccess(user);
            } catch (BadDataException e) {
                e.printStackTrace();
                if (e.isRepeatPasswordIncorrect()) {
                    registerView.showError(StringResources.passwordArentTheSameError);
                }
            }

        }

    }


    @Override
    public void onLoginTextChanged(String loginText) {
        boolean result = emailValidator.validate(loginText);
        if (result) {
            registerView.showEmailIsCorrect();
        } else {
            registerView.showEmailIsIncorrect();
        }
    }
}
