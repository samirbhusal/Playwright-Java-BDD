package core;

public class ConfigLoader {
    public static boolean headless() {
        String explicit = System.getProperty("headless");
        if (explicit != null) {
            return Boolean.parseBoolean(explicit);   // -Dheadless=true/false wins
        }
        return System.getenv("CI") != null;          // else: headless in CI, headed locally
    }
}
