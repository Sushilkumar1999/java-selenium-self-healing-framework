package autoheal.Logic.in;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;

public class HealingStore {

	// Creating a file path constant.
	private static final String filePath = System.getProperty("user.dir")
			+ "//src//main//resources//Healed_Locators.json";

	// Here object mapper from jackson data bind is used
	private static ObjectMapper mapper = new ObjectMapper();

	// This method converts JSON file into hash map.
	private static HashMap<String, Map<String, Integer>> readFile() {
		try {
			File file = new File(filePath);
			if (!file.exists()) {
				System.out.println("LocatorFile not found.");
				return new HashMap<>();
			}
			return mapper.readValue(file, HashMap.class);
		} catch (Exception e) {
			return new HashMap<>();
		}
	}

	// This method save the successful locator.
	public static void saveLocator(String elementName, String locator) {
		try {
			// creating a HashMap and stroing both ele and locator.
			System.out.println("Inside saveLocator Method.");
			Map<String, Map<String, Integer>> data = readFile();
			data.putIfAbsent(elementName, new HashMap<>());
			Map<String, Integer> locatorMap = data.get(elementName);
			locatorMap.put(locator, locatorMap.getOrDefault(locator, 0) + 1);

			// saving the above hash map to our file with the help of mapper
			File file = new File(filePath);
			mapper.writeValue(file, data);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// This method get the last successful locator for a particular element.
	public static String getBestLocator(String elementName) {
		HashMap<String, Map<String, Integer>> data = readFile();
		Map<String, Integer> locatorMap = data.get(elementName);
		if (locatorMap == null || locatorMap.isEmpty()) {
	        return null;
	    }
		return locatorMap.entrySet().stream().max(Map.Entry.comparingByValue()).get().getKey();
	}

}
