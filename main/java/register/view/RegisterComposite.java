package register.view;

import java.sql.Connection;
import java.sql.SQLException;
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
import org.eclipse.swt.widgets.Text;

import DataBase.DbConnection;
import DataBase.UserAccountSQL;
import DataBase.UserSQL;
import main.Main;
import register.presenter.RegisterPresenter;
import register.presenter.RegisterPresenterImpl;
import user.User;
import user.repository.MemoryUserRepository;
import user.repository.UserRepository;

public class RegisterComposite extends Composite implements RegisterView{

	List<User> users = new ArrayList<User>();
	UserRepository repository;
	RegisterPresenter presenter;
	Label lblEmail;
	Text textEmail;
	Label lblPasswordFirst;
	Text textPasswordFirst;
	Label lblPasswordSecound;
	Text textPasswordSecound;
	Button butLoguj;
	Button butRejestruj;
	
	
	public RegisterComposite(Composite parent, int style) {
		super(parent, style);
		initialize();
	}
	
	private void initialize() {
		
		this.setLayout(new GridLayout(2, false));
		
		repository = new MemoryUserRepository(UserSQL.getUsersRepository(),UserAccountSQL.getUserAccounts());
		
		presenter = new RegisterPresenterImpl(this,repository);
		
		Composite comp = new Composite(this, SWT.BORDER);
		comp.setLayout(new GridLayout(2, false));
		comp.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 2, 1));
		
		
		lblEmail = new Label(comp, SWT.NONE);
		lblEmail.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false, 1, 1));
		lblEmail.setText("Email: ");
		
		textEmail = new Text(comp, SWT.BORDER);
		textEmail.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 1, 1));
		
		lblPasswordFirst = new Label(comp, SWT.NONE);
		lblPasswordFirst.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false, 1, 1));
		lblPasswordFirst.setText("Has³o: ");
		
		textPasswordFirst = new Text(comp, SWT.BORDER | SWT.PASSWORD);
		textPasswordFirst.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 1, 1));
		
		lblPasswordSecound = new Label(comp, SWT.NONE);
		lblPasswordSecound.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false, 1, 1));
		lblPasswordSecound.setText("Powtórz has³o: ");
		
		textPasswordSecound = new Text(comp, SWT.BORDER | SWT.PASSWORD);
		textPasswordSecound.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 1, 1));
		
		butRejestruj = new Button(comp, SWT.PUSH);
		butRejestruj.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false, 2, 1));
		butRejestruj.setText("Zarejestruj");
		butRejestruj.addMouseListener(new MouseAdapter() {
			public void mouseUp(MouseEvent e) {
				presenter.onConfirm(textEmail.getText(), textPasswordFirst.getText(), textPasswordSecound.getText());
			}
		});
		
		butLoguj = new Button(comp, SWT.PUSH);
		butLoguj.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 2, 1));
		butLoguj.setText("Loguj");
		butLoguj.addMouseListener(new MouseAdapter() {
			public void mouseUp(MouseEvent e) {
				Main.openLogin();
			}
		});
		
	
	}

	@Override
	public void showLoading() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void showError(String errorMessage) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onRegisterSuccess(User user) {
		Connection conn = null;
		Long id = 0l;
		try {
			conn = DbConnection.getConnectionAndStartTransaction();
			id = UserSQL.setUser(user, conn);
			if(!id.equals(0l)) {
				UserAccountSQL.setUserAccount(id, 2000.0, conn);
				user.setId(id);
			}
			DbConnection.commitTransactionAndCloseConnection(conn);
		} catch (SQLException e) {
			e.printStackTrace();
			try {
				DbConnection.closeConnectionAndRollBackTransaction(conn);
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
		Main.openIntro(user);
	}

	@Override
	public void showEmailIsCorrect() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void showEmailIsIncorrect() {
		// TODO Auto-generated method stub
		
	}

}
