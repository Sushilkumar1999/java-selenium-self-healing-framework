# Selenium Self-Healing Automation Framework (Stage 1)

![Java](https://img.shields.io/badge/Java-17+-orange)
![Selenium](https://img.shields.io/badge/Selenium-WebDriver-green)
![TestNG](https://img.shields.io/badge/TestNG-Framework-blue)
![Build](https://img.shields.io/badge/Build-Maven-brightgreen)

---

## 📌 Project Overview

This project showcases a **custom Selenium automation framework** built using **Java, TestNG, and Maven**, with a strong focus on improving test stability through a **custom self-healing locator mechanism** and **thread-safe parallel execution**.

The framework is designed to handle **minor UI locator changes gracefully** and to execute **reliably in parallel across multiple browsers**, reducing flaky test failures and minimizing manual maintenance—a common challenge in real-world automation projects.

---

## 🎯 Project Goals

* Build a clean, scalable, and maintainable Selenium automation framework
* Implement a **custom self-healing mechanism** for UI locators (without third-party tools)
* Enable **true thread-safe parallel execution** using TestNG
* Demonstrate strong hands-on understanding of:

  * Framework architecture and design
  * Page Object Model (POM)
  * TestNG lifecycle, listeners, and retry logic
  * Maven-based execution
  * Failure handling, reporting, and screenshots

---

## 🧩 Key Features

* Selenium WebDriver with Java
* TestNG for test execution and lifecycle management
* Maven for build and dependency management
* Page Object Model (POM)
* **Custom self-healing locator logic**
* Retry mechanism for critical actions (`click`, `sendText`)
* **Thread-safe WebDriver management using `ThreadLocal<WebDriver>`**
* **Stable parallel execution across multiple browsers (Chrome & Edge)**
* TestNG Listener with:

  * Extent Reports
  * Screenshot capture on test failure (thread-safe)
* CI-friendly execution using `mvn clean test`

---

## 🧠 Why Self-Healing?

UI locators often break due to small front-end changes (IDs, attributes, DOM restructuring), causing unnecessary test failures. Instead of failing immediately, this framework attempts **alternative locators at runtime**, allowing tests to continue execution whenever possible.

⚠️ **Note:** This is a **custom deterministic self-healing implementation**, not an AI/ML-based solution (e.g., Healenium). The goal is transparency, control .

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
String[] loginButtonLocators = {
    "id:login-buttonn",        // intentionally broken
    "xpath://input[@value='Login']"
};

selfHeal.click(loginButtonLocators);
```

---

## 🧪 Self-Healing Utility (Sample)

```java
public void click(String[] locators) {
    for (String locator : locators) {
        try {
            By byEle = LocatorUtil.getBy(locator);
            getDriver().findElement(byEle).click();
            System.out.println("Locator worked for click : " + locator);
            return;
        } catch (Exception e) {
            System.out.println("Locator failed for click : " + locator);
        }
    }
    throw new RuntimeException("All locators failed");
}
```

---

## 🧵 Thread-Safe Parallel Execution

The framework supports **safe and stable parallel execution** using:

* `ThreadLocal<WebDriver>` to maintain **one browser per test thread**
* Thread-safe driver initialization and cleanup in the Base class
* Thread-safe screenshot capture in TestNG listeners

Parallel execution is configured using TestNG and allows the same test suite to run simultaneously on **multiple browsers**.

### Sample Parallel TestNG Configuration

```xml
<suite name="Suite" parallel="tests" thread-count="2">
    <test name="Chrome Tests">
        <parameter name="browserName" value="chrome"/>
        <classes>
            <class name="autoheal.UITestCase.in.UITest"/>
        </classes>
    </test>
    <test name="Edge Tests">
        <parameter name="browserName" value="edge"/>
        <classes>
            <class name="autoheal.UITestCase.in.UITest"/>
        </classes>
    </test>
</suite>
```

---

## 🏗 Framework Architecture

```
Test Case (Thread)
   ↓
Page Object
   ↓
Self-Healing Logic
   ↓
ThreadLocal WebDriver
   ↓
Browser Instance
```

Each test runs in its **own isolated browser session**, ensuring no cross-thread interference and stable parallel execution.

---

## ❌ Negative Test Scenario (Proof of Healing)

A dedicated negative test case demonstrates self-healing behavior:

* The **primary locator** for the login button is intentionally incorrect
* Selenium fails to locate the element using the primary locator
* The framework automatically retries using **fallback locators**
* A valid fallback locator succeeds
* The test continues execution **without failure**

---

## ▶️ How to Run the Tests

### Prerequisites

* Java 17 or higher
* Maven installed
* Google Chrome / Microsoft Edge browser

### Test Execution

```bash
mvn clean test
```

### 🌐 Test Application

```
https://www.saucedemo.com
```

---

## 📂 Project Structure (High Level)

```
src/main/java
 ├── base        → Driver & ThreadLocal management
 ├── logic       → Self-healing utilities
 ├── pages       → Page Object classes

src/test/java
 ├── tests       → E2E and functional test cases
 ├── listeners   → Reporting and screenshots
```

---

## ⚠️ Known Limitations

* Locator fallback order is static (no confidence scoring yet)
* Successful fallback locators are not persisted
* No AI/DOM similarity-based healing in Stage 1

---

## 🚀 Future Enhancements

* Persist successful fallback locators for future runs
* Locator confidence scoring
* External locator storage (JSON / YAML)
* Smarter healing strategy based on historical success
* Cross-browser self-healing
* CI integration with Jenkins / GitHub Actions

---

## 📌 Summary

This project demonstrates a **practical, simple Selenium automation framework** with **custom self-healing logic and thread-safe parallel execution**. It addresses real-world automation challenges while keeping the implementation simple, transparent, and fully under developer control.

⭐ If you find this project useful, feel free to fork or contribute.
