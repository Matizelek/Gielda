package intro.view;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;

import DataBase.CompanySQL;
import DataBase.UserAccountSQL;
import DataBase.UserSQL;
import exchange.repository.ExchangeRepository;
import exchange.repository.MemoryExchangeRepository;
import intro.presenter.IntroPresenter;
import intro.presenter.IntroPresenterImpl;
import main.Main;
import time.timeOfDay.TimeOfDayProvider;
import user.User;
import user.account.UserAccount;
import user.repository.MemoryUserRepository;
import user.repository.UserRepository;

public class IntroComposite extends Composite implements IntroView {
	
	List<User> users = new ArrayList<User>();
	User mainUser;
	UserAccount mainUserAccount;
	Date date;
	UserRepository userRepository;
	IntroPresenter presenter;
	ExchangeRepository exchangeRepository;
	Label lblUserBalance;
	Text textUserBalance;
	Label lblLastTransactionBalance;
	Text textLastTransactionBalance;
	Button btnExchangePrieview;
	Button btnUsersPrieview;
	Button btnBuySaleExchange;
	Table table;

	public IntroComposite(Composite parent, int style, User mainUser, Date date) {
		super(parent, style);
		this.mainUser = mainUser;
		this.date = date;
		initialize();
		initVariables();
	}
	
	private void initialize() {
		
		this.setLayout(new GridLayout(2, false)) ;
		
		userRepository = new MemoryUserRepository(UserSQL.getUsersRepository(),UserAccountSQL.getUserAccounts());
		
		exchangeRepository = new MemoryExchangeRepository(CompanySQL.getExchanges(date));
		
		presenter = new IntroPresenterImpl(this,userRepository,exchangeRepository);
		
		
		Composite compLeft = new Composite(this, SWT.BORDER);
		compLeft.setLayout(new GridLayout(2, false));
		compLeft.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		
		Composite compRight = new Composite(this, SWT.BORDER);
		compRight.setLayout(new GridLayout(1, false));
		compRight.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		
		lblUserBalance = new Label(compLeft, SWT.NONE);
		lblUserBalance.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false, 1, 1));
		lblUserBalance.setText("Stan konta: ");
		
		textUserBalance = new Text(compLeft, SWT.BORDER);
		textUserBalance.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 1, 1));
		textUserBalance.setEditable(false);
		
		lblLastTransactionBalance = new Label(compLeft, SWT.NONE);
		lblLastTransactionBalance.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false, 1, 1));
		lblLastTransactionBalance.setText("Saldo ostatnich transakcji: ");
		
		textLastTransactionBalance = new Text(compLeft, SWT.BORDER);
		textLastTransactionBalance.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 1, 1));
		textLastTransactionBalance.setEditable(false);
		
		table = new Table(compLeft, SWT.MULTI | SWT.BORDER | SWT.FULL_SELECTION);
		table.setLinesVisible(true);
		table.setHeaderVisible(true);
		table.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 4, 2));
		
			      
		btnExchangePrieview = new Button(compRight, SWT.WRAP |SWT.PUSH);
		GridData gd_1 = new GridData(SWT.FILL, SWT.FILL, true, false, 2, 1);
		gd_1.widthHint = 5;
		gd_1.heightHint = 40;
		btnExchangePrieview.setLayoutData(gd_1);
		btnExchangePrieview.setText("Przegl¹daj sytuacjê \nna gie³dzie");
		btnExchangePrieview.addMouseListener(new MouseAdapter() {
			public void mouseUp(MouseEvent e) {
			}
		});
		
		btnUsersPrieview = new Button(compRight, SWT.WRAP |SWT.PUSH);
		GridData gd_2 = new GridData(SWT.FILL, SWT.FILL, true, false, 2, 1);
		gd_2.heightHint = 40;
		gd_2.widthHint = 5;
		btnUsersPrieview.setLayoutData(gd_2);
		btnUsersPrieview.setText("Przegl¹daj najlepiej \nzarabiaj¹cych u¿ytkowników");
		btnUsersPrieview.addMouseListener(new MouseAdapter() {
			public void mouseUp(MouseEvent e) {
			}
		});
		
		btnBuySaleExchange = new Button(compRight, SWT.WRAP |SWT.PUSH);
		GridData gd_3 = new GridData(SWT.FILL, SWT.FILL, true, false, 2, 1);
		gd_3.heightHint = 40;
		gd_3.widthHint = 5;
		btnBuySaleExchange.setLayoutData(gd_3);
		btnBuySaleExchange.setText("Kup lub sprzedaj \nakcje");
		btnBuySaleExchange.addMouseListener(new MouseAdapter() {
			public void mouseUp(MouseEvent e) {
				Main.openBuySale(mainUser, userRepository.getAccountForUser(mainUser), exchangeRepository);
			}
		});
		
	}
	
	private void initVariables() {
		presenter.start(mainUser, TimeOfDayProvider.getTimeOfDay(new Date()));
	}

	@Override
	public void setAccountState(String money) {
		textUserBalance.setText(money);
		
	}

	@Override
	public void setLastTransactionBalance(String money) {
//		textLastTransactionBalance.setText(money);
		
	}

	@Override
	public void showExchangePurchases(List<ExchangePurchaseViewModel> exchangePurchases) {
		String[] titles = { "Nazwa", "Iloœæ", "Bierz¹ca Wartoœæ", "Cena Kupna" };
		for (int i = 0; i < titles.length; i++) {
			TableColumn column = new TableColumn(table, SWT.NONE);
			column.setText(titles[i]);
		}
		for (int i = 0; i < titles.length; i++) {
			table.getColumn(i).pack();
		}
		
		
		for (ExchangePurchaseViewModel exchange : exchangePurchases) {
		      TableItem item = new TableItem(table, SWT.NULL);

		      item.setText(0, exchange.getCompanyName());
		      item.setText(1, String.valueOf(exchange.getAmount()));
		      item.setText(2, exchange.getCurrentValue());
		      item.setText(3, exchange.getPurchaseValue());
		    }
		
		for (int i = 0; i < titles.length; i++) {
			table.getColumn(i).pack();
		}
		
		table.setSize(table.computeSize(SWT.DEFAULT, 200));

	}
	

}
