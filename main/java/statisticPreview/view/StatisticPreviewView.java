package statisticPreview.view;

import org.swtchart.Chart;

import statisticPreview.model.StatisticPreviewStatisticOption;
import user.User;

public interface StatisticPreviewView {

	void onReturn(User user);

	void createChart(StatisticPreviewChartViewModel statistic, Chart chart);
	
	Chart getChartForStatistic(StatisticPreviewStatisticOption stat);
}
