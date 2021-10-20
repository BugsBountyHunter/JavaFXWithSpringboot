package com.ahmedsr.javafxwithspringboot;

import com.ahmedsr.javafxwithspringboot.utils.Constants;
import com.dustinredmond.fxtrayicon.FXTrayIcon;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ConfigurableApplicationContext;

import java.io.IOException;

public class JavafxWithSpringbootApplication extends Application {
    private ConfigurableApplicationContext applicationContext;
    private SpringApplicationBuilder applicationBuilder;

    @Override
    public void init() throws Exception {
        applicationBuilder = new SpringApplicationBuilder(JavafxWithSpringbootUiApplication.class);
        applicationBuilder.headless(false);
        applicationContext = applicationBuilder.run();
    }

    @Override
    public void start(Stage stage) throws Exception {
        applicationContext.publishEvent(new StageReadyEvent(stage));
        
        //TODO:- for simplicity, you can write code here
        BorderPane root = new BorderPane();
        stage.setScene(new Scene(root));
        stage.setTitle("FXTrayIcon");
        try {
            stage.getIcons().add(new Image(Constants.APPLICATION_ICON.openStream()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        // Instantiate the FXTrayIcon providing the parent Stage and a path to an Image file
        FXTrayIcon trayIcon = new FXTrayIcon(stage, Constants.APPLICATION_ICON);
        trayIcon.show();

        // By default the FXTrayIcon's tooltip will be the parent stage's title, that we used in the constructor
        // This method can override this
        trayIcon.setTrayIconTooltip("An alternative tooltip!");

        // We can now add JavaFX MenuItems to the menu
        MenuItem menuItemTest = new MenuItem("Create some JavaFX component!");
        menuItemTest.setOnAction(e ->
                new Alert(Alert.AlertType.INFORMATION, "We just ran some JavaFX code from an AWT MenuItem!").showAndWait());
        trayIcon.addMenuItem(menuItemTest);

        // We can also nest menus, below is an Options menu with sub-items
        Menu menuOptions = new Menu("Options");
        MenuItem miOn = new MenuItem("On");
        miOn.setOnAction(e -> System.out.println("Options -> On clicked"));
        MenuItem miOff = new MenuItem("Off");
        miOff.setOnAction(e -> System.out.println("Options -> Off clicked"));
        menuOptions.getItems().addAll(miOn, miOff);
        trayIcon.addMenuItem(menuOptions);

        VBox vBox = new VBox(5);
        vBox.getChildren().add(new Label("You should see a tray icon!\nTry closing this window " +
                "and double-clicking the icon.\n" +
                "Try single-clicking it."));
        Button buttonRemoveTrayIcon = new Button("Remove TrayIcon");
        vBox.getChildren().add(buttonRemoveTrayIcon);

        // Removing the FXTrayIcon, this will also cause the JVM to terminate
        // after the last JavaFX Stage is hidden
        buttonRemoveTrayIcon.setOnAction(e -> trayIcon.hide());

        Button buttonDefaultMsg = new Button("Show a \"Default\" message");
        // showDefaultMessage uses the FXTrayIcon image in the notification
        buttonDefaultMsg.setOnAction(e -> trayIcon.showMessage("A caption text", "Some content text."));

        Button buttonInfoMsg = new Button("Show a \"Info\" message");
        // other showXXX methods use an icon appropriate for the message type
        buttonInfoMsg.setOnAction(e -> trayIcon.showInfoMessage("A caption text", "Some content text"));

        Button buttonWarnMsg = new Button("Show a \"Warn\" message");
        buttonWarnMsg.setOnAction(e -> trayIcon.showWarningMessage("A caption text", "Some content text"));

        Button buttonErrorMsg = new Button("Show a \"Error\" message");
        buttonErrorMsg.setOnAction(e -> trayIcon.showErrorMessage("A caption text", "Some content text"));

        HBox hBox = new HBox(5, buttonDefaultMsg, buttonInfoMsg, buttonWarnMsg, buttonErrorMsg);
        vBox.getChildren().add(hBox);

        root.setCenter(vBox);
        stage.sizeToScene();
        stage.show();

    }

    @Override
    public void stop(){
        applicationContext.close();
        Platform.exit();
    }

    static class StageReadyEvent extends ApplicationEvent {
        public StageReadyEvent(Stage stage) {
            super(stage);
        }

        public Stage getStage(){
           return ((Stage) getSource());
        }
    }
}
