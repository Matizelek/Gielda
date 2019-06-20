package exchangePreview.model;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.swtchart.Chart;
import org.swtchart.IAxis;
import org.swtchart.IAxisTick;
import org.swtchart.ILineSeries;
import org.swtchart.LineStyle;
import org.swtchart.ISeries.SeriesType;

import DataBase.DateRepositorySQL;
import exchangePreview.view.ExchangePreviewChartViewModel;
import exchangePreview.view.ExchangePreviewViewModel;
import time.timeManager.TimeManager;

public class ExchangePreviewModel {

	 private static Font font;
	
	public ExchangePreviewModel() {
		
	}
	
	public List<ExchangePreviewViewModel> sort(ExchangePreviewSortOption sortOption, Date date) {
		return DateRepositorySQL.getSortedExchanges(TimeManager.getCurrentDate(), date, sortOption.getQueryEnd());
	}
	
	public void generateChart(int id, Shell parent) {
		ExchangePreviewChartViewModel exchange = DateRepositorySQL.getExchangePreviewChartViewModel(id, TimeManager.getCurrentDate());
		createWindow(exchange, parent);
		
	}
	
	private void createWindow(ExchangePreviewChartViewModel exchange, Shell parent) {
		Display display = Display.getCurrent();
	    Shell shell = new Shell(parent, SWT.CLOSE);
	    shell.setText("Historia notowañ: "+exchange.getName());
	    shell.setSize(500, 400);
	    shell.setLayout(new FillLayout());
	    

	    font = new Font(display, "Baz", 12, SWT.NONE);
	    shell.setFont(font);

	    createChart(shell, exchange);

	    shell.open();
	    font.dispose();
	}
	
	private Chart createChart(Composite parent, ExchangePreviewChartViewModel exchange)
	{
	    // create a chart
	    Chart chart = new Chart(parent, SWT.NONE);
	    // set titles
	    chart.getTitle().setText(exchange.getName());
	    chart.getAxisSet().getXAxis(0).getTitle().setText("Dzieñ");
	    chart.getAxisSet().getYAxis(0).getTitle().setText("Cena akcji");
	    chart.getAxisSet().getYAxis(0).getTick().setFont(font);
	    chart.getAxisSet().getXAxis(0).getTick().setFont(font);
	    // create scatter series
	    ILineSeries series = (ILineSeries) chart.getSeriesSet().createSeries(SeriesType.LINE, "Akcje");
	    series.setLineStyle(LineStyle.SOLID);

	    Date[] days = exchange.getDays().stream().toArray(Date[]::new);
	    series.setYSeries(exchange.getPrices());
	    series.setXDateSeries(days); // xSeries is an array of java.util.Date
	    IAxisTick xTick = chart.getAxisSet().getXAxis(0).getTick();
	    
	    IAxis yAxis = chart.getAxisSet().getYAxis(0);
	    
	    xTick.setTickMarkStepHint(1);
	    
	    yAxis.zoomOut();
//	    yAxis.adjustRange();
	    
//	    yAxis.scrollUp();
	    yAxis.scrollDown();
	    Color color = new Color(Display.getDefault(), 255, 0, 0);
	    series.setSymbolColor(color);
	    

	    DateFormat format = new SimpleDateFormat("dd-MM-yyyy");
	    xTick.setFormat(format);
	    
	    
	    // adjust the axis range
	    chart.getAxisSet().adjustRange();
	    return chart;
	}
	
}
