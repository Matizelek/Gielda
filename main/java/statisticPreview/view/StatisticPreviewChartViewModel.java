package statisticPreview.view;

import java.util.Date;
import java.util.List;

public class StatisticPreviewChartViewModel {

	
	private final int id;
	private final Long userId;
	private final List<Date> days;
    private final double[] prices;

	public StatisticPreviewChartViewModel(int id, Long userId, List<Date> days,double[] prices) {
		this.id= id;
		this.userId = userId;
		this.days = days;
		this.prices = prices;
	}

	public int getId() {
		return id;
	}

	public Long getUserId() {
		return userId;
	}

	public List<Date> getDays() {
		return days;
	}

	public double[] getPrices() {
		return prices;
	}
	
}
