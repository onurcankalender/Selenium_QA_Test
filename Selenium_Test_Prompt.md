# Cursor Prompt: Selenium UI Test (Insider Careers – Simple POM, no deep-linking)

> Use this prompt in Cursor to scaffold a small, clean Selenium project that follows Page Object Model (POM), **starts only from the main homepage URL**, and navigates exclusively via UI interactions (hover/click). The flow and minimal requirements below are adapted from the provided PDF brief.

---

## Objective

Create a **single end-to-end UI test** that:

1. Opens Insider’s **main homepage** (this is the **only** direct URL you may load).
2. Hovers the **“Company”** menu in the top navigation, then **clicks “Careers”** once it becomes visible.
3. On the Careers page (URL should **contain `/careers/`**), clicks the button **“Find your dream job”**.
4. On the Jobs page:
   - Uses the **Location** select filter to choose **“Istanbul, Turkey”**.
   - Uses the **Department** select filter to choose **“Quality Assurance”**.
   - Stays on the **first job card** and, when visible, clicks **“View Role.”**
5. A new tab opens for the job detail (Lever page). **Switch** to that tab and click **“APPLY FOR THIS JOB”** (top-right of the page).
6. **End the test** after clicking the apply button.

---

## Key Constraints & Requirements

- **Programming language:** Java  
- **Libraries/Frameworks:** Selenium WebDriver + TestNG (or JUnit), WebDriverManager  
- **Architecture:** **Simple POM**, no BDD.  
- **Navigation rule:** **Only** call `driver.get()` for the **homepage**. All subsequent pages must be reached via **hover + click** interactions—no direct `get()` to deep URLs.  
- **Stability:** Use **explicit waits** (WebDriverWait) and **hover actions** (Actions API).  
- **Reporting:** On **any test failure**, take a **screenshot** and save it under `./artifacts/screenshots/`.  
- **Assertions:** Add sanity assertions that pages/elements are visible and that the **filters are applied** before clicking “View Role.”

---

## Project Skeleton

```
selenium-insider-careers/
├─ pom.xml
├─ README.md
├─ src
│  ├─ main/java
│  │  └─ com.example.insider
│  │     ├─ core/
│  │     │  ├─ DriverFactory.java
│  │     │  ├─ BasePage.java
│  │     │  └─ Waiter.java
│  │     └─ pages/
│  │        ├─ HomePage.java
│  │        ├─ NavBar.java
│  │        ├─ CareersPage.java
│  │        ├─ JobsPage.java
│  │        └─ JobDetailPage.java
│  └─ test/java
│     └─ com.example.insider/tests/
│        └─ CareersE2ETest.java
└─ artifacts/
   └─ screenshots/   (auto-created)
```

---

## Locators & Interaction Hints

- **Nav bar → “Company” (hover)**  
  - `//nav//a[normalize-space()="Company"]`
- **Submenu → “Careers”**  
  - `//nav//a[normalize-space()="Careers"]`
- **Careers → “Find your dream job” button**  
  - `//a[contains(normalize-space(),"Find your dream job")]`
- **Jobs → Location filter**  
  - `//label[contains(., "Location")]/following::select[1]`
- **Jobs → Department filter**  
  - `//label[contains(., "Department")]/following::select[1]`
- **First job card → “View Role”**  
  - `//section[contains(@class,"jobs-list")]//article[1]//a[contains(., "View Role")]`
- **Job detail page → “APPLY FOR THIS JOB”**  
  - `//a[normalize-space()="APPLY FOR THIS JOB" or normalize-space()="Apply for this job"]`

---

## Test Flow

1. **Open Home**  
   - `home.goTo()` loads homepage.  
   - Assert a key homepage element is visible.
2. **Nav → Company → Careers**  
   - Hover then click.  
   - Assert URL contains `/careers/`.
3. **Click “Find your dream job.”**
4. **Apply filters**  
   - Location = Istanbul, Turkey  
   - Department = Quality Assurance
5. **Open first card → View Role**  
   - Switch to new tab.
6. **Click “APPLY FOR THIS JOB.”**  
   - End test.

---

## Assertions

- Homepage loaded (logo or hero section visible).  
- Careers page URL contains `/careers/`.  
- Selected filter values are set (Location & Department).  
- View Role opens new tab and switch successful.  
- Apply button is visible and clickable.

---

## Utilities & Helpers

- `Waiter`: explicit waits (visible, clickable).  
- `BasePage`: common helpers (`hover`, `safeClick`, `selectByVisibleText`, `switchToNewTab`).  
- **Failure screenshot** in `@AfterMethod`.

---

## Test Runner & Config

- **TestNG** suite with one test `CareersE2ETest`.  
- **Maven** deps: `selenium-java`, `webdrivermanager`, `testng`.  
- Default browser: Chrome.  
- Run: `mvn -q -Dtest=CareersE2ETest test`.

---

## Acceptance Criteria

- Test **starts at homepage only**.  
- Uses **hover + click** (no deep links).  
- Implements **POM** with separate page classes.  
- Uses **explicit waits**.  
- **Screenshots** on failure.  
- Ends after clicking **APPLY FOR THIS JOB**.
