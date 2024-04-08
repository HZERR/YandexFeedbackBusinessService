package ru.hzerr.playwright;

import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;

public class Browser implements AutoCloseable {

    private Playwright playwright;
    private com.microsoft.playwright.Browser browser;
    private BrowserContext browserContext;

    private Browser() {
        Runtime.getRuntime().addShutdownHook(Thread.ofVirtual().unstarted(this::close));
    }

    public Page openPage(String url) {
        Page page = browserContext.newPage();
        page.navigate(url);
        return page;
    }

    public Page openPage(String url, Page.NavigateOptions options) {
        Page page = browserContext.newPage();
        page.navigate(url, options);
        return page;
    }

    @Override
    public void close() {
        browser.close();
    }

    public static Browser create(BrowserOptions options) {
        Browser browser = new Browser();
        // Установка options -> nullable
        browser.playwright = Playwright.create(options.getCreateOptions());
        browser.playwright.selectors().setTestIdAttribute(options.getTestIdAttribute());
        switch (options.getBrowserType()) {
            case CHROMIUM:
                //noinspection DuplicateBranchesInSwitch
                browser.browser = browser.playwright.chromium().launch(options.getLaunchOptions());
                break;
            case FIREFOX:
                browser.browser = browser.playwright.firefox().launch(options.getLaunchOptions());
                break;
            case WEBKIT:
                browser.browser = browser.playwright.webkit().launch(options.getLaunchOptions());
                break;
            case null, default:
                browser.browser = browser.playwright.chromium().launch(options.getLaunchOptions());
                break;
        }

        browser.browserContext = browser.browser.newContext(options.getNewContextOptions());
        return browser;
    }
}
