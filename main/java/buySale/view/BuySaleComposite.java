package buySale.view;

import java.util.Date;
import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Spinner;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;

import boxMessage.BoxMessage;
import buySale.presenter.BuySalePresenter;
import buySale.presenter.BuySalePresenterImpl;
import exchange.repository.ExchangeRepository;
import main.Main;
import time.timeOfDay.TimeOfDayProvider;
import user.User;
import user.account.UserAccount;


public class BuySaleComposite extends Composite implements BuySaleView{

	
	BuySalePresenter presenter;
	User mainUser;
	UserAccount userAccount;
	ExchangeRepository exchangeRepository;
	Button btnBuy;
	Button btnSale;
	Button btnReturn;
	Table table;
	TableItem updatedItem;
	Spinner spinner;
	
	
	public BuySaleComposite(Composite parent, int style, User user, UserAccount userAccount, ExchangeRepository exchangeRepository ) {
		super(parent, style);
		this.mainUser = user;
		this.userAccount = userAccount;
		this.exchangeRepository = exchangeRepository;
		initialize();
		initializeVirable();
	}
	
	private void initialize() {
		this.setLayout(new GridLayout(2, false)) ;
		
		presenter = new BuySalePresenterImpl(this,mainUser, userAccount, exchangeRepository);
		
		Composite compLeft = new Composite(this, SWT.BORDER);
		compLeft.setLayout(new GridLayout(2, false));
		compLeft.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		
		Composite compRight = new Composite(this, SWT.BORDER);
		compRight.setLayout(new GridLayout(1, false));
		compRight.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		
		table = new Table(compLeft, SWT.MULTI | SWT.BORDER | SWT.FULL_SELECTION);
		table.setLinesVisible(true);
		table.setHeaderVisible(true);
		table.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 4, 2));
		
		
		btnBuy = new Button(compRight, SWT.WRAP |SWT.PUSH);
		GridData gd_1 = new GridData(SWT.FILL, SWT.FILL, true, false, 2, 1);
		gd_1.widthHint = 5;
		gd_1.heightHint = 50;
		btnBuy.setLayoutData(gd_1);
		btnBuy.setText("Kup");
		btnBuy.addMouseListener(new MouseAdapter() {
			public void mouseUp(MouseEvent e) {
				TableItem[] items = table.getSelection();
				if (items.length == 1) {
					for (TableItem item : items) {
						updatedItem = item;
						int amount= item.getText(4).equals(" - ")?0:Integer.parseInt(item.getText(4));
						presenter.onBuy(new ExchangeBuySaleViewModel(Integer.parseInt(item.getText(5)),item.getText(0),item.getText(1),Integer.parseInt(item.getText(2)),
								item.getText(3),amount), spinner.getSelection());
					}
				}
			}
		});
		
		btnSale = new Button(compRight, SWT.WRAP |SWT.PUSH);
		GridData gd_2 = new GridData(SWT.FILL, SWT.FILL, true, false, 2, 1);
		gd_2.heightHint = 50;
		gd_2.widthHint = 5;
		btnSale.setLayoutData(gd_2);
		btnSale.setText("Sprzedaj");
		btnSale.addMouseListener(new MouseAdapter() {
			public void mouseUp(MouseEvent e) {
				TableItem[] items = table.getSelection();
				if (items.length == 1) {
					for (TableItem item : items) {
						updatedItem = item;
						int amount= item.getText(4).equals(" - ")?0:Integer.parseInt(item.getText(4));
						presenter.onSale(new ExchangeBuySaleViewModel(Integer.parseInt(item.getText(5)),item.getText(0),item.getText(1),Integer.parseInt(item.getText(2)),
								item.getText(3),amount), spinner.getSelection());
					}
				}
			}
		});
		
		spinner = new Spinner(compRight, SWT.BORDER);
	    spinner.setMinimum(1);
	    spinner.setMaximum(100);
	    spinner.setSelection(1);
	    spinner.setIncrement(1);
	    spinner.setPageIncrement(10);
	    spinner.pack();
		
		btnReturn = new Button(compRight, SWT.WRAP |SWT.PUSH);
		GridData gd_3 = new GridData(SWT.FILL, SWT.FILL, true, false, 2, 1);
		gd_3.heightHint = 50;
		gd_3.widthHint = 5;
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
	public void onBuySuccess(ExchangeBuySaleViewModel exchange) {
		setTableItem(exchange, updatedItem);
		updatedItem = null;
	}

	@Override
	public void onSaleSuccess(ExchangeBuySaleViewModel exchange) {
		setTableItem(exchange, updatedItem);
		updatedItem = null;
	}

	@Override
	public void onBuyFail(String error) {
		BoxMessage.showMessage(getShell(), "B³¹d", error, SWT.ICON_WARNING);
		
	}

	@Override
	public void onSaleFail(String error) {
		BoxMessage.showMessage(getShell(), "B³¹d", error, SWT.ICON_WARNING);
	}
	
	@Override
	public void onReturn(User user){
		Main.openIntro(user);
	}
	
	@Override
	public void showExchangeBuySaleModel(List<ExchangeBuySaleViewModel> exchangeBuySale) {
		String[] titles = { "Nazwa", "ISIN","Dostêpna Iloœæ", "Bierz¹ca Cena", "Iloœæ Zakupionych", "ID" };
		for (int i = 0; i < titles.length; i++) {
			TableColumn column = new TableColumn(table, SWT.NONE);
			column.setText(titles[i]);
			if(column.getText().equals("ID")) {
				column.setWidth(0);
				column.setResizable(false);
			}
		}
		
		for (ExchangeBuySaleViewModel exchange : exchangeBuySale) {
		      TableItem item = new TableItem(table, SWT.NULL);
		      setTableItem(exchange, item);
		    }
		
		for (int i = 0; i < titles.length; i++) {
			table.getColumn(i).pack();
		}
		table.setSize(table.computeSize(SWT.DEFAULT, 200));
	}
	
	private void setTableItem(ExchangeBuySaleViewModel exchange,TableItem item ) {
		if (item != null && exchange != null) {
			item.setText(0, exchange.getName());
			item.setText(1, exchange.getIsin());
			item.setText(2, String.valueOf(exchange.getAmount()));
			item.setText(3, exchange.getCurrentValue());
			item.setText(4, String.valueOf(exchange.getUserAmount()).equals("0") ? " - "
					: String.valueOf(exchange.getUserAmount()));
			item.setText(5, String.valueOf(exchange.getId()));
		}
	}
}
