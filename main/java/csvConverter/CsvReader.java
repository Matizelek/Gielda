package csvConverter;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;
import java.util.regex.Pattern;

public class CsvReader {

	public static String readDataFromCSV(String fileName) {
		String fileString = "";
        Path pathToFile = Paths.get("csv/"+fileName);
//        try (BufferedReader br = Files.newBufferedReader(pathToFile,
//                StandardCharsets.US_ASCII)) {
//        	fileString = br.toString();
//        	CsvExchangeConverter csvConventer = new CsvExchangeConverter();
//        	csvConventer.convert(br.toString());
//
//        } catch (IOException ioe) {
//            ioe.printStackTrace();
//        }
//        
        try (Scanner scanner = new Scanner(new File("csv/"+fileName));) {
            while (scanner.hasNextLine()) {
            	fileString += scanner.nextLine()+"\n";

            }
        } catch (FileNotFoundException e) {
			e.printStackTrace();
		}
        

        return fileString;
    }
	
	private static String getRecordFromLine(String line) {
	    String values = "";
	    try (Scanner rowScanner = new Scanner(line)) {
	        rowScanner.useDelimiter(Pattern.compile("(\\n)|;"));
	        while (rowScanner.hasNext()) {
	            values+=rowScanner.next();
	        }
	    }
	    return values;
	}
	
}
