package money;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

public class MoneyTest {


    @Test
    public void should_return_12_30() {

        Money money = new Money(12.3);

        assertThat(money.toString()).isEqualTo("12.30");
        assertThat(money.getCount()).isEqualTo(1230);

        Money money2= new Money(12.06);

        assertThat(money2.toString()).isEqualTo("12.06");
        assertThat(money2.getCount()).isEqualTo(1206);
    }

    @Test
    public void should_return_minus_12_30() {

        Money money = new Money(-12.3);
        assertThat(money.toString()).isEqualTo("-12.30");

    }

    @Test
    public void error_test() {


        Throwable thrown = catchThrowable(() -> {
            Money money = new Money(12.332);
        });

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class);

        Money money = new Money(12);

    }

    @Test
    public void after_add_should_return_5_26() {
        Money money = new Money(2.20);
        Money money2 = new Money(3.06);

        assertThat(money.getCount()).isEqualTo(220);
        assertThat(money2.getCount()).isEqualTo(306);

        Money newMoney = money.add(money2);

        assertThat(newMoney.getCount()).isEqualTo(526);
    }

    @Test
    public void after_minus_should_return_0_86() {
        Money money = new Money(2.20);
        Money money2 = new Money(3.06);


        Money newMoney = money2.minus(money);

        assertThat(newMoney.getCount()).isEqualTo(86);
    }

    @Test
    public void after_minus_should_return_minus_0_30() {
        Money money = new Money(0.50);
        Money money2 = new Money(0.20);


        Money newMoney = money2.minus(money);

        assertThat(newMoney.getCount()).isEqualTo(-30);
    }

}
