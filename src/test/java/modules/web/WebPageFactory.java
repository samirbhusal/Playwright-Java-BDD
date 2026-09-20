package modules.web;

import com.microsoft.playwright.Page;
import pages.CommonPage;
import pages.Dashboard;
import pages.LoginPage;

public class WebPageFactory {
    private CommonPage commonPage;
    private Dashboard dashboard;
    private LoginPage loginPage;

    private Page page;

    public WebPageFactory(Page page) {
        this.page = page;
    }

    public CommonPage commonPage() {
        if (commonPage == null) {
            commonPage = new CommonPage(page);
        }
        return commonPage;
    }

    public Dashboard dashboard() {
        if (dashboard == null) {
            dashboard = new Dashboard(page);
        }
        return dashboard;
    }

    public LoginPage loginPage() {
        if (loginPage == null) {
            loginPage = new LoginPage(page);
        }
        return loginPage;
    }
}
