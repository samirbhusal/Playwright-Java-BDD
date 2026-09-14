package core;

import java.util.Random;

public enum BrowserChoice {
    CHROME,
    FIREFOX,
    WEBKIT;

    public static BrowserChoice random() {
        return BrowserChoice.values()[new Random().nextInt(BrowserChoice.values().length)];
    }
}
