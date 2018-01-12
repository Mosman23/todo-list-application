package net.mhabib.todo.integrations.steps;

import cucumber.api.java.en.And;
import cucumber.api.java.en.When;
import java.io.IOException;
import java.util.Map;
import org.apache.http.HttpEntity;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class GetVersionSteps extends BaseSteps {

    @When("^the client calls /version$")
    public void the_client_issues_GET_version() throws IOException {
        response = httpClientUtils.get("/version");
    }

    @And("^the client receives server version (.+)$")
    public void the_client_receives_server_version_body(String version) throws IOException {
        final HttpEntity entity = response.getEntity();
        final Map<String, String> map = objectMapper.readValue(entity.getContent(), Map.class);
        assertThat(map.get("version"), is(version));
    }
}
