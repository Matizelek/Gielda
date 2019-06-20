package statistic;

import java.util.Date;

public class Statistic {
	
	private final Long id;
	private final Date date;
	private final Long userId;
	private final Double accountBalance;
	
	public Statistic(Long id, Date date, Long userId, Double accountBalance) {
		this.id = id;
		this.date = date;
		this.userId = userId;
		this.accountBalance = accountBalance;
	}

	public Long getId() {
		return id;
	}

	public Date getDate() {
		return date;
	}

	public Long getUserId() {
		return userId;
	}

	public Double getAccountBalance() {
		return accountBalance;
	}
}
