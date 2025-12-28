package AniketTestAcademy.resources;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

public class ExtentReporterNG {
	private static ExtentReports extent;
	private static ExtentSparkReporter reporter;

	public static ExtentReports getReportObject( ) {

		// ExtentReports , ExtentSparkReporter ---> need to create object and then access these classes/methods
		if(extent==null) {
			String path = System.getProperty("user.dir") + "/reports/index.html";
			reporter = new ExtentSparkReporter(path);
			reporter.config().setReportName("Web Automation Results");
			reporter.config().setDocumentTitle("ECommerce Test Results");
			reporter.config().setTheme(Theme.DARK);

			extent = new ExtentReports();
			extent.attachReporter(reporter);  // attach reporter to reports class
			extent.setSystemInfo("Tester", "Aniket Gapat");
			extent.setSystemInfo("Operating System", System.getProperty("os.name"));
			extent.setSystemInfo("JAVA Version", System.getProperty("java.version"));
			extent.setSystemInfo("Tester/User Name", System.getProperty("user.name"));
		}
		return extent;
	}

}
