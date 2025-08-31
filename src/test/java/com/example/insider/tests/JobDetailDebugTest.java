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

public class JobDetailDebugTest {
    
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
    public void testJobDetailPageDebug() {
        System.out.println("=== Job Detail Page Debug Test ===");
        
        // Navigate to jobs page and click View Role
        System.out.println("Step 1: Navigating to jobs page...");
        homePage.goTo();
        homePage.hoverCompanyMenu();
        careersPage = homePage.clickCareers();
        jobsPage = careersPage.clickFindDreamJob();
        
        // Apply filters
        System.out.println("Step 2: Applying filters...");
        jobsPage.selectLocation("Istanbul, Turkiye");
        jobsPage.selectDepartment("Quality Assurance");
        
        // Click View Role
        System.out.println("Step 3: Clicking View Role...");
        jobsPage.clickFirstJobViewRole();
        
        // Analyze the job detail page
        System.out.println("Step 4: Analyzing job detail page...");
        System.out.println("Current URL: " + driver.getCurrentUrl());
        System.out.println("Page title: " + driver.getTitle());
        
        // Analyze page elements
        analyzeJobDetailElements();
        
        // Check for apply buttons
        checkApplyButtons();
        
        // Check page source for keywords
        checkPageSource();
        
        System.out.println("=== Job Detail debug test completed ===");
    }
    
    private void analyzeJobDetailElements() {
        System.out.println("\n--- Analyzing Job Detail Elements ---");
        
        // Check for headers
        List<WebElement> headers = driver.findElements(By.cssSelector("h1, h2, h3"));
        System.out.println("Header elements found: " + headers.size());
        for (int i = 0; i < Math.min(5, headers.size()); i++) {
            try {
                String text = headers.get(i).getText().trim();
                String tagName = headers.get(i).getTagName();
                if (!text.isEmpty()) {
                    System.out.println("Header " + i + " (" + tagName + "): '" + text + "'");
                }
            } catch (Exception e) {
                // Skip stale elements
            }
        }
        
        // Check for job-related content
        List<WebElement> jobContent = driver.findElements(By.cssSelector(".job-description, .description, .content, .details"));
        System.out.println("Job content elements found: " + jobContent.size());
        
        // Check for any text content
        List<WebElement> paragraphs = driver.findElements(By.tagName("p"));
        System.out.println("Paragraph elements found: " + paragraphs.size());
        for (int i = 0; i < Math.min(3, paragraphs.size()); i++) {
            try {
                String text = paragraphs.get(i).getText().trim();
                if (!text.isEmpty() && text.length() > 20) {
                    System.out.println("Paragraph " + i + ": '" + text.substring(0, Math.min(100, text.length())) + "...'");
                }
            } catch (Exception e) {
                // Skip stale elements
            }
        }
    }
    
    private void checkApplyButtons() {
        System.out.println("\n--- Checking Apply Buttons ---");
        
        // Check for apply links
        List<WebElement> applyLinks = driver.findElements(By.xpath("//a[contains(., 'Apply') or contains(., 'APPLY')]"));
        System.out.println("Apply links found: " + applyLinks.size());
        for (int i = 0; i < applyLinks.size(); i++) {
            try {
                String text = applyLinks.get(i).getText().trim();
                String href = applyLinks.get(i).getAttribute("href");
                String className = applyLinks.get(i).getAttribute("class");
                System.out.println("Apply link " + i + ": '" + text + "' -> " + href + " (class: " + className + ")");
            } catch (Exception e) {
                // Skip stale elements
            }
        }
        
        // Check for apply buttons
        List<WebElement> applyButtons = driver.findElements(By.xpath("//button[contains(., 'Apply') or contains(., 'APPLY')]"));
        System.out.println("Apply buttons found: " + applyButtons.size());
        for (int i = 0; i < applyButtons.size(); i++) {
            try {
                String text = applyButtons.get(i).getText().trim();
                String className = applyButtons.get(i).getAttribute("class");
                System.out.println("Apply button " + i + ": '" + text + "' (class: " + className + ")");
            } catch (Exception e) {
                // Skip stale elements
            }
        }
        
        // Check for any clickable elements with apply-related text
        List<WebElement> allClickables = driver.findElements(By.cssSelector("a, button"));
        System.out.println("Total clickable elements: " + allClickables.size());
        for (int i = 0; i < allClickables.size(); i++) {
            try {
                WebElement element = allClickables.get(i);
                String text = element.getText().trim();
                String tagName = element.getTagName();
                
                if (!text.isEmpty() && (text.toLowerCase().contains("apply") || 
                                       text.toLowerCase().contains("submit") ||
                                       text.toLowerCase().contains("send"))) {
                    System.out.println("Clickable " + i + " (" + tagName + "): '" + text + "'");
                }
            } catch (Exception e) {
                // Skip stale elements
            }
        }
    }
    
    private void checkPageSource() {
        System.out.println("\n--- Checking Page Source ---");
        
        String pageSource = driver.getPageSource().toLowerCase();
        System.out.println("Page source contains 'apply': " + pageSource.contains("apply"));
        System.out.println("Page source contains 'job': " + pageSource.contains("job"));
        System.out.println("Page source contains 'position': " + pageSource.contains("position"));
        System.out.println("Page source contains 'career': " + pageSource.contains("career"));
        System.out.println("Page source contains 'description': " + pageSource.contains("description"));
        System.out.println("Page source contains 'requirements': " + pageSource.contains("requirements"));
        
        // Check if it's a Lever page (common job application platform)
        System.out.println("Page source contains 'lever': " + pageSource.contains("lever"));
        System.out.println("Page source contains 'greenhouse': " + pageSource.contains("greenhouse"));
        System.out.println("Page source contains 'workday': " + pageSource.contains("workday"));
    }
    
    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            DriverFactory.quitDriver();
        }
    }
}
