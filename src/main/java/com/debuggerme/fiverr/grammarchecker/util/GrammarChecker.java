package com.debuggerme.fiverr.grammarchecker.util;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.awt.*;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;
import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * @author Jamith Nimantha
 */
public class GrammarChecker {

    private static WebDriver DRIVER_INSTANCE;

    private GrammarChecker(){}


    private static void getDriverInstance(){
        if (null == DRIVER_INSTANCE){
            System.setProperty("webdriver.gecko.driver", "geckodriver");
            FirefoxOptions options = new FirefoxOptions();
//            options.setCapability("requireWindowFocus", true);
            options.addArguments("--disable-blink-features=AutomationControlled");
//            options.setExperimentalOption("excludeSwitches", Collections.singletonList("enable-automation"));
//            options.setExperimentalOption("useAutomationExtension", false);
            options.setHeadless(false); // false mean browser is visible
//            FirefoxProfile fp = new FirefoxProfile();
//            fp.setPreference("browser.safebrowsing.malware.enabled", true);
//            options.setProfile(fp);
            DRIVER_INSTANCE = new FirefoxDriver(options);
            DRIVER_INSTANCE.manage().window().maximize();
            DRIVER_INSTANCE.manage().deleteAllCookies();
            DRIVER_INSTANCE.manage().timeouts().pageLoadTimeout(40, TimeUnit.SECONDS);
            DRIVER_INSTANCE.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
            DRIVER_INSTANCE.navigate().to("https://quillbot.com/grammar-check");
            try {
                Thread.sleep(6000);
            } catch (InterruptedException ignored) {
            }
        }
    }

    private static long random(int low, int high){
        Random r = new Random();
        return (long) r.nextInt(high-low) + low;
    }

    public static String correctGrammar(String text){
        getDriverInstance();
        try {
            Thread.sleep(random(1000, 2000));
        } catch (InterruptedException ignored) {
        }
        StringSelection stringSelection = new StringSelection(text);
        java.awt.datatransfer.Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        clipboard.setContents(stringSelection, null);
        WebElement element1 = DRIVER_INSTANCE.findElement(By.xpath("//*[@id=\"grammarbot\"]"));
        element1.click();
        element1.sendKeys(Keys.COMMAND,"v" );
//        new Actions(DRIVER_INSTANCE).contextClick(element1).keyDown(Keys.COMMAND)
//                .sendKeys("v").keyUp(Keys.COMMAND).build().perform();
        WebDriverWait wait = new WebDriverWait(DRIVER_INSTANCE, 40);
        try {
            Thread.sleep(random(6000, 8000));
        } catch (InterruptedException ignored) {
        }
        WebElement element = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("/html/body/div[1]/div[2]/div[3]/div/div/div[1]/div/div/div[1]/div/div[3]/div/div[2]/div/button")));
        try {
            Thread.sleep(random(6000, 8000));
        } catch (InterruptedException ignored) {
        }
        element.click();
        try {
            Thread.sleep(random(6000, 8000));
        } catch (InterruptedException ignored) {
        }
        element = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("/html/body/div[1]/div[2]/div[3]/div/div/div[1]/div/div/div[1]/div/div[3]/div/div[3]/div/div[3]/div/button")));
        try {
            Thread.sleep(random(2000, 4000));
        } catch (InterruptedException ignored) {
        }
        element.click();
        try {
            Thread.sleep(random(2000, 4000));
        } catch (InterruptedException ignored) {
        }
        DRIVER_INSTANCE.findElement(By.xpath("/html/body/div[1]/div[2]/div[3]/div/div/div[1]/div/div/div[1]/div/div[1]/div[1]/div[4]/div/div/button")).click();
        try {
            Thread.sleep(random(5000, 7000));
        } catch (InterruptedException ignored) {
        }
        WebElement clearTextElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("/html/body/div[4]/div[3]/div/div[3]/button")));
        clearTextElement.click();
        try {
            Thread.sleep(random(1000, 2000));
        } catch (InterruptedException ignored) {
        }
        try {
            return (String) Toolkit.getDefaultToolkit().getSystemClipboard().getData(DataFlavor.stringFlavor);
        } catch (UnsupportedFlavorException | IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void quitWebDriver(){
        if (null != DRIVER_INSTANCE){
            DRIVER_INSTANCE.quit();
            DRIVER_INSTANCE = null;
        }
    }


}
