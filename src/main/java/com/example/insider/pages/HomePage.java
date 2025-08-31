package com.example.insider.pages;

import com.example.insider.core.BasePage;
import com.example.insider.core.Waiter;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class HomePage extends BasePage {
    
    // Locators
    private static final By COMPANY_MENU = By.xpath("//nav//a[normalize-space()='Company']");
    private static final By CAREERS_LINK = By.xpath("//nav//a[normalize-space()='Careers']");
    private static final By LOGO = By.cssSelector("img[alt*='Insider']");
    private static final By HOME_PAGE_HEADER = By.cssSelector("header, .header, nav");
    private static final By PAGE_TITLE = By.cssSelector("title");
    
    public HomePage(WebDriver driver) {
        super(driver);
    }
    
    public void goTo() {
        driver.get("https://useinsider.com/");
        Waiter.waitForPageLoad(driver);
    }
    
    public boolean isHomePageLoaded() {
        // Try multiple elements to verify homepage is loaded
        boolean logoVisible = isElementVisible(LOGO);
        boolean headerVisible = isElementVisible(HOME_PAGE_HEADER);
        boolean titleContainsInsider = driver.getTitle().toLowerCase().contains("insider");
        
        System.out.println("Homepage verification - Logo visible: " + logoVisible + 
                          ", Header visible: " + headerVisible + 
                          ", Title contains 'insider': " + titleContainsInsider);
        
        return logoVisible || headerVisible || titleContainsInsider;
    }
    
    public void hoverCompanyMenu() {
        hover(COMPANY_MENU);
    }
    
    public CareersPage clickCareers() {
        safeClick(CAREERS_LINK);
        Waiter.waitForUrlContains(driver, "/careers/");
        return new CareersPage(driver);
    }
}
