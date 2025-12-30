package autoheal.listener.in;

import java.io.IOException;

import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;

import autoheal.base.in.Base;



public class Listeners extends Base implements ITestListener  {


	ExtentTest test;
	ExtentReports extent=ExtentReportTestNG.getReportConfig();
	ThreadLocal<ExtentTest> extentList = new ThreadLocal<ExtentTest>();
	@Override
	public void onTestStart(ITestResult result) {
		test=extent.createTest(result.getMethod().getMethodName());
		extentList.set(test);
	}
	
	@Override
	public void onTestSuccess(ITestResult result) {
		extentList.get().log(Status.PASS, "Test Passed");
	}
	
	@Override
	public void onTestFailure(ITestResult result) {
		extentList.get().fail(result.getThrowable());
		String sSPath=null;
		try {
			driver = (WebDriver) result.getTestClass().getRealClass().getField("driver").get(result.getInstance());
		} catch (Exception e) {
			e.printStackTrace();
		} 
		try {
			sSPath=getScreenshot(result.getMethod().getMethodName(),driver);
		} catch (IOException e) {
			e.printStackTrace();
		}
		extentList.get().addScreenCaptureFromPath(sSPath, result.getMethod().getMethodName());
		extentList.get().log(Status.FAIL, "Test Failed");
	}
	
	@Override
	public void onTestSkipped(ITestResult result) {
		extentList.get().log(Status.SKIP, "Test Skipped");
	}
	
	@Override
	public void onFinish(ITestContext context) {
		extent.flush();
	}
}
