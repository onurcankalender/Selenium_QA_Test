package com.example.insider.pages;

import com.example.insider.core.BasePage;
import com.example.insider.core.Waiter;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class JobsPage extends BasePage {
    
    // Locators
    private static final By LOCATION_FILTER = By.xpath("//label[contains(., 'Location')]/following::select[1]");
    private static final By DEPARTMENT_FILTER = By.xpath("//label[contains(., 'Department')]/following::select[1]");
    private static final By FIRST_JOB_CARD = By.xpath("//section[contains(@class,'jobs-list')]//article[1]//a[contains(., 'View Role')]");
    private static final By JOBS_LIST_SECTION = By.cssSelector("section.jobs-list, .jobs-container");
    
    // Alternative locators for better reliability
    private static final By JOBS_PAGE_TITLE = By.cssSelector("h1, .page-title, .jobs-title");
    private static final By FILTER_SECTION = By.cssSelector(".filters, .filter-section, [class*='filter']");
    private static final By ANY_JOB_CARD = By.cssSelector("article, .job-card, .position-card");
    
    // Alternative locators for View Role button
    private static final By VIEW_ROLE_BUTTON = By.xpath("//a[contains(., 'View Role') or contains(., 'View role') or contains(., 'Apply') or contains(., 'View Job')]");
    private static final By FIRST_JOB_ARTICLE = By.cssSelector("article:first-child, .job-card:first-child, .position-card:first-child");
    private static final By ANY_APPLY_BUTTON = By.cssSelector("a[href*='apply'], a[href*='job'], .apply-button, .view-role");
    
    public JobsPage(WebDriver driver) {
        super(driver);
    }
    
    public boolean isJobsPageLoaded() {
        // Try multiple elements to verify jobs page is loaded
        boolean jobsListVisible = isElementVisible(JOBS_LIST_SECTION);
        boolean pageTitleVisible = isElementVisible(JOBS_PAGE_TITLE);
        boolean filterSectionVisible = isElementVisible(FILTER_SECTION);
        boolean anyJobCardVisible = isElementVisible(ANY_JOB_CARD);
        boolean urlContainsJobs = driver.getCurrentUrl().toLowerCase().contains("jobs") || 
                                 driver.getCurrentUrl().toLowerCase().contains("careers");
        
        System.out.println("Jobs page verification - Jobs list visible: " + jobsListVisible + 
                          ", Page title visible: " + pageTitleVisible + 
                          ", Filter section visible: " + filterSectionVisible + 
                          ", Any job card visible: " + anyJobCardVisible + 
                          ", URL contains jobs/careers: " + urlContainsJobs);
        
        return jobsListVisible || pageTitleVisible || filterSectionVisible || anyJobCardVisible || urlContainsJobs;
    }
    
    public void selectLocation(String location) {
        System.out.println("Attempting to select location: " + location);
        try {
            // Wait for the location filter to be present and clickable
            Waiter.waitForElementClickable(driver, LOCATION_FILTER);
            try {
                Thread.sleep(1000); // Small wait to ensure dropdown is ready
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            
            selectByVisibleText(LOCATION_FILTER, location);
            System.out.println("Location selected successfully: " + location);
            Waiter.waitForPageLoad(driver);
        } catch (Exception e) {
            System.out.println("Error selecting location '" + location + "': " + e.getMessage());
            throw e;
        }
    }
    
    public void selectDepartment(String department) {
        System.out.println("Attempting to select department: " + department);
        try {
            // Wait for the department filter to be present and clickable
            Waiter.waitForElementClickable(driver, DEPARTMENT_FILTER);
            try {
                Thread.sleep(1000); // Small wait to ensure dropdown is ready
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            
            selectByVisibleText(DEPARTMENT_FILTER, department);
            System.out.println("Department selected successfully: " + department);
            Waiter.waitForPageLoad(driver);
        } catch (Exception e) {
            System.out.println("Error selecting department '" + department + "': " + e.getMessage());
            throw e;
        }
    }
    
    public String getSelectedLocation() {
        return getSelectedOptionText(LOCATION_FILTER);
    }
    
    public String getSelectedDepartment() {
        return getSelectedOptionText(DEPARTMENT_FILTER);
    }
    
    public JobDetailPage clickFirstJobViewRole() {
        System.out.println("Attempting to click View Role button...");
        System.out.println("Current URL: " + driver.getCurrentUrl());
        System.out.println("Page title: " + driver.getTitle());
        
        // Wait a moment for any dynamic content to load
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        
        // First, let's analyze what's actually on the page
        printPageElements();
        
        // Try multiple locators to find the View Role button
        try {
            System.out.println("Trying original locator: " + FIRST_JOB_CARD);
            safeClick(FIRST_JOB_CARD);
        } catch (Exception e1) {
            System.out.println("Original locator failed: " + e1.getMessage());
            try {
                System.out.println("Trying alternative: " + VIEW_ROLE_BUTTON);
                safeClick(VIEW_ROLE_BUTTON);
            } catch (Exception e2) {
                System.out.println("Alternative locator failed: " + e2.getMessage());
                try {
                    System.out.println("Trying any apply button: " + ANY_APPLY_BUTTON);
                    safeClick(ANY_APPLY_BUTTON);
                } catch (Exception e3) {
                    System.out.println("All locators failed. Trying any clickable element...");
                    
                    // Try to find any clickable element that might be a job link
                    try {
                        var anyClickable = driver.findElement(By.cssSelector("a[href*='job'], a[href*='position'], a[href*='career']"));
                        System.out.println("Found clickable element: " + anyClickable.getText() + " -> " + anyClickable.getAttribute("href"));
                        anyClickable.click();
                    } catch (Exception e4) {
                        System.out.println("Even generic job link failed. Current page elements:");
                        printPageElements();
                        throw new RuntimeException("Could not find View Role button with any locator", e3);
                    }
                }
            }
        }
        
        System.out.println("View Role button clicked successfully, switching to new tab...");
        switchToNewTab();
        return new JobDetailPage(driver);
    }
    
    private void printPageElements() {
        try {
            System.out.println("\n=== PAGE ELEMENT ANALYSIS ===");
            
            // Print all links on the page
            var links = driver.findElements(By.tagName("a"));
            System.out.println("Number of links found: " + links.size());
            for (int i = 0; i < Math.min(15, links.size()); i++) {
                try {
                    String text = links.get(i).getText().trim();
                    String href = links.get(i).getAttribute("href");
                    String className = links.get(i).getAttribute("class");
                    if (!text.isEmpty()) {
                        System.out.println("Link " + i + ": '" + text + "' -> " + href + " (class: " + className + ")");
                    }
                } catch (Exception e) {
                    // Skip stale elements
                }
            }
            
            // Print all buttons
            var buttons = driver.findElements(By.tagName("button"));
            System.out.println("Number of buttons found: " + buttons.size());
            for (int i = 0; i < Math.min(10, buttons.size()); i++) {
                try {
                    String text = buttons.get(i).getText().trim();
                    String className = buttons.get(i).getAttribute("class");
                    if (!text.isEmpty()) {
                        System.out.println("Button " + i + ": '" + text + "' (class: " + className + ")");
                    }
                } catch (Exception e) {
                    // Skip stale elements
                }
            }
            
            // Check for job-related elements
            var jobElements = driver.findElements(By.cssSelector("[class*='job'], [class*='position'], [class*='card']"));
            System.out.println("Job-related elements found: " + jobElements.size());
            
            // Check for any clickable elements with relevant text
            var allClickables = driver.findElements(By.cssSelector("a, button"));
            System.out.println("Total clickable elements: " + allClickables.size());
            for (int i = 0; i < allClickables.size(); i++) {
                try {
                    WebElement element = allClickables.get(i);
                    String text = element.getText().trim();
                    String tagName = element.getTagName();
                    
                    if (!text.isEmpty() && (text.toLowerCase().contains("view") || 
                                           text.toLowerCase().contains("apply") || 
                                           text.toLowerCase().contains("role") ||
                                           text.toLowerCase().contains("job") ||
                                           text.toLowerCase().contains("position"))) {
                        System.out.println("Relevant clickable " + i + " (" + tagName + "): '" + text + "'");
                    }
                } catch (Exception e) {
                    // Skip stale elements
                }
            }
            
            System.out.println("=== END PAGE ANALYSIS ===\n");
        } catch (Exception e) {
            System.out.println("Error printing page elements: " + e.getMessage());
        }
    }
}
