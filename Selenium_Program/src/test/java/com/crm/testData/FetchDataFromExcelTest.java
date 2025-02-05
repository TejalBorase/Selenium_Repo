package com.crm.testData;

import java.io.File;
import java.io.FileInputStream;
import java.lang.reflect.Method;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Iterator;
import java.util.Properties;
import java.util.Random;
import java.util.Set;
import java.util.TreeSet;

import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import com.crm.entity.Organization;

@Listeners(value = com.crm.listener.ListenerImpl.class)
public class FetchDataFromExcelTest {

	public static WebDriver driver;
	Properties properties ;
	ExtentReports report;

	@DataProvider(name = "data")
	public Iterator<Organization> getDataFromExcel() throws Exception {
		FileInputStream stream = new FileInputStream(new File("./TestData/TestData.xlsx"));
		Workbook workbook = WorkbookFactory.create(stream);
		Sheet sheet = workbook.getSheet("OrgData");
		Set<Organization> organizations = new TreeSet<Organization>();
		System.out.println(sheet.getPhysicalNumberOfRows());
		Random random = new Random();
		for (int i = 1; i < sheet.getPhysicalNumberOfRows(); i++) {

			String orgName = sheet.getRow(i).getCell(0).toString();
			String otherEmail = sheet.getRow(i).getCell(1).toString();

			organizations.add(new Organization(orgName+random.nextInt(1000), otherEmail+random.nextInt(100)));
		}
		return organizations.iterator();
	}

	@BeforeSuite
	public void launchBrowser() throws Exception {
		properties = new Properties();
		properties.load(new FileInputStream(new File("./configurationData/common.properties")));
		String browser = properties.getProperty("browser");
		driver = null;
		switch (browser) {
		case "chrome":
			driver = new ChromeDriver();
			break;

		case "firefox":

			driver = new FirefoxDriver();
			break;

		default:
			System.out.println("Invalid Data...");
			break;
		}

		String url = properties.getProperty("url");
		driver.get(url);
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		driver.manage().window().maximize();
		
		LocalDateTime date = LocalDateTime.now();
		String format = date.format(DateTimeFormatter.ofPattern("DD_MM_YY_hh_mm_ss"));
		
		ExtentSparkReporter spark = new ExtentSparkReporter(new File("./Reports/Report_"+format+".html"));
		spark.config().setDocumentTitle("Sample");
		spark.config().setReportName("Sample");
		spark.config().setTheme(Theme.DARK);
		
		 report = new ExtentReports();
		report.attachReporter(spark);
		report.setSystemInfo("OS", "Windows-11");
		report.setSystemInfo("browser", browser);
	}
	
	
	@BeforeMethod
	public void login() {
		driver.findElement(By.name("user_name")).sendKeys(properties.getProperty("username"));
		driver.findElement(By.name("user_password")).sendKeys(properties.getProperty("password"));
		driver.findElement(By.id("submitButton")).click();
	}
	
	@Test(dataProvider = "data")
	public void addOrgWithEmail(Organization org, Method method) {
		ExtentTest test = report.createTest(method.getName());
		driver.findElement(By.linkText("Organizations")).click();
		driver.findElement(By.cssSelector("img[title='Create Organization...']")).click();
		driver.findElement(By.name("accountname")).sendKeys(org.getOrgName());
		driver.findElement(By.name("email2")).sendKeys(org.getOtherEmail());
		driver.findElement(By.xpath("//input[@type='button']")).click();
		String orgName = driver.findElement(By.xpath("//span[contains(text(),'Organization Information')]")).getText();
		test.log(Status.PASS, orgName);
	}
	
	@AfterMethod
	public void logout(){
		Actions actions = new Actions(driver);
		actions.moveToElement(driver.findElement(By.xpath("//img[@src='themes/softed/images/user.PNG']"))).build().perform();
		actions.moveToElement(driver.findElement(By.linkText("Sign Out"))).click().build().perform();
//		report.flush();
		
	}

	@AfterSuite
	public void closeBrowser() {
		driver.close();
	}

	
}
