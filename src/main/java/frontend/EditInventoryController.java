package frontend;

import backend.DatabaseCommunicator;
import backend.InventoryItem;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * Created by Kimmy on 3/15/17.
 */
public class EditInventoryController {
  @FXML
  private TextField inventoryNameText;
  @FXML private TextField inventoryCountText;
  @FXML private Button saveButton;
  @FXML private Button deleteButton;


  private InventoryItem inventoryItem;
  private InventoryListController controller;

  @FXML
  public void initialize() {

  }

  public void populateFields() {
    inventoryNameText.setText(inventoryItem.getName());
    inventoryCountText.setText(Integer.toString(inventoryItem.getCount()));
  }

  @FXML
  public void saveInventoryItem(ActionEvent event) {
    String name = inventoryNameText.getText();
    int count = Integer.parseInt(inventoryCountText.getText());

    InventoryItem updatedItem = new InventoryItem(name, count);

    DatabaseCommunicator.getInstance();
    DatabaseCommunicator.replaceDatabase(updatedItem);

    controller.populateInventory();

    Stage currentStage = (Stage) saveButton.getScene().getWindow();
    currentStage.close();
  }

  @FXML
  public void deleteInventoryItem(ActionEvent event) {
    DatabaseCommunicator.deleteFromDatabase(inventoryItem, "'"+ inventoryItem.getName()+"'");

    controller.populateInventory();

    Stage currentStage = (Stage) saveButton.getScene().getWindow();
    currentStage.close();
  }

  public void setInventoryItem(InventoryItem inventoryItem) {
    this.inventoryItem = inventoryItem;
  }

  public void setController(InventoryListController controller) {
    this.controller = controller;
  }
}

