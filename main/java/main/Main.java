package main;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StackLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

import DataBase.CompanySQL;
import DataBase.DateRepositorySQL;
import DataBase.DbConnection;
import DataBase.DbFunctions;
import DataBase.ExchangePurchaseSQL;
import DataBase.StatisticSQL;
import DataBase.TimeManagerSQL;
import DataBase.UserAccountSQL;
import DataBase.UserSQL;
import buySale.view.BuySaleComposite;
import csvConverter.CsvExchangeConverter;
import csvConverter.CsvReader;
import csvConverter.ExchangeCsvModel;
import exchange.repository.ExchangeRepository;
import exchangePreview.view.ExchnagePreviewComposite;
import input.InputDirectory;
import intro.view.IntroComposite;
import login.view.LoginComposite;
import register.view.RegisterComposite;
import statisticPreview.view.StatisticPreviewComposite;
import time.exchangeDate.DateRepository;
import time.exchangeDate.ExchangeDate;
import time.exchangeDate.ExchengeDateRepository;
import time.timeInfoProvider.TimeInfoProvider;
import time.timeInfoProvider.TimeInformator;
import time.timeManager.TimeManager;
import user.User;
import user.account.UserAccount;
import user.repository.UserRepository;
import usersPreview.view.UsersPreviewComposite;

public class Main {
	
	  static int pageNum = -1;
	  static Shell shell;
	  static Composite contentPanel;
	  static StackLayout layout;
	  static Composite composite;
	  static boolean isDirectoryExist = false;
	  static Date simulatedDate;

		
	static public void main(String[] args) {

		isTablesExists();
		checkDateRepository();

		Display display = new Display();
		shell = new Shell(display);
		shell.setText("VTG " + simulatedDate.toString());
		shell.setSize(250, 200);
		shell.setLayout(new GridLayout(1, false));

		contentPanel = new Composite(shell, SWT.BORDER);
		layout = new StackLayout();
		contentPanel.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		contentPanel.setLayout(layout);

		composite = new LoginComposite(contentPanel, SWT.NONE);

		shell.open();
		layout.topControl = composite;
		composite.layout();
		contentPanel.layout();

		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}

