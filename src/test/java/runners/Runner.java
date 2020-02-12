package runners;


import cucumber.api.CucumberOptions;
import cucumber.api.testng.AbstractTestNGCucumberTests;
import org.apache.commons.io.FileUtils;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import utilities.Base;
import utilities.CucumberLogUtils;
import utilities.Driver;

import java.io.File;
import java.util.concurrent.TimeUnit;

@CucumberOptions(
        plugin = {"pretty", "html:target/cucumber-report", "json:target/cucumber.json"},
        features = "src/test/resources/features/",
        glue = "step_definitions",
        dryRun = false,
        tags = "@demo" +
                ""

)

public class Runner extends AbstractTestNGCucumberTests {

    @BeforeMethod
    public void startTest(){
        Base base = Base.instantiatePage();
        Driver.getInstance().manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

    }

//    @AfterMethod
    public void  closeTest(){
        Driver.closeDriver();
    }

    @BeforeSuite
    public static void setUp(){
        try {
            FileUtils.deleteDirectory(new File("./target/cucumber-report"));
        }catch (Exception e){
            CucumberLogUtils.logInfo("Cucumber-report directory wasn't cleanned");
        }
    }
}
