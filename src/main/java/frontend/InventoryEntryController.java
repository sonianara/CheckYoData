package frontend;

import backend.DatabaseCommunicator;
import backend.InventoryItem;
import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

/**
 * Created by Kimmy on 3/15/17.
 */
public class InventoryEntryController {
  InventoryItem inventoryItem;
  InventoryListController inventoryListController;

  @FXML
  private Label inventoryName;
  @FXML private Label inventoryCount;
  @FXML private Button inventoryEditButton;

  @FXML
  private void editAction(ActionEvent event) throws IOException {
    Stage stage = new Stage();
    Pane myPane = null;

    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("EditInventoryForm.fxml"));
    myPane = (Pane)fxmlLoader.load();

    EditInventoryController editInventoryController = fxmlLoader.<EditInventoryController>getController();
    DatabaseCommunicator.getInstance();
    editInventoryController.setInventoryItem(DatabaseCommunicator.getInventoryItem(inventoryName.getText()));
    editInventoryController.setController(inventoryListController);


    editInventoryController.populateFields();

    Scene scene = new Scene(myPane);
    stage.setScene(scene);
    stage.show();
  }

  public void setInventoryItem(InventoryItem inventoryItem) {
    this.inventoryItem = inventoryItem;
  }

  public void setInventoryController(InventoryListController controller) {
    this.inventoryListController = controller;
  }
}
