package exchangePreview.view;

import java.util.Date;
import java.util.List;

public class ExchangePreviewChartViewModel {
	
	private final int id;
	private final String name;
	private final List<Date> days;
    private final double[] prices;

	public ExchangePreviewChartViewModel(int id,String name, List<Date> days,double[] prices) {
		this.id= id;
		this.name = name;
		this.days = days;
		this.prices = prices;
	}

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public List<Date> getDays() {
		return days;
	}

	public double[] getPrices() {
		return prices;
	}
	
	
	
	
}
