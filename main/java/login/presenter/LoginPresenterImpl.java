package login.presenter;

import email.EmailValidator;
import hasher.SHAPasswordHasher;
import login.BadCredentialsException;
import login.model.LoginModel;
import login.resources.StringResourcesLogin;
import login.view.LoginView;
import user.User;
import user.repository.UserRepository;

public class LoginPresenterImpl implements LoginPresenter{

	final private LoginView loginView;
	final private LoginModel model;
	final private EmailValidator emailValidator;
	
	public LoginPresenterImpl(LoginView loginView, UserRepository repository) {
		this.loginView = loginView;
		this.model = new LoginModel(new SHAPasswordHasher(), repository);
		emailValidator = new EmailValidator();
	}

	@Override
	public void onConfirm(String login, String password) {
		if(emailValidator.validate(login)) {
			
			try{
				User user = model.login(login, password);
				loginView.onLoginSuccess(user);
			}catch(BadCredentialsException e) {
				e.printStackTrace();
				if(e.isWrongPassword()) {
					loginView.showError(StringResourcesLogin.passwordIsIncorrect);
				}
				else {
					loginView.showError(StringResourcesLogin.userNotFound);
				}
			}
		}
	}

	
	@Override
	public void onLoginTextChanged(String loginText) {
		boolean result = emailValidator.validate(loginText);
		if(result) {
			loginView.showLoginIsCorrect();
		}else {
			loginView.showLoginIsIncorrect();
		}
	}

}
