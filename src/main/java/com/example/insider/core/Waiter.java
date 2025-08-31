package com.example.insider.core;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class Waiter {
    private static final int DEFAULT_TIMEOUT = 10;
    private static WebDriverWait wait;

    public static WebDriverWait getWait(WebDriver driver) {
        if (wait == null) {
            wait = new WebDriverWait(driver, Duration.ofSeconds(DEFAULT_TIMEOUT));
        }
        return wait;
    }

    public static WebElement waitForElementVisible(WebDriver driver, By locator) {
        try {
            return getWait(driver).until(ExpectedConditions.visibilityOfElementLocated(locator));
        } catch (Exception e) {
            System.out.println("Element not visible after timeout: " + locator);
            System.out.println("Error: " + e.getMessage());
            throw e;
        }
    }

    public static WebElement waitForElementClickable(WebDriver driver, By locator) {
        return getWait(driver).until(ExpectedConditions.elementToBeClickable(locator));
    }

    public static WebElement waitForElementClickable(WebDriver driver, WebElement element) {
        return getWait(driver).until(ExpectedConditions.elementToBeClickable(element));
    }

    public static WebElement waitForElementVisible(WebDriver driver, WebElement element) {
        return getWait(driver).until(ExpectedConditions.visibilityOf(element));
    }

    public static void waitForUrlContains(WebDriver driver, String urlPart) {
        getWait(driver).until(ExpectedConditions.urlContains(urlPart));
    }

    public static void waitForElementToBePresent(WebDriver driver, By locator) {
        getWait(driver).until(ExpectedConditions.presenceOfElementLocated(locator));
    }

    public static void waitForPageLoad(WebDriver driver) {
        getWait(driver).until(webDriver -> 
            ((org.openqa.selenium.JavascriptExecutor) webDriver)
                .executeScript("return document.readyState").equals("complete"));
    }
}
