package ru.hzerr.service.mail.naming.strategy;

import java.time.LocalDateTime;

public class MailRuServiceSelectorNamingStrategy implements IMailRuServiceSelectorNamingStrategy {

    private final int CURRENT_YEAR = LocalDateTime.now().getYear();

    @Override
    public String firstNameSelector() {
        return "#fname";
    }

    @Override
    public String lastNameSelector() {
        return "#lname";
    }

    @Override
    public String openDaysSelector() {
        return "//*[@id=\"root\"]/div/div[4]/div[4]/div/div/div/div/form/div[6]/div[2]/div/div/div/div[1]";
    }

    @Override
    public String calculateTargetDaySelector(String day) {
        return STR."#react-select-2-option-\{Integer.parseInt(day) - 1}";
    }

    @Override
    public String openMonthsSelector() {
        return "//*[@id=\"root\"]/div/div[4]/div[4]/div/div/div/div/form/div[6]/div[2]/div/div/div/div[3]";
    }

    @Override
    public String calculateTargetMonthSelector(String month) {
        return STR."#react-select-3-option-\{Integer.parseInt(month) - 1}";
    }

    @Override
    public String openYearsSelector() {
        return "//*[@id=\"root\"]/div/div[4]/div[4]/div/div/div/div/form/div[6]/div[2]/div/div/div/div[5]";
    }

    @Override
    public String calculateTargetYearSelector(String year) {
        return STR."#react-select-4-option-\{CURRENT_YEAR - Integer.parseInt(year)}";
    }

    @Override
    public String femaleSelector() {
        return "//*[@id=\"root\"]/div/div[4]/div[4]/div/div/div/div/form/div[9]/div[2]/div/label[2]/div[1]";
    }

    @Override
    public String maleSelector() {
        return "//*[@id=\"root\"]/div/div[4]/div[4]/div/div/div/div/form/div[9]/div[2]/div/label[1]/div[1]";
    }

    @Override
    public String loginSelector() {
        return "#aaa__input";
    }

    @Override
    public String invalidLoginSelector() {
        return "#root > div > div.-iGylzk8u50zKdna3C_sh > div:nth-child(4) > div > div > div > div > form > div:nth-child(13) > div > div:nth-child(2) > div.base-1-2-8.hiddenDesktop-1-2-10 > div:nth-child(3) > div > small";
    }

    @Override
    public String generatePasswordSelector() {
        return "#root > div > div.-iGylzk8u50zKdna3C_sh > div:nth-child(4) > div > div > div > div > form > div:nth-child(16) > span";
    }

    @Override
    public String changeFocusSelector() {
        return "#root > div > div.-iGylzk8u50zKdna3C_sh > div:nth-child(4) > div > div > div > div > form > h3";
    }

    @Override
    public String showReservedEmailInputSelector() {
        return "#root > div > div.-iGylzk8u50zKdna3C_sh > div:nth-child(4) > div > div > div > div > form > div:nth-child(19) > span";
    }

    @Override
    public String reservedEmailSelector() {
        return "#extra-email";
    }

    @Override
    public String switchCaptchaFormSelector() {
        return "#root > div > div.-iGylzk8u50zKdna3C_sh > div:nth-child(4) > div > div > div > div > form > button";
    }

    @Override
    public String captchaImageSelector() {
        return "#root > div > div.-iGylzk8u50zKdna3C_sh > div:nth-child(3) > div > div > div > form > div:nth-child(7) > img";
    }

    @Override
    public String errorSelector() {
        return "#root > div > div.-iGylzk8u50zKdna3C_sh > div:nth-child(3) > div > div > div > form > div:nth-child(7) > div > div.formRow-0-2-100 > div > div.error-0-2-102 > small";
    }

    @Override
    public String captchaTextFieldSelector() {
        return "#root > div > div.-iGylzk8u50zKdna3C_sh > div:nth-child(3) > div > div > div > form > div:nth-child(7) > div > div.formRow-0-2-100 > div > div > div > div > input";
    }

    @Override
    public String applyCaptchaSelector() {
        return "#root > div > div.-iGylzk8u50zKdna3C_sh > div:nth-child(3) > div > div > div > form > button.base-0-2-32.primary-0-2-46";
    }

    @Override
    public String reloadCaptchaSelector() {
        return "#root > div > div.-iGylzk8u50zKdna3C_sh > div:nth-child(3) > div > div > div > form > div:nth-child(7) > div > a";
    }
}
