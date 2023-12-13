package ru.hzerr.controller.processor;

import javafx.event.ActionEvent;
import javafx.scene.control.ListView;
import ru.hzerr.configuration.database.IEmailRepository;
import ru.hzerr.fx.engine.core.FXEngine;
import ru.hzerr.fx.engine.core.annotation.Include;
import ru.hzerr.fx.engine.core.annotation.Registered;
import ru.hzerr.fx.engine.core.event.ActionEventProcessor;
import ru.hzerr.fx.engine.logging.factory.ILogProvider;
import ru.hzerr.model.MailRuAccount;

@Registered
public class CreateMailRuAccountEventProcessor extends ActionEventProcessor {

    private ListView<?> accounts;
//    private final IEmailRepository<MailRuAccount> repository;
    private final ILogProvider logProvider = FXEngine.getContext().getApplicationLogProvider();

//    @Include
//    public CreateMailRuAccountEventProcessor(IEmailRepository<MailRuAccount> repository) {
//        this.repository = repository;
//    }

    @Override
    protected void onProcess(ActionEvent actionEvent) throws Exception {

    }

    @Override
    protected void onFailure(Exception e) {
        logProvider.getLogger().error("CreateMailRuAccountEventProcessor", e);
    }
}
