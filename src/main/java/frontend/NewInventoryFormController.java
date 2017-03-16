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
public class NewInventoryFormController {
  @FXML
  private TextField inventoryNameText;
  @FXML private TextField inventoryCountText;
  @FXML private Button inventorySubmitButton;

  private InventoryListController controller;

  @FXML
  public void initialize() {
  }

  @FXML
  public void submitButtonClick(ActionEvent event) {
    String name = inventoryNameText.getText();
    int count = Integer.parseInt(inventoryCountText.getText());

    InventoryItem newItem = new InventoryItem(name, count);

    DatabaseCommunicator.getInstance();
    DatabaseCommunicator.addToDatabase(newItem);

    controller.populateInventory();

    // close the new item form
    Stage currentStage = (Stage) inventorySubmitButton.getScene().getWindow();
    currentStage.close();
  }

  public void setInventoryListController(InventoryListController controller) {
    this.controller = controller;
  }

}
