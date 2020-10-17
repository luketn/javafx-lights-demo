package com.mycodefu.dashboard.tables;

import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.TextInputControl;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.stage.Stage;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.testfx.api.FxToolkit;
import org.testfx.framework.junit5.ApplicationTest;
import org.testfx.util.WaitForAsyncUtils;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import static com.mycodefu.MyFxTestUtils.takeScreenshot;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.testfx.assertions.api.Assertions.assertThat;

class TablesTest extends ApplicationTest {
    static TablesView tablesView;
    private Stage stage;

    @Override
    public void start(Stage stage) {
        this.stage = stage;
        tablesView = new TablesView();
        stage.setScene(new Scene(tablesView.getView()));
        stage.show();
        stage.toFront();
    }

    @AfterEach
    void cleanup() throws Exception {
        FxToolkit.hideStage();
        release(new KeyCode[]{});
        release(new MouseButton[]{});
    }

    @Test
    void add_person() throws TimeoutException {
        takeScreenshot(tablesView);

        TableView<TableRowData> tableView = lookup("#dataTable").queryTableView();
        assertEquals(2, tableView.getItems().size());

        TextInputControl personTextField = lookup("#personTextField").queryTextInputControl();
        Platform.runLater(() -> personTextField.requestFocus());
        write("Big Nessy", 15);
        takeScreenshot(tablesView);
        assertThat(personTextField.getText()).isEqualTo("Big Nessy");

        clickOn(lookup("#addPerson").queryButton());
        takeScreenshot(tablesView);

        WaitForAsyncUtils.waitFor(10, TimeUnit.SECONDS, () -> tableView.getItems().size() == 3);

        TableRowData tableRowData = tableView.getItems().get(2);
        assertEquals("Big Nessy", tableRowData.name());
        assertEquals("Friend", tableRowData.description());
    }

}
