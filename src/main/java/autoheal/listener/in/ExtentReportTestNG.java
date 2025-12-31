package autoheal.listener.in;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

public class ExtentReportTestNG {

	public static ExtentReports getReportConfig() {
	// ExtentSparkReporter does the configuration for the Report , like setting the doc title,report Name and all
	String path=System.getProperty("user.dir")+"//Reports//Index.html";
	ExtentSparkReporter reporter=new ExtentSparkReporter(path);
	reporter.config().setDocumentTitle("Test Results");
	reporter.config().setReportName("UI Automation Report");
	reporter.config().setTheme(Theme.DARK);
	reporter.config().setCss(".badge-primary{background-color:#1abc9c;}");
	
	
	// extentReport attach the Report with the above configurations.
	ExtentReports extentReport=new ExtentReports();
	extentReport.attachReporter(reporter);
	extentReport.setSystemInfo("Tester", "	Sushil");
	extentReport.setSystemInfo("Browser", "Chrome/edge/firefox");
	return extentReport;
	}
}
