package fr.mifa.client.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.github.cdimascio.dotenv.Dotenv;

public enum ClientProperties {
    INSTANCE;

    private static final Logger logger = LoggerFactory.getLogger(ClientProperties.class);

    private Dotenv dotenv;

    ClientProperties() {
        dotenv = Dotenv.configure().ignoreIfMissing().load();
    }

    public String get(String key, String defaultValue) {
        return dotenv.get(key, defaultValue);
    }
}