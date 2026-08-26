package core;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class ConfigLoader {
    private static final Gson gson = new Gson();
    private static final String RUN_CONFIG_PATH = "src/test/resources/configs/Run.Config";
    private static final String ENV_CONFIG_PATH = "src/test/resources/configs/Environment.config";

    private static JsonObject runConfig;
    private static JsonObject envConfig;

    static {
        loadConfigs();
    }

    /**
     * Load configuration files on class initialization
     */
    private static void loadConfigs() {
        try {
            runConfig = loadJsonConfig(RUN_CONFIG_PATH);
            envConfig = loadJsonConfig(ENV_CONFIG_PATH);
        } catch (IOException e) {
            throw new RuntimeException("Failed to load config files", e);
        }
    }

    /**
     * Load and parse a JSON config file
     */
    private static JsonObject loadJsonConfig(String filePath) throws IOException {
        Path path = Paths.get(filePath);
        String content = Files.readString(path);
        return gson.fromJson(content, JsonObject.class);
    }

    /**
     * Get the platform from Run.Config (e.g., "web")
     */
    public static String getPlatform() {
        return runConfig.get("platform").getAsString();
    }

    /**
     * Get the environment from Run.Config (e.g., "qa", "stg")
     */
    public static String getEnv() {
        return runConfig.getAsJsonObject("platformValues").get("env").getAsString();
    }

    /**
     * Get the base URL for the current environment from Environment.config
     */
    public static String getBaseUrl() {
        String env = getEnv();
        return envConfig.getAsJsonObject(env).get("baseUrl").getAsString();
    }

    /**
     * Get base URL for a specific environment
     */
    public static String getBaseUrl(String environment) {
        if (!envConfig.has(environment)) {
            throw new RuntimeException("Environment '" + environment + "' not found in config");
        }
        return envConfig.getAsJsonObject(environment).get("baseUrl").getAsString();
    }

    /**
     * Check if headless mode is enabled
     * Priority: System property (-Dheadless=true/false) > CI environment > default false
     */
    public static boolean headless() {
        String explicit = System.getProperty("headless");
        if (explicit != null) {
            return Boolean.parseBoolean(explicit);   // -Dheadless=true/false wins
        }
        return false;          // else: headless in CI, headed locally
    }

    /**
     * Reload configs from disk (useful for testing)
     */
    public static void reload() {
        try {
            loadConfigs();
        } catch (Exception e) {
            throw new RuntimeException("Failed to reload config files", e);
        }
    }
}
