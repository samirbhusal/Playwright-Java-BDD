import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(
        features = "src/test/resources/features",
        glue = {"step_def"},
        plugin = {
                "json:output/cucumber.json",
                "junit:output/TEST-report.xml",
                "io.qameta.allure.cucumber7jvm.AllureCucumber7Jvm"},
        monochrome = true,
        tags = "@TES-001"
)

public class TestRunner extends AbstractTestNGCucumberTests {
}
