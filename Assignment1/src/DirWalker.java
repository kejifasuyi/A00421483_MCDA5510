import java.io.File;
import java.io.FileReader;
import java.io.IOException;
//import java.io.PrintWriter;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.io.BufferedWriter;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.apache.commons.csv.CSVPrinter;

public class DirWalker {
	public static int skippedRows = 0;
	public static int validRows = 0;

	public void walk(String path, CSVPrinter csv) {

		File root = new File(path);
		File[] list = root.listFiles();

		if (list == null)
			return;

		for (File f : list) {
			if (f.isDirectory()) {
				walk(f.getAbsolutePath(), csv);
			} else {
				Reader in;
				try {
					in = new FileReader(f.getAbsoluteFile());
					Iterable<CSVRecord> records = CSVFormat.EXCEL.parse(in);

					for (CSVRecord record : records) {
						String firstName = record.get(0);
						String lastName = record.get(1);
						String stNo = record.get(2);
						String street = record.get(3);
						String city = record.get(4);
						String prov = record.get(5);
						String postCode = record.get(6);
						String country = record.get(7);
						String phNo = record.get(8);
						String email = record.get(9);
						String[] output = new String[] { firstName, lastName, stNo, street, city, prov, postCode,
								country, phNo, email };

						if (firstName == null || firstName.equalsIgnoreCase("") || lastName == null
								|| lastName.equalsIgnoreCase("") || stNo.equalsIgnoreCase("")
								|| street.equalsIgnoreCase("") || city.equalsIgnoreCase("") || prov.equalsIgnoreCase("")
								|| postCode.equalsIgnoreCase("") || country.equalsIgnoreCase("")
								|| phNo.equalsIgnoreCase("") || email == null || email.equalsIgnoreCase("")) {
							skippedRows++;
//							logger.log(Level.INFO,
//									f.getAbsoluteFile() + "This row is incomplete: \nFirst Name: " + firstName
//											+ " Last Name: " + lastName + " Email Address: " + email);
						} else if (firstName.equalsIgnoreCase("first Name") || lastName.equals("Last name")) {

						} else {
							csv.printRecord(output);
							validRows++;
						}

					}

				} catch (IOException e) {
					e.printStackTrace();
					Logger.getLogger("Main").log(Level.WARNING, e.getLocalizedMessage().toString());

				} catch (ArrayIndexOutOfBoundsException e) {
					e.printStackTrace();
					Logger.getLogger("Main").log(Level.SEVERE, e.getLocalizedMessage().toString());

				}

			}
		}

	}

	public static void main(String[] args) throws IOException {
		final long startTime = System.currentTimeMillis();
		System.setProperty("java.util.logging.config.file", "./logging.properties");
		DirWalker fw = new DirWalker();
		BufferedWriter bWriter = Files.newBufferedWriter(Paths.get("A00421483_Output.csv"));

		CSVPrinter csvPrinter = new CSVPrinter(bWriter,
				CSVFormat.DEFAULT.withHeader("First Name", "Last Name", "Street Number", "Street", "City", "Province",
						"Postal Code", "Country", "Phone Number", "Email Address"));
		fw.walk("C:\\Users\\Dunnyfashion\\Documents\\GitHub\\MCDA5510_Assignments\\Sample Data", csvPrinter);
		csvPrinter.flush();
		csvPrinter.close();
		final long endTime = System.currentTimeMillis();
		Logger.getLogger("Main").log(Level.INFO, "Total Number of skipped rows: " + skippedRows);
		Logger.getLogger("Main").log(Level.INFO, "Total Number of valid rows: " + validRows);
		Logger.getLogger("Main").log(Level.INFO, "Total execution time: " + (endTime - startTime) + " ms");

	}

}