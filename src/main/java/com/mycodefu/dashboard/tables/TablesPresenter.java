package com.mycodefu.dashboard.tables;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import org.controlsfx.control.textfield.AutoCompletionBinding;
import org.controlsfx.control.textfield.TextFields;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.net.URL;
import java.util.Arrays;
import java.util.HashSet;
import java.util.ResourceBundle;
import java.util.Set;

public class TablesPresenter implements Initializable {
    @FXML
    Node root;

    @FXML
    TableView<TableRowData> dataTable;

    @FXML
    TextField personTextField;
    AutoCompletionBinding<String> namesAutocompleteBinding;
    Set<String> names = new HashSet<>(Arrays.asList(new String[]{"Luke", "Bob", "Fred", "Jane", "Amanda", "Smithy", "Nessa", "Brin"}));

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        namesAutocompleteBinding = TextFields.bindAutoCompletion(personTextField, names);
        personTextField.setOnKeyPressed(event -> {
            switch (event.getCode()) {
                case ENTER: {
                    addPerson();
                    break;
                }
            }
        });

        dataTable.getItems().add(new TableRowData("Jacob", "Buddy"));
        dataTable.getItems().add(new TableRowData("Bob", "Pal"));

        System.out.println("Tables initialized by JavaFX.");

        root.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ESCAPE) {
                ((Stage) root.getScene().getWindow()).close();
            }
        });
    }

    @PostConstruct
    public void postConstruct() {
        System.out.println("Tables constructed.");
    }

    @PreDestroy
    public void preDestroy() {
        System.out.println("Tables about to be destroyed.");
    }

    public void addPerson() {
        System.out.println("Adding Person");

        String name = personTextField.getText().trim();
        if (name.isBlank()) {
            System.out.println("Exit adding person early - name is empty.");
            return;
        }

        if (!names.contains(name)) {
            names.add(name);
            namesAutocompleteBinding.dispose();
            namesAutocompleteBinding = TextFields.bindAutoCompletion(personTextField, names);
        }
        dataTable.getItems().add(new TableRowData(name, "Friend"));

        personTextField.setText("");
        personTextField.requestFocus();

        System.out.println("Added Person: " + name);
    }

    public void keyPressed(KeyEvent keyEvent) {
        switch (keyEvent.getCode()) {
            case DELETE, BACK_SPACE -> {
                TableRowData selectedItem = dataTable.getSelectionModel().getSelectedItem();
                dataTable.getItems().remove(selectedItem);
            }
        }
    }

    public void mouseClicked(MouseEvent mouseEvent) {
        if (mouseEvent.getClickCount() >= 2) {
            TableRowData selectedItem = dataTable.getSelectionModel().getSelectedItem();
            System.out.printf("Double-clicked on %s!\n", selectedItem);
        }
    }
}
