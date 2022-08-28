package com.olshevchenko.dbclient.utils;

import lombok.extern.slf4j.Slf4j;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.Objects;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Oleksandr Shevchenko
 */
@Slf4j
public class PropertiesReader {
    private static final String JDBC_URL = "jdbc.url";
    private static final String JDBC_USER = "jdbc.user";
    private static final String JDBC_PASSWORD = "jdbc.password";
    private static final String REPORT_HTML_PATH = "report.html.path";

    private final Map<String, Properties> cachedProperties = new ConcurrentHashMap<>();

    public Properties getProperties(String path)  {
        if (!cachedProperties.containsKey(path)) {
            cachedProperties.put(path, getPropertiesFromEnv(path));
        }
        return new Properties(cachedProperties.get(path));
    }

    private Properties readProperties(String path)  {
        Properties properties = new Properties();
        try (InputStream inputStream = PropertiesReader.class.getClassLoader().getResourceAsStream(path);
             BufferedInputStream resource = new BufferedInputStream(Objects.requireNonNull(inputStream))) {
            properties.load(resource);
            return properties;
        } catch (IOException e) {
            log.error("Cannot read properties from file: {} ", path, e);
            throw new RuntimeException("Cannot read properties from file: " + path, e);
        }
    }

    private Properties getPropertiesFromEnv(String path) {
        Properties propertiesFromFile = readProperties(path);
        Properties properties = new Properties();

        Map<String, String> envVar = System.getenv();
        String url = envVar.get("url");
        String user = envVar.get("user");
        String password = envVar.get("password");

        if (url == null) {
            url = propertiesFromFile.getProperty(JDBC_URL);
            log.info("Property 'url' is missing, so it was loaded from a file: {}", url);
        }
        if (user == null) {
            user = propertiesFromFile.getProperty(JDBC_USER);
            log.info("Property 'user' is missing, so it was loaded from a file: {}", user);
        }
        if (password == null) {
            password = propertiesFromFile.getProperty(JDBC_PASSWORD);
            log.info("Property 'password' is missing, so it loaded from a file: {}", password);
        }

        String pathToReports = propertiesFromFile.getProperty(REPORT_HTML_PATH);

        properties.setProperty(JDBC_URL, url);
        properties.setProperty(JDBC_USER, user);
        properties.setProperty(JDBC_PASSWORD, password);
        properties.setProperty(REPORT_HTML_PATH, pathToReports);

        return properties;
    }


}
