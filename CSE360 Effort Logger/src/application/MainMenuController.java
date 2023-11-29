package application;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class MainMenuController
{
	private Scene scene;
	private Parent root;
	private Stage stage;
	
	@FXML
	Label nameLabel;
	@FXML
	Label roleLabel;

	// States the username and the role of the user
	public void WelcomeMessage()
	{
		if(Main.getMode() == 1)
		{
			roleLabel.setText("Supervisor");
		}
		else
		{
			roleLabel.setText("Standard");
		}
		nameLabel.setText(Main.getCurrUser() + "!");
	}
	
	// Name this logout
	public void Signout(ActionEvent e) throws IOException
	{
		nameLabel.setText(null);
		
		Main.SignOut();
		
		root = FXMLLoader.load(getClass().getResource("Login.fxml"));
		stage = (Stage)((Node)e.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}
	
	// Pressed Logger button on main menu
	public void SwitchToLogger(ActionEvent e) throws IOException
	{
		FXMLLoader loader = new FXMLLoader(getClass().getResource("EffortLog.fxml"));
		root = loader.load();
		
		//EffortLogController EffortLogController = loader.getController();
		
		stage = (Stage)((Node)e.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}

	public void SwitchToDefectLogger(ActionEvent e) throws IOException
	{
		FXMLLoader loader = new FXMLLoader(getClass().getResource("DefectLogger.fxml"));
		root = loader.load();
		
		stage = (Stage)((Node)e.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}
	// Pressed Search button on main menu
	public void SwitchToSearch(ActionEvent e) throws IOException
	{
		FXMLLoader loader = new FXMLLoader(getClass().getResource("Search.fxml"));
		root = loader.load();
		
		stage = (Stage)((Node)e.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}
	
	// Pressed timer button on main menu
	public void SwitchToTimer(ActionEvent e) throws IOException
	{
		FXMLLoader loader = new FXMLLoader(getClass().getResource("Timer.fxml"));
		root = loader.load();
		
		stage = (Stage)((Node)e.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}
}
