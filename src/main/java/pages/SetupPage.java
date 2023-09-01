package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.support.PageFactory;

import base.Common;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.qameta.allure.Step;

public class SetupPage extends Common {

	public SetupPage() {
		PageFactory.initElements(driver, this);
	}

	@Step("Enter environment link")
	public void enterDemoLink(AppiumDriver<MobileElement> driver, String link) throws InterruptedException {
		driver.findElement(By.xpath("//*[@text='Paste link in here']")).sendKeys(link);
		System.out.println("Entered environment link");
		Thread.sleep(3000);
	}

	@Step("Click on Continue")
	public void clickOnContinue(AppiumDriver<MobileElement> driver) throws InterruptedException {
		driver.findElement(By.xpath("//*[@text='Continue']")).click();
		System.out.println("Clicked on Continue Button");
		Thread.sleep(3000);
	}

}
