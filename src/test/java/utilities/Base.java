package utilities;

import org.openqa.selenium.support.PageFactory;
import pages.login;

public class Base {

    protected static login loginpage;

    public static Base instantiatePage(){
        loginpage = PageFactory.initElements(Driver.getInstance(), login.class);// to get element working
        return new Base();
    }
}
