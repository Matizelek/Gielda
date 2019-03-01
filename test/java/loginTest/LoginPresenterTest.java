package loginTest;

import hasher.PasswordHasher;
import hasher.SHAPasswordHasher;
import login.presenter.LoginPresenter;
import login.presenter.LoginPresenterImpl;
import login.resources.StringResourcesLogin;
import login.view.LoginView;
import org.junit.Test;
import org.mockito.Mockito;
import user.repository.MemoryUserRepository;
import user.repository.UserRepository;

import java.util.ArrayList;

public class LoginPresenterTest {


    @Test
    public void errorShouldBeCalledOnView() {

        LoginView mockView = Mockito.mock(LoginView.class);
        UserRepository repository = new MemoryUserRepository(new ArrayList<>(),new ArrayList<>());
        PasswordHasher hasher = new SHAPasswordHasher();
        LoginPresenter presenter = new LoginPresenterImpl(mockView, repository);

        String hashed = hasher.hashPassword("blabla");
        repository.addUser(hashed, "moka2@gmail.pl");

        presenter.onConfirm("moka@gmail.pl", "blabla");

        Mockito.verify(mockView).showError(StringResourcesLogin.userNotFound);

    }

    @Test
    public void correctEmailShouldBeCalledOnView() {

        LoginView mockView = Mockito.mock(LoginView.class);
        UserRepository repository = new MemoryUserRepository(new ArrayList<>(),new ArrayList<>());
        PasswordHasher hasher = new SHAPasswordHasher();
        LoginPresenter presenter = new LoginPresenterImpl(mockView, repository);

        String hashed = hasher.hashPassword("blabla");
        repository.addUser(hashed, "moka@gmail.pl");

        presenter.onLoginTextChanged("rafal.ka@gmail.com");

        Mockito.verify(mockView, Mockito.times(1)).showLoginIsCorrect();

    }

    @Test
    public void wrongEmailShouldBeCalledOnView() {

        LoginView mockView = Mockito.mock(LoginView.class);
        UserRepository repository = new MemoryUserRepository(new ArrayList<>(),new ArrayList<>());
        PasswordHasher hasher = new SHAPasswordHasher();
        LoginPresenter presenter = new LoginPresenterImpl(mockView, repository);

        String hashed = hasher.hashPassword("blabla");
        repository.addUser(hashed, "moka@gmail.pl");

        presenter.onLoginTextChanged("rafal.kagmail.com");

        Mockito.verify(mockView, Mockito.times(1)).showLoginIsIncorrect();

    }

    @Test
    public void successShouldBeCalledOnView() {
        LoginView mockView = Mockito.mock(LoginView.class);
        UserRepository repository = new MemoryUserRepository(new ArrayList<>(),new ArrayList<>());
        PasswordHasher hasher = new SHAPasswordHasher();
        LoginPresenter presenter = new LoginPresenterImpl(mockView, repository);

        String hashed = hasher.hashPassword("blabla");
        repository.addUser(hashed, "moka@gmail.pl");
        repository.addUser(hashed, "rafal.ka@gmail.com");

        presenter.onConfirm("rafal.ka@gmail.com", "blabla");

        Mockito.verify(mockView).onLoginSuccess(Mockito.any());
    }
}
