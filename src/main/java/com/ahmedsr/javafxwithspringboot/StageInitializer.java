package com.ahmedsr.javafxwithspringboot;

import com.ahmedsr.javafxwithspringboot.uicontroller.SimpleController;
import com.ahmedsr.javafxwithspringboot.utils.Constants;
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

    public StageInitializer(@Value("${spring.application.ui-title}") String applicationTitle, FxWeaver fxWeaver) {
        this.applicationTitle = applicationTitle;
        this.fxWeaver = fxWeaver;
    }


    @Override
    public void onApplicationEvent(StageReadyEvent event) {
        Stage stage = event.getStage();
        //TODO:- add FXTrayIcon code here (best practice)

    }
}
