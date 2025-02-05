package com.crm.commondatafeching;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.Duration;
import java.util.Properties;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class PropertiesFileUsageTest {

	public static void main(String[] args) throws FileNotFoundException, IOException {
		
		Properties properties = new Properties();
		properties.load(new FileInputStream(new File("./configurationData/common.properties")));
		String browser = properties.getProperty("browser");
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
		
		String url = properties.getProperty("url");
		driver.get(url);
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		driver.manage().window().maximize();
		
		driver.findElement(By.name("user_name")).sendKeys(properties.getProperty("username"));
		driver.findElement(By.name("user_password")).sendKeys(properties.getProperty("password"));
		driver.findElement(By.id("submitButton")).click();
	}
}
