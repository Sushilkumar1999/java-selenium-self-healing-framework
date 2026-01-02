package autoheal.Logic.in;

public class PromptBuilder {
		
	public static String buildPrompt(String elementName, String pageDom, String [] failedLocators) {
		
		StringBuilder prompt = new StringBuilder();
		
		prompt.append("Imagine yourself as an experienced QA automation engineer and you are helping with selenium test automation.\n\n");
		
		prompt.append("Task: \n\n");
		prompt.append("Suggest one stable optimized selenium locator for element named : \n\n");
		prompt.append("\"").append(elementName).append("\"\n\n");
		
		prompt.append("Rules: \n");
		prompt.append("- Use only one locator. \n");
		prompt.append("- Prefer id,name or CSS selector. \n");
		prompt.append("- Use xpath only if no better option exists. \n");
		prompt.append("- Do not explain anything. \n");
		prompt.append("- Output type must be exactly: \n");
		prompt.append("locatorType:locatorValue\n\n");
		
		prompt.append("Do not add any code blocks.\n");
		prompt.append("Do not add extra text.\n");
		
		prompt.append("HTML snippet : \n");
		prompt.append(pageDom).append("\n\n");
		
		prompt.append("Locators tried and already failed: \n");
		for( String locator : failedLocators) {
			prompt.append(locator).append("\n");
		}
		return prompt.toString();
	}
}
