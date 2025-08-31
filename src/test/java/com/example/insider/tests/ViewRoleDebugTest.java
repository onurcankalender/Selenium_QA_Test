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

public class ViewRoleDebugTest {
    
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
    public void testViewRoleButtonDebug() {
        System.out.println("=== View Role Button Debug Test ===");
        
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
        
        System.out.println("Step 3: Analyzing page for View Role button...");
        System.out.println("Current URL: " + driver.getCurrentUrl());
        System.out.println("Page title: " + driver.getTitle());
        
        // Analyze all clickable elements
        analyzeClickableElements();
        
        // Analyze job cards specifically
        analyzeJobCards();
        
        // Check for specific View Role patterns
        checkViewRolePatterns();
        
        System.out.println("=== View Role debug test completed ===");
    }
    
    private void analyzeClickableElements() {
        System.out.println("\n--- Analyzing Clickable Elements ---");
        
        // All links
        List<WebElement> links = driver.findElements(By.tagName("a"));
        System.out.println("Total links found: " + links.size());
        
        for (int i = 0; i < links.size(); i++) {
            try {
                WebElement link = links.get(i);
                String text = link.getText().trim();
                String href = link.getAttribute("href");
                String className = link.getAttribute("class");
                
                if (!text.isEmpty() && (text.toLowerCase().contains("view") || 
                                       text.toLowerCase().contains("apply") || 
                                       text.toLowerCase().contains("role") ||
                                       text.toLowerCase().contains("job"))) {
                    System.out.println("Link " + i + ": '" + text + "' -> " + href + " (class: " + className + ")");
                }
            } catch (Exception e) {
                // Skip stale elements
            }
        }
        
        // All buttons
        List<WebElement> buttons = driver.findElements(By.tagName("button"));
        System.out.println("Total buttons found: " + buttons.size());
        
        for (int i = 0; i < buttons.size(); i++) {
            try {
                WebElement button = buttons.get(i);
                String text = button.getText().trim();
                String className = button.getAttribute("class");
                
                if (!text.isEmpty() && (text.toLowerCase().contains("view") || 
                                       text.toLowerCase().contains("apply") || 
                                       text.toLowerCase().contains("role"))) {
                    System.out.println("Button " + i + ": '" + text + "' (class: " + className + ")");
                }
            } catch (Exception e) {
                // Skip stale elements
            }
        }
    }
    
    private void analyzeJobCards() {
        System.out.println("\n--- Analyzing Job Cards ---");
        
        // Check for article elements
        List<WebElement> articles = driver.findElements(By.tagName("article"));
        System.out.println("Article elements found: " + articles.size());
        
        for (int i = 0; i < Math.min(3, articles.size()); i++) {
            try {
                WebElement article = articles.get(i);
                String className = article.getAttribute("class");
                System.out.println("Article " + i + " class: " + className);
                
                // Check for links inside this article
                List<WebElement> articleLinks = article.findElements(By.tagName("a"));
                System.out.println("  Links in article " + i + ": " + articleLinks.size());
                for (int j = 0; j < articleLinks.size(); j++) {
                    String linkText = articleLinks.get(j).getText().trim();
                    if (!linkText.isEmpty()) {
                        System.out.println("    Link " + j + ": '" + linkText + "'");
                    }
                }
            } catch (Exception e) {
                System.out.println("Error analyzing article " + i + ": " + e.getMessage());
            }
        }
        
        // Check for div elements that might be job cards
        List<WebElement> jobDivs = driver.findElements(By.cssSelector("div[class*='job'], div[class*='position'], div[class*='card']"));
        System.out.println("Job-related div elements found: " + jobDivs.size());
        
        for (int i = 0; i < Math.min(3, jobDivs.size()); i++) {
            try {
                WebElement div = jobDivs.get(i);
                String className = div.getAttribute("class");
                System.out.println("Job div " + i + " class: " + className);
            } catch (Exception e) {
                System.out.println("Error analyzing job div " + i + ": " + e.getMessage());
            }
        }
    }
    
    private void checkViewRolePatterns() {
        System.out.println("\n--- Checking View Role Patterns ---");
        
        // Check for various View Role patterns
        String[] patterns = {
            "//a[contains(., 'View Role')]",
            "//a[contains(., 'View role')]",
            "//a[contains(., 'Apply')]",
            "//a[contains(., 'View Job')]",
            "//button[contains(., 'View Role')]",
            "//button[contains(., 'Apply')]",
            "//a[contains(@class, 'apply')]",
            "//a[contains(@class, 'view')]"
        };
        
        for (String pattern : patterns) {
            try {
                List<WebElement> elements = driver.findElements(By.xpath(pattern));
                if (!elements.isEmpty()) {
                    System.out.println("Pattern '" + pattern + "' found " + elements.size() + " elements");
                    for (int i = 0; i < Math.min(3, elements.size()); i++) {
                        String text = elements.get(i).getText().trim();
                        String href = elements.get(i).getAttribute("href");
                        System.out.println("  Element " + i + ": '" + text + "' -> " + href);
                    }
                }
            } catch (Exception e) {
                System.out.println("Error with pattern '" + pattern + "': " + e.getMessage());
            }
        }
    }
    
    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            DriverFactory.quitDriver();
        }
    }
}
