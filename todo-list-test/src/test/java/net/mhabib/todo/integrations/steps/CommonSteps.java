package net.mhabib.todo.integrations.steps;

import cucumber.api.java.en.Then;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class CommonSteps extends BaseSteps {

    @Then("^the client receives status code of (\\d+)$")
    public void the_client_receives_status_code_of(int statusCode) {
        assertThat(response.getStatusLine().getStatusCode(), is(statusCode));
    }
}
