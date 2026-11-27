package br.com.caelum.stella.validation.bdd;

import org.junit.runner.RunWith;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = "src/test/resources/features",
        glue = "br.com.caelum.stella.validation.bdd",
        plugin = {"pretty", "html:target/cucumber-report.html"})
public class RunCucumberTest {
}
