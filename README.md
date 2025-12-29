# Selenium Self-Healing Automation Framework (Stage 1)

## 📌 Project Overview

This project showcases a **custom Selenium automation framework** built using **Java, TestNG, and Maven**, with a strong focus on improving test stability through a **custom self-healing locator mechanism**.

The framework is designed to handle **minor UI locator changes gracefully**, reducing flaky test failures and minimizing manual maintenance—a common challenge in real-world automation projects.

---

## 🎯 Project Goals

* Build a clean, scalable, and maintainable Selenium automation framework
* Implement a **custom self-healing mechanism** for UI locators (without third-party tools)
* Demonstrate strong hands-on understanding of:

  * Framework architecture and design
  * Page Object Model (POM)
  * TestNG lifecycle and listeners
  * Maven-based execution
  * Failure handling and reporting

---

## 🧩 Key Features

* Selenium WebDriver with Java
* TestNG for test execution and lifecycle management
* Maven for build and dependency management
* Page Object Model (POM) for clean separation of concerns
* **Custom self-healing locator logic**
* Retry mechanism for critical actions (`click`, `sendText`)
* TestNG Listener with:

  * Extent Reports
  * Screenshot capture on test failure
* CI-friendly execution using `mvn clean test`

---

## 🧠 Why Self-Healing?

UI locators often break due to small front-end changes (IDs, attributes, DOM restructuring), causing unnecessary test failures. Instead of failing immediately, this framework attempts **alternative locators at runtime**, allowing tests to continue execution whenever possible.

⚠️ **Note:** This is a **custom deterministic self-healing implementation**, not an AI/ML-based solution (e.g., Healenium). The goal is control, transparency, and interview-ready clarity.

---

## 🔄 Self-Healing Mechanism (Stage 1)

The self-healing logic works as follows:

1. Each UI element is associated with **multiple locator strategies** (primary + fallbacks)
2. The framework attempts to locate the element using the **primary locator**
3. If it fails (e.g., `NoSuchElementException`, `TimeoutException`), fallback locators are tried **sequentially**
4. Execution continues as soon as a valid locator is found
5. The test **fails only if all locators fail**

### Example Usage

```java
click(
    By.name("login-button"),   // intentionally broken
    By.xpath("//input[@value='Login']")
);
```

### 🧪 Self-Healing Utility (Sample)

```java
	public void click(String[] locators) {
		for (String locator : locators) {
			try {
				By byEle = LocatorUtil.getBy(locator);
				driver.findElement(byEle).click();
				System.out.println("Locator worked for click : " + locator);
				return;
			} catch (Exception e) {
				System.out.println("Locator failed for click : " + locator);
			}
		}
		throw new RuntimeException("All locators failed");
	}

	// This method coverts text Locator into By
	public static By getBy(String locatorText) {
		//Input: "id:user-name"
		String type = locatorText.split(":")[0].trim(); // "id"
		String value = locatorText.split(":")[1].trim(); // "user-name"

		if (type.equals("id"))
			return By.id(value);
		else if (type.equals("className"))
			return By.className(value);
		else if (type.equals("xpath"))
			return By.xpath(value);
		else if (type.equals("css"))
			return By.cssSelector(value);
		else if (type.equals("tagName"))
			return By.tagName(value);

		throw new RuntimeException("Invalid Locator Type");
		//returns By.id("user-name")
	}
```

This approach allows the test to survive minor UI changes without immediate code updates.

---

## 🏗 Framework Architecture

```
Test Case
   ↓
Page Object
   ↓
Self-Healing Logic
   ↓
Selenium WebDriver
```

Each layer has a single responsibility, keeping the framework modular and easy to extend.

---

## ❌ Negative Test Scenario (Proof of Healing)

A dedicated negative test case is included to demonstrate self-healing behavior:

* The **primary locator** for the login button is intentionally incorrect
* Selenium fails to locate the element using the primary locator
* The framework automatically retries using **fallback locators**
* A valid fallback locator succeeds
* The test continues execution **without failure**

This confirms that the self-healing logic works as intended.

---

## ▶️ How to Run the Tests

### Prerequisites

* Java 17 or higher
* Maven installed and configured
* Google Chrome browser

### Test Execution

```bash
mvn clean test
```

### 🌐 Test Application

```
https://www.saucedemo.com
```

A public demo application commonly used for Selenium automation practice.

---

## 📂 Project Structure (High Level)

```
src/main/java
 ├── base        → Driver setup and core framework logic
 ├── pages       → Page Object classes
 ├── utils       → Self-healing utilities and helpers

src/test/java
 ├── tests       → TestNG test cases
 ├── listeners   → TestNG listeners (Extent Reports, screenshots)
```

---

## 🚀 Future Enhancements (Planned)

* Persist successful fallback locators for future runs
* Locator confidence scoring
* External locator storage (JSON / YAML)
* Parallel execution support
* Cross-browser self-healing
* CI integration with Jenkins/GitHub Actions

---

## 📌 Summary

This project demonstrates a **practical, interview-ready Selenium framework** that addresses real-world automation challenges. The custom self-healing mechanism improves test resilience while keeping the implementation simple, transparent, and fully under developer control.

---

⭐ If you find this project useful, feel free to fork, explore, or suggest improvements.
