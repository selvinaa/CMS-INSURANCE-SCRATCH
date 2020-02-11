package utilities;

import com.mifmif.common.regex.Generex;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;
import java.util.Random;
import java.util.Set;

public class SeleniumUtilies {


        private static WebDriverWait wait = new WebDriverWait(Driver.getInstance(), 10);

        public static void checkLink(WebElement element, String expectedURL) throws InterruptedException {
            String parentWindow = Driver.getInstance().getWindowHandle();
            wait.until(ExpectedConditions.elementToBeClickable(element));
            Thread.sleep(1000);
            highlightFieldUnhighlight(element);
            element.click();
            Thread.sleep(1500);
            Set<String> windowLinks = Driver.getInstance().getWindowHandles();
            for (String window : windowLinks) {
                if (!window.equals(parentWindow)) {
                    Driver.getInstance().switchTo().window(window);
                    String newUrl = Driver.getInstance().getCurrentUrl();
//                System.out.println("expected url "+expected_URL);
//                System.out.println(newUrl);
                    newUrl = newUrl.substring(0, expectedURL.length());
                    if (expectedURL.equals(newUrl)) {
                        CucumberLogUtils.logInfo("Expected -" + expectedURL + "\nActual -" + newUrl);
                        CucumberLogUtils.logPass("Switched to " + newUrl, true);
                    } else {
                        CucumberLogUtils.logInfo("Expected -" + expectedURL + "\nActual -" + newUrl);
                        CucumberLogUtils.logFail("Didn`t switch " + newUrl, true);
                    }
                    Driver.getInstance().close();
                }
            }
            Driver.getInstance().switchTo().window(parentWindow);
        }

        public static void switchWindow(WebElement element, String expectedURL) throws InterruptedException {
            String parentWindow = Driver.getInstance().getWindowHandle();
            wait.until(ExpectedConditions.elementToBeClickable(element));
            Thread.sleep(1000);
            highlightFieldUnhighlight(element);
            element.click();
            Thread.sleep(1000);
            Set<String> windowLinks = Driver.getInstance().getWindowHandles();
            for (String window : windowLinks) {
                if (!window.equals(parentWindow)) {
                    Driver.getInstance().switchTo().window(window);
                    String newUrl = Driver.getInstance().getCurrentUrl();
                    Assert.assertEquals(newUrl, expectedURL);
                    if (expectedURL.equals(newUrl)) {
                        CucumberLogUtils.logInfo("Expected -" + expectedURL + "\nActual -" + newUrl);
                        CucumberLogUtils.logPass("Switched to " + newUrl, true);
                    } else {
                        CucumberLogUtils.logInfo("Expected -" + expectedURL + "\nActual -" + newUrl);
                        CucumberLogUtils.logFail("Didn`t switch " + newUrl, true);
                    }
                }
            }
        }

        public static void assertEquals(WebElement element, String expectedResult) {
            wait.until(ExpectedConditions.visibilityOf(element));
            highlightFieldUnhighlight(element);
            Assert.assertEquals(element.getText().trim(), expectedResult);
            CucumberLogUtils.logPass("Expected value: " + expectedResult + "\n Actual value: " + element.getText().trim(), true);
        }

        public static void enterValueFromPropertyFile(WebElement element, String boxName) {
            wait.until(ExpectedConditions.visibilityOf(element));
            highlightFieldUnhighlight(element);
            element.sendKeys(ConfigurationReader.getProperty(boxName));
        }

        public static void enterValueForWebElement(WebElement element, String value) {
            wait.until(ExpectedConditions.visibilityOf(element));
            highlightFieldUnhighlight(element);
            element.sendKeys(value);
        }

        public static void deleteValueAndEnterNewValue(WebElement element, String value, String inputField) throws InterruptedException {
            wait.until(ExpectedConditions.visibilityOf(element));
            highlightFieldUnhighlight(element);
            JavascriptExecutor js = (JavascriptExecutor) Driver.getInstance();
            js.executeScript("arguments[0].value = '';", element);
            element.sendKeys(Keys.HOME, Keys.chord(Keys.SHIFT, Keys.END));
            element.sendKeys(value);
            Thread.sleep(1000);
            CucumberLogUtils.logPass("The value " + value + " was entered into " + inputField, true);
        }

        public static void deleteValueAndEnterNumberOfCharactersAsNewValue(WebElement element, int numberOfCharacters, String inputField) throws InterruptedException {
            wait.until(ExpectedConditions.visibilityOf(element));
            highlightFieldUnhighlight(element);
            JavascriptExecutor js = (JavascriptExecutor) Driver.getInstance();
            js.executeScript("arguments[0].value = '';", element);
            element.sendKeys(Keys.HOME, Keys.chord(Keys.SHIFT, Keys.END));
            String SALTCHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890!-_=[]{}?/':;";
            StringBuilder salt = new StringBuilder();
            Random rnd = new Random();
            while (salt.length() <= numberOfCharacters) { // length of the random string.
                int index = (int) (rnd.nextFloat() * SALTCHARS.length());
                salt.append(SALTCHARS.charAt(index));
            }
            String saltStr = salt.toString();
            element.sendKeys(saltStr);
            Thread.sleep(1000);
            CucumberLogUtils.logPass("The value with " + saltStr.length() + " number of characters was entered into " + inputField, true);
        }

