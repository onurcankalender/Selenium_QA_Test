package com.example.insider.tests;

import com.example.insider.core.DriverFactory;
import com.example.insider.pages.CareersPage;
import com.example.insider.pages.HomePage;
import com.example.insider.pages.JobDetailPage;
import com.example.insider.pages.JobsPage;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class CareersE2ETest {
    
    private WebDriver driver;
    private HomePage homePage;
    private CareersPage careersPage;
    private JobsPage jobsPage;
    private JobDetailPage jobDetailPage;
    
    @BeforeMethod
    public void setUp() {
        driver = DriverFactory.getDriver();
        homePage = new HomePage(driver);
        careersPage = new CareersPage(driver);
        jobsPage = new JobsPage(driver);
        jobDetailPage = new JobDetailPage(driver);
    }
    
    @Test
    public void testInsiderCareersFlow() {
        // Step 1: Open main homepage
        System.out.println("Step 1: Opening homepage...");
        homePage.goTo();
        System.out.println("Current URL: " + driver.getCurrentUrl());
        System.out.println("Page title: " + driver.getTitle());
        
        boolean homepageLoaded = homePage.isHomePageLoaded();
        Assert.assertTrue(homepageLoaded, "Homepage should be loaded");
        System.out.println("Step 1 completed: Homepage loaded successfully");
        
        // Step 2: Hover Company menu and click Careers
        System.out.println("Step 2: Navigating to Careers page...");
        homePage.hoverCompanyMenu();
        System.out.println("Company menu hovered successfully");
        careersPage = homePage.clickCareers();
        System.out.println("Careers link clicked, current URL: " + driver.getCurrentUrl());
        Assert.assertTrue(careersPage.isCareersPageLoaded(), "Careers page should be loaded");
        Assert.assertTrue(driver.getCurrentUrl().contains("/careers/"), "URL should contain /careers/");
        System.out.println("Step 2 completed: Careers page loaded successfully");
        
        // Step 3: Click "Find your dream job" button
        System.out.println("Step 3: Clicking 'Find your dream job' button...");
        jobsPage = careersPage.clickFindDreamJob();
        System.out.println("Jobs page object created, verifying page loaded...");
        boolean jobsPageLoaded = jobsPage.isJobsPageLoaded();
        Assert.assertTrue(jobsPageLoaded, "Jobs page should be loaded");
        System.out.println("Step 3 completed: Jobs page loaded successfully");
        
        // Step 4: Apply filters
        System.out.println("Step 4: Applying filters...");
        
        // Add a wait before applying filters
        System.out.println("Waiting for filters to be ready...");
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        
        jobsPage.selectLocation("Istanbul, Turkiye");
        String selectedLocation = jobsPage.getSelectedLocation();
        System.out.println("Selected location: '" + selectedLocation + "'");
        Assert.assertEquals(selectedLocation, "Istanbul, Turkiye", "Location filter should be set correctly");
        System.out.println("Location filter applied successfully");
        
        // Add a wait between filters
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        
        jobsPage.selectDepartment("Quality Assurance");
        String selectedDepartment = jobsPage.getSelectedDepartment();
        System.out.println("Selected department: '" + selectedDepartment + "'");
        Assert.assertEquals(selectedDepartment, "Quality Assurance", "Department filter should be set correctly");
        System.out.println("Department filter applied successfully");
        System.out.println("Step 4 completed: Filters applied successfully");
        
        // Step 5: Click "View Role" on first job card
        System.out.println("Step 5: Clicking 'View Role' on first job card...");
        System.out.println("Current URL before clicking: " + driver.getCurrentUrl());
        System.out.println("Page title before clicking: " + driver.getTitle());
        
        // Add a longer wait to ensure page is fully loaded and allow manual inspection
        System.out.println("Waiting 10 seconds for page to load completely...");
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        
        // Print some basic page info
        System.out.println("Page source length: " + driver.getPageSource().length());
        System.out.println("Number of links on page: " + driver.findElements(By.tagName("a")).size());
        System.out.println("Number of buttons on page: " + driver.findElements(By.tagName("button")).size());
        
        jobDetailPage = jobsPage.clickFirstJobViewRole();
        System.out.println("Job detail page object created, verifying page loaded...");
        Assert.assertTrue(jobDetailPage.isJobDetailPageLoaded(), "Job detail page should be loaded");
        System.out.println("Step 5 completed: Job detail page loaded successfully");
        
        // Step 6: Click "APPLY FOR THIS JOB" button
        System.out.println("Step 6: Clicking 'APPLY FOR THIS JOB' button...");
        System.out.println("Current URL: " + driver.getCurrentUrl());
        System.out.println("Page title: " + driver.getTitle());
        Assert.assertTrue(jobDetailPage.isApplyButtonVisible(), "Apply button should be visible");
        jobDetailPage.clickApplyForThisJob();
        System.out.println("Step 6 completed: Apply button clicked successfully");
        
        // Test completed successfully
        System.out.println("Test completed successfully - Apply button clicked!");
    }
    
    @AfterMethod
    public void tearDown(org.testng.ITestResult result) {
        if (result.getStatus() == org.testng.ITestResult.FAILURE) {
            takeScreenshot(result.getName());
        }
        if (driver != null) {
            DriverFactory.quitDriver();
        }
    }
    
    private void takeScreenshot(String testName) {
        try {
            // Create screenshots directory if it doesn't exist
            Path screenshotsDir = Paths.get("./artifacts/screenshots/");
            Files.createDirectories(screenshotsDir);
            
            // Generate timestamp
            String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
            String fileName = testName + "_" + timestamp + ".png";
            Path screenshotPath = screenshotsDir.resolve(fileName);
            
            // Take screenshot
            File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            Files.copy(screenshot.toPath(), screenshotPath);
            
            System.out.println("Screenshot saved: " + screenshotPath.toAbsolutePath());
        } catch (IOException e) {
            System.err.println("Failed to take screenshot: " + e.getMessage());
        }
    }
}
