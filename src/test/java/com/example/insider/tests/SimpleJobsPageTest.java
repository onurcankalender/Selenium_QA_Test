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

public class SimpleJobsPageTest {
    
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
    public void testJobsPageElements() {
        System.out.println("=== Simple Jobs Page Analysis ===");
        
        try {
            // Navigate to jobs page
            System.out.println("Step 1: Navigating to jobs page...");
            homePage.goTo();
            homePage.hoverCompanyMenu();
            careersPage = homePage.clickCareers();
            jobsPage = careersPage.clickFindDreamJob();
            
            // Apply filters
            System.out.println("Step 2: Applying filters...");
            jobsPage.selectLocation("Istanbul, Turkiye");
            jobsPage.selectDepartment("Quality Assurance");
            
            // Wait a moment for page to load
            Thread.sleep(3000);
            
            // Analyze the page
            System.out.println("Step 3: Analyzing page elements...");
            System.out.println("Current URL: " + driver.getCurrentUrl());
            System.out.println("Page title: " + driver.getTitle());
            
            analyzePageElements();
            
        } catch (Exception e) {
            System.out.println("Error during test: " + e.getMessage());
            e.printStackTrace();
        }
        
        System.out.println("=== Analysis completed ===");
    }
    
    private void analyzePageElements() {
        try {
            System.out.println("\n=== DETAILED PAGE ANALYSIS ===");
            
            // All links
            List<WebElement> links = driver.findElements(By.tagName("a"));
            System.out.println("Total links: " + links.size());
            
            for (int i = 0; i < links.size(); i++) {
                try {
                    WebElement link = links.get(i);
                    String text = link.getText().trim();
                    String href = link.getAttribute("href");
                    String className = link.getAttribute("class");
                    
                    if (!text.isEmpty()) {
                        System.out.println("Link " + i + ": '" + text + "' -> " + href + " (class: " + className + ")");
                    }
                } catch (Exception e) {
                    // Skip stale elements
                }
            }
            
            // All buttons
            List<WebElement> buttons = driver.findElements(By.tagName("button"));
            System.out.println("Total buttons: " + buttons.size());
            
            for (int i = 0; i < buttons.size(); i++) {
                try {
                    WebElement button = buttons.get(i);
                    String text = button.getText().trim();
                    String className = button.getAttribute("class");
                    
                    if (!text.isEmpty()) {
                        System.out.println("Button " + i + ": '" + text + "' (class: " + className + ")");
                    }
                } catch (Exception e) {
                    // Skip stale elements
                }
            }
            
            // Check page source for keywords
            String pageSource = driver.getPageSource().toLowerCase();
            System.out.println("\nPage source analysis:");
            System.out.println("Contains 'view role': " + pageSource.contains("view role"));
            System.out.println("Contains 'view': " + pageSource.contains("view"));
            System.out.println("Contains 'apply': " + pageSource.contains("apply"));
            System.out.println("Contains 'job': " + pageSource.contains("job"));
            System.out.println("Contains 'position': " + pageSource.contains("position"));
            
        } catch (Exception e) {
            System.out.println("Error analyzing page: " + e.getMessage());
        }
    }
    
    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            DriverFactory.quitDriver();
        }
    }
}
