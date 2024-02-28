package ru.hzerr.playwright.selector.naming.step.by.step;

import ru.hzerr.playwright.selector.naming.IStepSelectorNamingStrategy;

public interface IStepByStepCheckAccountSelectorNamingStrategy extends IStepSelectorNamingStrategy {
    String getInputLoginSelector();

    String getInputPasswordSelector();

    String getLoginFrameSelector();

    String getEmailNotFoundSelector();
}
