package exchangePreview.view;

import java.util.Date;
import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;

import DataBase.DateRepositorySQL;
import exchange.repository.ExchangeRepository;
import exchangePreview.model.ExchangePreviewSortOption;
import exchangePreview.presenter.ExchangePreviewPresenter;
import exchangePreview.presenter.ExchangePreviewPresenterImpl;
import main.Main;
import time.exchangeDate.ExchengeDateRepository;
import time.timeOfDay.TimeOfDayProvider;
import user.User;

public class ExchnagePreviewComposite extends Composite implements ExchangePreviewView{
	
	
	private User mainUser;
	private ExchangeRepository exchangeRepository;
	ExchangePreviewPresenter presenter;
	Combo comboSort;
	Table table;
	Button btnReturn;
	Button btnChart;


	public ExchnagePreviewComposite(Composite parent, int style, User mainUser, ExchangeRepository exchangeRepository) {
		super(parent, style);
		this.mainUser = mainUser;
		this.exchangeRepository = exchangeRepository;
		initialize();
		initVariables();
	}
	

	private void initialize() {
		this.setLayout(new GridLayout(2, false));
		
		ExchengeDateRepository exchengeDateRepository = new ExchengeDateRepository(DateRepositorySQL.getExchangeDate());
		
		presenter = new ExchangePreviewPresenterImpl(this,exchangeRepository, exchengeDateRepository);
		
		Composite comp = new Composite(this, SWT.BORDER);
		comp.setLayout(new GridLayout(2, false));
		comp.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 2, 1));
		
		comboSort = new Combo(comp, SWT.DROP_DOWN | SWT.READ_ONLY);
		GridData gd_1 = new GridData(SWT.FILL, SWT.FILL, true, false, 2, 1);
		gd_1.heightHint = 10;
		comboSort.setLayoutData(gd_1);
		comboSort.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if(comboSort.getSelectionIndex() != 0) {
					presenter.onSort(ExchangePreviewSortOption.byId(comboSort.getSelectionIndex()));
				}else {
					presenter.getExchangePreviewViewModel(TimeOfDayProvider.getTimeOfDay(new Date()));
				}
			}
		});
		
		table = new Table(comp, SWT.MULTI | SWT.BORDER | SWT.FULL_SELECTION);
		table.setLinesVisible(true);
		table.setHeaderVisible(true);
		table.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 2, 1));
		table.setSize(table.computeSize(SWT.DEFAULT, 200));
		
		btnReturn = new Button(comp, SWT.PUSH);
		GridData gd_2 = new GridData(SWT.RIGHT, SWT.FILL, false, false, 1, 1);
		gd_2.heightHint = 30;
		gd_2.widthHint = 70;
		btnReturn.setLayoutData(gd_2);
		btnReturn.setText("Powrót");
		btnReturn.addMouseListener(new MouseAdapter() {
			public void mouseUp(MouseEvent e) {
				onReturn(mainUser);
			}
		});
		btnChart = new Button(comp, SWT.PUSH);
		GridData gd_3 = new GridData(SWT.LEFT, SWT.FILL, false, false, 1, 1);
		gd_3.heightHint = 30;
		gd_3.widthHint = 110;
		btnChart.setLayoutData(gd_3);
		btnChart.setText("Wyœwietl wykres");
		btnChart.addMouseListener(new MouseAdapter() {
			public void mouseUp(MouseEvent e) {
				displayChart();
			}
		});
		
	}


	private void initVariables() {
		presenter.start(TimeOfDayProvider.getTimeOfDay(new Date()));
		
	}


	@Override
	public void showExchangePreviews(List<ExchangePreviewViewModel> exchangePreviews) {
		table.removeAll();
		table.setRedraw( false );
		while ( table.getColumnCount() > 0 ) {
		    table.getColumns()[ 0 ].dispose();
		}
		String[] titles = { "ID","Nazwa", "ISIN","Dostêpna Iloœæ", "Bierz¹ca Cena" };
		for (int i = 0; i < titles.length; i++) {
			TableColumn column = new TableColumn(table, SWT.NONE);
			column.setText(titles[i]);
			if(column.getText().equals("ID")) {
				column.setWidth(0);
				column.setResizable(false);
			}
		}
		
		for (ExchangePreviewViewModel exchange : exchangePreviews) {
		      TableItem item = new TableItem(table, SWT.NULL);
		      setTableItem(exchange, item);
		    }
		table.setRedraw( true );
		for (int i = 0; i < titles.length; i++) {
			table.getColumn(i).pack();
		}
		
		
	}
	
	@Override
	public void showExchangePreviewsSorted(List<ExchangePreviewViewModel> exchangePreviews) {
		table.removeAll();
		table.setRedraw( false );
		while ( table.getColumnCount() > 0 ) {
		    table.getColumns()[ 0 ].dispose();
		}
		String[] titles = { "ID","Nazwa", "ISIN","Dostêpna Iloœæ", "Bierz¹ca Cena","Ró¿nica" };
		for (int i = 0; i < titles.length; i++) {
			TableColumn column = new TableColumn(table, SWT.NONE);
			column.setText(titles[i]);
			if(column.getText().equals("ID")) {
				column.setWidth(0);
				column.setResizable(false);
			}
		}
		
		for (ExchangePreviewViewModel exchange : exchangePreviews) {
		      TableItem item = new TableItem(table, SWT.NULL);
		      setTableItemSorted(exchange, item);
		    }
		table.setRedraw( true );
		
		for (int i = 0; i < titles.length; i++) {
			table.getColumn(i).pack();
		}
	}
	
	private void setTableItem(ExchangePreviewViewModel exchange,TableItem item ) {
		if (item != null && exchange != null) {

			item.setText(0, String.valueOf(exchange.getId()));
			item.setText(1, exchange.getName());
			item.setText(2, exchange.getIsin());
			item.setText(3, String.valueOf(exchange.getAmount()));
			item.setText(4, exchange.getCurrentValue());
			
		}
	}
	
	private void setTableItemSorted(ExchangePreviewViewModel exchange,TableItem item ) {
		if (item != null && exchange != null) {

			item.setText(0, String.valueOf(exchange.getId()));
			item.setText(1, exchange.getName());
			item.setText(2, exchange.getIsin());
			item.setText(3, String.valueOf(exchange.getAmount()));
			item.setText(4, exchange.getCurrentPrice(TimeOfDayProvider.getTimeOfDay(new Date())));
			item.setText(5, exchange.getDifference());
			
		}
	}
	
	@Override
	public void onReturn(User user){
		Main.openIntro(user);
	}

	@Override
	public void initSortCombo() {
		comboSort.add("Wszystkie Akcje",0);
		for(ExchangePreviewSortOption so :ExchangePreviewSortOption.values()) {
			comboSort.add(so.getName(),so.getId());
		}
		comboSort.select(0);
		
	}
	
	private void displayChart() {
		if (table.getSelection() != null && table.getSelection().length == 1) {
			TableItem[] items = table.getSelection();
			for (TableItem item : items) {
				presenter.onChartView(Integer.parseInt(item.getText(0)), getShell());
			}
		}
	}
}
