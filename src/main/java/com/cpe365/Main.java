package com.cpe365;

import java.sql.SQLException;

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
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class Main extends Application {    
  
  /**
   * Login screen
   */
  
  Authentication auth = new Authentication();
  
  public void start(final Stage primaryStage) {
    Label name = new Label("Meathead Manager");
    Hyperlink createAcct = new Hyperlink();
    createAcct.setText("Create Account");
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

    textfields.getChildren().addAll(name, username, password, loginBtn, createAcct);

    createAcct.setOnAction(new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent e) {
          accountForm(primaryStage);
      }
    });
    
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
            manageEmployees(primaryStage);
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
    sp.getChildren().add(textfields);
    sp.setStyle("-fx-background-color : #e1e1e5;");
    primaryStage.setScene(new Scene(sp, 800, 800));
    primaryStage.show();
  }
  
  public void accountForm(Stage primaryStage) {
    TextField name = new TextField();
    name.setPromptText("Name");
    
  }
  
  public void manageEmployees(Stage primaryStage) {
    
  }
  
  public static void main(String[] args) {
    launch(args);
  }

}
