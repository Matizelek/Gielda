package intro.view;

import java.util.List;

public interface IntroView {

    void setAccountState(String money);

    void showExchangePurchases(List<ExchangePurchaseViewModel> exchangePurchases);
    
    void showExchangePurchasesDetails(List<ExchangePurchaseViewModel> exchangePurchases);

}
