package de.kaibra.swaggercodegenintegration.utils;

import com.fasterxml.jackson.databind.JsonNode;
import io.swagger.v3.core.util.Json;
import io.swagger.v3.core.util.Yaml;

import java.io.IOException;
import java.io.InputStream;

final public class TestingUtils {

    private TestingUtils() {
    }

    public static JsonNode loadYamlOrJson(final String file) throws IOException {
        try (InputStream in = TestingUtils.class.getClassLoader().getResourceAsStream(file)) {
            if (file.endsWith("yml")) {
                return Yaml.mapper().readTree(in);
            } else {
                return Json.mapper().readTree(in);
            }
        }
    }
}