        public static void clickButton(WebElement element) throws InterruptedException {
            wait.until(ExpectedConditions.elementToBeClickable(element));
            Thread.sleep(1000);
            highlightFieldUnhighlight(element);
            element.click();
            try {
                CucumberLogUtils.logPass("User clicked the " + element.getText(), true);
            } catch (Exception e) {
                CucumberLogUtils.logInfo("The element doesn't have a text");
            }
        }

        public static void clickButton(WebElement element, String buttonName) throws InterruptedException {
            wait.until(ExpectedConditions.elementToBeClickable(element));
//        System.out.println(element.getText());
            if (buttonName.equalsIgnoreCase(element.getText().trim())) {
                CucumberLogUtils.logPass("Pressed " + element.getText(), true);
            } else {
                CucumberLogUtils.logFail("Couldn't find " + buttonName, true);
            }
            Thread.sleep(1000);
            highlightFieldUnhighlight(element);
            element.click();
            Thread.sleep(1500);
        }

        public static void clickButtonWithJavascript(WebElement element, String buttonName) throws InterruptedException {
            wait.until(ExpectedConditions.visibilityOf(element));
            if (buttonName.equalsIgnoreCase(element.getText().trim())) {
                CucumberLogUtils.logPass("Selected " + element.getText(), true);
            } else {
                CucumberLogUtils.logFail("Couldn't find " + buttonName, true);
            }
            Thread.sleep(1000);
            highlightFieldUnhighlight(element);
            ((JavascriptExecutor) Driver.getInstance()).executeScript("arguments[0].click();", element);
        }

        public static String createRandomEmail() {
            String SALTCHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
            StringBuilder salt = new StringBuilder();
            Random rnd = new Random();
            while (salt.length() < 10) { // length of the random string.
                int index = (int) (rnd.nextFloat() * SALTCHARS.length());
                salt.append(SALTCHARS.charAt(index));
            }
            String saltStr = salt.toString();
            return saltStr + "@gmail.com";
        }

        //    public static void main(String[] args) {
//        System.out.println(getRandomEmailRegEx());
//    }
        public static String getInvalidEmailRegex() {
            Generex generex1 = new Generex("[a-z0-9!#$%&'*+=^_`{|}~-]+(\\.[a-z0-9!#$%&'*+=^_`{|}~-]+)*|\"([\\x01-\\x08\\x0b\\x0c\\x0e\\x1f\\x21\\x23-\\x5b\\x5d\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e\\x7f])*\"");
            String prefix = generex1.random();
            Generex generex2 = new Generex("([a-z0-9]([a-z0-9-]*[a-z0-9])\\.)+[a-z0-9]([a-z0-9-]*[a-z0-9])|\\[(25[0-5]|2[0-4][0-9]|[01][0-9][0-9])\\.(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]|[a-z0-9-]*[a-z0-9]:([\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\]");
            String postfix = generex2.random();
            Generex generex3 = new Generex("[a-zA-Z0-9-]{2,}");
            String domain = generex3.random();
            String randomEmail = prefix + "@" + postfix + "." + domain;
            System.out.println("Email: " + randomEmail);
            return randomEmail;
        }

        public static String getRandomEmailRegEx() {
            // Random String generator based on RegEx provided by developers
            //Javascript REGEX = /^(([^<>()\[\]\\.,;:\s@"]+(\.[^<>()\[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/
            // java regex (?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|"(?:[\x01-\x08\x0b\x0c\x0e-\x1f\x21\x23-\x5b\x5d-\x7f]|\\[\x01-\x09\x0b\x0c\x0e-\x7f])*")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\x01-\x08\x0b\x0c\x0e-\x1f\x21-\x5a\x53-\x7f]|\\[\x01-\x09\x0b\x0c\x0e-\x7f])+)\])
            Generex generex1 = new Generex("\\w+(\\.|\\+|\\-|\\_)?\\w+");
            String prefix = generex1.random();
            Generex generex2 = new Generex("[a-zA-Z0-9-]");
            String postfix = generex2.random();
            Generex generex3 = new Generex("[a-zA-Z]{2,}");
            String domain = generex3.random();
            String randomEmail = prefix + "@" + postfix + "." + domain;
            System.out.println("Email: " + randomEmail);
            return randomEmail;
        }

        public static void checkLabelIsPresent(WebElement element, String expectedResult) {
            wait.until(ExpectedConditions.visibilityOf(element));
            highlightFieldUnhighlight(element);
            System.out.println(element.getText());
            if (element.getText().equals(expectedResult)) {
                CucumberLogUtils.logPass(element.getText() + " is present on the page and matches the expected result: " + expectedResult, true);
            } else
                CucumberLogUtils.logFail(element.getText() + " is not present on the page and doesn't match the expected result: " + expectedResult, true);
        }

