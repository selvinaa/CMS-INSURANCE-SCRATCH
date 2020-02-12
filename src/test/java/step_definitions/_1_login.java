package step_definitions;

import cucumber.api.java.en.Given;
import utilities.Base;
import utilities.Driver;

public class _1_login extends Base {


    @Given("I log in")
    public void i_log_in() {
    Driver.getInstance().get("http://google.com");
    }

    @Given("User logs in the account")
    public void user_logs_in_the_account() {
        Driver.getInstance().get("http://yahoo.com");

    }
}
