# Selenium Self-Healing Automation Framework (Stage 1 & Stage 2)

## 🔗 Technology Stack

![Java](https://img.shields.io/badge/Java-17+-orange)
![Selenium](https://img.shields.io/badge/Selenium-WebDriver-green)
![TestNG](https://img.shields.io/badge/TestNG-Framework-blue)
![Build](https://img.shields.io/badge/Build-Maven-brightgreen)

---

## 📌 Project Overview

This project demonstrates a **custom Selenium automation framework** built using **Java, TestNG, and Maven**, with a strong focus on **test stability, self-healing locators, and reliable parallel execution**.

The framework is designed to handle **real-world UI changes gracefully**, learn from past executions, and run **safely in parallel across multiple browsers**. Over time, the framework becomes **more stable** by remembering which locators work best.

This project is implemented in **stages**, with each stage adding practical capabilities on top of a clean base framework.

---

## 🎯 Project Goals

* Build a clean, scalable, and maintainable Selenium automation framework
* Implement a **custom self-healing mechanism** (without third-party tools)
* Enable **thread-safe parallel execution** using TestNG
* Allow the framework to **learn from previous runs**
* Keep the design **simple, deterministic, and transparent**
* Demonstrate strong hands-on understanding of:

  * Framework architecture
  * Page Object Model (POM)
  * TestNG lifecycle, listeners, retry logic
  * Maven-based execution
  * Reporting, screenshots, and failure handling

---

## 🧩 Key Features

* Selenium WebDriver with Java
* TestNG for execution and lifecycle management
* Maven for build and dependency management
* Page Object Model (POM)
* **Custom self-healing locator logic**
* Retry mechanism for critical actions (`click`, `sendText`)
* **Thread-safe WebDriver management using `ThreadLocal<WebDriver>`**
* **Stable parallel execution across browsers (Chrome & Edge)**
* TestNG Listener with:

  * Extent Reports
  * Thread-safe screenshot capture
* CI-friendly execution using `mvn clean test`

---

## 🧠 Why Self-Healing?

In real applications, UI locators often break due to:

* Small attribute changes
* DOM restructuring
* UI refactoring

Instead of failing immediately, this framework **tries alternative locators at runtime**, allowing tests to continue whenever possible.

⚠️ **Note:** This is a **custom deterministic self-healing implementation**, not an AI/ML-based solution. The goal is full control, predictability, and clarity.

---

## 🔄 Self-Healing Mechanism — Stage 1 (Fallback Healing)

Stage 1 focuses on **surviving locator changes**.

### How it works:

1. Each UI element has **multiple locator strategies** (primary + fallbacks)
2. The framework tries locators **one by one**
3. If the primary locator fails, fallback locators are attempted
4. Execution continues as soon as a locator works
5. The test fails **only if all locators fail**

### Example

```java
String[] loginButtonLocators = {
    "id:login-buttonn",        // intentionally broken
    "xpath://input[@value='Login']"
};

selfHeal.click(loginButtonLocators, "loginBtn");
```

---

## 🧠 Self-Healing Mechanism — Stage 2 (Learning & Confidence Scoring)

Stage 2 adds **learning behavior** to the framework.

Instead of forgetting what worked, the framework **remembers successful locators across runs**.

### What Stage 2 adds:

* Persistent storage of successful locators in JSON
* **Confidence score** for each locator
* Reuse of the **most reliable locator first**
* **Score increase on success**
* **Score decrease on failure**
* **Maximum confidence cap** to prevent overfitting

---

## 📊 Confidence Scoring Logic (Simple Rules)

* Locator works → **score +1**
* Locator fails → **score -1** (minimum 0)
* Maximum score capped at **10**
* Locator with highest score is tried first

This keeps learning:

* Stable
* Predictable
* Self-correcting over time

---

## 📁 Healed Locator Storage (Example)

```json
{
  "password": {
    "xpath://input[@placeholder='Passwordd']": 7,
    "id:password": 3
  },
  "loginBtn": {
    "xpath://input[@value='Login']": 10
  },
  "userName": {
    "xpath://input[@placeholder='Username']": 10
  }
}
```

This file shows **which locators worked most often** and how confident the framework is about them.

---

## 🧾 Healing Visibility in Reports

Every healing decision is logged into **Extent Reports**, making test behavior easy to understand.

### Example Report Logs

* Healed locator worked for userName
* Healed locator failed for password → score reduced
* Fallback locator worked for password
* Healed locator worked for loginBtn

This makes debugging and framework behavior **transparent and explainable**.

---

## 🧵 Thread-Safe Parallel Execution

The framework supports **safe parallel execution** using:

* `ThreadLocal<WebDriver>` (one browser per thread)
* Thread-safe driver setup and teardown
* Thread-safe screenshots and reporting

### Sample TestNG Parallel Configuration

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

### High-Level Execution Flow

```
Test Case (Thread)
   ↓
Page Object (UI actions)
   ↓
Self-Healing Engine
   ├─ Try Healed Locator (Highest Confidence)
   │    ├─ Success → Increase Score
   │    └─ Failure → Decrease Score
   ↓
Fallback Locators (Sequential)
   ├─ Success → Save & Increase Score
   └─ Failure → Try Next Locator
   ↓
Healing Store (JSON)
   ├─ Persist Locator + Confidence
   └─ Read Best Locator for Next Run
   ↓
ThreadLocal WebDriver
   ↓
Browser Instance
```

This flow ensures that **each test thread**:

* Uses its **own browser instance**
* Learns independently
* Improves locator reliability over time

```
Test Case (Thread)
   ↓
Page Object
   ↓
Self-Healing Logic
   ↓
Healing Store (JSON)
   ↓
ThreadLocal WebDriver
   ↓
Browser Instance
```

Each test runs in its **own isolated browser session**, ensuring stable and reliable parallel execution.

---

## ▶️ How to Run the Tests

### Prerequisites

* Java 17 or higher
* Maven installed
* Chrome / Edge browser

### Execution

```bash
mvn clean test
```

### Test Application

```
https://www.saucedemo.com
```

---

## 📂 Project Structure (High Level)

```
src/main/java
 ├── base        → Driver & ThreadLocal management
 ├── logic       → Self-healing & learning logic
 ├── pages       → Page Object classes

src/test/java
 ├── tests       → End-to-end and functional tests
 ├── listeners   → Reporting and screenshots
```

---

## ⚠️ Known Limitations

* Locator healing is deterministic (not AI-based)
* DOM similarity matching is not implemented
* Healing decisions are rule-based by design

---

## 🚀 Future Enhancements (Stage 3 Ideas)

* Smarter locator ordering rules (ID vs XPath preference)
* Time-based confidence decay
* Externalized locator definitions
* CI integration with Jenkins / GitHub Actions

---

## 📌 Summary

This project demonstrates a **real-world, learning Selenium automation framework** that:

* Heals broken locators
* Learns from execution history
* Adapts over time
* Runs safely in parallel
* Explains its behavior clearly through reports

The framework is intentionally kept **simple, transparent**, focusing on strong fundamentals rather than hidden magic.

⭐ If you find this project useful, feel free to fork or contribute.
