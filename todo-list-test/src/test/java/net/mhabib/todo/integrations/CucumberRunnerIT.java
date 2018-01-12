package net.mhabib.todo.integrations;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(features = {
        "src/test/resources/features"
}, tags = {
        "~@ignored"
}, format = {
        "pretty",
        "html:target/reports/cucumber/html",
        "json:target/reports/cucumber/cucumber.json",
        "usage:target/reports/cucumber/cucumber.jsonx",
        "junit:target/reports/cucumber/cucumber.xml"
})
public class CucumberRunnerIT {
}
