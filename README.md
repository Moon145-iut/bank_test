# ParaBank Selenium Automation Framework

A Java + Selenium WebDriver + TestNG + Maven UI automation framework, built with the
Page Object Model, targeting the public ParaBank demo banking site
(https://parabank.parasoft.com). Built as a CV/portfolio project for QA job applications.

## What this project demonstrates
- Page Object Model (POM) design — page logic separated from test logic
- Data-driven testing via a CSV-fed TestNG `@DataProvider`
- Automatic HTML reporting (Extent Reports) with screenshots attached on failure
- A runnable Maven build (`mvn clean test`) — the kind of thing a recruiter can actually clone and run

## Step 1 — Install prerequisites
1. **JDK 17+** — check with `java -version`.
2. **Maven** — check with `mvn -version`.
3. **Google Chrome** (any recent version — WebDriverManager auto-downloads the matching driver).
4. **An IDE** — IntelliJ IDEA Community Edition is the easiest for this stack.
5. **Git**, if you plan to push this to GitHub (you should).

## Step 2 — Register a real ParaBank test account
ParaBank is a live demo app with a real backend, not fixed demo credentials.
Before running anything:
1. Go to https://parabank.parasoft.com/parabank/register.htm
2. Register a test user (pick any username/password).
3. Log in once manually and note your account number(s) under "Accounts Overview."

Pass credentials at runtime instead of committing them to source control:
`-Dparabank.username=... -Dparabank.password=...`.
The CSV success row resolves the same Maven properties automatically.

## Step 3 — Understand the project structure
```
parabank-selenium-framework/
├── pom.xml                          # dependencies + build config
├── testng.xml                       # test suite definition + report listener
├── src/test/java/
│   ├── base/BaseTest.java           # opens/closes the browser before & after each test
│   ├── pages/                       # one class per page (Page Object Model)
│   │   ├── LoginPage.java
│   │   ├── AccountsOverviewPage.java
│   │   ├── TransferFundsPage.java
│   │   ├── BillPayPage.java
│   │   └── RegistrationPage.java
│   ├── tests/                       # the actual test cases
│   │   ├── LoginTest.java           # data-driven, reads login_data.csv
│   │   ├── TransferFundsTest.java   # valid transfer + negative-amount edge case
│   │   ├── BillPayTest.java          # opt-in bill payment flow
│   │   └── RegistrationTest.java     # opt-in registration + duplicate username
│   ├── listeners/ExtentReportListener.java  # wires up HTML report + failure screenshots
│   └── utils/CsvDataReader.java     # tiny CSV parser, no external library needed
└── src/test/resources/testdata/login_data.csv
```

**Why it's organized this way:** if ParaBank changes a button's ID tomorrow, you fix it in
one Page Object — not in every test that clicks that button. This separation is exactly
what interviewers look for when they ask "what is the Page Object Model and why use it?"

## Step 4 — Verify the locators yourself first
The locators in the Page Object classes (`By.id("amount")`, `By.name("username")`, etc.)
match ParaBank's well-documented, stable markup — but **before you trust any locator,
open the real site, right-click the element, and choose "Inspect"** to confirm it. Demo
sites do get tweaked. This verification step is itself worth mentioning in an interview:
it shows you don't blindly copy automation code.

## Step 5 — Run the tests
From the project root:
```bash
mvn clean test -Dheadless=true
```

Authenticated transfer and bill-pay scenarios require a registered account. Provide
`-Dparabank.username`, `-Dparabank.password`, and `-Dparabank.payeeAccount`; those tests
are skipped when properties are absent so a fresh clone cannot move money accidentally.
Or, in IntelliJ: right-click `testng.xml` → **Run**.

You'll see a Chrome window open and drive itself through login and transfer flows.

## Step 6 — Check the results
- **HTML report:** `reports/ExtentReport.html` — open it in any browser.
- **Failure screenshots:** `reports/screenshots/` — automatically attached to the report too.

## Step 7 — Push it to GitHub
```bash
git init
git add .
git commit -m "Initial ParaBank Selenium automation framework"
git branch -M main
git remote add origin https://github.com/Moon145-iut/parabank-selenium-framework.git
git push -u origin main
```
(`.gitignore` already excludes `target/`, `reports/`, and IDE files so your repo stays clean.)

## Step 8 — CI, GitHub Pages, and next extensions
The repository includes `.github/workflows/tests.yml`, which runs Maven in headless Chrome
on every push and pull request. Add GitHub Actions secrets named `PARABANK_USERNAME`,
`PARABANK_PASSWORD`, and `PARABANK_PAYEE_ACCOUNT` for authenticated flows.

To publish the Extent report as a website:
1. Push the repository to GitHub using the commands above.
2. Open **Settings → Pages** and select **GitHub Actions** as the source.
3. Push to the `main` branch or rerun the workflow.
4. Open **Settings → Pages** to copy the published report URL.

Every workflow also stores `parabank-test-reports` as a downloadable artifact. The Pages
deployment runs only after a successful push to `main`; pull requests still run tests and
upload their reports without deploying a public site.

The framework now includes Bill Pay, Registration, duplicate-username coverage, and
additional login edge cases. Before showcasing it, re-check the live locators and consider
adding account-history and registration field-validation scenarios.

## Step 9 — How to describe this on your CV
> Built a UI test automation framework (Java, Selenium WebDriver, TestNG, Maven, Page
> Object Model) for a banking application, covering login and fund-transfer flows with
> data-driven test cases and automated HTML reporting with failure screenshots.

Keep the bullet outcome-focused (what it covers, what it proves) rather than just listing
tool names — recruiters skim for both.
