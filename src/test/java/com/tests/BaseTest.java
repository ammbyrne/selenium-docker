package com.tests;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.ITestContext;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxOptions;
import java.net.MalformedURLException;
import java.net.URL;

public class BaseTest {

    protected WebDriver driver;

    @BeforeTest
    public void setupDriver(ITestContext ctx) throws MalformedURLException {
        // BROWSER => chrome / firefox
        // HUB_HOST => localhost / 10.0.1.3 / hostname

        String host = "localhost";
        DesiredCapabilities dc;
        ChromeOptions chrome = null;
        FirefoxOptions firefox = null;


        if(System.getProperty("BROWSER") != null &&
                System.getProperty("BROWSER").equalsIgnoreCase("firefox")){
            chrome = new ChromeOptions();
        }else{
            firefox = new FirefoxOptions();
        }

        if(System.getProperty("HUB_HOST") != null){
            host = System.getProperty("HUB_HOST");
        }

        String testName = ctx.getCurrentXmlTest().getName();

        String completeUrl = "http://" + host + ":4444/wd/hub";
   
        if (chrome != null) {
            chrome.setCapability("name", testName);
            this.driver = new RemoteWebDriver(new URL(completeUrl), chrome);        
        }
        else {
            firefox.setCapability("name", testName);
            this.driver = new RemoteWebDriver(new URL(completeUrl), firefox);
        }
    
    }

    @AfterTest
    public void quitDriver(){
        this.driver.quit();
    }
}
