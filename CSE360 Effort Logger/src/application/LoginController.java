package application;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class LoginController {
	private Scene scene;
	private Parent root;
	private Stage stage;
	
	@FXML
	private TextField usernameInput;
	@FXML
	private TextField passwordInput;
	@FXML
	private Label errorLabel;
	
	// Place to store the role of the user(gets assigned in the UsernameMatch() function)
	private int role;
	
	// Create new user
	public void switchToNewUser(ActionEvent e) throws IOException
	{
		root = FXMLLoader.load(getClass().getResource("NewUser.fxml"));
		stage = (Stage)((Node)e.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}
	
	// Logging in
//	public void switchToMainMenu(ActionEvent e) throws IOException
//	{	
//		FXMLLoader loader = new FXMLLoader(getClass().getResource("MainMenu.fxml"));
//		root = loader.load();
//		
//		MainMenuController mainMenuController = loader.getController();
//		
//		// 0 is regular user, 1 is supervisor
//		Main.AssignCurrUser("Test User", 1);
//		mainMenuController.WelcomeMessage();
//		
//		stage = (Stage)((Node)e.getSource()).getScene().getWindow();
//		scene = new Scene(root);
//		stage.setScene(scene);
//		stage.show();
//	}
	
	// IF YOU DONT WANT AUTO SIGN IN AND WANT TO MAKE YOUR OWN ACCOUNTS UN-COMMENT THIS METHOD AND REMOVE THE ABOVE METHOD
	// I.E. This is the normal method
	public void switchToMainMenu(ActionEvent e) throws IOException
	{
		//New Method begins here
		String username = usernameInput.getText();
		String password = passwordInput.getText();
		
		if(username == "" || password == "")
		{
			errorLabel.setText("Empty username or password");
			return;
		}
		else if(!ValidLogin(username, password))
		{
			errorLabel.setText("Incorrect Username or Password");
			return;
		}
		
		FXMLLoader loader = new FXMLLoader(getClass().getResource("MainMenu.fxml"));
		root = loader.load();
		
		MainMenuController mainMenuController = loader.getController();
     	Main.AssignCurrUser(username, role);
		mainMenuController.WelcomeMessage();
		
		// Clean up leftover data
		username = null;
		password = null;
		
		stage = (Stage)((Node)e.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}
	
	// Returns true if the username and password input are within parameters
	private boolean ValidLogin(String username, String password)
	{
		try {
			String userInfoFile = Main.getUserListFileLoc();

			// Read the file and check for the username and password
			@SuppressWarnings("resource")
			BufferedReader reader = new BufferedReader(new FileReader(userInfoFile));
			String line;
			while ((line = reader.readLine()) != null)
			{
				if(UsernameMatch(username, line))
				{
					String hashedPass = Main.hashPassword(password + username);
					if(PasswordMatch(hashedPass, line))
					{
						return true;
					}
					else
					{
						return false;
					}
				}
			}

			reader.close();
			return false;
		}
		catch(IOException e)
		{
			e.printStackTrace();
			return false;
		}
	}
	
	// Checks to see if the given username matches the username on the given line
	private boolean UsernameMatch(String username, String line)
	{
		int i;

		for(i = 0; i < username.length(); i ++)
		{
			if(username.charAt(i) != line.charAt(i))
			{
				return false;
			}
		}
		// Makes sure this is the end of the username
		if(line.charAt(i) == ',')
		{
			// Sets role to the value at the end of the line
			role = Character.getNumericValue(line.charAt(line.length()-1));
			return true;
		}
		return false;
	}

	// Checks to see if the given password matches the password in the given line
	private boolean PasswordMatch(String password, String line)
	{
		// i is used as an index, uses a loop to find beginning of the password
		// Couldn't exactly find a more elegant or efficient way to do this
		int i = 0;

		while(line.charAt(i) != ',')
		{
			i++;
		}
		i++;

		int k = 0;

		while(k < password.length())
		{
			if(password.charAt(k) != line.charAt(i+k))
			{
				return false;
			}
			k++;
		}

		if(line.charAt(i+k) == ';')
		{
			return true;
		}

		return false;
	}
}