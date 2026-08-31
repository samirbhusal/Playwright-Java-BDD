package pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;
import org.testng.Assert;

public class Dashboard {
    private final Locator dashBoardTitle;

    public Dashboard(Page page) {
        this.dashBoardTitle = page.getByRole(AriaRole.IMG, new Page.GetByRoleOptions().setName("Website for automation practice"));
    }

    public void dashboardTitle(String title) {
        Assert.assertEquals(title, dashBoardTitle.getAttribute("alt"));
    }
}
