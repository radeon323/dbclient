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
    private static final Map<String, Properties> cachedProperties = new ConcurrentHashMap<>();

    public static Properties getProperties(String path)  {
        if (!cachedProperties.containsKey(path)) {
            cachedProperties.put(path, readProperties(path));
        }
        return new Properties(cachedProperties.get(path));
    }

    private static Properties readProperties(String path)  {
        Properties properties = new Properties();
        try (BufferedInputStream resource = new BufferedInputStream
                (Objects.requireNonNull(PropertiesReader.class.getClassLoader().getResourceAsStream(path)))) {
            properties.load(resource);
            return properties;
        } catch (IOException e) {
            log.error("Cannot read properties from file: {} ", path, e);
            throw new RuntimeException("Cannot read properties from file: " + path, e);
        }
    }


}
