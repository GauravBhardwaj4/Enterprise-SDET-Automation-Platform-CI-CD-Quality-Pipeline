package com.sdet.tests.base;

import com.sdet.utils.DriverFactory;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;

public class BaseTest {

    @BeforeMethod
    @Parameters({"browser"})
    public void setUp(){
        //Initialize ChromeDriver and assign the ThreadLocal
        ChromeDriver chromeDriver = new ChromeDriver();
        DriverFactory.setDriver(chromeDriver);
        DriverFactory.getDriver().manage().window().maximize();
    }

    @AfterMethod
    public void teardown(){
        DriverFactory.quitDriver();
    }

}
