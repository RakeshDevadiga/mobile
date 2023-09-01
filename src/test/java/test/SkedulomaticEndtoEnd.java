package test;

import java.net.MalformedURLException;
import java.util.List;

import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import base.Common;
import base.TestAllureListener;
import base.Utility;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;
import pages.LoginPage;
import pages.ManageAccountPage;
import pages.PermissionPage;
import pages.SetupPage;


@SuppressWarnings("unchecked")
@Listeners({ TestAllureListener.class })
public class SkedulomaticEndtoEnd extends Common {

	@BeforeClass
	public void initialize() throws MalformedURLException {
		SetUp();
	}

	@Test(dataProvider = "DocFileRead", priority = 0)
	@Description("Verify the login test")
	@Epic("Login end to end flow")
	@Feature("Login Feature")
	@Story("login and create and fill collector layer details and log out")
	@Severity(SeverityLevel.CRITICAL)
	public void LoginTestCases(String rowsCount) throws InterruptedException {
		try {
			System.out.println("Rowcount " + rowsCount);
			int Row_number = Integer.parseInt(rowsCount);

			List<String> list = Utility.getRowData("userData", "Sheet1", Row_number);
			SetupPage setupPage = new SetupPage();
			
			setupPage.enterDemoLink(driver, "testing");
			setupPage.clickOnContinue(driver);
			
			PermissionPage permission = new PermissionPage();
			
			permission.clickOnAllow(driver);
			permission.clickOnLocationPermission(driver);
			permission.allowOK(driver);
			permission.allowWhileUsingApp(driver);
			permission.clickOnAllowAllTimeRadio(driver);
			permission.clickOnBackButton(driver);
			permission.clickOnBatteryPermission(driver);
			permission.clickOnCameraPermission(driver);
			permission.allowWhileUsingApp(driver);
			permission.clickOnStoragePermission(driver);
			permission.clickOnAllow(driver);
			permission.clickOnGo(driver);

			
			// boolean loginStatus = login.ValidLogin(driver, list.get(2), list.get(3));
			//permission.clickOnAllowForPhysicalActivity(driver);
			LoginPage loginPage = new LoginPage();
			// loginPage.enterUsername(driver, list.get(2));
			// loginPage.enterPassword(driver, list.get(3));
			// loginPage.clickLogin(driver);
			loginPage.login(driver, "testautomation1","123123");
			// permission.clickOnAllow(driver); // For Notification
			
	
			

		} catch (Exception e) {
			e.printStackTrace();

		}
	}

	@AfterClass
	public void Teardown() throws MalformedURLException {
		driver.quit();
	}

}
