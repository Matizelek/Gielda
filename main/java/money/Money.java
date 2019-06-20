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


    public String toString(String currencyString) {
        return this.toString() + " " + currencyString;
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
        return new Money(count + money2.count);
    }

    public Money minus(Money money2) {
        return new Money(count - money2.count);
    }

    public Money multiply(Double multiplier) {
        return new Money(Math.round(count * multiplier));
    }
    
    public Money multiply(int multiplier) {
        return new Money(Math.round(count * multiplier));
    }

    public Money divide(Double divisor) {
        return new Money(Math.round(count / divisor));
    }

    public long getCount() {
        return count;
    }

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (count ^ (count >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Money other = (Money) obj;
		if (count != other.count)
			return false;
		return true;
	}
    
    
}
