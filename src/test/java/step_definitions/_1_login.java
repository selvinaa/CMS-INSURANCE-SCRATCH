package step_definitions;

import cucumber.api.java.en.Given;
import utilities.Base;
import utilities.Driver;

public class _1_login extends Base {


    @Given("user logs in the account")
    public void user_logis_inthe_account(){
        Driver.getInstance().get("https://orangehrm-demo-6x.orangehrmlive.com/auth/login");
        loginpage.userName.sendKeys("admin");
        loginpage.password.sendKeys("admin");
        loginpage.login.click();
    }

}
