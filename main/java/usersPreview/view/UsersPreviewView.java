package usersPreview.view;

import java.util.List;

import user.User;

public interface UsersPreviewView {

	void showUsersPreviewViewModel(List<UsersPreviewViewModel> usersPreviewViewModel);
	
	void onReturn(User mainUser);
}
