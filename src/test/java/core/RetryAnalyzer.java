package core;

import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

public class RetryAnalyzer implements IRetryAnalyzer {
    private int count = 0;
    static boolean isCI = Boolean.parseBoolean(System.getenv().getOrDefault("CI", "false"));
    private static final int MAX_RETRY = isCI ? 1 : 0;

    @Override
    public boolean retry(ITestResult result) {
        return count++ < MAX_RETRY;
    }
}
