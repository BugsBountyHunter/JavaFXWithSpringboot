package com.ahmedsr.javafxwithspringboot;

import com.ahmedsr.javafxwithspringboot.uicontroller.SimpleController;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import net.rgielen.fxweaver.core.FxWeaver;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationListener;

import com.ahmedsr.javafxwithspringboot.JavafxWithSpringbootApplication.*;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URL;

@Component
public class StageInitializer implements ApplicationListener<StageReadyEvent> {
    private final String applicationTitle;
    private final FxWeaver fxWeaver;
    private final URL applicationIcon; // = AlphaSyncUiApplication.class.getClassLoader().getResource(Constants.APPLICATION_ICON_LOCATION);

    public StageInitializer(@Value("${spring.application.ui-title}") String applicationTitle, FxWeaver fxWeaver) {
        this.applicationTitle = applicationTitle;
        this.fxWeaver = fxWeaver;
        this.applicationIcon = JavafxWithSpringbootApplication.class.getClassLoader().getResource("assets/icons8-link-64.png");
    }


    @Override
    public void onApplicationEvent(StageReadyEvent event) {
        Stage stage = event.getStage();
        stage.setScene(new Scene(fxWeaver.loadView(SimpleController.class), 320, 240));
        stage.setTitle(applicationTitle);
        try {
            stage.getIcons().add(new Image(applicationIcon.openStream()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        stage.show();
    }
}
