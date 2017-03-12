package frontend;

import java.util.ArrayList;
import java.util.Arrays;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.collections.FXCollections; 

public class NewMemberFormController {
	@FXML private TextField firstName; 
	@FXML private TextField lastName; 
	@FXML private TextField email; 
	@FXML private TextField phoneNumber; 
	@FXML private TextField address; 
	@FXML private TextField city; 
	@FXML private ComboBox state; 
	@FXML private TextField zipCode; 

	@FXML
	public void initialize() {
		ArrayList<String> states = new ArrayList<String>(Arrays.asList("AK","AL","AR","AZ","CA",
				"CO","CT","DC","DE","FL","GA","GU","HI","IA","ID", "IL","IN","KS","KY","LA","MA",
				"MD","ME","MH","MI","MN","MO","MS","MT","NC","ND","NE","NH","NJ","NM","NV","NY", 
				"OH","OK","OR","PA","PR","PW","RI","SC","SD","TN","TX","UT","VA","VI","VT","WA",
				"WI","WV","WY")); 
		state.setItems(FXCollections.observableArrayList(states)); 
	}
	
}
