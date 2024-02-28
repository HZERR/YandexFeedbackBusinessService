package ru.hzerr;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import ru.hzerr.controller.YandexFeedbackBusinessServiceController;
import ru.hzerr.fx.engine.core.FXEngine;
import ru.hzerr.fx.engine.core.context.IExtendedAnnotationConfigApplicationContext;
import ru.hzerr.fx.engine.core.entity.Entity;
import ru.hzerr.fx.engine.core.entity.SpringLoadMetaData;

import java.util.Objects;

public class YandexFeedbackBusinessService extends FXEngine {

    private static final String RELATIVE_PATH_TO_LOGO = "/images/logo/icons8-feedback-32.png";

    @Override
    protected IExtendedAnnotationConfigApplicationContext createApplicationContext() {
        return createAutomaticExtendedAnnotationConfigApplicationContextProvider(YandexFeedbackBusinessService.class).getContext();
    }

    @Override
    protected Scene onStart(Stage stage) throws Exception {
        stage.setResizable(false);
        stage.requestFocus();

        stage.getIcons().addFirst(new Image(Objects.requireNonNull(YandexFeedbackBusinessService.class.getResourceAsStream(RELATIVE_PATH_TO_LOGO))));

        Entity<YandexFeedbackBusinessServiceController, Parent> entity = context.getEntityLoader()
                .load(SpringLoadMetaData.from(YandexFeedbackBusinessServiceController.class), Parent.class);

        return new Scene(entity.getNode());
    }
}