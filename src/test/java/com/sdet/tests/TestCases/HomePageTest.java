package com.sdet.tests.TestCases;

import com.sdet.pageobjects.HomePage;
import com.sdet.tests.base.BaseTest;
import com.sdet.utils.DriverFactory;
import org.testng.Assert;
import org.testng.annotations.Test;

public class HomePageTest extends BaseTest {
    @Test
    public void verifyHomePage(){
        DriverFactory.getDriver().get("https://testautomationpractice.blogspot.com/");
        HomePage hp = new HomePage();
        String actualTitle = hp.getPageTitile();
        System.out.println("Page Title = "+actualTitle);
        Assert.assertTrue(actualTitle.contains("Automation Testing Practice"),"Page Title Did not Match");

        hp.enterUserDetails("Gaurav B", "Tester@user.com","1236549870","123 S Main St");
        hp.selectMaleGender();
        System.out.println("HomePage Test Executed Successfully");








    }

}
