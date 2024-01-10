package ru.hzerr.controller.template;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import ru.hzerr.fx.engine.core.entity.Controller;
import ru.hzerr.model.MailRuAccount;

public abstract class MailRuAccountMasterControllerTemplate extends Controller {

    @FXML protected ListView<MailRuAccount> accountsList;
    @FXML protected Label blockingFilledText;
    @FXML protected Label blockingText;
    @FXML protected ImageView switchBlockingImageView;
    @FXML protected Button changeSelectedAccount;
    @FXML protected AnchorPane content;
    @FXML protected Button createAccountButton;
    @FXML protected Label creationDateFilledText;
    @FXML protected TextField creationDateTextField;
    @FXML protected Label dateOfBirthFilledText;
    @FXML protected TextField dateOfBirthTextField;
    @FXML protected Label firstNameFilledText;
    @FXML protected TextField firstNameTextField;
    @FXML protected AnchorPane informationPane;
    @FXML protected Label lastNameFilledText;
    @FXML protected TextField lastNameTextField;
    @FXML protected Label loginFilledText;
    @FXML protected TextField loginTextField;
    @FXML protected Label passwordFilledText;
    @FXML protected TextField passwordTextField;
    @FXML protected Button removeAccountButton;
    @FXML protected Label sexFilledText;
    @FXML protected Label sexText;
    @FXML protected ImageView switchSexImageView;
}
