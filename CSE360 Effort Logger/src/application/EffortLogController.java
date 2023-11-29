package application;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;

public class EffortLogController implements Initializable{
	private Parent root;
	private Stage stage;
	private Scene scene;
	
	@FXML
	private DatePicker datePicker;
	@FXML
	private TextField timeLog;
	@FXML
	private ChoiceBox<String> projectSelect;
	@FXML 
	private Button enterLogButton;
	@FXML
	private TextArea outputText;
	@FXML
	private Label outputLabel;
	
	//public static String[] projects = {"Project 1", "Project 2", "Project 3"};
	static String filePath = "src/application/Projects.txt";
	String[] projects = readAndPopulateArray(filePath);
	
	//This method updates the drop down for project select with all of the projects in Projects.txt
	static String[] readAndPopulateArray(String filePath) {
		ArrayList<String> projectList = new ArrayList<>();
		
		try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                // Split the line into words using a comma as the delimiter
                String[] words = line.split(",");

                // Add each word to the list
                for (String word : words) {
                    projectList.add(word.trim()); // trim to remove leading/trailing whitespaces
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Convert the ArrayList to an array
        String[] projectsArray = new String[projectList.size()];
        projectsArray = projectList.toArray(projectsArray);

        return projectsArray;
	}
	
	//When the Enter Log button is pressed
	public void enterLog(ActionEvent e) {
		LocalDate date = datePicker.getValue();
		String project = projectSelect.getValue();
		String time = timeLog.getText();
		
		PauseTransition pause = new PauseTransition(Duration.seconds(1));
		if (projectSelect.getValue() != null && datePicker.getValue() != null && time != null && isNumeric(time)) {
			outputLabel.setTextFill(Color.GREEN);
			outputLabel.setText("Effort Log Successfully Entered!");
			try (BufferedWriter bw = new BufferedWriter(new FileWriter(Main.effortLogsFile, true))) {
		        bw.write(projectSelect.getValue() + " -- Date: " + datePicker.getValue() + ", Time: " + timeLog.getText() + " hours");
		        bw.newLine();
		    } catch (IOException err) {
		        err.printStackTrace();
		    }
			projectSelect.setValue(null);
			datePicker.setValue(null);
			timeLog.clear();
			pause.setOnFinished(f -> outputLabel.setText(""));
	        pause.play();
		} else if (projectSelect.getValue() == null && outputLabel.getText().isEmpty()) {
			outputLabel.setTextFill(Color.RED);
			outputLabel.setText("Please Select A Project");
			pause.setOnFinished(f -> outputLabel.setText(""));
	        pause.play();
		} else if ((timeLog.getText() == null || !isNumeric(time)) && outputLabel.getText().isEmpty()) {
			outputLabel.setTextFill(Color.RED);
			outputLabel.setText("Please Enter a Time In Hours");
			pause.setOnFinished(f -> outputLabel.setText(""));
	        pause.play();
		} else if (datePicker.getValue() == null && outputLabel.getText().isEmpty()) {
			outputLabel.setTextFill(Color.RED);
			outputLabel.setText("Please Enter a Date");
			pause.setOnFinished(f -> outputLabel.setText(""));
	        pause.play();
		}
		
		/*
		if (isNumeric(time)) {
			outputText.setText( project + " log successfully created!");
		} else {
			outputText.setText("Enter a valid number for time entered.");
		}*/
		
	}
	
	public void BackToMainMenu(ActionEvent e) throws IOException
	{	
		FXMLLoader loader = new FXMLLoader(getClass().getResource("MainMenu.fxml"));
		root = loader.load();
		
		MainMenuController mainMenuController = loader.getController();
		
		mainMenuController.WelcomeMessage();
		
		//root = FXMLLoader.load(getClass().getResource("TempLogin.fxml"));
		stage = (Stage)((Node)e.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}
	

	@Override //for select project choice box
	public void initialize(URL arg0, ResourceBundle arg1) {
		projectSelect.getItems().addAll(projects);
	}
	
	private boolean isNumeric(String str) {
	    try {
	        Double.parseDouble(str);
	        return true;
	    } catch (NumberFormatException e) {
	        return false;
	    }
}
	
	public void SwitchToCreateNewProject(ActionEvent e) throws IOException
	{
		FXMLLoader loader = new FXMLLoader(getClass().getResource("CreateNewProject.fxml"));
		root = loader.load();
		
		stage = (Stage)((Node)e.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}
}
