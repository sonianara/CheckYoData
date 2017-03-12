package frontend;

import java.io.IOException;

import backend.Member;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button; 
import javafx.scene.control.Label; 
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MemberEntryController {
	Member member; 
	MemberListController memberListController; 
	
	@FXML private Label firstName; 
	@FXML private Label lastName; 
	@FXML private Label phoneNumber; 
	@FXML private Label memberType; 
	@FXML private Button editButton; 
	
	@FXML
    private void editAction(ActionEvent event) {	
		Stage stage = new Stage();
    	FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("EditMemberForm.fxml"));     

    	Parent root = null;
		try {
			root = (Parent)fxmlLoader.load();
		} catch (IOException e) {
			e.printStackTrace();
		}          
		//TODO update this function 
		// Sets values so the ResourceEntryController knows which course it contains.
		EditMemberController memberController = fxmlLoader.<EditMemberController>getController();
		memberController.setFirstName(member.getFirstName());
		memberController.setLastName(member.getLastName());
		memberController.setPhoneNumber(member.getPhoneNumber());
		memberController.setMemberType(member.getMemberType());
		memberController.setController(memberListController);
    	
		Scene scene = new Scene(root); 
    	stage.setScene(scene);    
    	stage.show();   
	}
	
	public void setMember(Member member) {
		this.member = member; 
	}
	
	public void setMemberController(MemberListController controller) {
		this.memberListController = controller; 
	}
}
