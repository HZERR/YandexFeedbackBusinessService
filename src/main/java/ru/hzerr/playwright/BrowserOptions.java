package ru.hzerr.playwright;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Playwright;

public class BrowserOptions {

    private ru.hzerr.playwright.BrowserType browserType;
    private Playwright.CreateOptions createOptions;
    private BrowserType.LaunchOptions launchOptions;
    private Browser.NewContextOptions newContextOptions;
    private String testIdAttribute = "data-testid";

    private BrowserOptions() {
    }

    public BrowserOptions setCreateOptions(Playwright.CreateOptions createOptions) {
        this.createOptions = createOptions;
        return this;
    }

    public Playwright.CreateOptions getCreateOptions() {
        return createOptions;
    }

    public BrowserOptions setBrowserType(ru.hzerr.playwright.BrowserType browserType) {
        this.browserType = browserType;
        return this;
    }

    public ru.hzerr.playwright.BrowserType getBrowserType() {
        return browserType;
    }

    public BrowserOptions setLaunchOptions(BrowserType.LaunchOptions launchOptions) {
        this.launchOptions = launchOptions;
        return this;
    }

    public BrowserType.LaunchOptions getLaunchOptions() {
        return launchOptions;
    }

    public BrowserOptions setNewContextOptions(Browser.NewContextOptions newContextOptions) {
        this.newContextOptions = newContextOptions;
        return this;
    }

    public Browser.NewContextOptions getNewContextOptions() {
        return newContextOptions;
    }

    public BrowserOptions setTestIdAttribute(String testIdAttribute) {
        this.testIdAttribute = testIdAttribute;
        return this;
    }

    public String getTestIdAttribute() {
        return testIdAttribute;
    }

    public static BrowserOptions create() {
        return new BrowserOptions();
    }
}
