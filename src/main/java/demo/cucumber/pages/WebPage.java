package demo.cucumber.pages;

import org.openqa.selenium.*;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import demo.cucumber.common.Config;

import java.time.Duration;

public class WebPage {
    WebDriver driver;

    public WebPage(WebDriver driver) {
        this.driver = driver;
    }

    public int getDefaultDriverTimeoutSeconds() {
        return Config.getDriverTimeout();
    }

    public RemoteWebDriver getDriver() {
        return (RemoteWebDriver) driver;
    }

    public String getPageTitle() {
        return driver.getTitle();
    }

    public void navigateTo(String url) {
        driver.navigate().to(url);
    }

    public boolean waitUntilElementVisible(WebElement element, int timeout) {
         WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
         return wait.until(ExpectedConditions.visibilityOf(element)) != null;
    }

    public boolean waitUntilElementVisible(WebElement element) {
         return waitUntilElementVisible(element, getDefaultDriverTimeoutSeconds());
    }

    public boolean waitUntilElementInvisible(WebElement element) {
        return waitUntilElementInvisible(element, getDefaultDriverTimeoutSeconds());
    }

    public boolean waitUntilElementInvisible(WebElement element, int timeout) {
        try {
            // Turn off implicit wait to prevent premature return
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(0));
            Wait<? extends RemoteWebDriver> wait = new FluentWait<>(getDriver())
                    .withTimeout(Duration.ofSeconds(timeout))
                    .pollingEvery(Duration.ofMillis(500));
            return wait.until(drv -> {
                try {
                    return !element.isDisplayed();
                } catch (NoSuchElementException e) {
                    return true;
                }
            });
        } catch (TimeoutException e) {
            return false;
        }
        finally {
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(Config.getDriverTimeout()));
        }
    }

    public boolean waitUntilElementClickable(WebElement element, int timeout) {
         WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
         return wait
                 .pollingEvery(Duration.ofMillis(500))
                 .until(ExpectedConditions.elementToBeClickable(element)) != null;
    }

    public boolean waitUntilElementClickable(WebElement element) {
        return waitUntilElementClickable(element, getDefaultDriverTimeoutSeconds());
    }
}
