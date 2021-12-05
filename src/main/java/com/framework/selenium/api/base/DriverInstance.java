package com.framework.selenium.api.base;

import java.io.IOException;
import java.net.URL;

import org.openqa.selenium.Platform;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.github.bonigarcia.wdm.WebDriverManager;

public class DriverInstance  {

	private static final ThreadLocal<RemoteWebDriver> remoteWebdriver = new ThreadLocal<RemoteWebDriver>();
	private static final ThreadLocal<WebDriverWait> wait = new  ThreadLocal<WebDriverWait>();

	public void setWait() throws Exception {
		wait.set(new WebDriverWait(getDriver(), 10));
	}

	public WebDriverWait getWait() {
		return wait.get();
	}

	public void setDriver(String browser, boolean headless) {		
		switch (browser) {
		case "chrome":
			WebDriverManager.chromedriver().setup();
			ChromeOptions options = new ChromeOptions();
			options.addArguments("--start-maximized"); 
			options.addArguments("--disable-notifications"); 
			options.addArguments("--incognito");
			remoteWebdriver.set(new ChromeDriver(options));
			break;
		case "firefox":
			WebDriverManager.firefoxdriver().setup();
			remoteWebdriver.set(new FirefoxDriver());
			break;
		case "ie":
			WebDriverManager.iedriver().setup();
			remoteWebdriver.set(new InternetExplorerDriver());
		default:
			break;
		}
	}
	public RemoteWebDriver getDriver() {
		return remoteWebdriver.get();
	}
	
	public void executionMode(String browser, String remote, boolean headless) throws IOException {
		if (remote.equalsIgnoreCase("No"))
		{
			setDriver(browser, headless);
		}
		else if((remote.equalsIgnoreCase("Yes")))
		{	
			DesiredCapabilities dc = new DesiredCapabilities();
			dc.setPlatform(Platform.ANY);
			dc.setBrowserName(browser);
			ChromeOptions options =  new ChromeOptions();
			options.addArguments("--start-maximized"); 
			options.addArguments("--disable-notifications"); 
			options.addArguments("--incognito");
			dc.setCapability(ChromeOptions.CAPABILITY, options);
			remoteWebdriver.set(new RemoteWebDriver(new URL("http://192.168.0.102:4444/wd/hub/"),dc));
			
		}
		}
}