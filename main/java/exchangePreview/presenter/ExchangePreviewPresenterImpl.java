package exchangePreview.presenter;

import java.util.ArrayList;
import java.util.List;
import org.eclipse.swt.widgets.Shell;

import exchange.Exchange;
import exchange.repository.ExchangeRepository;
import exchangePreview.model.ExchangePreviewModel;
import exchangePreview.model.ExchangePreviewSortOption;
import exchangePreview.view.ExchangePreviewView;
import exchangePreview.view.ExchangePreviewViewModel;
import time.exchangeDate.ExchengeDateRepository;
import time.timeInfoProvider.TimeInfoProvider;
import time.timeInfoProvider.TimeInformator;
import time.timeManager.TimeManager;
import time.timeOfDay.TimeOfDay;

public class ExchangePreviewPresenterImpl implements ExchangePreviewPresenter{

	ExchangeRepository exchangeRepository;
	ExchangePreviewView exchangePreviewView;
	ExchengeDateRepository exchengeDateRepository;
	final private ExchangePreviewModel model;
	
	public ExchangePreviewPresenterImpl(ExchangePreviewView exchangePreviewView, ExchangeRepository exchangeRepository, ExchengeDateRepository exchengeDateRepository) {
		this.exchangeRepository = exchangeRepository;
		this.exchangePreviewView = exchangePreviewView;
		this.exchengeDateRepository = exchengeDateRepository;
		this.model = new ExchangePreviewModel();
	}
	
	
	@Override
	public void start(TimeOfDay timeOfDay) {
		
		exchangePreviewView.initSortCombo();
		
		List<ExchangePreviewViewModel> viewModels = getExchangePreviewModels(timeOfDay);

		exchangePreviewView.showExchangePreviews(viewModels);
	}
	
	private List<ExchangePreviewViewModel> getExchangePreviewModels(TimeOfDay timeOfDay) {

        List<Exchange> exchanges = exchangeRepository.getAllExchanges();

        List<ExchangePreviewViewModel> viewModels = new ArrayList<>();

        for (Exchange exchan: exchanges){
            Exchange exchange = exchan;

            ExchangePreviewViewModel viewModel = mapToViewModel(timeOfDay, exchange);

            viewModels.add(viewModel);
        }
        return viewModels;
    }

    private ExchangePreviewViewModel mapToViewModel(TimeOfDay timeOfDay, Exchange exchange) {
        String currentPrice = exchange.getCurrentPrice(timeOfDay).toString("z³");

        return new ExchangePreviewViewModel(
        		exchange.getCompanyId(),
                exchange.getCompanyName(),
                exchange.getIsin(),
                exchange.getAmount(),
                currentPrice,
                "");

    }

	@Override
	public void onSort(ExchangePreviewSortOption sortOption) {
		TimeInfoProvider time = new TimeInformator();
		TimeManager timeManager = new TimeManager(exchengeDateRepository,time);
		model.sort(sortOption, timeManager.returnEqualsOrAfterDate(sortOption.getDays()));
		exchangePreviewView.showExchangePreviewsSorted(model.sort(sortOption, timeManager.returnEqualsOrAfterDate(sortOption.getDays())));
		
	}
	
	@Override
	public void getExchangePreviewViewModel(TimeOfDay timeOfDay) {
		List<ExchangePreviewViewModel> viewModels = getExchangePreviewModels(timeOfDay);

		exchangePreviewView.showExchangePreviews(viewModels);
	}

	@Override
	public void onChartView(int id, Shell parent) {
		if(id>0) {
			model.generateChart(id, parent);
		}
	}
	
	
}
