package frontend;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import backend.DatabaseCommunicator;
import javafx.application.Application;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
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
    Image image = new Image("http://i67.tinypic.com/jb38ys.png");
    ImageView iv1 = new ImageView();
    iv1.setImage(image);

    Alert alert = new Alert(AlertType.INFORMATION);
    alert.setContentText("Incorrect Username and Password");
    StackPane sp = new StackPane();
    Button loginBtn = new Button("Login");
    Button newAccountBtn = new Button("Create Account");
    VBox textfields = new VBox();
    final BooleanProperty firstTime = new SimpleBooleanProperty(true);
    final TextField username = new TextField();
    username.setPromptText("Username");

    final TextField password = new PasswordField();
    password.setPromptText("Password");
  
    VBox.setMargin(username, new Insets(0, 100, 10, 100));
    VBox.setMargin(password, new Insets(0, 100, 10, 100));
    
    textfields.getChildren().addAll(iv1, username, password, loginBtn, newAccountBtn);
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
    
    
    // display the new account form and allow user to create a new account
    newAccountBtn.setOnAction(new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent event) {
		try {
		  // Open the member list
		  String fxmlFile = "NewAccountForm.fxml";
		  Stage stage = new Stage(); 
		  Pane myPane = null; 
		  FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFile));
		  myPane = (Pane) loader.load(); 
		  Scene scene = new Scene(myPane); 
		  stage.setScene(scene);
		  stage.show(); 
		}
		catch (IOException e) {
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
    VBox buttons = new VBox(10);
    buttons.setAlignment(Pos.CENTER);
    Image image = new Image("http://i67.tinypic.com/jb38ys.png");
    ImageView iv1 = new ImageView();
    iv1.setImage(image);
    Button employeeMan = new Button("Employee Management");
    Button classMan = new Button("Class Management");
    Button inventoryMan = new Button("Inventory Management");
    Button memberMan = new Button("Member Management"); 
    bp.setTop(iv1);
    bp.setAlignment(iv1, Pos.TOP_CENTER);
    bp.setMargin(iv1, new Insets(200, 0, -200, 0));
    employeeMan.setStyle("" + "-fx-font-size: 20px;" + "-fx-font-family: Helvetica Neue;");
    classMan.setStyle("" + "-fx-font-size: 20px;" + "-fx-font-family: Helvetica Neue;");
    inventoryMan.setStyle("" + "-fx-font-size: 20px;" + "-fx-font-family: Helvetica Neue;");
    memberMan.setStyle("" + "-fx-font-size: 20px;" + "-fx-font-family: Helvetica Neue;");

    buttons.getChildren().addAll(memberMan, employeeMan, classMan, inventoryMan);
  
    
    employeeMan.setOnAction(new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent event) {
      }
    });
    
    memberMan.setOnAction(new EventHandler<ActionEvent>() {
    	@Override
    	public void handle(ActionEvent event) {
    		try {
    			// Open the member list
    	    	String fxmlFile = "MemberListView.fxml";
    	    	Stage stage = new Stage(); 
    	    	Pane myPane = null; 
    	    	FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFile));
    	    	myPane = (Pane) loader.load(); 
    	    	Scene scene = new Scene(myPane); 
    	    	stage.setScene(scene);
    	    	stage.show(); 
    		}
    		catch (IOException e) {
    			e.printStackTrace();
    		}
    	}
    });
    
    classMan.setOnAction(new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent event) {
        try {
          Stage stage = new Stage(); 
          Pane myPane = null; 
          FXMLLoader loader = new FXMLLoader(getClass().getResource("ClassListView.fxml"));
          myPane = (Pane) loader.load(); 
          Scene scene = new Scene(myPane); 
          stage.setScene(scene);
          stage.show(); 
      }
      catch (IOException e) {
          System.out.println("Could not find XML file");
          e.printStackTrace();
        }
      }
    });
    
    inventoryMan.setOnAction(new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent event) {

        try {
          Stage stage = new Stage();
          Pane myPane = null;
          FXMLLoader loader = new FXMLLoader(getClass().getResource("InventoryListView.fxml"));
          myPane = (Pane) loader.load();
          Scene scene = new Scene(myPane);
          stage.setScene(scene);
          stage.show();
        }
        catch (IOException e) {
          System.out.println("Could not find XML file");
          e.printStackTrace();
        }
      }
    });
    
    bp.setCenter(buttons);
    
    primaryStage.setScene(new Scene(bp, 800, 800));
    primaryStage.show();
 
  }
  

  public static void main(String[] args) {
    launch(args);
  }

}
