# Fullstack Selenium Automation Framework
Created By: Muhammad Arsalan

A Maven-based test automation framework for **UI (Selenium WebDriver)** and **API (Rest Assured)** testing, with **TestNG** as the test runner and **Allure Report** for rich, interactive reporting.


---

## Tech Stack

| Tool | Purpose |
|------|---------|
| Java 17 | Programming language |
| Maven | Build and dependency management |
| Selenium 4.x | Browser automation |
| TestNG | Test execution and suites |
| Rest Assured | API automation |
| Allure Report 2.29.0 | Test reporting |
| WebDriverManager | Automatic browser driver setup |
| JSON Schema Validator | API response schema validation |

---

## Project Structure

```
Fullstack_selenium__automation/
├── src/test/java/
│   ├── api/
│   │   ├── base/BaseAPI.java          # Shared API request setup & validations
│   │   ├── get/GetBooking.java        # GET booking endpoints
│   │   └── post/CreateBooking.java    # POST booking endpoints
│   ├── baseTest/
│   │   ├── DriverFactory.java         # Browser launch & teardown
│   │   └── PropertyReader.java        # Config & expectations reader
│   ├── constants/
│   │   └── LandingPageEnum.java       # UI locators
│   ├── pages/
│   │   └── LandingPage.java           # Page Object Model
│   ├── tests/
│   │   ├── E2ETest.java               # UI test suite
│   │   └── E2EAPIRunner.java          # API test suite
│   └── utlis/
│       ├── allure/                    # Allure helpers & cleanup
│       ├── listerners/Listener.java   # TestNG listener (screenshots, cleanup)
│       ├── JSONUtils.java
│       └── Utilities.java
├── src/test/resources/
│   ├── Configuration/
│   │   ├── config.properties          # Environment & browser settings
│   │   └── expectations.properties    # Expected assertion values
│   ├── request/                       # API request JSON payloads
│   ├── schemas/                       # JSON schemas for API validation
│   └── allure.properties
├── Reports/
│   ├── allure-results/                # Raw Allure data (after test run)
│   └── allure-report/                 # Generated HTML report files
├── testng.xml                         # UI test suite
├── API.xml                            # API test suite
├── pom.xml
├── mvnw / mvnw.cmd                    # Maven Wrapper
├── mvnw-jdk21.cmd                     # Maven Wrapper with JDK 21
├── open-allure-report.cmd             # Start Allure server & open Chrome
├── run-tests-and-open-report.cmd      # Run tests + open report
└── serve-allure-report.cmd            # Serve existing HTML report
```

---

## Prerequisites

- **JDK 17+** (JDK 21 recommended)
- **Google Chrome** (for UI tests and viewing reports)
- **IntelliJ IDEA** (optional, recommended)
- Internet connection (for browser drivers, API, and Amazon UI tests)

> Maven is included via the **Maven Wrapper** (`mvnw.cmd`). A global Maven install is not required.

---

## Configuration

### `src/test/resources/Configuration/config.properties`

| Key | Description | Example |
|-----|-------------|---------|
| `browser` | Browser name | `chrome`, `firefox`, `edge` |
| `URL` | UI application URL | `https://www.amazon.com/` |
| `searchProduct` | Product search keyword | `toys` |
| `baseURI` | API base URL | `https://restful-booker.herokuapp.com` |
| `implicit.wait` | Selenium implicit wait (seconds) | `10` |
| `page.load.timeout` | Page load timeout (seconds) | `30` |

### `src/test/resources/Configuration/expectations.properties`

| Key | Description |
|-----|-------------|
| `landingPageTitle` | Expected Amazon page title |
| `createBooking.firstname` | Expected booking first name |
| `createBooking.lastname` | Expected booking last name |
| `createBooking.statusCode` | Expected API success status code |
| `invalidBooking.id` | ID used for negative GET tests |

---

## Running Tests

### From Command Line (Windows)

```powershell
cd Fullstack_selenium__automation

# UI tests (testng.xml)
.\mvnw-jdk21.cmd clean test

# API tests (API.xml)
.\mvnw-jdk21.cmd clean test -Dsurefire.suiteXmlFiles=API.xml
```

### From IntelliJ IDEA

