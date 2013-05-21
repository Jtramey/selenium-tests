package com.wikia.webdriver.Common.Templates;

import com.wikia.webdriver.Common.Core.GeoEdge.GeoEdgeProxyServer;
import com.wikia.webdriver.Common.Core.CommonUtils;
import com.wikia.webdriver.Common.Core.Global;
import com.wikia.webdriver.Common.Core.URLBuilder.UrlBuilder;
import com.wikia.webdriver.Common.DriverProvider.DriverProvider;
import com.wikia.webdriver.Common.DriverProvider.NewDriverProvider;
import com.wikia.webdriver.Common.Logging.PageObjectLogging;
import com.wikia.webdriver.Common.Properties.Properties;
import java.io.File;
import java.lang.reflect.Method;
import org.browsermob.proxy.ProxyServer;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;


public class TestTemplate {

	public WebDriver driver;
	public ProxyServer server;
	protected String improvedPageUrl;
	protected UrlBuilder urlBuilder;
	protected String page;

	@BeforeSuite(alwaysRun = true)
	public void beforeSuite() {
		CommonUtils.deleteDirectory("." + File.separator + "logs");
		CommonUtils.createDirectory("." + File.separator + "logs");
		Properties.setProperties();
		PageObjectLogging.startLoggingSuite();
	}

	@AfterSuite(alwaysRun = true)
	public void afterSuite() {
		PageObjectLogging.stopLoggingSuite();
	}

	@BeforeMethod(alwaysRun = true)
	public void start(Method method, Object[] data) {
		startBrowser();
		PageObjectLogging.startLoggingMethod(
			getClass().getSimpleName().toString(), method.getName()
		);
	}

	@AfterMethod(alwaysRun = true)
	public void stop() {
		stopBrowser();
		PageObjectLogging.stopLoggingMethod();
	}

	protected void startBrowser() {
		DriverProvider.getInstance();
		driver = DriverProvider.getWebDriver();
		server = DriverProvider.getServer();
	}

	protected void stopBrowser() {
		driver = DriverProvider.getWebDriver();
		if (driver != null) {
			driver.quit();
			driver = null;
		}
	}

	protected DesiredCapabilities setServerCaps(GeoEdgeProxyServer server) {
		DesiredCapabilities capabilities = new DesiredCapabilities();
		try {
			capabilities.setCapability(
				CapabilityType.PROXY, server.seleniumProxy()
			);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return capabilities;
	}
}
