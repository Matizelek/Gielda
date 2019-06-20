package exchangePreview.presenter;

import org.eclipse.swt.widgets.Shell;

import exchangePreview.model.ExchangePreviewSortOption;
import time.timeOfDay.TimeOfDay;

public interface ExchangePreviewPresenter {
	
	void start(TimeOfDay timeOfDay);

	void onSort(ExchangePreviewSortOption sortOption);
	
	void getExchangePreviewViewModel(TimeOfDay timeOfDay);

	void onChartView(int id, Shell parent);
	
}
