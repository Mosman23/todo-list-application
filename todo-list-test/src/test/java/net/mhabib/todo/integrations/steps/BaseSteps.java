package net.mhabib.todo.integrations.steps;

import com.fasterxml.jackson.databind.ObjectMapper;
import net.mhabib.todo.integrations.common.HttpClientUtils;
import net.mhabib.todo.integrations.common.PropertyReader;
import org.apache.http.HttpResponse;
import static net.mhabib.todo.integrations.common.PropertyReader.BASE_URL_KEY;

class BaseSteps {

    private static final PropertyReader PROPERTY_READER = new PropertyReader();
    private static final String BASE_URL = "http://localhost:32339";

    private static String baseUrl;

    static HttpResponse response;

    static ObjectMapper objectMapper;

    static HttpClientUtils httpClientUtils;

    BaseSteps() {
        objectMapper = objectMapper == null ? new ObjectMapper() : objectMapper;
        baseUrl = baseUrl == null ? PROPERTY_READER.readProperty(BASE_URL_KEY, BASE_URL) : baseUrl;
        httpClientUtils = httpClientUtils == null ? new HttpClientUtils(baseUrl) : httpClientUtils;
    }

}