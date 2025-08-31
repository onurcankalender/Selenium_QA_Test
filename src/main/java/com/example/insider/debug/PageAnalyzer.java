package com.example.insider.debug;

import com.example.insider.core.DriverFactory;
import com.example.insider.pages.CareersPage;
import com.example.insider.pages.HomePage;
import com.example.insider.pages.JobsPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

public class PageAnalyzer {
    
    public static void main(String[] args) {
        System.out.println("=== PAGE ANALYZER STARTED ===");
        
        WebDriver driver = null;
        try {
            // Initialize driver
            driver = DriverFactory.getDriver();
            
            // Create page objects
            HomePage homePage = new HomePage(driver);
            CareersPage careersPage = new CareersPage(driver);
            JobsPage jobsPage = new JobsPage(driver);
            
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
            
            // Analyze the page
            System.out.println("Step 4: Analyzing page...");
            System.out.println("Current URL: " + driver.getCurrentUrl());
            System.out.println("Page title: " + driver.getTitle());
            
            analyzePageElements(driver);
            
        } catch (Exception e) {
            System.out.println("Error during analysis: " + e.getMessage());
            e.printStackTrace();
        } finally {
            if (driver != null) {
                DriverFactory.quitDriver();
            }
        }
        
        System.out.println("=== PAGE ANALYZER COMPLETED ===");
    }
    
    private static void analyzePageElements(WebDriver driver) {
        try {
            System.out.println("\n=== DETAILED PAGE ANALYSIS ===");
            
            // Count all elements
            List<WebElement> allLinks = driver.findElements(By.tagName("a"));
            List<WebElement> allButtons = driver.findElements(By.tagName("button"));
            List<WebElement> allDivs = driver.findElements(By.tagName("div"));
            
            System.out.println("Total links: " + allLinks.size());
            System.out.println("Total buttons: " + allButtons.size());
            System.out.println("Total divs: " + allDivs.size());
            
            // Show all links with text
            System.out.println("\n=== ALL LINKS WITH TEXT ===");
            for (int i = 0; i < allLinks.size(); i++) {
                try {
                    WebElement link = allLinks.get(i);
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
            
            // Show all buttons with text
            System.out.println("\n=== ALL BUTTONS WITH TEXT ===");
            for (int i = 0; i < allButtons.size(); i++) {
                try {
                    WebElement button = allButtons.get(i);
                    String text = button.getText().trim();
                    String className = button.getAttribute("class");
                    
                    if (!text.isEmpty()) {
                        System.out.println("Button " + i + ": '" + text + "' (class: " + className + ")");
                    }
                } catch (Exception e) {
                    // Skip stale elements
                }
            }
            
            // Check for job-related elements
            List<WebElement> jobElements = driver.findElements(By.cssSelector("[class*='job'], [class*='position'], [class*='card']"));
            System.out.println("\nJob-related elements found: " + jobElements.size());
            
            // Check for any clickable elements with relevant text
            List<WebElement> allClickables = driver.findElements(By.cssSelector("a, button"));
            System.out.println("Total clickable elements: " + allClickables.size());
            
            System.out.println("\n=== RELEVANT CLICKABLE ELEMENTS ===");
            for (int i = 0; i < allClickables.size(); i++) {
                try {
                    WebElement element = allClickables.get(i);
                    String text = element.getText().trim();
                    String tagName = element.getTagName();
                    
                    if (!text.isEmpty() && (text.toLowerCase().contains("view") || 
                                           text.toLowerCase().contains("apply") || 
                                           text.toLowerCase().contains("role") ||
                                           text.toLowerCase().contains("job") ||
                                           text.toLowerCase().contains("position") ||
                                           text.toLowerCase().contains("career"))) {
                        System.out.println("Relevant clickable " + i + " (" + tagName + "): '" + text + "'");
                    }
                } catch (Exception e) {
                    // Skip stale elements
                }
            }
            
            // Check page source for keywords
            String pageSource = driver.getPageSource();
            System.out.println("\n=== PAGE SOURCE ANALYSIS ===");
            System.out.println("Contains 'View Role': " + pageSource.contains("View Role"));
            System.out.println("Contains 'view role': " + pageSource.contains("view role"));
            System.out.println("Contains 'Apply': " + pageSource.contains("Apply"));
            System.out.println("Contains 'apply': " + pageSource.contains("apply"));
            System.out.println("Contains 'Job': " + pageSource.contains("Job"));
            System.out.println("Contains 'job': " + pageSource.contains("job"));
            System.out.println("Contains 'Position': " + pageSource.contains("Position"));
            System.out.println("Contains 'position': " + pageSource.contains("position"));
            
            // Look for any text that might be a job link
            System.out.println("\n=== SEARCHING FOR JOB-RELATED TEXT ===");
            String[] searchTerms = {"View", "Apply", "Job", "Position", "Role", "Career", "Open", "Details"};
            for (String term : searchTerms) {
                if (pageSource.contains(term)) {
                    System.out.println("Found '" + term + "' in page source");
                }
            }
            
        } catch (Exception e) {
            System.out.println("Error analyzing page elements: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