Pre-configured run configurations are available under **Run → Edit Configurations**:

| Configuration | Description |
|---------------|-------------|
| **Allure - Clean Test Serve** | Clean → run tests → start Allure server |
| **Allure - Serve Report** | Start Allure server for existing results |
| **API Tests (API.xml)** | Run API test suite |

You can also right-click `testng.xml`, `API.xml`, or any test class and select **Run**.

---

## Allure Reporting

### Report locations

| Path | Description |
|------|-------------|
| `Reports/allure-results/` | Raw result files (JSON, attachments) — **not for direct browser viewing** |
| `Reports/allure-report/` | Generated static HTML report files |

### View report in Chrome (required)

Allure **must** be served over HTTP. Opening `index.html` directly (`file://`) will show a loading spinner and 404 errors because Chrome blocks local JSON file requests.

**Start the Allure server and view the report at:**

### http://127.0.0.1:5050

#### Option 1 — Quick script (recommended)

```powershell
# Run tests, then open report server
.\run-tests-and-open-report.cmd

# Or only serve an existing report (after tests already ran)
.\open-allure-report.cmd
```

#### Option 2 — Maven

```powershell
# Run tests first
.\mvnw-jdk21.cmd clean test

# Start Allure server (opens at http://127.0.0.1:5050)
.\mvnw-jdk21.cmd allure:serve
```

Keep the **Allure Server** terminal window open while viewing the report. Press **Ctrl+C** to stop the server.

#### Option 3 — IntelliJ

Run the **Allure - Serve Report** or **Allure - Clean Test Serve** configuration, then open:

**http://127.0.0.1:5050**

### Fresh report on every run

Previous Allure data is automatically cleared before each test execution via:

- `AllureCleanup` in the TestNG `Listener` (IntelliJ / TestNG runs)
- Maven `antrun` plugin (before `mvn test`)
- Maven `clean` plugin (on `mvn clean`)

### What you will see in the report

- Test pass / fail / skip status
- `@Epic`, `@Feature`, `@Story` behaviour grouping
- `@Step` level execution steps
- Screenshots (UI tests)
- API request/response attachments (via `AllureRestAssured`)
- JSON response attachments
- Failure screenshots and page source on UI test failures

---

## UI Test Flow

1. `DriverFactory` launches the browser using `config.properties`
2. `LandingPage` (Page Object) performs actions
3. Expected values are read from `expectations.properties`
4. Screenshots are captured and attached to Allure
5. Browser is closed after the test class completes

**Test class:** `src/test/java/tests/E2ETest.java`  
**Suite file:** `testng.xml`

---

## API Test Flow

1. `BaseAPI.setupRequest()` configures Rest Assured with `AllureRestAssured` filter
2. Request body is loaded from `src/test/resources/request/*.json`
3. `BaseAPI.successJsonSpec()` / `negativeSpec()` / `notFoundSpec()` handle common validations
4. JSON schema and field assertions are applied per endpoint
5. Responses are saved under `src/test/resources/response/`

**Test class:** `src/test/java/tests/E2EAPIRunner.java`  
**Suite file:** `API.xml`

---

## Helper Scripts

| Script | Purpose |
|--------|---------|
| `mvnw-jdk21.cmd` | Run Maven with JDK 21 |
| `run-tests-and-open-report.cmd` | Execute tests and open Allure at http://127.0.0.1:5050 |
| `open-allure-report.cmd` | Start Allure server and open Chrome |
| `serve-allure-report.cmd` | Serve existing HTML report without Maven |

---

## Troubleshooting

| Issue | Solution |
|-------|----------|
| Report shows **Loading…** or **404** | Do not open `index.html` directly. Use **http://127.0.0.1:5050** |
| `Reports` folder not visible in IntelliJ | Enable **Show Excluded Files** in Project view, then reload Maven project |
| Port 5050 already in use | Stop the previous Allure server (Ctrl+C) or change `servePort` in `pom.xml` |
| `SeverityLevel` not resolved in IDE | Reload Maven project; ensure JDK 21 is set as Project SDK |
| UI test fails on title | Update `landingPageTitle` in `expectations.properties` if Amazon title changes |

---

## Author

**Muhammad Arsalan**

---

## License

Internal / educational use.
