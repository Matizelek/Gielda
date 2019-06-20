package csvConverter;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class CsvReader {

	public static String readDataFromCSV(String fileName) {
		String fileString = "";
		
        try (Scanner scanner = new Scanner(new File("csv/"+fileName));) {
            while (scanner.hasNextLine()) {
            	fileString += scanner.nextLine()+"\n";

            }
        } catch (FileNotFoundException e) {
			e.printStackTrace();
		}
        

        return fileString;
    }
	
}
