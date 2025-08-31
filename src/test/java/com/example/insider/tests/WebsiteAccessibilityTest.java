package com.example.insider.tests;

import com.example.insider.core.DriverFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.List;

public class WebsiteAccessibilityTest {
    
    private WebDriver driver;
    
    @BeforeMethod
    public void setUp() {
        driver = DriverFactory.getDriver();
    }
    
    @Test
    public void testWebsiteAccessibility() {
        System.out.println("Testing website accessibility...");
        
        // Navigate to homepage
        driver.get("https://useinsider.com/");
        System.out.println("Current URL: " + driver.getCurrentUrl());
        System.out.println("Page title: " + driver.getTitle());
        
        // Check for navigation elements
        List<WebElement> navLinks = driver.findElements(By.tagName("nav"));
        System.out.println("Number of nav elements found: " + navLinks.size());
        
        // Check for Company menu
        List<WebElement> companyLinks = driver.findElements(By.xpath("//a[contains(text(), 'Company')]"));
        System.out.println("Number of Company links found: " + companyLinks.size());
        
        // Check for logo
        List<WebElement> logos = driver.findElements(By.cssSelector("img[alt*='Insider']"));
        System.out.println("Number of Insider logos found: " + logos.size());
        
        // Check for any images
        List<WebElement> images = driver.findElements(By.tagName("img"));
        System.out.println("Total number of images: " + images.size());
        
        // Print first few image alt texts
        for (int i = 0; i < Math.min(5, images.size()); i++) {
            String altText = images.get(i).getAttribute("alt");
            System.out.println("Image " + i + " alt: " + altText);
        }
        
        // Check for header elements
        List<WebElement> headers = driver.findElements(By.cssSelector("header, .header"));
        System.out.println("Number of header elements found: " + headers.size());
        
        // Check page source for any clues
        String pageSource = driver.getPageSource();
        if (pageSource.contains("insider")) {
            System.out.println("Page source contains 'insider'");
        } else {
            System.out.println("Page source does not contain 'insider'");
        }
        
        System.out.println("Website accessibility test completed");
    }
    
    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            DriverFactory.quitDriver();
        }
    }
}
