package statisticPreview.view;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;
import org.swtchart.Chart;
import org.swtchart.IAxis;
import org.swtchart.IAxisTick;
import org.swtchart.ILineSeries;
import org.swtchart.LineStyle;
import org.swtchart.ISeries.SeriesType;

import main.Main;
import statisticPreview.model.StatisticPreviewStatisticOption;
import statisticPreview.presenter.StatisticPreviewPresenter;
import statisticPreview.presenter.StatisticPreviewPresenterImpl;
import user.User;

public class StatisticPreviewComposite extends Composite implements StatisticPreviewView{

	
	private User mainUser;
	StatisticPreviewPresenter presenter;
	Combo statisticCombo;
	Button btnReturn;
	Chart chartMounth;
	Chart chartQuarter;
	Chart chartHalfYear;
	Composite comp;


	public StatisticPreviewComposite(Composite parent, int style, User mainUser) {
		super(parent, style);
		this.mainUser = mainUser;
		initialize();
		initVariables();
	}
	

	private void initialize() {
		this.setLayout(new GridLayout(2, false));
		
		presenter = new StatisticPreviewPresenterImpl(this);
		
		comp = new Composite(this, SWT.BORDER);
		comp.setLayout(new GridLayout(2, false));
		comp.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 2, 1));
		
		final TabFolder tabFolder = new TabFolder(comp, SWT.BORDER);
		GridData gd_1 = new GridData(SWT.FILL, SWT.FILL, true, true, 2, 1);
		tabFolder.setLayoutData(gd_1);
		for (StatisticPreviewStatisticOption statics : StatisticPreviewStatisticOption.values()) {
			TabItem tabItem = new TabItem(tabFolder, SWT.NULL);
			tabItem.setText(statics.getName());
			
			if(statics.getId() == 0) {
				chartMounth = new Chart(tabFolder, SWT.NONE);
				chartMounth.setLayoutData( new GridData(SWT.FILL, SWT.FILL, true, true, 2, 1));
				tabItem.setControl(chartMounth);
			}else if(statics.getId() == 1) {
				chartQuarter = new Chart(tabFolder, SWT.NONE);
				chartQuarter.setLayoutData( new GridData(SWT.FILL, SWT.FILL, true, true, 2, 1));
				tabItem.setControl(chartQuarter);
			}else {
				chartHalfYear = new Chart(tabFolder, SWT.NONE);
				chartHalfYear.setLayoutData( new GridData(SWT.FILL, SWT.FILL, true, true, 2, 1));
				tabItem.setControl(chartHalfYear);
			}

			
		}
		
		tabFolder.setSize(400, 200);
		
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
		
	}
	
	public void createChart(StatisticPreviewChartViewModel statistic, Chart chart){
		
		
	    // create a chart

	    // set titles
	    chart.getTitle().setText(mainUser.getUsername());
	    chart.getAxisSet().getXAxis(0).getTitle().setText("Dzieñ");
	    chart.getAxisSet().getYAxis(0).getTitle().setText("Kwoty");
	    // create scatter series
	    ILineSeries series = (ILineSeries) chart.getSeriesSet().createSeries(SeriesType.LINE, "Wartoœæ");
	    series.setLineStyle(LineStyle.SOLID);

	    Date[] days = statistic.getDays().stream().toArray(Date[]::new);
	    series.setYSeries(statistic.getPrices());
	    series.setXDateSeries(days); // xSeries is an array of java.util.Date
	    IAxisTick xTick = chart.getAxisSet().getXAxis(0).getTick();
	    
	    IAxis yAxis = chart.getAxisSet().getYAxis(0);
	    yAxis.adjustRange();
	    
	    yAxis.scrollUp();
	    yAxis.scrollDown();
	    Color color = new Color(Display.getDefault(), 255, 0, 0);
	    series.setSymbolColor(color);
	    

	    DateFormat format = new SimpleDateFormat("dd-MM-yyyy");
	    xTick.setFormat(format);
	    
	    
	    // adjust the axis range
	    chart.getAxisSet().adjustRange();
	    
	    chart.getParent().layout();
	    comp.getParent().layout();
	    

	    comp.getParent().layout();
	    chart.setVisible(true);
	}
	
	private void initVariables() {
		presenter.start(mainUser);
		
	}
	
	@Override
	public void onReturn(User user){
		Main.openIntro(user);
	}
	
	public Chart getChartForStatistic(StatisticPreviewStatisticOption stat) {
		if(stat.getId() == 0) {
			return chartMounth;
		}else if (stat.getId() == 1) {
			return chartQuarter;
		}else {
			return chartHalfYear;
		}
	}
}