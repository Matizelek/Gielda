package input;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;
import java.util.stream.Stream;

import time.exchangeDate.DateRepository;
import time.exchangeDate.ExchangeDate;
import time.exchangeDate.ExchengeDateRepository;

public class InputDirectory {
	
	private String directory = ".//csv";
	private List<String> filesNames =new ArrayList<String>();
	private List<ExchangeDate> datyGieldowe = new ArrayList<>();
	final private Pattern pattern = Pattern.compile("((19|20)\\d\\d)-(0?[1-9]|1[012])-(0?[1-9]|[12][0-9]|3[01])_akcje\\.csv");

	public InputDirectory(){
		
	}
	
	public boolean checkIfDirectoryExist(){
		File tmpDir = new File(directory);
		return  tmpDir.exists();
	}
	
	public void createDirectory() {
		try {
			Files.createDirectory(Paths.get(directory));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public DateRepository loadDirectory() {
		DateRepository repository = null;
		try{
			@SuppressWarnings("resource")
			Stream<Path> entries = Files.list(Paths.get(directory));
			entries.filter(e ->pattern.matcher(e.getFileName().toString()).matches())
					.map(e ->e.getFileName().toString())
					.forEach(e ->filesNames.add(e));
			
			for (String s : filesNames) {
				String[] parts = s.split("_");
				datyGieldowe.add(new ExchangeDate(parts[0]));
			}
			repository = new ExchengeDateRepository(datyGieldowe);
			
			System.out.println("Ilosc dat: "+datyGieldowe.size()
					+ "\nData poczatkowa: "+datyGieldowe.get(0)
					+ "\nData koncowa: "+datyGieldowe.get(datyGieldowe.size()-1)
					+ "\nRoznica miedzy pierwsza a ostatna data: "+obliczanieZakresu(datyGieldowe));
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		return repository;
	}
	
	private long obliczanieZakresu(List<ExchangeDate> datyGieldowe) {
		long diff = datyGieldowe.get(datyGieldowe.size()-1).getDate().getTime() - datyGieldowe.get(0).getDate().getTime();
		return TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
	}
	
	public List<String> getFileNames(){
		return filesNames;
	}
}
