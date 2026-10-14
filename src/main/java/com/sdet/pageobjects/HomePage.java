package com.sdet.pageobjects;

import com.sdet.utils.DriverFactory;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class HomePage {
    private WebDriver driver;
    private WebDriverWait wait;

    //Example element on the main practice homepage(e.g. Name Input Field)
    @FindBy(id="name")
    private WebElement nameInput;

    @FindBy(id = "email")
    private WebElement emailInput;

    @FindBy(id="phone")
    private WebElement phoneInput;

    @FindBy(id="textarea")
    private WebElement addressInput;

    @FindBy(id="male")
    private WebElement maleRadioButton;

    @FindBy(id="female")
    private WebElement femaleRadioButton;

    public HomePage() {
        this.driver = DriverFactory.getDriver();
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        PageFactory.initElements(driver, this);
    }
    public void enterUserDetails(String name, String email, String phone, String address) {
        wait.until(ExpectedConditions.visibilityOf(nameInput));
        nameInput.clear();
        nameInput.sendKeys(name);

        emailInput.clear();
        emailInput.sendKeys(email);

        phoneInput.clear();
        phoneInput.sendKeys(phone);

        addressInput.clear();
        addressInput.sendKeys(address);
    }


    public void selectMaleGender(){
        wait.until(ExpectedConditions.elementToBeClickable(maleRadioButton));
        if(!maleRadioButton.isSelected()){
            maleRadioButton.click();
        }
    }
    public void selectFemaleGender(){
        wait.until(ExpectedConditions.elementToBeClickable(femaleRadioButton));
        if (!femaleRadioButton.isSelected()){
            femaleRadioButton.click();
        }
    }
    public String getPageTitile(){
        return driver.getTitle();
    }
}
