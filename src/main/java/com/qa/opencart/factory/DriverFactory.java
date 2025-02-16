package com.qa.opencart.factory;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.safari.SafariDriver;

import com.qa.opencart.constants.AppConstants;
import com.qa.opencart.errors.AppError;
import com.qa.opencart.exceptions.BrowserException;
import com.qa.opencart.exceptions.FrameworkException;

public class DriverFactory {
	WebDriver driver;
	Properties prop;
	
	public static ThreadLocal<WebDriver> tlDriver=new ThreadLocal<WebDriver>();
	/**
	 * This is used to initialize the driver on the basis of browserName.
	 * 
	 * @param browserName
	 */
	public WebDriver initDriver(Properties prop) {

		String browserName = prop.getProperty("browser");
		// cross browser logic

		System.out.println("browser name is :" + browserName);
		switch (browserName.toLowerCase().trim()) {
		case "chrome":
			//driver = new ChromeDriver();
			tlDriver.set(new ChromeDriver());
			break;
		case "firefox":
			//driver = new FirefoxDriver();
			tlDriver.set(new FirefoxDriver());
			break;
		case "edge":
			//driver = new EdgeDriver();
			tlDriver.set(new EdgeDriver());
			break;
		case "safari":
			//driver = new SafariDriver();
			tlDriver.set(new SafariDriver());
			break;
		default:
			System.out.println("plx pass the right browser name..." + browserName);
			throw new BrowserException(AppError.BROWSER_NOT_FOUND);

		}
		getDriver().manage().deleteAllCookies();
		getDriver().manage().window().maximize();
		getDriver().get(prop.getProperty("url"));
		return getDriver();
	}
	
	
	/**
	 * get the local copy of the driver 
	 * 
	 */
public static WebDriver getDriver()
{
	return tlDriver.get();
}
	/**
	 * this is used to init the properties from the .properties file
	 * 
	 * @return this returns properties (prop)
	 * @throws FileNotFoundException
	 */
	public Properties initProp() {
		prop = new Properties();
		FileInputStream ip = null;

		// mvn clean install -Denv="qa"
		String envName = System.getProperty("env");
		System.out.println("running test suite on env--->" + envName);
		if (envName == null) {
			System.out.println("env name is null , hence running it on QA environment....");
			try {
				ip = new FileInputStream(AppConstants.CONFIG_QA_FILE_PATH);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		} else {
			try {
				switch (envName.trim().toLowerCase()) {
				case "qa":
					ip = new FileInputStream(AppConstants.CONFIG_QA_FILE_PATH);
					break;
				case "stage":
					ip = new FileInputStream(AppConstants.CONFIG_STAGE_FILE_PATH);
					break;
				case "dev":
					ip = new FileInputStream(AppConstants.CONFIG_DEV_FILE_PATH);
					break;
				case "uat":
					ip = new FileInputStream(AppConstants.CONFIG_UAT_FILE_PATH);
					break;
				case "prod":
					ip = new FileInputStream(AppConstants.CONFIG_FILE_PATH);
					break;
				default:
					System.out.println("please pass the right env name.." + envName);
					throw new FrameworkException("===WRONGENVPASSED===");
				}
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		}

		try {
			prop.load(ip);
		} catch (IOException e) {
			e.printStackTrace();
		}

		return prop;

	}
}
