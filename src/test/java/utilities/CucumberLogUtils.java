package utilities;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.log4j.Logger;
import org.junit.Assert;



public class CucumberLogUtils {
    private static Logger logger = Logger.getLogger(CucumberLogUtils.class);
    public static boolean scenarioResult = true;

    public CucumberLogUtils() {
    }
    public static void logScreenShot(String msg) {
        if (ScenarioContext.scenario.get() != null) {
            (ScenarioContext.scenario.get()).write(DateUtils.getLogTime() + ": Screenshot: " + msg);
            (ScenarioContext.scenario.get()).embed(DateUtils.getScreenShot(), "image/png");
        }
    }
    public static void logPass(String msg, boolean takeScreenShot) {
        if (ScenarioContext.scenario.get() != null) {
            (ScenarioContext.scenario.get()).write(DateUtils.getLogTime() + ": PASS: " + msg);
            logger.info(String.format(":PASS:=%s", msg));
            if (takeScreenShot ) {
                (ScenarioContext.scenario.get()).embed(DateUtils.getScreenShot(), "image/png");
            }
        }
    }
    public static void logFail(String msg, Throwable t, boolean takeScreenshot) {
        String stackTrace = ExceptionUtils.getStackTrace(t);
        logFail(String.format("%s \n%s", msg, stackTrace), takeScreenshot);
    }
    public static void logFail(String msg, boolean takeScreenshot) {
//        try {
        if (ScenarioContext.scenario.get() != null) {
            if (takeScreenshot) {
                (ScenarioContext.scenario.get()).embed(DateUtils.getScreenShot(), "image/png");
            }
            logger.error(String.format(":FAIL:=%s", msg));
            Assert.assertTrue(DateUtils.getLogTime() + ": FAIL: " + msg, false);
        }
//        }catch (Exception e){
//            e.printStackTrace();
//        }
    }
    public static void logInfo(String msg) {
        if (ScenarioContext.scenario.get() != null) {
            logger.info(msg);
            (ScenarioContext.scenario.get()).write(DateUtils.getLogTime() + ": INFO: " + msg);
        }
    }
    public static void logError(String msg, Throwable t) {
        String stackTrace = ExceptionUtils.getStackTrace(t);
        logError(String.format("%s \n%s", msg, stackTrace));
    }
    public static void logError(String msg) {
        if (ScenarioContext.scenario.get() != null) {
            logger.error(msg);
            (ScenarioContext.scenario.get()).write(DateUtils.getLogTime() + ": ERROR: " + msg);
            scenarioResult = false;
        }
    }
    public static void logToConsole(String msg) {
        logger.info(msg);
        System.out.println(Thread.currentThread().getName() + " : " + DateUtils.getLogTime() + ": CONSOLE: " + msg);
    }
    public static void writeHTML(String htmlContent) {
        if (ScenarioContext.scenario.get() != null) {
            (ScenarioContext.scenario.get()).write(htmlContent);
        }
    }
    public static void logLink(String hyperlink, String hyperlinkMsg) {
        if (ScenarioContext.scenario.get() != null) {
            StringBuilder htmlString = (new StringBuilder()).append("<a href='");
            htmlString.append(hyperlink).append("' ");
            htmlString.append("target='_blank'");
            htmlString.append(">");
            htmlString.append(hyperlinkMsg).append("</a>");
            (ScenarioContext.scenario.get()).write(htmlString.toString());
        }
    }
}


