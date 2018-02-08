package com.qa.base;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.events.EventFiringWebDriver;

import com.qa.utilities.TestUtil;
import com.qa.utilities.WebEventListener;

public class TestBase {
	
	public static WebDriver driver;
	public static Properties prop;
	public  static EventFiringWebDriver e_driver;
	public static WebEventListener eventListener; 
	
	
	public TestBase(){
		
		try {
			prop = new Properties();
			FileInputStream io = new FileInputStream("../PageObjectModel_TestNG/src/main/java/com/qa/config/config.properties");
			prop.load(io);
			}catch (FileNotFoundException e) {
				e.printStackTrace();
			}catch (IOException e) {
				e.printStackTrace();
			}
	}
	
	public static void intialization(){
		String browserName = prop.getProperty("Browser");
		if(browserName.equals("Chrome")){
			System.setProperty("webdriver.chrome.driver", "../PageObjectModel_TestNG/Drivers/chromedriver.exe");
			driver = new ChromeDriver();
		}else if (browserName.equals("Firefox")){
			System.setProperty("webdriver.gecko.driver", "../PageObjectModel_TestNG/Drivers/geckodriver.exe");
			driver = new FirefoxDriver();
		}
		
		e_driver = new EventFiringWebDriver(driver);  
		eventListener = new WebEventListener(); 	// Now create object of EventListerHandler to register it with EventFiringWebDriver
		e_driver.register(eventListener);
		driver = e_driver;
		
		
		driver.manage().window().maximize();
		driver.manage().deleteAllCookies();
		driver.manage().timeouts().pageLoadTimeout(TestUtil.PAGE_LONG_TIMEOUT, TimeUnit.SECONDS);
		driver.manage().timeouts().implicitlyWait(TestUtil.IMPLICIT_WAIT, TimeUnit.SECONDS);
		
		driver.get(prop.getProperty("URL"));
	}

}
