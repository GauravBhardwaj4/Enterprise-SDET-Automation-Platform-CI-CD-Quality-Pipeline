package com.sdet.tests;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.Test;

public class FirstTest {

    @Test
    public void openPracticeWebsite() {
        WebDriver driver = new ChromeDriver();
        driver.get("https://testautomationpractice.blogspot.com/p/download-files_25.html");
        driver.manage().window().fullscreen();
        driver.quit();



    }

}
