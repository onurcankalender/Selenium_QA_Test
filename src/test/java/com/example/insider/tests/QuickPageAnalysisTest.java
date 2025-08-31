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

public class QuickPageAnalysisTest {
    
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
    public void testQuickPageAnalysis() {
        System.out.println("=== QUICK PAGE ANALYSIS ===");
        
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
            
            // Wait for page to load
            System.out.println("Step 3: Waiting for page to load...");
            Thread.sleep(5000);
            
            // Quick analysis
            System.out.println("Step 4: Quick page analysis...");
            System.out.println("Current URL: " + driver.getCurrentUrl());
            System.out.println("Page title: " + driver.getTitle());
            
            // Count all elements
            List<WebElement> allLinks = driver.findElements(By.tagName("a"));
            List<WebElement> allButtons = driver.findElements(By.tagName("button"));
            List<WebElement> allDivs = driver.findElements(By.tagName("div"));
            
            System.out.println("Total links: " + allLinks.size());
            System.out.println("Total buttons: " + allButtons.size());
            System.out.println("Total divs: " + allDivs.size());
            
            // Show first 20 links
            System.out.println("\nFirst 20 links:");
            for (int i = 0; i < Math.min(20, allLinks.size()); i++) {
                try {
                    WebElement link = allLinks.get(i);
                    String text = link.getText().trim();
                    String href = link.getAttribute("href");
                    if (!text.isEmpty()) {
                        System.out.println("Link " + i + ": '" + text + "' -> " + href);
                    }
                } catch (Exception e) {
                    // Skip stale elements
                }
            }
            
            // Show first 10 buttons
            System.out.println("\nFirst 10 buttons:");
            for (int i = 0; i < Math.min(10, allButtons.size()); i++) {
                try {
                    WebElement button = allButtons.get(i);
                    String text = button.getText().trim();
                    if (!text.isEmpty()) {
                        System.out.println("Button " + i + ": '" + text + "'");
                    }
                } catch (Exception e) {
                    // Skip stale elements
                }
            }
            
            // Check page source for specific keywords
            String pageSource = driver.getPageSource();
            System.out.println("\nPage source analysis:");
            System.out.println("Contains 'View Role': " + pageSource.contains("View Role"));
            System.out.println("Contains 'view role': " + pageSource.contains("view role"));
            System.out.println("Contains 'Apply': " + pageSource.contains("Apply"));
            System.out.println("Contains 'apply': " + pageSource.contains("apply"));
            System.out.println("Contains 'Job': " + pageSource.contains("Job"));
            System.out.println("Contains 'job': " + pageSource.contains("job"));
            
        } catch (Exception e) {
            System.out.println("Error during test: " + e.getMessage());
            e.printStackTrace();
        }
        
        System.out.println("=== QUICK ANALYSIS COMPLETED ===");
    }
    
    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            DriverFactory.quitDriver();
        }
    }
}
