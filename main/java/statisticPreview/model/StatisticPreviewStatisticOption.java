package statisticPreview.model;


public enum StatisticPreviewStatisticOption {

	mounth(0,"Statystyka miesiêczna", 1),
	quarter(1,"Statystyka kwartalna", 3),
	halfYear(2,"Statystyka pó³roczna", 6);
	
	private int id;
	private String name;
	private int month;
	
	StatisticPreviewStatisticOption(int id, String name, int month){
		this.id = id;
		this.name = name;
		this.month = month;
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

	public int getMounths() {
		return month;
	}

	public void setMonth(int month) {
		this.month = month;
	}
	
	public static StatisticPreviewStatisticOption byId(int id) {
		for(StatisticPreviewStatisticOption so: StatisticPreviewStatisticOption.values()) {
			if(so.id == id) {
				return so;
			}
		}
		return null;
	}
}
