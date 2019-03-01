package registerTest;

import org.junit.Test;
import org.mockito.Mockito;
import register.presenter.RegisterPresenter;
import register.presenter.RegisterPresenterImpl;
import register.resources.StringResources;
import register.view.RegisterView;
import user.repository.MemoryUserRepository;
import user.repository.UserRepository;

import java.util.ArrayList;

public class RegisterPresenterTest {


    @Test
    public void errorShouldBeCalledOnView() {

        RegisterView mockView = Mockito.mock(RegisterView.class);
        UserRepository repository = new MemoryUserRepository(new ArrayList<>(),new ArrayList<>());
        RegisterPresenter presenter = new RegisterPresenterImpl(mockView, repository);

        presenter.onConfirm("abc@gmail.com", "blabla", "blablabla");

        Mockito.verify(mockView).showError(StringResources.passwordArentTheSameError);

    }

    @Test
    public void correctEmailShouldBeCalledOnView() {


        RegisterView mockView = Mockito.mock(RegisterView.class);
        UserRepository repository = new MemoryUserRepository(new ArrayList<>(),new ArrayList<>());
        RegisterPresenter presenter = new RegisterPresenterImpl(mockView, repository);

        presenter.onLoginTextChanged("rafal.ka@gmail.com");

        Mockito.verify(mockView, Mockito.times(1)).showEmailIsCorrect();

    }

    @Test
    public void wrongEmailShouldBeCalledOnView() {

        RegisterView mockView = Mockito.mock(RegisterView.class);
        UserRepository repository = new MemoryUserRepository(new ArrayList<>(),new ArrayList<>());

        RegisterPresenter presenter = new RegisterPresenterImpl(mockView,repository);

        presenter.onLoginTextChanged("rafal.kagmail.com");

        Mockito.verify(mockView, Mockito.times(1)).showEmailIsIncorrect();

    }

    @Test
    public void successShouldBeCalledOnView() {
        RegisterView mockView = Mockito.mock(RegisterView.class);
        UserRepository repository = new MemoryUserRepository(new ArrayList<>(),new ArrayList<>());
        RegisterPresenter presenter = new RegisterPresenterImpl(mockView, repository);

        presenter.onConfirm("rafal.ka@gmail.com", "blabla", "blabla");

        Mockito.verify(mockView).onRegisterSuccess(Mockito.any());
    }


}
