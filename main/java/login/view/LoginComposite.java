package login.view;


import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.ProgressBar;
import org.eclipse.swt.widgets.Text;

import DataBase.UserAccountSQL;
import DataBase.UserSQL;
import boxMessage.BoxMessage;
import login.presenter.LoginPresenter;
import login.presenter.LoginPresenterImpl;
import main.Main;
import user.User;
import user.repository.MemoryUserRepository;
import user.repository.UserRepository;

public class LoginComposite extends Composite implements LoginView {
	
		ProgressBar progressBar3;	
		List<User> users = new ArrayList<User>();
		UserRepository repository;
		LoginPresenter presenter;
		Label lblEmail;
		Text textEmail;
		Label lblPassword;
		Text textPassword;
		Button butLoguj;
		Button butRejestruj;

		public LoginComposite(Composite parent, int style) {
			super(parent, style);
			initialize();
		}

		
		
		private void initialize() {
			
			this.setLayout(new GridLayout(2, false));
			
			repository = new MemoryUserRepository(UserSQL.getUsersRepository(),UserAccountSQL.getUserAccounts());
			
			presenter = new LoginPresenterImpl(this,repository);
			
			Composite comp = new Composite(this, SWT.BORDER);
			comp.setLayout(new GridLayout(2, false));
			comp.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 2, 1));
			
			lblEmail = new Label(comp, SWT.NONE);
			lblEmail.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false, 1, 1));
			lblEmail.setText("Email: ");
			
			textEmail = new Text(comp, SWT.BORDER);
			textEmail.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 1, 1));
			
			lblPassword = new Label(comp, SWT.NONE);
			lblPassword.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false, 1, 1));
			lblPassword.setText("Has³o: ");
			
			textPassword = new Text(comp, SWT.BORDER | SWT.PASSWORD);
			textPassword.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 1, 1));
			
			butLoguj = new Button(comp, SWT.PUSH);
			butLoguj.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 2, 1));
			butLoguj.setText("Loguj");
			butLoguj.addMouseListener(new MouseAdapter() {
				public void mouseUp(MouseEvent e) {
					presenter.onConfirm(textEmail.getText(), textPassword.getText());
				}
			});
			
			butRejestruj = new Button(comp, SWT.PUSH);
			butRejestruj.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false, 2, 1));
			butRejestruj.setText("Zarejestruj");
			butRejestruj.addMouseListener(new MouseAdapter() {
				public void mouseUp(MouseEvent e) {
					Main.openRegistration();
				}
			});
		
		}

		@Override
		public void showLoading() {
			
		}

		@Override
		public void showError(String errorMessage) {
			BoxMessage.showMessage(getShell(), "B³¹d", errorMessage, SWT.ICON_WARNING);
			System.out.println("Error "+errorMessage);
			
		}

		@Override
		public void onLoginSuccess(User user) {
			Main.openIntro(user);
			
		}

		@Override
		public void showLoginIsCorrect() {
			System.out.println("LOGIN IS CORRECT ");
			
		}

		@Override
		public void showLoginIsIncorrect() {
			System.out.println("LOGIN IS INCORRECT ");
		}

		
		
		
	}
	
	

