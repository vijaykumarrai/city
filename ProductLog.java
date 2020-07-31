package test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ProductLog {
	private static String inputFileName = "C:/work/product.txt";
	private static String DATE_FORMAT_INPUT = "yyyy-mm-dd hh";
	private static String DATE_FORMAT_FILE = "yyyy-mm-dd hh:mm";

	static Map<String, Integer> productUserMap = new HashMap<>();
	static Map<Timestamp, Integer> timestampCount = new HashMap<>();

	/**
	 * Get the count of unique users.
	 * 
	 * @return Map key value pair of product and usercount
	 */
	public static Map<String, Integer> getUniqueUsersCount() {
		return productUserMap;
	}

	/**
	 * Get the count of entries based on timestamp.
	 * 
	 * @param timeStampString1
	 * @param timestampString2
	 * @return int count
	 */
	private static int getTimeStampCount(String timeStampString1, String timestampString2) {
		int resultCount = 0;
		Timestamp t1 = getTimeStamp(timeStampString1, DATE_FORMAT_INPUT);
		Timestamp t2 = getTimeStamp(timestampString2, DATE_FORMAT_INPUT);

		for (Timestamp key : timestampCount.keySet()) {
			if (key.after(t1) && key.before(t2) || (key.equals(t1) || key.equals(t2))) {
				resultCount++;
			}
		}
		return resultCount;
	}

	/**
	 * Read the file and load the data into list.
	 * 
	 * @param fileName
	 */
	public static void loadFile(String fileName) {
		List<String> inputList = new ArrayList<>();
		try {
			File inputF = new File(fileName);
			InputStream inputFS = new FileInputStream(inputF);
			BufferedReader br = new BufferedReader(new InputStreamReader(inputFS));
			inputList = br.lines().skip(1).map(s -> s).collect(Collectors.toList());
			br.close();
		} catch (IOException e) {
			System.out.println(e);
		}
		mapData(inputList);
	}

	/**
	 * Read data and load into Maps of required format.
	 * 
	 * @param inputList
	 */
	private static void mapData(List<String> inputList) {

		for (String line : inputList) {
			String[] values = line.split("      ");
			Integer userCount = 0;
			if (productUserMap.get(values[0].trim()) == null) {
				userCount = 1;
			} else {
				userCount = productUserMap.get(values[0].trim()) + 1;
			}
			productUserMap.put(values[0].trim(), userCount);
			Timestamp timestamp = getTimeStamp(values[2].trim(), DATE_FORMAT_INPUT);
			Integer count = 0;
			if (timestampCount.get(timestamp) == null) {
				count = 1;
			} else {
				count = timestampCount.get(timestamp) + 1;
			}
			timestampCount.put(timestamp, count);
		}
	}

	public static Timestamp getTimeStamp(String strTimeStamp, String format) {
		DateFormat formatter = new SimpleDateFormat(format);
		Date date;
		Timestamp timestamp = null;
		try {
			date = (Date) formatter.parse(strTimeStamp);
			timestamp = new Timestamp(date.getTime());
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return timestamp;
	}

	public static void main(String[] args) {
		if (args != null && args.length == 1)
			inputFileName = args[0];

		loadFile(inputFileName);
		System.out.println("Test case 1");
		Map<String, Integer> productUsers = getUniqueUsersCount();
		for (String key : productUsers.keySet()) {
			System.out.println(key + " : " + productUsers.get(key));
		}
		System.out.println("================================");
		System.out.println("Test case 2");
		System.out.println(getTimeStampCount("2019-01-01 00", "2019-01-03 00"));
		System.out.println("================================");
	}
}
