package frontend;

import backend.DatabaseCommunicator;
import backend.InventoryItem;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * Created by Kimmy on 3/15/17.
 */
public class InventoryListController {
  @FXML private VBox inventoryContainer;
  @FXML private Button newInventoryButton;
  @FXML private Button searchInventoryButton;


  List<InventoryItem> inventoryItems = new ArrayList<>();

  @FXML
  public void initialize() {
    getInventoryList();

    populateInventory();
  }

  public void populateInventory() {

    getInventoryList();
    inventoryContainer.getChildren().clear();

    addLabelsToContainer();

    for (int i = 0; i < inventoryItems.size(); i++) {
      addInventoryItemToContainer(inventoryItems.get(i));
    }

  }
  private void addLabelsToContainer() {
    Pane newPane = null;

    FXMLLoader loader = null;
    try {
      loader = new FXMLLoader(getClass().getResource("InventoryEntry.fxml"));
      newPane = (Pane) loader.load();
    }
    catch (IOException e) {
      e.printStackTrace();
    }
    inventoryContainer.getChildren().add(newPane);
    newPane.getChildren().remove(newPane.lookup("#inventoryEditButton"));
  }

  public void addInventoryItemToContainer(InventoryItem inventoryItem) {
    Pane newPane = null;

    FXMLLoader loader = null;
    try {
      loader = new FXMLLoader(getClass().getResource("InventoryEntry.fxml"));
      newPane = (Pane) loader.load();
    }
    catch (IOException e) {
      e.printStackTrace();
    }

    InventoryEntryController inventoryController = loader.<InventoryEntryController>getController();
    inventoryController.setInventoryItem(inventoryItem);
    inventoryController.setInventoryController(this);


    inventoryContainer.getChildren().add(newPane);
    Label inventoryName = (Label) newPane.lookup("#inventoryName");
    Label inventoryCount = (Label) newPane.lookup("#inventoryCount");

    inventoryName.setText(inventoryItem.getName());
    inventoryCount.setText(String.valueOf(inventoryItem.getCount()));
  }

  @FXML
  public void addInventoryItemButtonClick(ActionEvent event) throws IOException {
    String fxmlFile = "NewInventoryForm.fxml";
    Stage stage = new Stage();
    Pane myPane = null;
    FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFile));
    myPane = (Pane) loader.load();
    NewInventoryFormController inventoryController = loader.<NewInventoryFormController>getController();
    inventoryController.setInventoryListController(this);
    Scene scene = new Scene(myPane);
    stage.setScene(scene);
    stage.show();
  }

  @FXML
  public void searchButtonClick(ActionEvent event) throws IOException {
//    System.out.println("search button clicked");
//    String fxmlFile = "SearchMemberForm.fxml";
//    Stage stage = new Stage();
//    Pane myPane = null;
//    FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFile));
//    myPane = (Pane) loader.load();
//    SearchMemberFormController inventoryItemController = loader.<SearchMemberFormController>getController();
//    memberController.setMemberListController(this);
//    Scene scene = new Scene(myPane);
//    stage.setScene(scene);
//    stage.show();
  }

  public void getInventoryList() {
    inventoryItems.clear();
    DatabaseCommunicator.getInstance();

    String query = "SELECT * FROM inventory";
    query += ";";
    System.out.println(query);

    List<HashMap<String, Object>> rows = DatabaseCommunicator.queryDatabase(
        query);
    for (HashMap<String, Object> row : rows) {
      InventoryItem item = new InventoryItem();
      item.setName(row.get("name").toString());
      item.setCount(Integer.parseInt(row.get("count").toString()));
      this.inventoryItems.add(item);
    }
  }
}
