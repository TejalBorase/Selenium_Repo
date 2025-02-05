package com.crm.listener;

import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.testng.ISuite;
import org.testng.ISuiteListener;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import com.crm.testData.FetchDataFromExcel;

public class ListenerImpl  implements ITestListener, ISuiteListener{
	
	ExtentReports report;
	String ss;
	
	@Override
	public void onStart(ISuite suite) {
		LocalDateTime date = LocalDateTime.now();
		String format = date.format(DateTimeFormatter.ofPattern("DD_MM_YY_hh_mm_ss"));
		ExtentSparkReporter spark = new ExtentSparkReporter(new File("./Reports/Report_"+format+".html"));
		spark.config().setDocumentTitle("Sample");
		spark.config().setReportName("Sample");
		spark.config().setTheme(Theme.DARK);
		
		report = new ExtentReports();
		report.attachReporter(spark);
		report.setSystemInfo("OS", "Windows-11");
		report.setSystemInfo("browser", "Chrome");
	}

	@Override
	public void onFinish(ISuite suite) {
		report.flush();
	}

	@Override
	public void onTestSuccess(ITestResult result) {
		TakesScreenshot shot = (TakesScreenshot)FetchDataFromExcel.driver;
		ss = shot.getScreenshotAs(OutputType.BASE64);
		LocalDateTime date = LocalDateTime.now();
		String format = date.format(DateTimeFormatter.ofPattern("DD_MM_YY_hh_mm_ss"));
		ExtentTest test = report.createTest(result.getMethod().getMethodName());
		test.addScreenCaptureFromBase64String(ss);
//		File target = new File("./ScreenShot/Result"+format+".png");
//		try {
//			FileHandler.copy(source, target);
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
	}

	@Override
	public void onTestFailure(ITestResult result) {
		// TODO Auto-generated method stub
		ITestListener.super.onTestFailure(result);
	}

	@Override
	public void onTestStart(ITestResult result) {
		
	}


	
	
}
