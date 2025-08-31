package com.example.insider.debug;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import io.github.bonigarcia.wdm.WebDriverManager;

import java.time.Duration;
import java.util.List;

public class StandalonePageAnalyzer {
    
    public static void main(String[] args) {
        System.out.println("=== STANDALONE PAGE ANALYZER ===");
        WebDriver driver = null;
        
        try {
            // Setup Chrome driver
            WebDriverManager.chromedriver().setup();
            ChromeOptions options = new ChromeOptions();
            options.addArguments("--start-maximized");
            driver = new ChromeDriver(options);
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
            
            // Navigate to homepage
            System.out.println("Step 1: Navigating to homepage...");
            driver.get("https://useinsider.com/");
            
            // Hover over Company menu
            System.out.println("Step 2: Hovering over Company menu...");
            WebElement companyMenu = driver.findElement(By.xpath("//a[contains(text(), 'Company')]"));
            org.openqa.selenium.interactions.Actions actions = new org.openqa.selenium.interactions.Actions(driver);
            actions.moveToElement(companyMenu).perform();
            
            // Click Careers
            System.out.println("Step 3: Clicking Careers...");
            WebElement careersLink = driver.findElement(By.xpath("//a[contains(text(), 'Careers')]"));
            careersLink.click();
            
            // Wait for careers page to load
            Thread.sleep(3000);
            
            // Click "Find your dream job"
            System.out.println("Step 4: Clicking 'Find your dream job'...");
            WebElement findDreamJobButton = driver.findElement(By.xpath("//a[contains(text(), 'Find your dream job')]"));
            findDreamJobButton.click();
            
            // Wait for jobs page to load
            Thread.sleep(5000);
            
            // Apply location filter
            System.out.println("Step 5: Applying location filter...");
            WebElement locationFilter = driver.findElement(By.id("filter-by-location"));
            Select locationSelect = new Select(locationFilter);
            locationSelect.selectByVisibleText("Istanbul, Turkiye");
            
            // Apply department filter
            System.out.println("Step 6: Applying department filter...");
            WebElement departmentFilter = driver.findElement(By.id("filter-by-department"));
            Select departmentSelect = new Select(departmentFilter);
            departmentSelect.selectByVisibleText("Quality Assurance");
            
            // Wait for filters to take effect
            System.out.println("Step 7: Waiting for filters to take effect...");
            Thread.sleep(5000);
            
            // Print basic info
            System.out.println("Current URL: " + driver.getCurrentUrl());
            System.out.println("Page title: " + driver.getTitle());
            
            // Count elements
            List<WebElement> allLinks = driver.findElements(By.tagName("a"));
            List<WebElement> allButtons = driver.findElements(By.tagName("button"));
            
            System.out.println("Total links: " + allLinks.size());
            System.out.println("Total buttons: " + allButtons.size());
            
            // Show ALL links with text
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
            
            // Show ALL buttons with text
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
                                           text.toLowerCase().contains("career") ||
                                           text.toLowerCase().contains("open") ||
                                           text.toLowerCase().contains("details"))) {
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
            System.out.println("Contains 'Open': " + pageSource.contains("Open"));
            System.out.println("Contains 'open': " + pageSource.contains("open"));
            
            // Keep browser open for manual inspection
            System.out.println("\nBrowser will stay open for 30 seconds for manual inspection...");
            System.out.println("Please check the page manually to see what elements are available.");
            Thread.sleep(30000);
            
        } catch (Exception e) {
            System.out.println("Error during analysis: " + e.getMessage());
            e.printStackTrace();
        } finally {
            if (driver != null) {
                driver.quit();
            }
        }
        
        System.out.println("=== STANDALONE PAGE ANALYZER COMPLETED ===");
    }
}
