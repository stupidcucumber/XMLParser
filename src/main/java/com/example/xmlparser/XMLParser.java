package com.example.xmlparser;

import Windows.Window;
import javafx.application.Application;
import javafx.stage.Stage;

import java.io.IOException;

public class XMLParser extends Application {
    @Override
    public void start(Stage stage) throws IOException {

        Window.launch(stage);
    }

    public static void main(String[] args) {
        launch();
    }
}