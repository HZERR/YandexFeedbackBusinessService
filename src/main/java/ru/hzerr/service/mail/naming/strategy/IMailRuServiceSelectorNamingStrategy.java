package ru.hzerr.service.mail.naming.strategy;

public interface IMailRuServiceSelectorNamingStrategy {

    String firstNameSelector();
    String lastNameSelector();
    String openDaysSelector();
    String calculateTargetDaySelector(String day);
    String openMonthsSelector();
    String calculateTargetMonthSelector(String month);
    String openYearsSelector();
    String calculateTargetYearSelector(String year);
    String femaleSelector();
    String maleSelector();
    String loginSelector();
    String invalidLoginSelector();
    String generatePasswordSelector();
    String changeFocusSelector();
    String showReservedEmailInputSelector();
    String reservedEmailSelector();
}
