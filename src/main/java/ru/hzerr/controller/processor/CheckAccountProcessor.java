package ru.hzerr.controller.processor;

import javafx.event.ActionEvent;
import javafx.scene.control.ListView;
import ru.hzerr.fx.engine.core.annotation.Registered;
import ru.hzerr.model.EmailValidator;
import ru.hzerr.model.IEmailValidator;
import ru.hzerr.model.MailRuAccount;

@Registered
public class CheckAccountProcessor extends AsyncActionEventProcessor {

    private ListView<MailRuAccount> accounts;

    @Override
    protected void onProcess(ActionEvent actionEvent) throws InterruptedException {
        MailRuAccount account = accounts.getSelectionModel().getSelectedItem();
        if (account != null) {
            getLogProvider().getLogger().debug("Проверка аккаунта: {}", account.getLogin());
            IEmailValidator emailValidator = new EmailValidator();
            switch (emailValidator.validate(account)) {
                case SUCCESS -> getLogProvider().getLogger().debug("Аккаунт {} полностью проверен и является валидным", account.getLogin());
                case PASSWORD_NOT_MATCH -> getLogProvider().getLogger().debug("Аккаунт {} проверен. Пароли не совпадают", account.getLogin());
                case LOGIN_NOT_FOUND -> getLogProvider().getLogger().debug("Аккаунт {} не существует", account.getLogin());
            }
        }
    }

    @Override
    protected void onFailure(Exception e) {
        if (e instanceof InterruptedException) {
            Thread.currentThread().interrupt();
        }

        getLogProvider().getLogger().error(e.getMessage(), e);
    }

    public void setListView(ListView<MailRuAccount> accounts) {
        this.accounts = accounts;
    }
}