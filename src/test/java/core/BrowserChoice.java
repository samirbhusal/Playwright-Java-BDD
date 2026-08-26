package core;

import java.util.Random;

public enum BrowserChoice {
    CHROME,
    EDGE,
    FIREFOX,
    WEBKIT;

    public static BrowserChoice random() {
        return BrowserChoice.values()[new Random().nextInt(BrowserChoice.values().length)];
    }
}
