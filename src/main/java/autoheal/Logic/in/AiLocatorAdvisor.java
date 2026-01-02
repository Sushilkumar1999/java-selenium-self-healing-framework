package autoheal.Logic.in;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class AiLocatorAdvisor {

	private static final String ollamaUrl = "http://localhost:11434/api/generate";
	private static final String model = "llama3";

	public static String locatorAdvisor(String elementName, String pageDom, String[] failedLocators) {
		try {
			// Building the AI prompt.
			String prompt = PromptBuilder.buildPrompt(elementName, pageDom, failedLocators);
			System.out.println("AI prompt sent : " + prompt);
			System.out.println("AI locator advisor invoked for : " + elementName);

			URL url = new URL(ollamaUrl);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Content-Type", "application/json");
			conn.setDoOutput(true);

			String requestBody = "{" + "\"model\":\"" + model + "\"," + "\"prompt\":\"" + escapeJson(prompt) + "\","
					+ "\"stream\":false" + "}";

			try (OutputStream os = conn.getOutputStream()) {
				os.write(requestBody.getBytes(StandardCharsets.UTF_8));
			}

			ObjectMapper mapper = new ObjectMapper();
			JsonNode responseJson = mapper.readTree(conn.getInputStream());
			String aiModelResponse = responseJson.get("response").asText().trim();
			System.out.println("AI model response is : " + aiModelResponse);
			String extractedAIResponse = extractValidLocator(aiModelResponse);
			System.out.println("====================="+extractedAIResponse);
			if (extractedAIResponse != null)
				return extractedAIResponse;
		} catch (Exception e) {
			System.out.println("AI locator advisor failed " + e.getMessage());
		}
		return null;
	}

	private static String escapeJson(String text) {
		return text.replace("\\", "\\\\").replace("\"", "\\\"").replace("\n", "\\n");
	}

	private static String extractValidLocator(String aiModelResponse) {

	    String locatorType = null;
	    String locatorValue = null;

	    String[] lines = aiModelResponse.split("\\R");

	    for (String line : lines) {
	        line = line.trim();

	        if (line.startsWith("locatorType:")) {
	            locatorType = normalizeType(line.replace("locatorType:", "").trim());
	        }

	        if (line.startsWith("locatorValue:")) {
	            locatorValue = line.replace("locatorValue:", "").trim();
	        }

	        // Case where AI already returns id:foo / css:bar
	        if (line.matches("^(id|name|css|xpath):.+")) {
	            return line;
	        }
	    }

	    if (locatorType != null && locatorValue != null) {
	        return locatorType + ":" + locatorValue;
	    }

	    return null;
	}

	
	private static String normalizeType(String type) {
	    switch (type.toLowerCase()) {
	        case "cssselector":
	        case "css":
	            return "css";
	        case "id":
	            return "id";
	        case "name":
	            return "name";
	        case "xpath":
	            return "xpath";
	        default:
	            return null;
	    }
	}


}
