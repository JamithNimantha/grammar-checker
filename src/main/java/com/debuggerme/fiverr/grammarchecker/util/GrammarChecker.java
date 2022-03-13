package com.debuggerme.fiverr.grammarchecker.util;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.awt.*;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * @author Jamith Nimantha
 */
public class GrammarChecker {

    private static WebDriver DRIVER_INSTANCE;

    private GrammarChecker(){}


    private static void getDriverInstance(){
        if (null == DRIVER_INSTANCE){
            FirefoxOptions options = new FirefoxOptions();
            options.setCapability("requireWindowFocus", true);
            options.setHeadless(false); // false mean browser is visible
            DRIVER_INSTANCE = new FirefoxDriver(options);
            DRIVER_INSTANCE.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
            DRIVER_INSTANCE.navigate().to("https://quillbot.com/grammar-check");
            try {
                Thread.sleep(6000);
            } catch (InterruptedException ignored) {
            }
        }
    }

    public static String correctGrammar(String text){
        getDriverInstance();
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
            Thread.sleep(6000);
        } catch (InterruptedException ignored) {
        }
        WebElement element = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("/html/body/div[1]/div[2]/div[3]/div/div/div[1]/div/div/div[1]/div/div[3]/div/div[2]/div/button")));
        try {
            Thread.sleep(6000);
        } catch (InterruptedException ignored) {
        }
        element.click();
        try {
            Thread.sleep(6000);
        } catch (InterruptedException ignored) {
        }
        element = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("/html/body/div[1]/div[2]/div[3]/div/div/div[1]/div/div/div[1]/div/div[3]/div/div[3]/div/div[3]/div/button")));
        try {
            Thread.sleep(2000);
        } catch (InterruptedException ignored) {
        }
        element.click();
        try {
            Thread.sleep(2000);
        } catch (InterruptedException ignored) {
        }
        DRIVER_INSTANCE.findElement(By.xpath("//*[@id=\"grammarbot\"]")).clear();
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
