package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.support.PageFactory;
import base.Common;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.qameta.allure.Step;

public class LoginPage extends Common{
	public LoginPage() {
		PageFactory.initElements(driver, this);
	}
	
	@Step("Entered Username")
	public void enterUsername(AppiumDriver<MobileElement> driver, String username) {
		driver.findElement(By.xpath("//*[@text='User name']")).sendKeys(username);
		System.out.println("Entered Username:" +username);
	}
	
	@Step("Entered Password")
	public void enterPassword(AppiumDriver<MobileElement> driver, String password) {
		driver.findElement(By.xpath("//*[@text='Password']")).sendKeys(password);
		System.out.println("Entered Password:" +password);
	}
	
	@Step("Click on Login button")
	public void clickLogin(AppiumDriver<MobileElement> driver) {
		driver.findElement(By.xpath("//*[@text='Login']/..")).click();
		System.out.println("Click on Login");
	}
	
	public void login(AppiumDriver<MobileElement> driver, String username, String password) throws InterruptedException {
		enterUsername(driver, username);
		enterPassword(driver, password); 
		clickLogin(driver);
		Thread.sleep(3000);
	}
}
