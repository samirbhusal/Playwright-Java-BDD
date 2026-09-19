package pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;
import core.PlaywrightDriverManager;
import org.testng.Assert;

public class Dashboard {
    private Page page = null;
    private final Locator dashBoardTitle;

    public Dashboard() {
        this.page = PlaywrightDriverManager.getPage();
        this.dashBoardTitle = page.getByRole(AriaRole.IMG, new Page.GetByRoleOptions().setName("Website for automation practice"));
    }

    public void dashboardTitle(String title) {
        Assert.assertEquals(title, dashBoardTitle.getAttribute("alt"));
    }
}
