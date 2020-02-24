package com.company;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.io.IOException;

public class EVRA {

    @Before
    //Open URL
    public void setup(){TestHelper.setup(Page.URL);}

    @After
    public void cleanup(){TestHelper.quit();}

    @Test
    //Check that login is successful with given credentials
    public void login(){
        WebDriverWait wait = new WebDriverWait(TestHelper.drv, 5);
        //Log in
        Page.login();
        //Check that SEARCH link is present on the page
        TestHelper.elementIsDisplayed("//a[@id='nav-search-link']");
        //Check that welcome text is displayed
        TestHelper.elementIsDisplayed("//span[text()=' Welcome back, QA Geophy.']");
    }

    @Test
    //Search for a property with given parameters and check that search is being executed
    public void search(){
        WebDriverWait wait = new WebDriverWait(TestHelper.drv, 5);
        //Log in
        Page.login();
        //Enter search parameters
        Page.fillParameters();
        //Click the RUN VALUATION button
        Page.runValuationBtn();
        //Check that "search in progress" is happening
        Assert.assertTrue(wait.until(ExpectedConditions.textToBePresentInElementLocated(By.cssSelector("h4"),
                "RUNNING VALUATION")));
    }

    @Test
    //Check that Report page contains results for the searched property
    public void checkParameters(){
        WebDriverWait wait = new WebDriverWait(TestHelper.drv, 10);
        //Log in
        Page.login();
        //Enter search parameters
        Page.fillParameters();
        //Read the address line value
        String addr = TestHelper.drv.findElement(By.xpath("//input[@name='address_formatted']")).getAttribute("value").toUpperCase();
        //Click the RUN VALUATION button
        Page.runValuationBtn();
        //Check address line
        Assert.assertEquals(addr, wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath
                ("(//h4)[3]"))).getText());
        //Check Number of units
        Assert.assertEquals(Page.units,
                wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath
                        ("//td[text()='Number of units']/../td[2]"))).getText());
        //Check Year of construction
        Assert.assertEquals(Page.year,
                wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath
                        ("//td[text()='Year of construction']/../td[2]"))).getText());
    }

    @Test
    //Check that Confidence indicator is green for the given input
    public void indicatorGreen() throws IOException {
        //Log in
        Page.login();
        //Enter search parameters
        Page.fillParameters();
        //Click the RUN VALUATION button
        Page.runValuationBtn();
        //Check that the indicator is green and save a screenshot
        String value = Page.indicatorValue();
        TestHelper.takeScreenshot("/Users/denisdavydov/Screenshots/indicatorGreen.png");
        Assert.assertEquals("green", value);
    }

    @Test
    //Transaction price changes when user edits year of construction
    public void editYear(){
        WebDriverWait wait = new WebDriverWait(TestHelper.drv, 10);
        //Log in
        Page.login();
        //Enter search parameters
        Page.fillParameters();
        //Click the RUN VALUATION button
        Page.runValuationBtn();
        //Read the transaction price
        String price = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("(//h1)[1]"))).getText();
        //Click the edit link
        Page.edit();
        //Edit Year of construction
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@name='year_built']"))).clear();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@name='year_built']"))).sendKeys("1999");
        //Click the RUN VALUATION button
        Page.runValuationBtn();

        //Check that Year of construction was changed
        Assert.assertEquals("1999",
                wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath
                        ("//td[text()='Year of construction']/../td[2]"))).getText());

        //Check that estimated transaction price was changed
        Assert.assertNotEquals(price, wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("(//h1)[1]"))).getText());
    }

    @Test
    //Confidence indicator indicates discrepancy
    public void indicatorRed() throws IOException {
        //Log in
        Page.login();
        //Enter search parameters
        Page.fillParameters();
        //Enter a different NOI value
        TestHelper.drv.findElement(By.xpath("//input[@id='noi']")).clear();
        TestHelper.drv.findElement(By.xpath("//input[@id='noi']")).sendKeys("20000");
        //Click the RUN VALUATION button
        Page.runValuationBtn();
        //Check that the indicator is red and save a screenshot
        String value = Page.indicatorValue();
        TestHelper.takeScreenshot("/Users/denisdavydov/Screenshots/indicatorRed.png");
        Assert.assertEquals("red", value);
    }
}
