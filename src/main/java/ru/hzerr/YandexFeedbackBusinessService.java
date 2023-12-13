package ru.hzerr;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import ru.hzerr.configuration.ExtendedStructureConfigurationInitializer;
import ru.hzerr.configuration.IExtendedStructureConfiguration;
import ru.hzerr.configuration.database.FileSystemMailRuDatabase;
import ru.hzerr.configuration.database.MailRuDatabaseFileLoader;
import ru.hzerr.configuration.database.MailRuRepository;
import ru.hzerr.controller.YandexFeedbackBusinessServiceController;
import ru.hzerr.fx.engine.core.FXEngine;
import ru.hzerr.fx.engine.core.context.IExtendedAnnotationConfigApplicationContext;
import ru.hzerr.fx.engine.core.entity.Entity;
import ru.hzerr.fx.engine.core.entity.LoadMetaData;
import ru.hzerr.fx.engine.core.entity.SpringLoadMetaData;
public class YandexFeedbackBusinessService extends FXEngine {

    @Override
    protected IExtendedAnnotationConfigApplicationContext createApplicationContext() {
        return createAutomaticExtendedAnnotationConfigApplicationContextProvider(YandexFeedbackBusinessService.class).getContext();
    }

    @Override
    protected void onInit() throws Exception {
        context.getBean(ExtendedStructureConfigurationInitializer.class).initialize();
        context.register(MailRuDatabaseFileLoader.class);
        context.register(FileSystemMailRuDatabase.class);
        context.register(MailRuRepository.class);
    }

    @Override
    protected Scene onStart(Stage stage) throws Exception {
        Entity<YandexFeedbackBusinessServiceController, Parent> entity = context.getEntityLoader()
                .load(SpringLoadMetaData.from(YandexFeedbackBusinessServiceController.class), Parent.class);

        return new Scene(entity.getNode());
    }
}