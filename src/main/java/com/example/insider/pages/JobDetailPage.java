package com.example.insider.pages;

import com.example.insider.core.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class JobDetailPage extends BasePage {
    
    // Locators
    private static final By APPLY_BUTTON = By.xpath("//a[normalize-space()='APPLY FOR THIS JOB' or normalize-space()='Apply for this job']");
    private static final By JOB_TITLE = By.cssSelector("h1, .job-title");
    
    // Alternative locators for better reliability
    private static final By JOB_DETAIL_HEADER = By.cssSelector("h1, h2, .job-title, .position-title, .title");
    private static final By JOB_DESCRIPTION = By.cssSelector(".job-description, .description, .content");
    private static final By ANY_APPLY_BUTTON = By.xpath("//a[contains(., 'Apply') or contains(., 'APPLY')]");
    private static final By ANY_BUTTON = By.cssSelector("button, .btn, .button");
    
    public JobDetailPage(WebDriver driver) {
        super(driver);
    }
    
    public boolean isJobDetailPageLoaded() {
        // Try multiple elements to verify job detail page is loaded
        boolean jobTitleVisible = isElementVisible(JOB_TITLE);
        boolean jobHeaderVisible = isElementVisible(JOB_DETAIL_HEADER);
        boolean jobDescriptionVisible = isElementVisible(JOB_DESCRIPTION);
        boolean urlContainsJob = driver.getCurrentUrl().toLowerCase().contains("job") || 
                                driver.getCurrentUrl().toLowerCase().contains("position") ||
                                driver.getCurrentUrl().toLowerCase().contains("career");
        
        System.out.println("Job detail page verification - Job title visible: " + jobTitleVisible + 
                          ", Job header visible: " + jobHeaderVisible + 
                          ", Job description visible: " + jobDescriptionVisible + 
                          ", URL contains job/position/career: " + urlContainsJob);
        
        return jobTitleVisible || jobHeaderVisible || jobDescriptionVisible || urlContainsJob;
    }
    
    public void clickApplyForThisJob() {
        safeClick(APPLY_BUTTON);
    }
    
    public boolean isApplyButtonVisible() {
        boolean applyButtonVisible = isElementVisible(APPLY_BUTTON);
        boolean anyApplyButtonVisible = isElementVisible(ANY_APPLY_BUTTON);
        
        System.out.println("Apply button verification - Specific apply button visible: " + applyButtonVisible + 
                          ", Any apply button visible: " + anyApplyButtonVisible);
        
        return applyButtonVisible || anyApplyButtonVisible;
    }
}
