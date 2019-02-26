package login.presenter;

public interface LoginPresenter {
	
	void onConfirm(String login, String password);
	
	void onLoginTextChanged(String loginText);

}
