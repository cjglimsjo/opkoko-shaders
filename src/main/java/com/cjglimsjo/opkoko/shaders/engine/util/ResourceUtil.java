package com.cjglimsjo.opkoko.shaders.engine.util;

import java.io.InputStream;
import java.util.Scanner;

public class ResourceUtil {

    public static String loadAsString(String name) {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        InputStream inputStream = classLoader.getResourceAsStream(name);
        if (inputStream == null) {
            throw new IllegalArgumentException(String.format("Could not find resource with name \"%s\"", name));
        }

        try (Scanner scanner = new Scanner(inputStream, "UTF-8")) {
            return scanner.useDelimiter("\\A").next();
        }
    }
}
