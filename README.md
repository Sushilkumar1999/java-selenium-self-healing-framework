# Selenium Self-Healing Automation Framework (Stages 1–3)

![Java](https://img.shields.io/badge/Java-17+-orange)
![Selenium](https://img.shields.io/badge/Selenium-WebDriver-green)
![TestNG](https://img.shields.io/badge/TestNG-Framework-blue)
![Build](https://img.shields.io/badge/Build-Maven-brightgreen)
![AI](https://img.shields.io/badge/AI-Ollama-Yellow)

---

## 📌 Project Overview

This project demonstrates a **custom-built Selenium automation framework** developed using **Java, TestNG, and Maven**, with a strong focus on **test stability, maintainability, and intelligent self-healing**.

The framework evolves step by step:

* **Stage 1** – Deterministic self-healing with fallback locators
* **Stage 2** – Confidence scoring and learning from past executions
* **Stage 3** – AI-assisted locator healing using **Ollama (LLM)**

The goal is to simulate **real-world UI automation challenges** and solve them using **clean, understandable design**.

---

## 🎯 Project Goals

* Build a clean, scalable Selenium automation framework from scratch
* Reduce flaky failures caused by UI locator changes
* Enable **thread-safe parallel execution**
* Introduce **learning-based locator healing**
* Carefully integrate **AI assistance** without losing control or transparency

---

## 🧩 Key Features

* Selenium WebDriver with Java
* TestNG for execution, retry, listeners, and parallel runs
* Maven-based project structure
* Page Object Model (POM)
* **Custom self-healing locator engine**
* **Thread-safe WebDriver management using ThreadLocal**
* Extent Reports with thread-safe screenshots
* End-to-end flow (login → product selection → checkout)
* CI-friendly execution (`mvn clean test`)

---

## 🧠 Why Self-Healing?

UI locators often break due to:

* Minor attribute changes
* DOM restructuring
* Front-end refactoring

Instead of failing immediately, this framework **recovers at runtime** by:

* Trying alternative locators
* Remembering what worked earlier
* Learning which locator is most reliable
* Asking an AI model **only when needed**

⚠️ This is **not** a black-box AI framework. Every decision is visible, logged, and controlled.

---

## 🔄 Stage 1 – Deterministic Self-Healing

In Stage 1, each element has **multiple locators**:

1. Primary locator
2. One or more fallback locators

### Execution Flow

1. Try primary locator
2. If it fails, try fallback locators one by one
3. Continue execution as soon as one works
4. Fail only if all locators fail

This alone eliminates many flaky failures.

---

## 🧠 Stage 2 – Confidence Scoring (Learning)

Stage 2 adds **memory and learning**.

### How it works

* Every time a locator works → its **score increases**
* If a locator fails → its **score decreases**
* A maximum score cap prevents runaway dominance

Locators are stored in a JSON file:

```json
{
  "userName": {
    "id:user-name": 5,
    "xpath://input[@placeholder='Username']": 2
  },
  "loginBtn": {
    "id:login-button": 5
  }
}
```

### Result

Over time, the framework **automatically prefers the most stable locator**.

---

## 🤖 Stage 3 – AI-Assisted Healing (Ollama)

Stage 3 introduces **AI as a last-resort advisor**.

### When AI is used

AI is invoked **only if**:

1. Stored (learned) locators fail
2. All fallback locators fail

### What AI receives

Instead of the full page HTML, the framework sends a **minimal DOM snapshot**:

```html
<form>
  <input id="user-name" name="user-name" data-test="username" />
  <input id="password" name="password" data-test="password" />
  <input id="login-button" name="login-button" data-test="login-button" />
</form>
```

This keeps AI:

* Fast
* Accurate
* Cost-efficient

### AI Rules (strict)

* Suggest **only one locator**
* Prefer `id`, `name`, or `css`
* Use XPath only if required
* Output format strictly:

```
locatorType:locatorValue
```

If the AI locator works, it is **stored and scored** like any other locator.

---

## 🧵 Thread-Safe Parallel Execution

The framework supports **true parallel execution** using:

* `ThreadLocal<WebDriver>`
* Thread-safe listeners
* Thread-safe healing context

Each test runs in its **own isolated browser session**, eliminating cross-thread interference.

---

## 🏗 Framework Architecture

```
Test (Thread)
   ↓
Page Object
   ↓
SelfHeal Engine
   ↓
Healing Store (JSON)
   ↓
AI Advisor (Stage 3 – optional)
   ↓
ThreadLocal WebDriver
```

---

## ▶️ How to Run

### Prerequisites

* Java 17+
* Maven
* Chrome / Edge
* Ollama (for Stage 3)

### Run tests

```bash
mvn clean test
```

---

## 🌐 Test Application

```
https://www.saucedemo.com
```

---

## 📌 Summary

This project shows a **progressive, real-world Selenium framework**:

* Starts simple
* Learns from failures
* Scales safely in parallel
* Uses AI only when necessary

It is intentionally designed to be:

* Easy to understand
* Easy to extend

⭐ Feel free to fork, explore, or build further stages on top of it.
