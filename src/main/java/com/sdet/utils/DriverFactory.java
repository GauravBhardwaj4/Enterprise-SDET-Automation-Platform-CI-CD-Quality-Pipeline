package com.sdet.utils;

import org.openqa.selenium.WebDriver;

public class DriverFactory {
    private static final ThreadLocal<WebDriver> driver = new ThreadLocal<>();

    public static WebDriver getDriver(){
        return driver.get();
    }

    public static void setDriver(WebDriver instance){
        driver.set(instance);
    }
    public static void quitDriver(){
        if (driver.get() != null) {
            driver.get().quit();

        }
    }
}
