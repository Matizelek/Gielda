package money;

public class Money {

   final private long count;

    public Money(double moneyValue) {

        String[] splitted = String.valueOf(moneyValue).split("\\.");

        if (splitted.length > 1 && splitted[1].length() > 2) {
            throw new IllegalArgumentException();
        }
        count = (long) (moneyValue * 100);
    }
    
    public Money(long moneyValue) {
        count = moneyValue;
    }



    @Override
    public String toString() {

        long full = count / 100;
        long partial = count - (full * 100);
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

    public long getCount() {
        return count;
    }
}
