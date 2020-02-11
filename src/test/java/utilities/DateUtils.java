package utilities;


import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
public class DateUtils {


        public static String getLogTime() {
            return getTimeStampByFormat("yyyy-MM-dd HH:mm:ss");
        }
        public static String getTimeStampByFormat(String format) {
            DateFormat dateFormat = new SimpleDateFormat(format);
            Calendar cal = Calendar.getInstance();
            return dateFormat.format(cal.getTime());
        }
        public static byte[] getScreenShot() {
            byte[] screenshot = null;
            WebDriver driver = Driver.getInstance();
            try {
                screenshot = (byte[])((TakesScreenshot)driver).getScreenshotAs(OutputType.BYTES);
            } catch (Exception var3) {
                CucumberLogUtils.logError("Couldn't take screenshot");
            }
            return screenshot;
        }
    }
