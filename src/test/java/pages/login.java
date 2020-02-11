package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;
import utilities.Driver;

public class login {

    public login (){
        PageFactory.initElements(Driver.getInstance(), this);
    }
    private WebDriverWait wait = new WebDriverWait(Driver.getInstance(),15);

    @FindBy(xpath = "txtUsername']")
    public WebElement userName;

    @FindBy(xpath = "txtPassword'")
    public WebElement password;

    @FindBy(xpath = "txtPassword'")
    public WebElement login;




}
