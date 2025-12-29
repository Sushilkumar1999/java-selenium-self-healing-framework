# Selenium Self-Healing Automation Framework (Stage 1)

## 📌 Project Overview
This project demonstrates a **custom Selenium automation framework** built using  
**Java, TestNG, and Maven**, with a focus on improving test stability through a  
**self-healing locator mechanism**.

The framework is designed to handle **UI locator changes gracefully**, reducing
test failures and manual maintenance effort in real-world automation projects.

---

## 🎯 Project Goal
- Build a clean, maintainable Selenium framework
- Implement a **custom self-healing mechanism** for UI locators
- Demonstrate strong understanding of:
  - Framework design
  - Page Object Model (POM)
  - TestNG lifecycle
  - Maven-based execution
  - Failure handling & reporting

---

## 🧩 Key Features
- Selenium WebDriver with Java
- TestNG for test execution
- Maven for build and dependency management
- Page Object Model (POM)
- **Custom self-healing logic** for locators
- Retry mechanism for `click` and `sendText`
- TestNG Listener with:
  - Extent Reports
  - Screenshot capture on failure
- CI-friendly execution using `mvn test`

---

## 🔄 Self-Healing Logic (Stage 1)
Instead of relying on a single locator, the framework:

1. Maintains **multiple locator strategies** for the same UI element  
2. Tries locators **one by one at runtime**
3. Proceeds with execution when a fallback locator works
4. Fails the test **only if all locators fail**

This approach helps tests survive minor UI changes without requiring
immediate code updates.

---

## 🏗 Framework Flow

Test Case --> Page Object --> SelfHeal Logic --> Selenium WebDriver


---

## ❌ Negative Test Scenario (Proof of Healing)
A negative test case is included where:
- The **primary locator is intentionally incorrect**
- The framework automatically retries a fallback locator
- The test continues execution **without failing**

This demonstrates the self-healing behavior in action.

---

## ▶️ How to Run the Tests

### Prerequisites
- Java 17 or above
- Maven installed
- Chrome browser

### Run Command
```bash
mvn clean test
```

### 🌐 Test Application
https://www.saucedemo.com
(A public demo application used for automation practice)

### 📂 Project Structure (High Level)

src
 ├── main
 │   └── java
 │       ├── base
 │       ├── logic
 │       ├── pageObject
 │       └── listener
 └── test
     └── java
         └── UITestCase

