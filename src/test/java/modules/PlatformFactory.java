package modules;

import helper.PlatformType;
import interfaces.PlatformSession;
import modules.api.APISession;
import modules.web.WebSession;

public final class PlatformFactory {
    private PlatformFactory() {
    }

    public static PlatformSession createSession(PlatformType type) {
        return switch (type) {
            case WEB -> new WebSession();
            case API -> new APISession();
        };

    }
}
