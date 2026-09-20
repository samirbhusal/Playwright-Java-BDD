package modules.web;

import com.microsoft.playwright.Page;
import core.PlaywrightDriverManager;
import exceptions.FrameworkException;

public abstract class AbstractWebStepDefinitions {

    private WebPageFactory webPageFactory;

    protected WebPageFactory pageFactory() {
        if (webPageFactory == null) {
            Page page = PlaywrightDriverManager.getPage();
            if (page == null) {
                throw new FrameworkException("No Playwright page is available for this scenario");
            }
            webPageFactory = new WebPageFactory(page);
        }
        return webPageFactory;
    }
}
