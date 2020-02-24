package com.company;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Page {

    public static String URL = "https://evra.geophy.com/login";
    public static String address = "555 N. College Avenue, Tempe, AZ, 85281";
    public static String units = "200";
    public static String occupancy = "80";
    public static String year = "2000";
    public static String NOI  = "2000000";

    public static void login(){
        WebDriverWait wait = new WebDriverWait(TestHelper.drv, 5);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#email"))).sendKeys(Credentials.login);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#password"))).sendKeys(Credentials.password);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("button"))).click();
    }

    public static void fillParameters(){
        WebDriverWait wait = new WebDriverWait(TestHelper.drv, 5);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@id='address_input']"))).sendKeys(address);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("(//div[@class='pac-item'])[1]"))).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@id='noi']"))).sendKeys(NOI);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@name='number_of_units']"))).sendKeys(units);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@name='year_built']"))).sendKeys(year);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@name='occupancy']"))).sendKeys(occupancy);
    }

    public static void runValuationBtn(){
        TestHelper.drv.findElement(By.xpath("//button")).click();
    }

    public static String indicatorValue(){
        WebDriverWait wait = new WebDriverWait(TestHelper.drv, 10);
        String value = wait.until(ExpectedConditions.visibilityOfElementLocated
                (By.cssSelector("#confidenceIndicator > span > div"))).getAttribute("data-confidence-indicator");
        return value;
    }

    //Click the edit link
    public static void edit(){
        WebDriverWait wait = new WebDriverWait(TestHelper.drv, 10);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[text()='edit']"))).click();
    }

}
