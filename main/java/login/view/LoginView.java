package login.view;

import user.User;

public interface LoginView {

	
	void showLoading();
	
	void showError(String errorMessage);
	
	void onLoginSuccess(User user);
	
	void showLoginIsCorrect();
	
	void showLoginIsIncorrect();
	
}
