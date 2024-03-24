package ru.hzerr.model;

import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.FrameLocator;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.WaitUntilState;
import ru.hzerr.playwright.Browser;
import ru.hzerr.playwright.BrowserOptions;
import ru.hzerr.playwright.selector.naming.step.by.step.IStepByStepCheckAccountSelectorNamingStrategy;
import ru.hzerr.playwright.selector.naming.step.by.step.StepByStepCheckAccountSelectorNamingStrategy;

public class EmailValidator implements IEmailValidator {

    private final String MAIL_RU_LOGIN_URL = "https://e.mail.ru/login?from=portal";
    private final IStepByStepCheckAccountSelectorNamingStrategy selectorNamingStrategy;
    private final BrowserOptions browserOptions;

    public EmailValidator() {
        selectorNamingStrategy = new StepByStepCheckAccountSelectorNamingStrategy();
        browserOptions = BrowserOptions.create()
                .setLaunchOptions(new BrowserType.LaunchOptions().setHeadless(false))
                .setTestIdAttribute("data-test-id")
                .setBrowserType(ru.hzerr.playwright.BrowserType.FIREFOX);
    }

    @Override
    public EmailValidationStatus validate(MailRuRecord account) throws InterruptedException {
        try (Browser browser = Browser.create(browserOptions)) {
            try (Page page = browser.openPage(MAIL_RU_LOGIN_URL, new Page.NavigateOptions().setWaitUntil(WaitUntilState.NETWORKIDLE))) {
                FrameLocator loginFrame = page.frameLocator(selectorNamingStrategy.getLoginFrameSelector());
                loginFrame.locator(selectorNamingStrategy.getInputLoginSelector()).fill(account.getLogin());
                loginFrame.getByTestId(selectorNamingStrategy.getNextSelector()).click();

                if (!waitPasswordInput(loginFrame)) {
                    return EmailValidationStatus.LOGIN_NOT_FOUND;
                }

                loginFrame.locator(selectorNamingStrategy.getInputPasswordSelector()).fill(account.getPassword());
                loginFrame.locator(selectorNamingStrategy.getNextSelector()).click();
                page.waitForURL(s -> s.contains("https://e.mail.ru/inbox") || s.contains("https://account.mail.ru/login"), new Page.WaitForURLOptions().setWaitUntil(WaitUntilState.NETWORKIDLE).setTimeout(100000000));
                // REWRITE -> ANTI CAPTCHA / CLOSE
                Locator verifyButton = loginFrame.locator("#recaptcha-inter-next");
                if (verifyButton != null) {
                    verifyButton.click();
                }

                Locator passwordInputError = loginFrame.getByTestId("password-input-error");
                if (passwordInputError != null && passwordInputError.isVisible()) {
                    return EmailValidationStatus.PASSWORD_NOT_MATCH;
                }
                Thread.sleep(10000000);
            }
        }

        return EmailValidationStatus.SUCCESS;
    }

    private boolean waitPasswordInput(FrameLocator loginFrame) throws InterruptedException {
        Locator inputPasswordLocator = getInputPasswordLocator(loginFrame);
        Locator emailNotFoundLocator = getEmailNotFoundLocator(loginFrame);

        while (!emailNotFoundLocator.isVisible() && !inputPasswordLocator.isVisible()) {
            Thread.sleep(1000);
        }

        return inputPasswordLocator.isVisible();
    }

    private Locator getEmailNotFoundLocator(FrameLocator loginFrame) {
        return loginFrame.locator(selectorNamingStrategy.getEmailNotFoundSelector());
    }

    private Locator getInputPasswordLocator(FrameLocator loginFrame) {
        return loginFrame.locator(selectorNamingStrategy.getInputPasswordSelector());
    }
}
