package exchangePreview.view;

import java.util.List;

import user.User;

public interface ExchangePreviewView {

	void showExchangePreviews(List<ExchangePreviewViewModel> exchangePreviews);
	
	void showExchangePreviewsSorted(List<ExchangePreviewViewModel> exchangePreviews);
	
	void onReturn(User user);
	
	void initSortCombo();
}
