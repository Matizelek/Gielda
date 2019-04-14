package company;

public class Company {

    private final int id;
    private final String name;
    private final String ISIN;

    public Company(int id, String name, String ISIN) {
        this.id = id;
        this.name = name;
        this.ISIN = ISIN;
    }
}
