package com.mycodefu;

import com.mycodefu.afterburner.injection.Injector;
import com.mycodefu.afterburner.views.FXMLView;
import com.mycodefu.dashboard.DashboardView;
import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.time.Month;
import java.util.HashMap;
import java.util.Map;

public class App extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        /*
         * Properties of any type can be easily injected.
         */
        LocalDate date = LocalDate.of(4242, Month.JULY, 21);
        Map<Object, Object> customProperties = new HashMap<>();
        customProperties.put("date", date);
        Injector.setConfigurationSource(customProperties::get);

        System.setProperty("happyEnding", " Enjoy the flight!");
        DashboardView appView = new DashboardView();
        Scene scene = new Scene(appView.getView());
        stage.setTitle(String.format("Java Template (Java %s, JavaFX %s)", System.getProperty("java.version"), System.getProperty("javafx.runtime.version")));
        setGlobalStylesheetToScene(scene);
        stage.setScene(scene);
        stage.show();
    }

    @Override
    public void stop() throws Exception {
        Injector.forgetAll();
    }

    public static void main(String[] args) {
        launch(args);
    }



    public static void setGlobalStylesheetToScene(Scene scene) {
        final String uri = App.class.getResource("app.css").toExternalForm();
        scene.getStylesheets().add(uri);
    }

    public static void showModalView(FXMLView view) {
        Parent root = view.getView();
        Stage stage = new Stage();
        Scene scene = new Scene(root);
        setGlobalStylesheetToScene(scene);
        stage.setScene(scene);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.showAndWait();
    }
}
