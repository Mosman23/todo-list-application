package net.mhabib.todo.integrations.common;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertyReader {

    Properties properties = new Properties();
    InputStream inputStream = null;

    public static final String BASE_URL_KEY = "base.url";

    public PropertyReader() {
        loadProperties();
    }

    private void loadProperties() {
        try {
            inputStream = new FileInputStream("src/test/resources/application.properties");
            properties.load(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String readProperty(String key, String defaultValue) {
        return properties.getProperty(key, defaultValue);
    }
}
