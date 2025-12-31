package autoheal.listener.in;

import java.io.IOException;
import java.util.List;

import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;

import autoheal.Logic.in.HealingContext;
import autoheal.base.in.Base;

public class Listeners extends Base implements ITestListener {

	ExtentTest test;
	ExtentReports extent = ExtentReportTestNG.getReportConfig();
	ThreadLocal<ExtentTest> extentList = new ThreadLocal<ExtentTest>();

	@Override
	public void onTestStart(ITestResult result) {
		test = extent.createTest(result.getMethod().getMethodName());
		extentList.set(test);
	}

	@Override
	public void onTestSuccess(ITestResult result) {
		List<String> healingMsg = HealingContext.getMsg();
		for (String msg : healingMsg) {
			extentList.get().info(msg);
		}
		extentList.get().log(Status.PASS, "Test Passed");
		HealingContext.clearMsg();
	}

	@Override
	public void onTestFailure(ITestResult result) {
		extentList.get().fail(result.getThrowable());
		String sSPath = null;
		WebDriver driver = Base.driver.get();
		try {
			sSPath = getScreenshot(result.getMethod().getMethodName(), driver);
		} catch (IOException e) {
			e.printStackTrace();
		}
		extentList.get().addScreenCaptureFromPath(sSPath, result.getMethod().getMethodName());
		List<String> healingMsg = HealingContext.getMsg();
		for (String msg : healingMsg) {
			extentList.get().info(msg);
		}
		extentList.get().log(Status.FAIL, "Test Failed");
		HealingContext.clearMsg();
	}

	@Override
	public void onTestSkipped(ITestResult result) {
		extentList.get().log(Status.SKIP, "Test Skipped");
	}

	@Override
	public void onFinish(ITestContext context) {
		extent.flush();
		extentList.remove();
	}
}
