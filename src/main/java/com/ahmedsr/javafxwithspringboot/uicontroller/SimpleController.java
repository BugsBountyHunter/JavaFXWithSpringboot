package com.ahmedsr.javafxwithspringboot.uicontroller;

import javafx.scene.control.Label;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.stereotype.Component;
import javafx.fxml.FXML;

@Component
@FxmlView("/view/simple-view.fxml")
public class SimpleController {

    @FXML
    private Label serviceStatusLbl;

    @FXML
    protected void onStartButtonClick() {
        serviceStatusLbl.setText("Running");
    }
    @FXML
    protected void onStopButtonClick() {
        serviceStatusLbl.setText("Stop");
    }
}
