import courgette.api.CourgetteOptions;
import courgette.api.CourgetteRunLevel;
import courgette.api.CucumberOptions;
import courgette.api.testng.TestNGCourgette;

@CourgetteOptions(
        threads = 1,
        runLevel = CourgetteRunLevel.SCENARIO,
        cucumberOptions = @CucumberOptions(
                features = "src/test/resources/features",
                glue = {"step_def"},
                plugin = {
                        "json:output/cucumber.json",
                        "junit:output/TEST-report.xml",
                        "io.qameta.allure.cucumber7jvm.AllureCucumber7Jvm"},
                monochrome = true,
                tags = {"@regression"}
        )
)

public class TestRunner extends TestNGCourgette {
}
