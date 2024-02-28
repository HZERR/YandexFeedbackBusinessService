package ru.hzerr.playwright.selector.naming;

import ru.hzerr.playwright.selector.naming.step.by.step.Resettable;

public interface IStepSelectorNamingStrategy extends Resettable {

    String getNextSelector();
}