        public static void checkElementIsPresent(WebElement element, String nameOfTheElement) {
            wait.until(ExpectedConditions.visibilityOf(element));
            highlightFieldUnhighlight(element);
            if (element.isDisplayed()) {
                CucumberLogUtils.logPass(nameOfTheElement + " is present on the page", true);
            } else
                CucumberLogUtils.logFail(nameOfTheElement + " is not present on the page", true);
        }

        public static void checkElementIsPresentAmongOtherElements(List<WebElement> elements, String expectedResult) {
            String actual_result = "";
            for (WebElement element : elements) {
                wait.until(ExpectedConditions.visibilityOf(element));
                highlightFieldUnhighlight(element);
                System.out.println(element.getText());
                if (element.getText().equals(expectedResult)) {
                    actual_result = element.getText();
                    break;
                }
            }
            if (actual_result.equals(expectedResult)) {
                CucumberLogUtils.logPass(actual_result + " is present on the page and matches the expected result: " + expectedResult, true);
            } else
                CucumberLogUtils.logFail(expectedResult + " is not present on the page ", true);
        }

        public static void selectFromDropDown(WebElement element, String visibleText) {
            Select select = new Select(element);
            select.selectByVisibleText(visibleText);
        }

        public static void getPageTitle(String expectedTitle) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            String currentPageTitle = Driver.getInstance().getTitle();
            if (currentPageTitle.equals(expectedTitle)) {
                CucumberLogUtils.logPass("The actual title " + currentPageTitle + " is matching the expected title " + expectedTitle, true);
            } else {
                CucumberLogUtils.logFail("The actual title " + currentPageTitle + " is not matching the expected title " + expectedTitle, true);
            }
        }

        public static void assertAttribute(WebElement element, String elementName, String expectedResult) {
            wait.until(ExpectedConditions.visibilityOf(element));
            if (element.getAttribute(elementName).equals(expectedResult)) {
                CucumberLogUtils.logPass("Actual result " + element.getAttribute(elementName) + "\n matches expected result " + expectedResult, true);
            } else {
                CucumberLogUtils.logFail("Actual result " + element.getAttribute(elementName) + "\n Expected result " + expectedResult, true);
            }
        }

        public static void assertElementNotFoundByCSS(String element, String cssOfTheElement) throws InterruptedException {
            Thread.sleep(2000);
            List<WebElement> elements = Driver.getInstance().findElements(By.cssSelector(cssOfTheElement));
            if (elements.size() > 0) {
                CucumberLogUtils.logFail("Page contains the element " + elements.toString(), true);
            } else
                CucumberLogUtils.logPass("Page doesn't contain an element " + element, true);
        }

        public static void assertElementNotFoundByXpath(String element, String xpathOfTheElement) throws InterruptedException {
            Thread.sleep(2000);
            List<WebElement> elements = Driver.getInstance().findElements(By.xpath(xpathOfTheElement));
            if (elements.size() > 0) {
                CucumberLogUtils.logFail("Page contains the element " + elements.toString(), true);
            } else
                CucumberLogUtils.logPass("Page doesn't contain an element " + element, true);
        }

        public static void checkButtonIsClickable(WebElement element, String buttonName) {
            wait.until(ExpectedConditions.elementToBeClickable(element));
            highlightFieldUnhighlight(element);
            if (element.getText().trim().equals(buttonName)) {
                CucumberLogUtils.logPass(element.getText().trim() + " was pressed", true);
            } else {
                CucumberLogUtils.logFail(buttonName + " button is not enabled", true);
            }
        }

        public static void checkButtonIsNotClickable(WebElement element, String buttonName) {
            try {
                wait.until(ExpectedConditions.elementToBeClickable(element));
                highlightFieldUnhighlight(element);
                CucumberLogUtils.logFail(element.getText().trim() + " is clickable", true);
            } catch (Exception e) {
                CucumberLogUtils.logPass(buttonName + " button is not clickable", true);
            }
        }

        public static void highlightFieldUnhighlight(WebElement element) {
            // Highlights and unhighlights the area with JS
            JavascriptExecutor js = (JavascriptExecutor) Driver.getInstance();
            js.executeScript("arguments[0].style.border= '3px solid yellow'", element);
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
//            e.printStackTrace();
            }
            js.executeScript("arguments[0].style.border= ''", element);
        }

        public static void deleteElement(WebElement element, String buttonName) throws InterruptedException {
            wait.until(ExpectedConditions.visibilityOf(element));
            clickButton(element, buttonName);
        }

        public static WebElement findElementByXpath(String xpath) {
            WebElement element = Driver.getInstance().findElement(By.xpath(xpath));
            wait.until(ExpectedConditions.visibilityOf(element));
            return element;
        }

        public static String getElementAttribute(WebElement element, String elementName) {
            wait.until(ExpectedConditions.visibilityOf(element));
            CucumberLogUtils.logInfo("Elements attribute: " + element.getAttribute(elementName));
            return element.getAttribute(elementName);
        }
    }
