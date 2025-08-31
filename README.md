# Selenium Insider Careers Test

This project implements an end-to-end UI test for the Insider careers website using Selenium WebDriver with Page Object Model (POM) architecture.

## Project Overview

The test automates the following flow:
1. Opens Insider's main homepage
2. Hovers over "Company" menu and clicks "Careers"
3. Clicks "Find your dream job" button
4. Applies filters: Location = "Istanbul, Turkey", Department = "Quality Assurance"
5. Clicks "View Role" on the first job card
6. Switches to the new tab and clicks "APPLY FOR THIS JOB"

## Architecture

- **Page Object Model (POM)**: Each page has its own class with locators and methods
- **Explicit Waits**: Uses WebDriverWait for better stability
- **WebDriverManager**: Automatic driver management
- **TestNG**: Test framework with reporting capabilities

## Project Structure

```
selenium-insider-careers/
├─ pom.xml                          # Maven dependencies
├─ testng.xml                       # TestNG configuration
├─ README.md                        # This file
├─ src/
│  ├─ main/java/
│  │  └─ com/example/insider/
│  │     ├─ core/                   # Core utilities
│  │     │  ├─ DriverFactory.java   # WebDriver management
│  │     │  ├─ BasePage.java        # Common page functionality
│  │     │  └─ Waiter.java          # Explicit wait utilities
│  │     └─ pages/                  # Page objects
│  │        ├─ HomePage.java        # Main homepage
│  │        ├─ CareersPage.java     # Careers page
│  │        ├─ JobsPage.java        # Job listings page
│  │        └─ JobDetailPage.java   # Job detail page
│  └─ test/java/
│     └─ com/example/insider/tests/
│        └─ CareersE2ETest.java     # Main test class
└─ artifacts/
   └─ screenshots/                  # Screenshots on failure (auto-created)
```

## Prerequisites

- Java 11 or higher
- Maven 3.6 or higher
- Chrome browser installed

## Setup Instructions

1. **Clone the repository**:
   ```bash
   git clone <repository-url>
   cd Selenium_QA_Test
   ```

2. **Install dependencies**:
   ```bash
   mvn clean install
   ```

3. **Test website accessibility first** (recommended):
   ```bash
   test-accessibility.bat
   ```
   Or:
   ```bash
   mvn test -Dtest=WebsiteAccessibilityTest
   ```

4. **Run the main test**:
   ```bash
   mvn test
   ```
   Or run a specific test:
   ```bash
   mvn test -Dtest=CareersE2ETest
   ```

## Key Features

### Navigation Constraints
- Only the main homepage URL is loaded directly
- All subsequent navigation is done via UI interactions (hover/click)
- No deep-linking to internal pages

### Stability Features
- Explicit waits for all element interactions
- Hover actions using Actions API
- Page load verification
- Screenshot capture on test failure

### Assertions
- Homepage loading verification
- URL validation for careers page
- Filter selection verification
- Page element visibility checks

## Test Flow Details

1. **Homepage Navigation**
   - Loads `https://useinsider.com/`
   - Verifies homepage elements are visible

2. **Company Menu Interaction**
   - Hovers over "Company" menu item
   - Clicks "Careers" when visible
   - Verifies URL contains `/careers/`

3. **Careers Page**
   - Clicks "Find your dream job" button
   - Waits for page transition

4. **Job Filters**
   - Selects "Istanbul, Turkey" in Location filter
   - Selects "Quality Assurance" in Department filter
   - Verifies filter selections are applied

5. **Job Application**
   - Clicks "View Role" on first job card
   - Switches to new tab
   - Clicks "APPLY FOR THIS JOB" button

## Screenshots

On test failure, screenshots are automatically saved to `./artifacts/screenshots/` with timestamps.

## Troubleshooting

### Common Issues

1. **Chrome Driver Issues**
   - WebDriverManager automatically downloads the correct ChromeDriver version
   - Ensure Chrome browser is installed and up to date

2. **Element Not Found**
   - The test uses explicit waits, but website changes may require locator updates
   - Check the console output for specific error messages

3. **Test Timeout**
   - Increase timeout values in `Waiter.java` if needed
   - Check internet connection and website availability

### Debug Mode

To run with more verbose output:
```bash
mvn test -Dtest=CareersE2ETest -Dselenium.log.level=DEBUG
```

## Contributing

When modifying the test:
1. Update locators if website structure changes
2. Maintain the POM architecture
3. Add appropriate assertions for new functionality
4. Test thoroughly before committing

## Dependencies

- **Selenium WebDriver**: 4.15.0
- **TestNG**: 7.7.1
- **WebDriverManager**: 5.6.2
- **Maven**: 3.6+
- **Java**: 11+
