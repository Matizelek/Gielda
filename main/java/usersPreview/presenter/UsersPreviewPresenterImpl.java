package usersPreview.presenter;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.platform.commons.util.StringUtils;

import DataBase.ExchangePurchaseSQL;
import exchange.repository.ExchangeRepository;
import money.Money;
import time.timeOfDay.TimeOfDay;
import user.User;
import user.account.UserAccount;
import user.repository.UserRepository;
import usersPreview.model.UsersPreviewModel;
import usersPreview.view.UsersPreviewView;
import usersPreview.view.UsersPreviewViewModel;

public class UsersPreviewPresenterImpl implements UsersPreviewPresenter{

	private final UsersPreviewView view;
	private final UserRepository userRepository;
	private final ExchangeRepository exchangeRepository;
	private UsersPreviewModel model;

	public UsersPreviewPresenterImpl(UsersPreviewView view, UserRepository userRepository,
			ExchangeRepository exchangeRepository) {
		this.view = view;
		this.userRepository = userRepository;
		this.exchangeRepository = exchangeRepository;
		this.model = new UsersPreviewModel();
	}

	@Override
	public void start(TimeOfDay timeOfDay) {
		
		List<UsersPreviewViewModel> viewModels = getUsersPreviewViewModel(timeOfDay);

        view.showUsersPreviewViewModel(viewModels);
		
	}
	
	private List<UsersPreviewViewModel> getUsersPreviewViewModel(TimeOfDay timeOfDay) {

        List<UsersPreviewViewModel> viewModels = new ArrayList<>();
        List<User> users = userRepository.getUsers();
        for(User user : users) {
        	Date firsDate = null;
        	if(ExchangePurchaseSQL.getFirsPurchaseDateUser(user.getId()).isPresent()) {
        		firsDate = ExchangePurchaseSQL.getFirsPurchaseDateUser(user.getId()).get();
        	}
        	UserAccount account =  userRepository.getAccountForUser(user);
        	Money sum = new Money(account.getAccountBalanceDouble()).add(model.getPerhapsSumOfExchanges(account, exchangeRepository,timeOfDay));

        	String dateString = " - ";
        	if(firsDate != null) {
        		dateString = firsDate.toString();
        	}
        	
        	UsersPreviewViewModel viewModel = mapToViewModel( user.getUsername(),dateString, sum);

            viewModels.add(viewModel);
        }
        viewModels = sortAndLimitList(viewModels);
        return viewModels;
    }

    private UsersPreviewViewModel mapToViewModel(String username,String firsDate, Money sum) {
    	
    	if(StringUtils.isBlank(firsDate)) {
    		firsDate = " - ";
    	}

        return new UsersPreviewViewModel(
        		username,
        		firsDate,
        		sum);
    }
    
    private List<UsersPreviewViewModel> sortAndLimitList(List<UsersPreviewViewModel> viewModels){
    	List<UsersPreviewViewModel> result = new ArrayList<>();
    	result = viewModels.stream().sorted(Comparator.comparing(v -> v.getMoney().getCount(),Comparator.reverseOrder()))
    	.limit(10)
        .collect(Collectors.toList());
    	
    	return result;
    }
	
}
