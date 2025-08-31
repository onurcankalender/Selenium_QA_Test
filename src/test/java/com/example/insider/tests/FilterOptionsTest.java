package com.example.insider.tests;

import com.example.insider.core.DriverFactory;
import com.example.insider.pages.CareersPage;
import com.example.insider.pages.HomePage;
import com.example.insider.pages.JobsPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.annotations.Test;

import java.util.List;

public class FilterOptionsTest {
    
    @Test
    public void testFilterOptions() {
        System.out.println("=== FILTER OPTIONS TEST ===");
        
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
            
            // Wait for page to load
            System.out.println("Step 2: Waiting for page to load...");
            Thread.sleep(5000);
            
            // Print basic info
            System.out.println("Current URL: " + driver.getCurrentUrl());
            System.out.println("Page title: " + driver.getTitle());
            
            // Find location filter
            System.out.println("\nStep 3: Analyzing location filter options...");
            try {
                WebElement locationSelect = driver.findElement(By.xpath("//label[contains(., 'Location')]/following::select[1]"));
                Select locationDropdown = new Select(locationSelect);
                
                List<WebElement> locationOptions = locationDropdown.getOptions();
                System.out.println("Location options found: " + locationOptions.size());
                
                System.out.println("Available location options:");
                for (int i = 0; i < locationOptions.size(); i++) {
                    String optionText = locationOptions.get(i).getText().trim();
                    if (!optionText.isEmpty()) {
                        System.out.println("  " + i + ": '" + optionText + "'");
                    }
                }
                
                // Check if Istanbul is available in any form
                System.out.println("\nChecking for Istanbul-related options:");
                for (WebElement option : locationOptions) {
                    String text = option.getText().trim().toLowerCase();
                    if (text.contains("istanbul") || text.contains("turkey") || text.contains("turkiye")) {
                        System.out.println("Found Istanbul-related option: '" + option.getText().trim() + "'");
                    }
                }
                
            } catch (Exception e) {
                System.out.println("Error finding location filter: " + e.getMessage());
            }
            
            // Find department filter
            System.out.println("\nStep 4: Analyzing department filter options...");
            try {
                WebElement departmentSelect = driver.findElement(By.xpath("//label[contains(., 'Department')]/following::select[1]"));
                Select departmentDropdown = new Select(departmentSelect);
                
                List<WebElement> departmentOptions = departmentDropdown.getOptions();
                System.out.println("Department options found: " + departmentOptions.size());
                
                System.out.println("Available department options:");
                for (int i = 0; i < departmentOptions.size(); i++) {
                    String optionText = departmentOptions.get(i).getText().trim();
                    if (!optionText.isEmpty()) {
                        System.out.println("  " + i + ": '" + optionText + "'");
                    }
                }
                
                // Check if Quality Assurance is available in any form
                System.out.println("\nChecking for Quality Assurance-related options:");
                for (WebElement option : departmentOptions) {
                    String text = option.getText().trim().toLowerCase();
                    if (text.contains("quality") || text.contains("assurance") || text.contains("qa") || text.contains("test")) {
                        System.out.println("Found QA-related option: '" + option.getText().trim() + "'");
                    }
                }
                
            } catch (Exception e) {
                System.out.println("Error finding department filter: " + e.getMessage());
            }
            
            // Keep browser open for manual inspection
            System.out.println("\nBrowser will stay open for 20 seconds for manual inspection...");
            Thread.sleep(20000);
            
        } catch (Exception e) {
            System.out.println("Error during test: " + e.getMessage());
            e.printStackTrace();
        } finally {
            if (driver != null) {
                DriverFactory.quitDriver();
            }
        }
        
        System.out.println("=== FILTER OPTIONS TEST COMPLETED ===");
    }
}
