package com.example.insider.pages;

import com.example.insider.core.BasePage;
import com.example.insider.core.Waiter;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class CareersPage extends BasePage {
    
    // Locators
    private static final By FIND_DREAM_JOB_BUTTON = By.xpath("//a[contains(normalize-space(),'Find your dream job')]");
    private static final By CAREERS_PAGE_TITLE = By.cssSelector("h1, .hero-title");
    
    public CareersPage(WebDriver driver) {
        super(driver);
    }
    
    public boolean isCareersPageLoaded() {
        return isElementVisible(CAREERS_PAGE_TITLE);
    }
    
    public JobsPage clickFindDreamJob() {
        System.out.println("Clicking 'Find your dream job' button...");
        safeClick(FIND_DREAM_JOB_BUTTON);
        System.out.println("Button clicked, waiting for page load...");
        Waiter.waitForPageLoad(driver);
        System.out.println("Page load completed. Current URL: " + driver.getCurrentUrl());
        System.out.println("Page title: " + driver.getTitle());
        return new JobsPage(driver);
    }
}
