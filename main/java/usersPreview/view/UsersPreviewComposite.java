package usersPreview.view;

import java.util.Date;
import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;

import exchange.repository.ExchangeRepository;
import main.Main;
import time.timeOfDay.TimeOfDayProvider;
import user.User;
import user.repository.UserRepository;
import usersPreview.presenter.UsersPreviewPresenter;
import usersPreview.presenter.UsersPreviewPresenterImpl;

public class UsersPreviewComposite extends Composite implements UsersPreviewView{
	
	UsersPreviewPresenter presenter;
	UserRepository userRepository;
	ExchangeRepository exchangeRepository;
	Table table;
	Button btnReturn;
	User mainUser;

	public UsersPreviewComposite(Composite parent, int style,User user,UserRepository userRepository,ExchangeRepository exchangeRepository ) {
		super(parent, style);
		this.userRepository = userRepository;
		this.exchangeRepository = exchangeRepository;
		this.mainUser = user;
		initialize();
		initializeVirable();
	}
	
	private void initialize() {
		this.setLayout(new GridLayout(2, false)) ;
		
		presenter = new UsersPreviewPresenterImpl(this,userRepository, exchangeRepository);
		
		Composite comp = new Composite(this, SWT.BORDER);
		comp.setLayout(new GridLayout(2, false));
		comp.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		
		table = new Table(comp, SWT.MULTI | SWT.BORDER | SWT.FULL_SELECTION);
		table.setLinesVisible(true);
		table.setHeaderVisible(true);
		table.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 2, 2));
		
		btnReturn = new Button(comp, SWT.WRAP |SWT.PUSH);
		GridData gd_3 = new GridData(SWT.LEFT, SWT.FILL, true, false, 1, 1);
		gd_3.heightHint = 30;
		gd_3.widthHint = 80;
		btnReturn.setLayoutData(gd_3);
		btnReturn.setText("Powrót");
		btnReturn.addMouseListener(new MouseAdapter() {
			public void mouseUp(MouseEvent e) {
				onReturn(mainUser);
			}
		});
	}
	
	private void initializeVirable() {
		presenter.start(TimeOfDayProvider.getTimeOfDay(new Date()));
	}

	@Override
	public void showUsersPreviewViewModel(List<UsersPreviewViewModel> usersPreviewViewModel) {

		table.removeAll();
		table.setRedraw(false);
		while (table.getColumnCount() > 0) {
			table.getColumns()[0].dispose();
		}
		String[] titles = { "Lp", "Mail", "Data zakupienia pierwszej akcji", "Hipotetyczny stan konta" };
		for (int i = 0; i < titles.length; i++) {
			TableColumn column = new TableColumn(table, SWT.NONE);
			column.setText(titles[i]);
		}
		int lp = 1;
		for (UsersPreviewViewModel users : usersPreviewViewModel) {
			TableItem item = new TableItem(table, SWT.NULL);
			setTableItem(lp++, users, item);
		}
		table.setRedraw(true);
		for (int i = 0; i < titles.length; i++) {
			table.getColumn(i).pack();
		}
	}

	private void setTableItem(int lp, UsersPreviewViewModel users, TableItem item) {
		if (item != null && users != null) {
			item.setText(0, Integer.toString(lp));
			item.setText(1, users.getUsername());
			item.setText(2, users.getPurchaseDateOfFirsExchange());
			item.setText(3, users.getMoneyString());
		}
	}

	@Override
	public void onReturn(User mainUser) {
		Main.openIntro(mainUser);
	}

}
