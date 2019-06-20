package statisticPreview.presenter;

import org.swtchart.Chart;

import DataBase.StatisticSQL;
import statisticPreview.model.StatisticPreviewModel;
import statisticPreview.model.StatisticPreviewStatisticOption;
import statisticPreview.view.StatisticPreviewChartViewModel;
import statisticPreview.view.StatisticPreviewView;
import time.timeManager.TimeManager;
import user.User;

public class StatisticPreviewPresenterImpl implements StatisticPreviewPresenter{
	
	StatisticPreviewView view;
	final private StatisticPreviewModel model;

	public StatisticPreviewPresenterImpl(StatisticPreviewView statisticPreviewComposite) {
		view = statisticPreviewComposite;
		this.model = new StatisticPreviewModel();
	}

	@Override
	public void start(User user) {
		for(StatisticPreviewStatisticOption stat: StatisticPreviewStatisticOption.values()) {
		Chart chart = view.getChartForStatistic(stat);
		StatisticPreviewChartViewModel statistic = StatisticSQL.getStatisticPreviewChartViewModel(user.getId(), TimeManager.getCurrentDate(),stat.getMounths());
		
		
		
		view.createChart(statistic, chart);
		}	
		
	}

}
