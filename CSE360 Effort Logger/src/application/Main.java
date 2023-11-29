package application;
	
import java.io.File;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalTime;

import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
//import javafx.scene.layout.BorderPane;
import javafx.fxml.FXMLLoader;

public class Main extends Application
{
	// Global variables------------
	// If you are on Mac, you will have to manually create the UserInfo.txt and enter its location
	private static final String USER_LIST_FILE_LOCATION = "src/application/UserInfo.txt";
	//File for storage of project names
	public static final File projectFile = new File("src/application/Projects.txt");
	//File for storage of effort logs
	public static final File effortLogsFile = new File("src/application/EffortLogs.txt");
	//File for storage of defect logs
	public static final File defectLogsFile = new File("src/application/DefectLog.txt");
	
	private static String currUser;
	private static int mode;
	public static Timeline timeline;
    public static LocalTime currentTime = LocalTime.parse("00:00:00");
	// ----------------------------
	
	@Override
	public void start(Stage stage) {
		try
		{
			try
			{
				File myObj = new File(USER_LIST_FILE_LOCATION);
				if (myObj.createNewFile())
				{
					System.out.println("File created: " + myObj.getName());
				}
			}
			catch (IOException e)
			{
				System.out.println(e);
			}


			Parent root = FXMLLoader.load(getClass().getResource("Login.fxml"));
			Scene scene = new Scene(root,600,400);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			stage.setScene(scene);
			stage.show();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args)
	{
		launch(args);
	}
	
	// Methods to use the global variables
	// Sets the data at sign in
	public static void AssignCurrUser(String username, int role)
	{
		currUser = username;
		mode = role;
	}
	
	// Removes the global data at sign out
	public static void SignOut()
	{
		mode = 0;
		currUser = null;
	}
	
	// Gets users username
	public static String getCurrUser()
	{
		return currUser;
	}
	
	// Gets mode that program is running in(users role)
	public static int getMode()
	{
		return mode;
	}
	
	public static String getUserListFileLoc()
	{
		return USER_LIST_FILE_LOCATION;
	}
	
	public static String hashPassword(String password) {
        try {
            // Create MessageDigest instance for SHA-256
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            
            // Perform the hashing
            byte[] encodedhash = digest.digest(password.getBytes());

            // Convert byte array into signum representation
            StringBuilder hexString = new StringBuilder(2 * encodedhash.length);
            for (int i = 0; i < encodedhash.length; i++) {
                String hex = Integer.toHexString(0xff & encodedhash[i]);
                if (hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }

            // Return the hashed password in hex format
            return hexString.toString();
        }
        catch (NoSuchAlgorithmException e)
        {
            throw new RuntimeException(e);
        }
    }
}