		display.dispose();
	}
	
	
	private static void checkDateRepository() {
		InputDirectory input = new InputDirectory();
		Connection conn = null;
		isDirectoryExist = input.checkIfDirectoryExist();
		if (isDirectoryExist) {
			DateRepository dataRepository = input.loadDirectory();
			int i = 0;
			if (dataRepository != null) {
				try {
					conn = DbConnection.getConnectionAndStartTransaction();
					for (String filename : input.getFileNames()) {
						if (isNewDate(filename)) {

							String fileData = CsvReader.readDataFromCSV(filename);
							CsvExchangeConverter csvConventer = new CsvExchangeConverter();

							for (ExchangeCsvModel csv : csvConventer.convert(fileData)) {
								DbFunctions.setCsvExchangeModel(dataRepository.getExchangeDatePairs().get(i), csv,
										conn);
							}
						}
						i++;
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
			}
		}
		simulatedDate = getSimulatedDate();
	}
	
	private static Date getSimulatedDate() {
		ExchengeDateRepository exchengeDateRepository = new ExchengeDateRepository(DateRepositorySQL.getExchangeDate());
		TimeInfoProvider time = new TimeInformator();
		TimeManager timeManager = new TimeManager(exchengeDateRepository,time);
		return timeManager.simulateCurrentDate(new Date());
	}
	
	public static void getNextSimulatedDate(User user) {
		ExchengeDateRepository exchengeDateRepository = new ExchengeDateRepository(DateRepositorySQL.getExchangeDate());
		TimeInfoProvider time = new TimeInformator();
		TimeManager timeManager = new TimeManager(exchengeDateRepository,time);
		simulatedDate = timeManager.simulateNextDate();
		openIntro(user);
	}
	
	private static void isTablesExists() {
		Connection conn = null;
		try {
			conn = DbConnection.getConnectionAndStartTransaction();
			if (!DbFunctions.isTableExist("company")) {
				CompanySQL.createTableCompany(conn);
			}
			if (!DbFunctions.isTableExist("date_repository")) {
				DateRepositorySQL.createTableDateRepository(conn);
			}

			if (!DbFunctions.isTableExist("time_manager")) {
				TimeManagerSQL.createTableTimeManager(conn);
			}
			if (!DbFunctions.isTableExist("user_repository")) {
				UserSQL.createTableUser(conn);
			}
			if (!DbFunctions.isTableExist("user_account")) {
				UserAccountSQL.createTableUserAccount(conn);
			}
			if (!DbFunctions.isTableExist("exchange_purchase")) {
				ExchangePurchaseSQL.createTableExchangePurchase(conn);
			}
			if (!DbFunctions.isTableExist("statistic")) {
				StatisticSQL.createStatisticTable(conn);
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
	}
	
	private static boolean isNewDate(String filename) {
		String[] parts = filename.split("_");
		List<ExchangeDate> exhcangeDates = DateRepositorySQL. getExchangeDate();
		for(ExchangeDate exchangeDate : exhcangeDates) {
			if(Objects.equals(parts[0], exchangeDate.toString())) {
				return false;
			}
		}
		return true;
	}
	
	public static void openRegistration() {
		composite = new RegisterComposite(contentPanel, SWT.NONE);
		composite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		shell.setSize(250, 200);
		shell.setText("VTG " + simulatedDate.toString()); 
		layout.topControl = composite;
		composite.layout();
		contentPanel.layout();
		  
	}
	
	public static void openLogin() {
		composite = new LoginComposite(contentPanel, SWT.NONE);
		composite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		shell.setSize(250, 200);
		shell.setText("VTG " + simulatedDate.toString()); 
		layout.topControl = composite;
		composite.layout();
		contentPanel.layout();
		  
	}
	
	public static void openIntro(User user) {
		composite = new IntroComposite(contentPanel, SWT.NONE,user, simulatedDate);
		composite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		shell.setSize(850, 400);
		shell.setText("Wstêp "+user.getUsername()+" " + simulatedDate.toString()); 
		layout.topControl = composite;
		composite.layout();
		contentPanel.layout();
		  
	}
	
	public static void openBuySale(User user, UserAccount userAccount, ExchangeRepository exchangeRepository ) {
		composite = new BuySaleComposite(contentPanel,SWT.NONE,user,userAccount,exchangeRepository );
		composite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		shell.setSize(850, 400);
		shell.setText("Kup/Sprzedaj Akcje "+user.getUsername()+" " + simulatedDate.toString()); 
		layout.topControl = composite;
		composite.layout();
		contentPanel.layout();
	}
	
	public static void openStatisticPreview(User user) {
		composite = new StatisticPreviewComposite(contentPanel,SWT.NONE,user);
		composite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		shell.setSize(850, 400);
		shell.setText("Statystyki "+user.getUsername()+" " + simulatedDate.toString()); 
		layout.topControl = composite;
		composite.layout();
		contentPanel.layout();
	}
	
	public static void openExchangePreview(User user, ExchangeRepository exchangeRepository ) {
		composite = new ExchnagePreviewComposite(contentPanel,SWT.NONE,user,exchangeRepository );
		composite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		shell.setSize(850, 400);
		shell.setText("Przegl¹d Gie³dy "+user.getUsername()+" " + simulatedDate.toString()); 
		layout.topControl = composite;
		composite.layout();
		contentPanel.layout();
	}
	
	public static void openUsersPreview(User user,UserRepository userRepository,ExchangeRepository exchangeRepository ) {
		composite = new UsersPreviewComposite(contentPanel,SWT.NONE,user,userRepository,exchangeRepository );
		composite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		shell.setSize(850, 400);
		shell.setText("Przegl¹d U¿ytkowników "+user.getUsername()+" " + simulatedDate.toString()); 
		layout.topControl = composite;
		composite.layout();
		contentPanel.layout();
		
	}
}
