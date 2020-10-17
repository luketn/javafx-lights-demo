package com.mycodefu.dashboard;

import com.mycodefu.dashboard.light.LightClickHandler;
import com.mycodefu.dashboard.light.LightView;
import com.mycodefu.dashboard.tables.TablesView;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.inject.Inject;
import java.net.URL;
import java.time.LocalDate;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicInteger;

import static com.mycodefu.App.showModalView;

public class DashboardPresenter implements Initializable, LightClickHandler {
    @FXML
    Node root;

    @FXML
    Label message;

    @FXML
    Pane lightsBox;

    @FXML
    Pane lightMovesBox;

    @Inject
    private LocalDate date;

    private String theEnd;

    private static Random random = new Random();
    private static AtomicInteger lightCounter = new AtomicInteger(0);

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        theEnd = rb.getString("theEnd");
        String dateLabel = rb.getString("date");
        message.setText(dateLabel + ": " + date + theEnd);

        root.setOnKeyPressed(keyEvent -> {
            if (keyEvent.getCode() == KeyCode.ESCAPE) {
                ((Stage) root.getScene().getWindow()).close();
            } else if (keyEvent.isControlDown()) {
                switch (keyEvent.getCode()) {
                    case NUMPAD1, DIGIT1 -> createLights(10);
                    case NUMPAD2, DIGIT2 -> createLights(20);
                    case NUMPAD3, DIGIT3 -> createLights(30);
                    case P -> lightsBox.getChildren().clear();
                }
            } else {
                switch (keyEvent.getCode()) {
                    case A, D, W, S -> handleLightMoves(keyEvent);
                }
            }
        });
        System.out.println("JavaFX initialize()");
    }


    @PostConstruct
    public void postConstruct() {
        System.out.println("Dashboard constructed.");
    }

    @PreDestroy
    public void preDestroy() {
        System.out.println("Dashboard about to be destroyed.");
    }


    public void createLights() {
        createLights(256);
    }

    public void createLights(int lightCount) {
        System.out.printf("Creating %d lights!\n", lightCount);

        for (int i = 0; i < lightCount; i++) {
            final int red = random.nextInt(255);
            final int green = random.nextInt(255);
            final int blue = random.nextInt(255);

            LightView view = new LightView(red, green, blue, this);

            Parent parent = view.getView();
            lightsBox.getChildren().add(parent);

            if (lightCounter.incrementAndGet() > 2048) {
                lightsBox.getChildren().remove(0);
            }
        }
    }

    public void launch() {
        TablesView tablesView = new TablesView();
        showModalView(tablesView);
        System.out.println("here");
    }

    @Override
    public void lightClicked(Node circle) {
        System.out.printf("Light clicked - %s\n", circle);
        lightsBox.getChildren().remove(circle.getParent());
        lightCounter.decrementAndGet();

        lightMovesBox.getChildren().clear();
        lightMovesBox.getChildren().add(circle);
    }

    private void handleLightMoves(KeyEvent keyEvent) {
        final int moveSpeed = 2;
        if (lightMovesBox.getChildren().size() > 0) {
            Node light = lightMovesBox.getChildren().get(0);
            switch (keyEvent.getCode()) {
                case D -> light.setTranslateX(light.getTranslateX() + moveSpeed);
                case A -> light.setTranslateX(light.getTranslateX() - moveSpeed);
                case S -> light.setTranslateY(light.getTranslateY() + moveSpeed);
                case W -> light.setTranslateY(light.getTranslateY() - moveSpeed);
            }
        }
    }

}
