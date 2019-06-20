package usersPreview.model;

import java.util.List;
import java.util.Optional;

import exchange.Exchange;
import exchange.ExchangePurchase;
import exchange.repository.ExchangeRepository;
import money.Money;
import time.timeOfDay.TimeOfDay;
import user.account.UserAccount;

public class UsersPreviewModel {

	public Money getPerhapsSumOfExchanges(UserAccount account, ExchangeRepository exchangeRepository, TimeOfDay timeOfDay) {
		Money result = new Money(0);
		List<ExchangePurchase> exchangePurchaseList = account.getExchangePurchaseList();
		for(ExchangePurchase purchase :exchangePurchaseList ) {
			Optional<Exchange> exch= exchangeRepository.findExchange(purchase.getCompanyId());
			if(exch.isPresent()) {
				result = result.add(exch.get().getCurrentPrice(timeOfDay).multiply(purchase.getAmount()));
			}
		}
		
		
		return result;
	}
	
}
