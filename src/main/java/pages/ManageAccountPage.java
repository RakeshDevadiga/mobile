package pages;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.support.PageFactory;
import io.qameta.allure.Step;
import base.Common;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;

public class ManageAccountPage extends Common {
	public ManageAccountPage() {
		PageFactory.initElements(driver, this);
	}
	@Step("Click on log out")
	public void clickOnLogOut(AppiumDriver<MobileElement> driver) {
		try {
			driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
			driver.findElement(By.xpath("//*[@text='Logout']")).click();
			System.out.println("Click on Log out");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
