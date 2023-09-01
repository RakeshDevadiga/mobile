package base;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDate;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.TimeUnit;
import org.apache.commons.io.FileUtils;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.ITestListener;
import org.testng.annotations.DataProvider;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.TouchAction;
import io.appium.java_client.touch.offset.PointOption;
import pages.SetupPage;

public class Common implements ITestListener {

	public AppiumDriver driver;
	public ExtentReports extent;
	public SetupPage setupPage;
	public static FileInputStream fis;
	public static FileOutputStream fos;
	public static XSSFWorkbook wb;
	public static XSSFSheet sheet;
	public static XSSFRow row;
	public static XSSFCell cell;

	
	@DataProvider(name = "DocFileRead")
	public Object[] DocFileRead() {
		Object[] arrayObject = getExcelData("userData", "Sheet1");
		return arrayObject;
	}

	@DataProvider(name = "DocFileReadFormCreation")
	public Object[] DocFileReadFormCreation() {
		Object[] arrayObject = getExcelData("userData", "Sheet2");
		return arrayObject;
	}

	@DataProvider(name = "DocFileReadFormEdit")
	public Object[] DocFileReadFormEdit() {
		Object[] arrayObject = getExcelData("userData", "Sheet3");
		return arrayObject;
	}
	
	@DataProvider(name = "DocFileReadFormNewCreation")
	public Object[] DocFileReadFormNewCreation() {
		Object[] arrayObject = getExcelData("userData", "Sheet4");
		return arrayObject;
	}
	
	@DataProvider(name = "DocFileReadFormSearch")
	public Object[] DocFileReadFormSearch() {
		Object[] arrayObject = getExcelData("userData", "Sheet5");
		return arrayObject;
	}
	
	@DataProvider(name = "DocFileReadFormFilter")
	public Object[] DocFileReadFormFilter() {
		Object[] arrayObject = getExcelData("userData", "Sheet6");
		return arrayObject;
	}
	public static ThreadLocal<AppiumDriver> tdriver = new ThreadLocal<AppiumDriver>();

	public AppiumDriver InitailizeDriver() throws MalformedURLException {
		String path = System.getProperty("user.dir") + "\\reports\\index.html";
		ExtentSparkReporter reporter = new ExtentSparkReporter(path);
		reporter.config().setReportName("Skedulomatic Android Automation");
		reporter.config().setDocumentTitle("Test Results");
		System.out.println("Location "+ System.getProperty("user.dir")+ "\\src\\test\\resources\\");
		extent = new ExtentReports();
		extent.attachReporter(reporter);

		DesiredCapabilities caps = new DesiredCapabilities();
		caps.setCapability("deviceName", "Android Emulator");
		caps.setCapability("platformName", "Android");
		caps.setCapability("app", System.getProperty("user.dir") + "/src/test/resources/Apk/Skedulomatic_m.apk");
		
		caps.setCapability("appActivity", "com.dcmsapp.MainActivity");
		//caps.setCapability("platformVersion", "12");
		caps.setCapability("udid", "emulator-5554");
		caps.setCapability("autoAcceptAlerts", true);
		caps.setCapability("permissions",
				"{\"com.ss.ndms.react.test\": {\"notifications\": \"inuse\", \"location\": \"yes\", \"battery\": \"yes\", \"camera\": \"yes\",\"storage\":\"yes\"}}");

		URL url = new URL("http://127.0.0.1:4723/wd/hub");
		driver = new AppiumDriver(url, caps);
		System.out.println("Inside initilixe "+driver.getSessionId());
		tdriver.set(driver);
		return getDriver();
	}

	public static synchronized AppiumDriver getDriver() {
		return tdriver.get();
	}

	//@BeforeMethod
	public void SetUp() throws MalformedURLException {
		try {
			InitailizeDriver();
			System.out.println("Inside setup "+driver.getSessionId());
			driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
			setupPage = new SetupPage();
			driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		} catch (Exception e) {
			System.out.println("Caused due to : " + e.getCause());
			System.out.println("Message is :" + e.getMessage());
			e.getStackTrace();
		}
	}

	
/*
	public String getScreenshot(AppiumDriver driver, String testCaseName) {
		File src = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		String path = System.getProperty("user.dir") + "/screenshots/" + testCaseName + ".png";
		File destination = new File(path);
		System.out.println("Screenshot captured for " + testCaseName);
		try {
			FileUtils.copyFile(src, destination);
		} catch (IOException e) {
			System.out.println("Capture Failed " + e.getMessage());
		}
		return path;
	}
*/
	public String getScreenshotPath(AppiumDriver driver, String testCaseName) throws IOException {
		File source = driver.getScreenshotAs(OutputType.FILE);
		String destinationFile = System.getProperty("user.dir") + "//reports//" + testCaseName + ".png";
		FileUtils.copyFile(source, new File(destinationFile));
		return destinationFile;
	}

	public String[] getExcelData(String xlsxFileName, String xlsheet) {
		String[] arrayExcelData = null;
		try {
			fis = new FileInputStream(
					System.getProperty("user.dir") + "/src/test/resources/TestData/" + xlsxFileName + ".xlsx");
			wb = new XSSFWorkbook(fis);
			sheet = wb.getSheet(xlsheet);
			int totalNoOfRows = sheet.getLastRowNum();
			arrayExcelData = new String[totalNoOfRows];

			DataFormatter formatter = new DataFormatter();
			for (int i = 1; i <= totalNoOfRows; i++) {
				row = sheet.getRow(i);
				String cellData = formatter.formatCellValue(row.getCell(0));
				arrayExcelData[i - 1] = cellData;
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
			e.printStackTrace();
		}
		return arrayExcelData;
	}
	
	//Date methods
		public DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd MMMM yyyy");
		
		public SimpleDateFormat formatter = new SimpleDateFormat("dd MMMM yyyy");
		
		LocalDate todayDate = LocalDate.now();
		
		LocalDate nxtDate = findDay(todayDate, 3);
		
		//public String SelectDate = formatter.format(nxtDate);
		
		
		public static LocalDate findToday(LocalDate localdate)
		{
			return LocalDate.now();
		}
		public static LocalDate findDay(LocalDate localdate, int dayPlus)
		{
			return localdate.plusDays(dayPlus);
		}
		public static LocalDate findNext2Day(LocalDate localdate)
		{
			return localdate.plusDays(2);
		}
		
		//Scroll methods
		@SuppressWarnings("rawtypes")
		public void scroll(int fromX, int fromY, int toX, int toY)
		{
			(new TouchAction(driver)).press(PointOption.point(fromX, fromY)).moveTo(PointOption.point(toX, toY)).release().perform();	  
		}
		
		@SuppressWarnings("rawtypes")
		public void tapOnScreen(int x, int y)
		{
			(new TouchAction(driver)).tap(PointOption.point(x, y)).perform();
		}
		
}
