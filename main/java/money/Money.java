package money;

public class Money {

   final private int count;

    public Money(double moneyValue) {

        String[] splitted = String.valueOf(moneyValue).split("\\.");

        if (splitted.length > 1 && splitted[1].length() > 2) {
            throw new IllegalArgumentException();
        }
        count = (int) (moneyValue * 100);
    }
    
    public Money(long moneyValue) {
        count = (int) (moneyValue);
    }

    public Money(int count) {
        this.count = count;
    }


    @Override
    public String toString() {

        int full = count / 100;
        int partial = count - (full * 100);
        String partialString = String.valueOf(Math.abs(partial));
        if (partialString.length() == 1)
            partialString = "0" + partialString;

        return String.format("%d.%s", full, partialString);
    }

    public Money add(Money money2) {
        return new Money(count+money2.count);
    }

    public Money minus(Money money2) {
        return new Money(count-money2.count);
    }
    
    public Money multiply(Double multiplier) {
    	return new Money(Math.round(count*multiplier));
    }

    public Money divide(Double divisor) {
    	return new Money(Math.round(count/divisor));
    }

    public int getCount() {
        return count;
    }
}
