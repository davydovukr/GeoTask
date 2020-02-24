package com.company;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.io.File;
import java.io.IOException;

public class TestHelper {

    public static WebDriver drv;

    private static void get(String url) {
        drv.get(url);
    }

    public static void setup(String url) {
        System.setProperty("webdriver.chrome.driver", "lib/chromedriver");
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--start-maximized");
//        options.addArguments("--auto-open-devtools-for-tabs");
        drv = new ChromeDriver(options);
        get(url);
    }

    public static void quit() {
        drv.quit();
    }

    //Check that a given element is displayed
    public static boolean elementIsDisplayed(String targetxpath){
        WebDriverWait wait = new WebDriverWait(drv, 5);
        return wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(targetxpath))).isDisplayed();
    }

    //Take screenshot
    public static void takeScreenshot(String filepath) throws IOException {
        File scr = ((TakesScreenshot)drv).getScreenshotAs(OutputType.FILE);
        FileUtils.copyFile(scr, new File(filepath));
    }
}
