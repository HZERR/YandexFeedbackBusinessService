package ru.hzerr.playwright.selector.naming.step.by.step;

public class StepByStepCheckAccountSelectorNamingStrategy implements IStepByStepCheckAccountSelectorNamingStrategy {

    private static final String loginFrameSelector = "#auth-form > div > div > iframe";
    private static final String inputLoginSelector = "//*[@id=\"root\"]/div/div/div/div/div/div/form/div[2]/div[2]/div[1]/div/div/div/div/div/div[1]/div/input";
    private static final String nextPasswordFillingFormSelector = "next-button";
    //                                                             //*[@id=\"root\"]/div/div/div/div/div/div/form/div[2]/div[2]/div[3]/div/div/div[1]/button
    private static final String inputPasswordSelector = "//*[@id=\"root\"]/div/div/div/div/div/div/form/div[2]/div/div[2]/div/div/div/div/div/input";
    private static final String tryLoginSelector = "//*[@id=\"root\"]/div/div/div/div/div/div/form/div[2]/div/div[3]/div/div/div[1]/div/button";

    private static final String emailNotFoundSelector = "//*[@id=\"root\"]/div/div/div/div/div/div/form/div[2]/div[2]/div[1]/div/div/div/div[2]/small";

    private final int COUNT_BY_DEFAULT = 1;
    private int counter = COUNT_BY_DEFAULT;

    public StepByStepCheckAccountSelectorNamingStrategy() {
    }

    @Override
    public String getInputLoginSelector() {
        return inputLoginSelector;
    }

    @Override
    public String getInputPasswordSelector() {
        return inputPasswordSelector;
    }

    @Override
    public String getLoginFrameSelector() {
        return loginFrameSelector;
    }

    @Override
    public String getEmailNotFoundSelector() {
        return emailNotFoundSelector;
    }

    @Override
    public String getNextSelector() {
        String nextSelector = switch (counter) {
            case 1 -> nextPasswordFillingFormSelector;
            case 2 -> tryLoginSelector;
            default -> throw new IllegalStateException(STR."Counter can't be \{counter}");
        };

        counter = counter + 1;

        return nextSelector;
    }

    @Override
    public void reset() {
        counter = COUNT_BY_DEFAULT;
    }
}
