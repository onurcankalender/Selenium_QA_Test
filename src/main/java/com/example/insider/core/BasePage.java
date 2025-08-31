package com.example.insider.core;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;

public class BasePage {
    protected WebDriver driver;

    public BasePage(WebDriver driver) {
        this.driver = driver;
    }

    protected void hover(WebElement element) {
        Actions actions = new Actions(driver);
        actions.moveToElement(element).perform();
    }

    protected void hover(By locator) {
        WebElement element = Waiter.waitForElementVisible(driver, locator);
        hover(element);
    }

    protected void safeClick(WebElement element) {
        Waiter.waitForElementClickable(driver, element);
        element.click();
    }

    protected void safeClick(By locator) {
        WebElement element = Waiter.waitForElementClickable(driver, locator);
        element.click();
    }

    protected void selectByVisibleText(WebElement selectElement, String text) {
        Waiter.waitForElementVisible(driver, selectElement);
        Select select = new Select(selectElement);
        select.selectByVisibleText(text);
    }

    protected void selectByVisibleText(By locator, String text) {
        WebElement selectElement = Waiter.waitForElementVisible(driver, locator);
        selectByVisibleText(selectElement, text);
    }

    protected void switchToNewTab() {
        String originalWindow = driver.getWindowHandle();
        for (String windowHandle : driver.getWindowHandles()) {
            if (!originalWindow.equals(windowHandle)) {
                driver.switchTo().window(windowHandle);
                break;
            }
        }
    }

    protected boolean isElementVisible(By locator) {
        try {
            Waiter.waitForElementVisible(driver, locator);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    protected String getSelectedOptionText(By locator) {
        WebElement selectElement = Waiter.waitForElementVisible(driver, locator);
        Select select = new Select(selectElement);
        return select.getFirstSelectedOption().getText();
    }
}
