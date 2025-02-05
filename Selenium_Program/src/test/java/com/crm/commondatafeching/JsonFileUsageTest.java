package com.crm.commondatafeching;

import java.io.File;
import java.io.FileReader;
import java.time.Duration;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.Test;

public class JsonFileUsageTest {

	@Test
	public void launchBrowser() throws Exception {
		JSONParser parser = new JSONParser();
		Object converted = parser.parse(new FileReader(new File("./configurationData/commonData.json")));

		JSONObject actualObj = (JSONObject) converted;
		String browser = (String) actualObj.get("browser");
		WebDriver driver = null;
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
		
		driver.get((String)actualObj.get("url"));
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds((Long)actualObj.get("timeout")));
		driver.manage().window().maximize();
		
		driver.findElement(By.name("user_name")).sendKeys((String)actualObj.get("username"));
		driver.findElement(By.name("user_password")).sendKeys((String)actualObj.get("password"));
		driver.findElement(By.id("submitButton")).click();
	}
}










