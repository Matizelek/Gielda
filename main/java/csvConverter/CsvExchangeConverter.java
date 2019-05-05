package csvConverter;

import java.util.ArrayList;
import java.util.List;

public class CsvExchangeConverter {
    public List<ExchangeCsvModel> convert(String csvContent) {

        String lines[] = csvContent.split("\\r?\\n");
        List<ExchangeCsvModel> exchanges = new ArrayList();

        for (int i = 0; i < lines.length; i++) {
            if (i > 0) {
                exchanges.add(convertLine(lines[i]));
            }
        }


        return exchanges;
    }

    private ExchangeCsvModel convertLine(String line) {
        String[] split = line.split(",");

        String name = split[1];
        String ISIN = split[2];
        String currency = split[3];
        double openingPrice = Double.parseDouble(parseZeroToDouble(split[4]));
        double maxPrice = Double.parseDouble(parseZeroToDouble(split[5]));
        double minPrice = Double.parseDouble(parseZeroToDouble(split[6]));
        double closingPrice = Double.parseDouble(parseZeroToDouble(split[7]));
        double change = Double.parseDouble(parseZeroToDouble(split[8]));
        double volume = Double.parseDouble(parseZeroToDouble(split[9]));
        int transcationAmount = Integer.parseInt(parseZeroToDouble(split[10]));
        double trade = Double.parseDouble(parseZeroToDouble(split[11]));

        return new ExchangeCsvModel(name, ISIN, currency, openingPrice, maxPrice, minPrice, closingPrice, change, volume, transcationAmount, trade);
    }
    
    private String parseZeroToDouble(String str) {
//    	str.replaceAll("\"", "");
//    	String result=str;
//    	if(str.equals("\"0\"")) {
//    		result = "0.0";
//    	}
    	return str.replaceAll("\"", "");
    }
}
