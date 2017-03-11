package frontend;

import java.sql.SQLException;

import com.mysql.jdbc.Connection;

import backend.DatabaseCommunicator;
import javafx.application.Application;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Main extends Application {   
	
	DatabaseCommunicator databaseCommunicator = null; 
  
  /**
   * Login screen
   */
  
  Authentication auth = new Authentication();
  
  public void start(final Stage primaryStage) {
    BorderPane bp = new BorderPane();
    Label name = new Label("Meathead Manager");
    name.setPadding(new Insets(0, 0, 50, 0));
    Alert alert = new Alert(AlertType.INFORMATION);
    alert.setContentText("Incorrect Username and Password");
    StackPane sp = new StackPane();
    Button loginBtn = new Button("Login");
    VBox textfields = new VBox();
    final BooleanProperty firstTime = new SimpleBooleanProperty(true);
    final TextField username = new TextField();
    username.setPromptText("Username");
    name.setStyle("" + "-fx-font-size: 30px;" + "-fx-font-family: Cambria;");

    final TextField password = new TextField();
    password.setPromptText("Password");
  
    VBox.setMargin(username, new Insets(0, 100, 10, 100));
    VBox.setMargin(password, new Insets(0, 100, 10, 100));
    
    textfields.getChildren().addAll(name, username, password, loginBtn);

    bp.setCenter(textfields);
  
    BooleanBinding bb = new BooleanBinding() {
      {
        super.bind(username.textProperty(), password.textProperty());
      }

      @Override
      protected boolean computeValue() {
        return username.getText().isEmpty() || password.getText().isEmpty();
      }
    };
    
    username.focusedProperty().addListener((observable,  oldValue,  newValue) -> {
      if(newValue && firstTime.get()){
          textfields.requestFocus(); // Delegate the focus to container
          firstTime.setValue(false); // Variable value changed for future references
      }
  });

    loginBtn.disableProperty().bind(bb);
    
    loginBtn.setOnAction(new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent event) {
        try {
          if (auth.login(username.getText(), password.getText())) {
            homeScreen(primaryStage);
          }
          else {
            alert.showAndWait();
          }
        } catch (ClassNotFoundException e) {
          // TODO Auto-generated catch block
          e.printStackTrace();
        } catch (SQLException e) {
          // TODO Auto-generated catch block
          e.printStackTrace();
        }
      }
    });

    textfields.setAlignment(Pos.CENTER);
    sp.getChildren().add(bp);
    sp.getChildren().add(textfields);
    sp.setStyle("-fx-background-color : #e1e1e5;");
    primaryStage.setScene(new Scene(sp, 800, 800));
    primaryStage.show();
    
    // Initiate database connection
    databaseCommunicator = DatabaseCommunicator.getInstance(); 

  }
  
  /** 
   * Method to close the connection when the window is closed
   */
  @Override
  public void stop() throws SQLException {
	  databaseCommunicator.closeConnection(); 
	  
  }
  
  public void accountForm(Stage primaryStage) {
    TextField name = new TextField();
    name.setPromptText("Name");
    
  }
  
  public void homeScreen(Stage primaryStage) {
    BorderPane bp = new BorderPane();
    bp.setTop(menuBar());
    HBox buttons = new HBox(10);
    buttons.setAlignment(Pos.CENTER);
    
    Button memberMan = new Button("Employee Management");
    Button classMan = new Button("Class Management");
    Button inventoryMan = new Button("Inventory Management");

    memberMan.setStyle("" + "-fx-font-size: 20px;" + "-fx-font-family: Cambria;");
    classMan.setStyle("" + "-fx-font-size: 20px;" + "-fx-font-family: Cambria;");
    inventoryMan.setStyle("" + "-fx-font-size: 20px;" + "-fx-font-family: Cambria;");

    buttons.getChildren().addAll(memberMan, classMan, inventoryMan);
    
    memberMan.setOnAction(new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent event) {
        manageEmployees(primaryStage);
      }
    });
    
    classMan.setOnAction(new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent event) {
        manageClasses(primaryStage);
      }
    });
    
    inventoryMan.setOnAction(new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent event) {
        manageInventory(primaryStage);
      }
    });
    
    bp.setCenter(buttons);
    
    primaryStage.setScene(new Scene(bp, 800, 800));
    primaryStage.show();
 
  }
  
  public HBox menuBar() {
    Hyperlink home = new Hyperlink("Home");
    Hyperlink pricing = new Hyperlink("Pricing");
    Hyperlink contact = new Hyperlink("Contact");
    HBox items = new HBox();
    items.getChildren().addAll(home, pricing, contact);
    return items;
  }
  
  public void manageEmployees(Stage primaryStage) {
//    menuBar(primaryStage);
    primaryStage.setTitle("Manage Employees");
    BorderPane layout = new BorderPane();
    //MenuBar
//    layout.setTop(menuBar());

//    Back Button
    Button backBtn = new Button("Back");
    layout.setTop(backBtn);

    backBtn.setOnAction(new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent event) {
        homeScreen(primaryStage);
      }
    });

    VBox employeeList = new VBox(5); //sets spacing
    Label emp1 = new Label("John Smith");
    Label emp2 = new Label("Jane Smith");
    Label emp3 = new Label("Kim Tan");
    employeeList.getChildren().addAll(emp1, emp2, emp3);
    employeeList.setAlignment(Pos.CENTER);
    Button addEmp = new Button("Add Employee");
    layout.setCenter(employeeList);
    layout.setBottom(addEmp);
    BorderPane.setAlignment(addEmp, Pos.CENTER);
    
    primaryStage.setScene(new Scene(layout, 800, 800));
    primaryStage.show();
  }
  
  public void manageClasses(Stage primaryStage) {

    primaryStage.setTitle("Manage Classes");
    BorderPane layout = new BorderPane();
    Button backBtn = new Button("Back");
    //add back button
    layout.setTop(backBtn);

    backBtn.setOnAction(new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent event) {
        homeScreen(primaryStage);
      }
    });
    VBox classList = new VBox(5); //sets spacing
    Label c1 = new Label("Spin");
    Label c2 = new Label("Body Pump");
    Label c3 = new Label("Yoga");
    classList.getChildren().addAll(c1, c2, c3);
    classList.setAlignment(Pos.CENTER);
    Button addClasses = new Button("Add Class");
    layout.setCenter(classList);
    layout.setBottom(addClasses);
    BorderPane.setAlignment(addClasses, Pos.CENTER);

    primaryStage.setScene(new Scene(layout, 800, 800));
    primaryStage.show();
  }
  
  public void manageInventory(Stage primaryStage) {
    primaryStage.setTitle("Manage Inventory");
    BorderPane layout = new BorderPane();
    Button backBtn = new Button("Back");
    //add back button
    layout.setTop(backBtn);

    backBtn.setOnAction(new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent event) {
        homeScreen(primaryStage);
      }
    });
    VBox equipmentList = new VBox(5); //sets spacing
    Label e1 = new Label("Treadmill");
    Label e2 = new Label("Elliptical");
    Label e3 = new Label("Squat Rack");
    equipmentList.getChildren().addAll(e1, e2, e3);
    equipmentList.setAlignment(Pos.CENTER);
    Button addEquipment = new Button("Add Equipment");
    addEquipment.setOnAction(new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent event) {
        addInventory(primaryStage);
      }
    });
    layout.setCenter(equipmentList);
    layout.setBottom(addEquipment);
    BorderPane.setAlignment(addEquipment, Pos.CENTER);

    primaryStage.setScene(new Scene(layout, 800, 800));
    primaryStage.show();

  }

  public void addInventory(Stage primaryStage) {
    primaryStage.setTitle("Add New Equipment");
    TextField textField = new TextField ();
    Label label1 = new Label("Inventory Name");
    HBox hb = new HBox();
    hb.getChildren().addAll(label1, textField);
    hb.setSpacing(10);
    primaryStage.setScene(new Scene(hb, 800, 800));
    primaryStage.show();

  }
  
  public static void main(String[] args) {
    launch(args);
  }

}
