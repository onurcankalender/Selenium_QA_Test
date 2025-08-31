package com.example.insider.tests;

import com.example.insider.core.DriverFactory;
import com.example.insider.pages.CareersPage;
import com.example.insider.pages.HomePage;
import com.example.insider.pages.JobsPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.List;

public class JobsPageDebugTest {
    
    private WebDriver driver;
    private HomePage homePage;
    private CareersPage careersPage;
    private JobsPage jobsPage;
    
    @BeforeMethod
    public void setUp() {
        driver = DriverFactory.getDriver();
        homePage = new HomePage(driver);
        careersPage = new CareersPage(driver);
        jobsPage = new JobsPage(driver);
    }
    
    @Test
    public void testJobsPageNavigation() {
        System.out.println("=== Jobs Page Navigation Debug Test ===");
        
        // Step 1: Navigate to homepage
        System.out.println("Step 1: Opening homepage...");
        homePage.goTo();
        System.out.println("Current URL: " + driver.getCurrentUrl());
        System.out.println("Page title: " + driver.getTitle());
        
        // Step 2: Navigate to Careers page
        System.out.println("Step 2: Navigating to Careers page...");
        homePage.hoverCompanyMenu();
        careersPage = homePage.clickCareers();
        System.out.println("Careers page URL: " + driver.getCurrentUrl());
        System.out.println("Careers page title: " + driver.getTitle());
        
        // Step 3: Click "Find your dream job" and analyze the result
        System.out.println("Step 3: Clicking 'Find your dream job' button...");
        jobsPage = careersPage.clickFindDreamJob();
        
        // Analyze the page after clicking
        System.out.println("After clicking 'Find your dream job':");
        System.out.println("Current URL: " + driver.getCurrentUrl());
        System.out.println("Page title: " + driver.getTitle());
        
        // Check for various elements that might indicate it's a jobs page
        List<WebElement> h1Elements = driver.findElements(By.tagName("h1"));
        System.out.println("Number of H1 elements: " + h1Elements.size());
        for (int i = 0; i < h1Elements.size(); i++) {
            System.out.println("H1 " + i + " text: " + h1Elements.get(i).getText());
        }
        
        List<WebElement> selectElements = driver.findElements(By.tagName("select"));
        System.out.println("Number of select elements: " + selectElements.size());
        
        List<WebElement> articleElements = driver.findElements(By.tagName("article"));
        System.out.println("Number of article elements: " + articleElements.size());
        
        List<WebElement> sectionElements = driver.findElements(By.tagName("section"));
        System.out.println("Number of section elements: " + sectionElements.size());
        
        // Check for filter-related elements
        List<WebElement> filterElements = driver.findElements(By.cssSelector("[class*='filter']"));
        System.out.println("Number of filter-related elements: " + filterElements.size());
        
        // Check for job-related elements
        List<WebElement> jobElements = driver.findElements(By.cssSelector("[class*='job'], [class*='position']"));
        System.out.println("Number of job-related elements: " + jobElements.size());
        
        // Check page source for keywords
        String pageSource = driver.getPageSource().toLowerCase();
        System.out.println("Page source contains 'jobs': " + pageSource.contains("jobs"));
        System.out.println("Page source contains 'position': " + pageSource.contains("position"));
        System.out.println("Page source contains 'career': " + pageSource.contains("career"));
        System.out.println("Page source contains 'filter': " + pageSource.contains("filter"));
        
        // Test the jobs page verification
        boolean isJobsPageLoaded = jobsPage.isJobsPageLoaded();
        System.out.println("Jobs page verification result: " + isJobsPageLoaded);
        
        System.out.println("=== Debug test completed ===");
    }
    
    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            DriverFactory.quitDriver();
        }
    }
}
