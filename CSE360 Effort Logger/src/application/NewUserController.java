package application;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class NewUserController implements Initializable{
	private Scene scene;
	private Parent root;
	private Stage stage;
	
	// Choice box for selecting role at user creation
	@FXML
	private ChoiceBox<String> userType;
	private String[] userTypes= {"Standard", "Supervisor"};
	
	@FXML
	private TextField usernameInput;
	@FXML
	private TextField passwordInput;
	@FXML
	private Label errorLabel;
	
	// Back Button
	public void SwitchToMainMenu(ActionEvent e) throws IOException
	{
		root = FXMLLoader.load(getClass().getResource("Login.fxml"));
		stage = (Stage)((Node)e.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}
	
	// Switch view to User Agreement(Not directly activated by agree button; activated in PressedAgree)
	public void SwitchToUserAgreement(ActionEvent e, String username, String password, int role) throws IOException
	{
		FXMLLoader loader = new FXMLLoader(getClass().getResource("UserAgreement.fxml"));
		root = loader.load();
		
		UserAgreementController userAgreementController = loader.getController();
		userAgreementController.SetNewUserInfo(username, password, role);
		
		//root = FXMLLoader.load(getClass().getResource("UserAgreement.fxml"));
		stage = (Stage)((Node)e.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}
	
	// Move on to user agreement to create new account
	public void PressedCreateAccount(ActionEvent e) throws IOException
	{
		String username = usernameInput.getText();
		String password = passwordInput.getText();
		
		if(userType.getValue() == null)
		{
			return;
		}
		
		int role;
		
		if(userType.getValue() == "Supervisor")
		{
			role = 1;
		}
		else
		{
			role = 0;
		}
		
		if(!UserExists(username))
		{
			if(ValidUsername(username))
			{
				if(ValidPassword(password))
				{
					SwitchToUserAgreement(e, username, password, role);
				}
				else
				{
					errorLabel.setText("Invalid Password");
				}
			}
			else
			{
				errorLabel.setText("Invalid Username");
			}
		}
		else
		{
			errorLabel.setText("User Already Exists");
		}			
	}
	
	// Tests to see if the username fits requirements
	private boolean ValidUsername(String username)
	{
		int size = username.length();
		
		if(size < 1 || size > 13)
		{
			return false;
		}

		return username.matches("^[a-zA-Z0-9 ]*$");
	}

	// Tests to see if password is within parameters
	private boolean ValidPassword(String password)
	{
		int size = password.length();

		if(size < 5 || size > 12)
		{
			return false;
		}
		int uppercaseCount = 0;
		int numCount = 0;
		for(int i=0; i<size; i++)
		{
			if(Character.isUpperCase(password.charAt(i)))
			{
				uppercaseCount++;
			}
			if(Character.isDigit(password.charAt(i)))
			{
				numCount++;
			}
		}

		if(numCount < 3 || uppercaseCount < 1)
		{
			return false;
		}

		return password.matches("^[a-zA-Z0-9 !@?]*$");
	}
	
	// Checks list of users to see if the username is taken
	private boolean UserExists(String username)
	{
		try
		{
			String userInfoFile = Main.getUserListFileLoc();

			// Read the file and check for the username and password
			@SuppressWarnings("resource")
			BufferedReader reader = new BufferedReader(new FileReader(userInfoFile));
			String line;
			while ((line = reader.readLine()) != null)
			{
				if(UsernameMatch(username, line))
				{
					return true;
				}
			}


			reader.close();
			return false;
		}
		catch(IOException e)
		{
			e.printStackTrace();
			return true;
		}
	}
	
	// Checks usernames to see if the username matches for login
	private boolean UsernameMatch(String username, String line)
	{
		username = username.toLowerCase();
		line = line.toLowerCase();
		int i;

		for(i = 0; i < username.length(); i ++)
		{
			if(username.charAt(i) != line.charAt(i))
			{
				return false;
			}
		}

		if(line.charAt(i) == ',')
			return true;

		return false;
	}

	// Populates the choice box 
	@Override
	public void initialize(URL arg0, ResourceBundle arg1)
	{
		userType.getItems().addAll(userTypes);
	}
}
