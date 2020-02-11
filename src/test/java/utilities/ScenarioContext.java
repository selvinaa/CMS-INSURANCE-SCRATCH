package utilities;

import cucumber.api.Scenario;


public class ScenarioContext {


        public static ThreadLocal<Scenario> scenario = new ThreadLocal();
        public ScenarioContext() {
        }
        public static String getScenarioName() {
            String scenarioName = "";
            if (scenario.get() != null) {
                scenarioName = (scenario.get()).getName();
                CucumberLogUtils.logToConsole("Scenario Name: " + scenarioName);
            }
            return scenarioName;
        }
        public static String getScenarioUniqueID() {
            String scenarioID = "";
            if (scenario.get() != null) {
                CucumberLogUtils.logToConsole("Scenario Name: " + (scenario.get()).getName());
                scenarioID = (scenario.get()).getId();
                scenarioID = scenarioID.replaceAll("[a-zA-Z0-9.-]", "_");
                CucumberLogUtils.logToConsole("Scenario ID: " + scenarioID);
            }
            return scenarioID;
        }
    }
