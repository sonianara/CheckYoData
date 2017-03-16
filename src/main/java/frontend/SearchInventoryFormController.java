package frontend;

import backend.DatabaseCommunicator;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * Created by Kimmy on 3/16/17.
 */
public class SearchInventoryFormController {
  @FXML
  private TextField inventoryNameText;
  @FXML private TextField inventoryCountText;
  @FXML private Button inventorySearchButton;

  private InventoryListController controller;

  @FXML
  public void searchButtonClick(ActionEvent event) {
	System.out.println("Seach button clicked" );
    String name = inventoryNameText.getText();
    String count = inventoryCountText.getText();

    DatabaseCommunicator.getInstance();

    String filter = "";
    // filter based on entered values
    if (!name.trim().isEmpty())
      filter += "name= '" + name + "'";
    if (!count.trim().isEmpty()) {
      if (filter != "")
        filter += " and ";
      filter += "count = '" + count + "'";
    }
    // if no filtering was entered, set the filter to null
    if (filter == "")
      filter = null;

    // update the inventory list view
    controller.getInventoryList(filter);
    controller.populateInventory();

    // close the new member form
    Stage currentStage = (Stage) inventorySearchButton.getScene().getWindow();
    currentStage.close();
  }

  public void setInventoryListController(InventoryListController controller) {
    this.controller = controller;
  }

}
