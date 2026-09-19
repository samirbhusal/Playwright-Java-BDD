package modules;

import pages.CommonPage;
import pages.Dashboard;
import pages.LoginPage;

public class PageObjectFactory {
    private CommonPage commonPage;
    private Dashboard dashboard;
    private LoginPage loginPage;

    public CommonPage commonPage() {
        if (commonPage == null) {
            commonPage = new CommonPage();
        }
        return commonPage;
    }

    public Dashboard dashboard() {
        if (dashboard == null) {
            dashboard = new Dashboard();
        }
        return dashboard;
    }

    public LoginPage loginPage() {
        if (loginPage == null) {
            loginPage = new LoginPage();
        }
        return loginPage;
    }
}
