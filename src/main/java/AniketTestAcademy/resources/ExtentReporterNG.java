package AniketTestAcademy.resources;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

public class ExtentReporterNG {
	
	public static ExtentReports getReportObject( ) {
		
		// ExtentReports , ExtentSparkReporter ---> need to create object and then access these classes/methods
				String path = System.getProperty("user.dir") + "/reports/index.html";
				ExtentSparkReporter reporter = new ExtentSparkReporter(path);
				reporter.config().setReportName("Web Automation Results");
				reporter.config().setDocumentTitle("Test Results");
				
				ExtentReports extent = new ExtentReports();
				extent.attachReporter(reporter);  // attach reporter to reports class
				extent.setSystemInfo("Tester", "Aniket Gapat");
				return extent;		
	}

}
