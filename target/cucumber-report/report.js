$(document).ready(function() {var formatter = new CucumberHTML.DOMFormatter($('.cucumber-report'));formatter.uri("file:src/test/resources/features/logout.feature");
formatter.feature({
  "name": "test",
  "description": "",
  "keyword": "Feature"
});
formatter.scenario({
  "name": "testing",
  "description": "",
  "keyword": "Scenario",
  "tags": [
    {
      "name": "@demo1"
    }
  ]
});
formatter.step({
  "name": "I log in",
  "keyword": "Given "
});
formatter.match({
  "location": "_1_login.i_log_in()"
});
formatter.result({
  "status": "passed"
});
formatter.step({
  "name": "User logs in the account",
  "keyword": "Given "
});
formatter.match({
  "location": "_1_login.user_logs_in_the_account()"
});
formatter.result({
  "status": "passed"
});
});