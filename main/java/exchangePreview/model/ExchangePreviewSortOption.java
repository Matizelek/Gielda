package exchangePreview.model;

public enum ExchangePreviewSortOption {

	CurrentCours1(1,"Kurs Bie¿¹cy 1 dzieñ", 1,"ORDER BY d1.closing_price DESC LIMIT 10"),
	CurrentCours2(2,"Kurs Bie¿¹cy 7 dni", 7,"ORDER BY d1.closing_price DESC LIMIT 10"),
	ChangeCourse1(3,"Zmiana kursu w ci¹gu 1 dnia", 1, "ORDER BY difference DESC LIMIT 10"),
	ChangeCourse2(4,"Zmiana kursu w ci¹gu 7 dnia", 7, "ORDER BY difference DESC LIMIT 10"),
	Aggressive(5,"Propozycja gry Agresywnie", 7,"ORDER BY difference DESC LIMIT 3"),
	Gentle(6,"Propozycja gry £agodnie", 7,"ORDER BY difference LIMIT 3"),
	;
	
	private int id;
	private String name;
	private int days;
	private String queryEnd;
	
	ExchangePreviewSortOption(int id,String name,int days,String queryEnd) {
		this.id = id;
		this.name = name;
		this.days = days;
		this.queryEnd = queryEnd;
	}

	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getDays() {
		return days;
	}

	public void setDays(int days) {
		this.days = days;
	}

	public String getQueryEnd() {
		return queryEnd;
	}

	public void setQueryEnd(String queryEnd) {
		this.queryEnd = queryEnd;
	}
	
	public static ExchangePreviewSortOption byId(int id) {
		for(ExchangePreviewSortOption so: ExchangePreviewSortOption.values()) {
			if(so.id == id) {
				return so;
			}
		}
		return null;
	}
	
	
}
