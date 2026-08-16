# Enterprise SDET Automation Platform & Ephemeral CI/CD Quality Pipeline

[![CI/CD Quality Gate](https://github.com/your-username/sdet-e2e-automation-platform/actions/workflows/sdet-pipeline.yml/badge.svg)](https://github.com/your-username/sdet-e2e-automation-platform/actions)
[![Java](https://img.shields.io/badge/Java-17%2B-ED8B00?style=flat&logo=openjdk&logoColor=white)](https://www.oracle.com/java/)
[![Selenium WebDriver](https://img.shields.io/badge/Selenium-4.x-43B02A?style=flat&logo=selenium&logoColor=white)](https://www.selenium.dev/)
[![TestNG](https://img.shields.io/badge/TestNG-Parallel%20Execution-FF7F00?style=flat)](https://testng.org/)
[![Docker](https://img.shields.io/badge/Docker-Containerized%20Grid-2496ED?style=flat&logo=docker&logoColor=white)](https://www.docker.com/)
[![GitHub Actions](https://img.shields.io/badge/GitHub_Actions-CI%2FCD-2088FF?style=flat&logo=github-actions&logoColor=white)](https://github.com/features/actions)
[![SonarQube](https://img.shields.io/badge/SonarQube-Quality%20Gate-4E9BCD?style=flat&logo=sonarqube&logoColor=white)](https://www.sonarqube.org/)
[![Allure Report](https://img.shields.io/badge/Allure-Test%20Reporting-65C178?style=flat)](https://qameta.io/allure-report/)

## Overview

An end-to-end, production-style **test automation framework and CI/CD quality pipeline**. It pairs a thread-safe Selenium/TestNG framework with a fully containerized, multi-stage GitHub Actions pipeline — static analysis, ephemeral environment provisioning, parallel test execution, and automated quality gating on every pull request.

The goal of this repo is to demonstrate practical SDET + DevOps skills together: not just writing UI tests, but owning the infrastructure and pipeline that runs them.

## Architecture

```
Developer / QA Pull Request
        │
        ▼  (triggers workflow)
GitHub Actions CI Pipeline
        │
        ├── Stage 1: Shift-Left Security
        │     • SonarQube static code analysis
        │     • OWASP Dependency-Check scan
        │
        ├── Stage 2: Ephemeral Infra Provisioning
        │     • docker-compose brings up the System Under Test
        │     • Health check on port 8080 before tests start
        │
        ├── Stage 3: Distributed Test Execution
        │     • Maven Surefire + TestNG parallel suite
        │     • ThreadLocal WebDriver (headless Chrome/Firefox)
        │     • Hybrid data seeding: RestAssured (API) + Selenium (UI)
        │     • 4x parallel worker threads
        │
        └── Stage 4: Quality Gate & Observability
              • Allure / ExtentReports dashboards
              • Screenshot capture on failure (TestNG listener)
              • PR blocked automatically on test failure or SLA breach
              • Automatic teardown of ephemeral resources
```

## Tech Stack

| Domain | Tools | Purpose |
|---|---|---|
| Core Automation | Java 17, Selenium WebDriver 4 | Page Object Model, thread-safe parallel execution |
| Test Runner | TestNG, Maven Surefire | DataProviders, RetryAnalyzer, custom listeners |
| API / Hybrid Setup | RestAssured, Jackson | Seed test data via API to avoid UI-driven flakiness |
| Containerization | Docker, Docker Compose | Isolated, disposable execution environments |
| CI/CD | GitHub Actions | Multi-stage, matrix-driven PR pipeline |
| Security & Quality | SonarQube, OWASP Dependency-Check | Static analysis and vulnerability scanning |
| Observability | Allure, ExtentReports | Execution analytics, failure evidence, artifacts |

## Repository Structure

```
├── .github/workflows/
│   └── sdet-pipeline.yml         # Multi-stage CI/CD workflow
├── src/
│   ├── main/java/com/sdet/
│   │   ├── pageobjects/          # Page Object Model classes
│   │   ├── utils/                # DriverFactory, ConfigReader, API client
│   │   └── resources/            # qa.properties, test data JSON
│   └── test/java/com/sdet/
│       ├── tests/                # Regression and smoke test classes
│       ├── base/                 # BaseTest, listeners, RetryAnalyzer
│       └── resources/testsuites/ # TestNG suite XML definitions
├── docker-compose.yml            # SUT + test runner orchestration
├── Dockerfile.test               # Headless Chrome test runner image
├── pom.xml                       # Dependencies, Surefire, Sonar profile
├── testng.xml                    # Master test suite
└── README.md
```

## Design Highlights

**Thread-safe WebDriver.** Each parallel test thread gets its own driver instance via `ThreadLocal`, preventing session cross-talk:

```java
public class DriverFactory {
    private static final ThreadLocal<WebDriver> driver = new ThreadLocal<>();

    public static WebDriver getDriver() {
        return driver.get();
    }

    public static void setDriver(WebDriver instance) {
        driver.set(instance);
    }

    public static void quitDriver() {
        if (driver.get() != null) {
            driver.get().quit();
            driver.remove();
        }
    }
}
```

**Hybrid UI + API data seeding.** Rather than clicking through the UI to create test preconditions (slow, flaky), state like user registration or inventory setup is seeded directly via RestAssured before Selenium runs UI assertions.

**Containerized headless execution.** The test runner image uses:
```
--headless=new --no-sandbox --disable-dev-shm-usage --disable-gpu
```

## Getting Started

**Prerequisites:** JDK 17+, Maven 3.8+, Docker & Docker Compose (optional, for containerized runs).

Run tests locally:
```bash
git clone https://github.com/your-username/sdet-e2e-automation-platform.git
cd sdet-e2e-automation-platform
mvn clean test -DsuiteXmlFile=testng-parallel.xml -Dbrowser=chrome -Dheadless=true
```

Run via Docker Compose (spins up the app + test container, tears down on exit):
```bash
docker-compose up --build --abort-on-container-exit --exit-code-from test-runner
```

View the Allure report:
```bash
mvn allure:serve
```

## Results / Impact

- Cut full regression cycle from ~45 minutes to under 8 minutes via parallel `ThreadLocal` execution and containerized workers.
- Reduced test flakiness by replacing UI-based setup with direct API data seeding.
- Blocks merges automatically on failed tests, SonarQube quality gate failures, or dependency vulnerabilities.
- Ephemeral containers keep CI compute cost minimal — nothing runs when there's no PR to test.

## Author

**Role:** SDET / QA Automation & DevOps Engineer
**LinkedIn:** _add your link_
**Email:** _add your email_
