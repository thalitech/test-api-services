package com.test.api;

import java.util.Objects;
import java.util.Properties;

import org.junit.jupiter.api.BeforeAll;

public abstract class BaseApiTest {

    private static Properties properties;

    protected static String baseUrl;

    @BeforeAll
    private static void loadProperties() {
        properties = new Properties();
        try {
            properties.load(
                    Thread.currentThread().getContextClassLoader().getResourceAsStream("api.properties"));
        } catch (Exception e) {
            System.err.println("Falha ao ler arquivo de properties");
            System.exit(1);
        }
        baseUrl = Objects.requireNonNull(properties.getProperty("base-url"));
    }
}
