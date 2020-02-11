package utilities;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.safari.SafariDriver;

import java.net.MalformedURLException;
import java.net.URL;

public class Driver {

    private static volatile WebDriver driver;
    // To invoke sauce tests locally
    private static final String USERNAME = "";// ENTER USER NAME
    private static final String ACCESS_KEY = "";
    private static String URL = "https://" + USERNAME + ":" + ACCESS_KEY + "";
    private static String HOST = "";
    //Use below line to invoke from jenkins
//    public static final String URL = "http://" + System.getenv("SAUCE_USERNAME") + ":" + System.getenv("SAUCE_ACCESS_KEY") + "@ondemand.saucelabs.com:80/wd/hub";
//    public static final String HOST = System.getProperty("host");
    private Driver() {
    }
    public static WebDriver getInstance() {
        if (driver == null) {
            synchronized (Driver.class) {
                if (driver == null) {
                    switch (System.getProperty("browser")) {
                        case "firefox":
                            WebDriverManager.firefoxdriver().setup();
                            driver = new FirefoxDriver();
                            break;
                        case "safari":
                            driver = new SafariDriver();
                            break;
                        case "chrome":
                            WebDriverManager.chromedriver().setup();
//                            driver = new ChromeDriver();
                            ChromeOptions optionss = new ChromeOptions();
                            optionss.setPageLoadStrategy(PageLoadStrategy.NONE);
//                            // Instantiate the chrome driver
                            optionss.addArguments("window-size=1200x600");
                            optionss.addArguments("--disable-infobars");
                            driver = new ChromeDriver(optionss);
                            break;
                        case "headless_chrome"://IS RUNNING ON THE BACK ONLY LIKE CONFIGURATION
                            ChromeOptions options = new ChromeOptions();
                            options.addArguments("headless");
                            options.addArguments("window-size=1200x600");
                            options.addArguments("--disable-infobars");
                            WebDriverManager.chromedriver().setup();
                            driver = new ChromeDriver(options);
                            break;
                        case "ie":
                            WebDriverManager.iedriver().setup();
                            driver = new InternetExplorerDriver();
                            break;
                        case "remote":// TO USE FOR JENKINS TO WORK RUNNING BY REMOTE IN SCHEDULE
                            DesiredCapabilities caps = DesiredCapabilities.chrome();
                            if (System.getProperty("env") != null) {
                                // This block of code is used when executing via Jenkins
                                caps.setBrowserName(System.getenv("SELENIUM_BROWSER"));
                                caps.setVersion(System.getenv("SELENIUM_VERSION"));
                                caps.setCapability("platform", System.getenv("SELENIUM_PLATFORM"));
                                caps.setCapability("build", System.getenv("JOB_NAME") + "__" + System.getenv("BUILD_NUMBER"));
                                URL = "http://" + System.getenv("SAUCE_USERNAME") + ":" + System.getenv("SAUCE_ACCESS_KEY");
                                HOST = System.getProperty("host");
                            } else {
                                // This block of code is used when executing locally via Sauce Labs
                                caps.setCapability("platform", "Mac OS X");
                                caps.setCapability("version", "latest");
                                caps.setCapability("name", "test");
                            }
                            try {
                                driver = new RemoteWebDriver(new URL(URL), caps);
                            } catch (MalformedURLException e) {
                                e.printStackTrace();
                            }
                            break;
                        default:
                            WebDriverManager.chromedriver().setup();
                            driver = new ChromeDriver();
                    }
                }
            }
        }
        return driver;
    }
    public static void closeDriver() {
        if (driver != null) {
            driver.quit();
            driver = null;
        }
    }
}

