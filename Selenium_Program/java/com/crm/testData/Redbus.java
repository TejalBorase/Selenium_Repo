package com.crm.testData;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;

public class Redbus {

	@Test
	public void bookTicket() throws InterruptedException {
		ChromeOptions options  = new ChromeOptions();
		WebDriver driver = new ChromeDriver(options);
		
		options.addArguments("--headless");
		driver.get("https://www.redbus.in");
		driver.manage().window().maximize();
//		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		
//		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
//		WebElement dateInput = wait.until(ExpectedConditions.elementToBeClickable(By.id("onwardCal")));
//		
//		dateInput.click();
//		
//		wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("rb-monthTable")));
//		
//		String targateDate = "31";
//		
//		WebElement calendar = driver.findElement(By.xpath("//div[@class='rb-monthTable first last']/table"));
//		
//		WebElement selectDate = calendar.findElement(By.xpath("//td[text()='"+targateDate+"]"));
//		selectDate.click();
//		
//		Thread.sleep(2000);
		
		Actions actions = new Actions(driver);
		driver.findElement(By.id("src")).click();
		actions.sendKeys(Keys.TAB).build().perform();
		actions.sendKeys(Keys.TAB).build().perform();
		
		driver.findElement(By.xpath("//div[@class='DayNavigator__IconBlock-qj8jdz-2 iZpveD']/*[local-name()='svg']")).click();
		driver.findElement(By.xpath("//div[@class='DayTiles__CalendarDaysBlock-sc-1xum02u-0 isgDNj']/span[(text()='3')]")).click();
	}
	
}
