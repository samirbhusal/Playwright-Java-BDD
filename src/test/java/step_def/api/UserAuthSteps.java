package step_def.api;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.microsoft.playwright.APIResponse;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import modules.api.AbstractApiStepDefinition;
import org.testng.Assert;

import java.util.HashMap;
import java.util.Map;

import static helper.HelperMethods.removeQuotes;

public class UserAuthSteps extends AbstractApiStepDefinition {


    @Given("^API environment is set up$")
    public void launchAPIEnvironment() {
        apiServices().userAuth().launch();
    }

    @When("^user sends POST request to (\\S+) with (valid|invalid) credentials$")
    public void userSendsPostRequest(String endPoint, String credentialType, Map<String, String> fields) {
        APIResponse response = apiServices().userAuth().sendPostRequest(endPoint, fields);
        save("statusCode", response.status());
        JsonObject responseBody = JsonParser.parseString(response.text()).getAsJsonObject();
        save("responseBody", responseBody);
    }

    @And("response status code should be {int}")
    public void validateStatuscode(int code) {
        Assert.assertEquals(get("statusCode", Integer.class), code);
    }

    @Then("user verifies the response data")
    public void verifyResponseData(Map<String, String> data) {
        JsonObject responseBody = get("responseBody", JsonObject.class);

        if (responseBody.has("error")) {
            verifyErrorResponseData(responseBody, data);
            return;
        }

        JsonObject userData = responseBody.getAsJsonObject("data").getAsJsonObject("user");


        Assert.assertEquals(removeQuotes(userData.get("email").getAsString()), data.get("email"));
        Assert.assertEquals(removeQuotes(userData.get("name").getAsString()), data.get("name"));
        Assert.assertEquals(removeQuotes(userData.get("role").getAsString()), data.get("role"));
    }

    private void verifyErrorResponseData(JsonObject body, Map<String, String> expectedData) {

        JsonObject error = body.getAsJsonObject("error");

        Assert.assertEquals(error.get("code").getAsString(), expectedData.get("code"));
        Assert.assertEquals(error.get("message").getAsString(), expectedData.get("message"));

        Map<String, String> actualDetails = new HashMap<>();
        if (error.has("details") && error.get("details").isJsonArray()) {
            for (JsonElement ele : error.getAsJsonArray("details")) {
                JsonObject detail = ele.getAsJsonObject();
                actualDetails.put(detail.get("field").getAsString(), detail.get("message").getAsString());
            }
        }

        Map<String, String> expectedDetails = new HashMap<>();
        expectedData.forEach((key, value) -> {
            if (key.endsWith("Field") && value != null && !value.isBlank()) {
                expectedDetails.put(key.substring(0, key.length() - "Field".length()), value);
            }
        });
        
        Assert.assertEquals(actualDetails, expectedDetails);

    }
}
