package application;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import javafx.event.ActionEvent;
//import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
//import javafx.scene.control.Label;
import javafx.stage.Stage;

public class UserAgreementController {
	private Scene scene;
	private Parent root;
	private Stage stage;
	
	private String username;
	private String password;
	private int role;
	
	
	public void SetNewUserInfo(String username, String password, int role)
	{
		this.username = username;
		this.password = password;
		this.role = role;
	}
	
	// Logs in new account
	public void PressedAgree(ActionEvent e) throws IOException
	{
		// User agreed so we add to the UserInfo.txt file
		AddUser(username, password, role);
		
		FXMLLoader loader = new FXMLLoader(getClass().getResource("MainMenu.fxml"));
		root = loader.load();
		MainMenuController mainMenuController = loader.getController();
		
		// Set global variables for sign in
		Main.AssignCurrUser(username, role);
		
		// Remove info left over before leaving page
		username = null;
		password = null;
		
		mainMenuController.WelcomeMessage();
		
		// leave the page to the logged-in page
		stage = (Stage)((Node)e.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}
	
	// Sends you to the starting login screen
	public void PressedDisagree(ActionEvent e) throws IOException
	{
		// Remove info left over before leaving page
		username = null;
		password = null;
		
		// leave the page to the login page
		root = FXMLLoader.load(getClass().getResource("Login.fxml"));
		stage = (Stage)((Node)e.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}
	
	// Adds the users info to the text file
	private void AddUser(String username, String password, int role)
	{
		try
		{
			String userInfoFile = Main.getUserListFileLoc();

			// Create a BufferedWriter to append data to the file
			BufferedWriter writer = new BufferedWriter(new FileWriter(userInfoFile, true));

			// Write the new users data to the file
			password = Main.hashPassword(password+username);
			
			writer.write(username + "," + password + ";" + role);
			writer.newLine();
			writer.close();
		}

		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
}
