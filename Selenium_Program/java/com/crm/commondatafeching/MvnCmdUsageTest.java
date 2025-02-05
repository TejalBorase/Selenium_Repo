package com.crm.commondatafeching;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.Test;

public class MvnCmdUsageTest {

	@Test
	public void mvncmd() {

		String browser = System.getProperty("browser");
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
		
		driver.get(System.getProperty("url"));
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(Long.parseLong(System.getProperty("timeout"))));
		driver.manage().window().maximize();
		
		driver.findElement(By.name("user_name")).sendKeys(System.getProperty("username"));
		driver.findElement(By.name("user_password")).sendKeys(System.getProperty("password"));
		driver.findElement(By.id("submitButton")).click();
	}
	
	
	
	
	
	
	
	
	
	
}
