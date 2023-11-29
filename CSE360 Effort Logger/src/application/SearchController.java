package application;

import java.util.Arrays;
import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;

public class SearchController {
	private Parent root;
	private Scene scene;
	private Stage stage;
	public TextArea outputBox;
	ArrayList<String> keywords = new ArrayList<>(
			Arrays.asList("EffortLog", "DefectLog", "Security Settings", "General Settings", "Help")
			);
	public static String newline = "";
    @FXML
    private TextField searchField;
    @FXML
    private Label resultLabel;
    @FXML
    public Button button; 
    @FXML
    public Button searchButton; 
    
   //when user clicks button 
public void searchButton(ActionEvent event) throws IOException {
	String input = searchField.getText();
	
	if (input.equals("Projects")) {
		outputBox.clear();
		String fileContent = readTextFile(Main.projectFile);
		outputBox.appendText(fileContent);
	
	} else if (input.equals("Defect Logs")) {
		outputBox.clear();
		String fileContent = readTextFile(Main.defectLogsFile);
		outputBox.appendText(fileContent);
		
	} else if (input.equals("Effort Logs")) {
		outputBox.clear();
		String fileContent = readTextFile(Main.effortLogsFile);
		outputBox.appendText(fileContent);
		
	} else {
		//Check if input matches name of a project
		String filePath = "src/application/Projects.txt";
		boolean projFound = searchForProject(filePath, input);
		
		if (projFound == true) {
		
		if (outputBox != null) {
			outputBox.clear();
		}
			
		outputBox.appendText("Project name found!\n"
				+ "-----------------------\n"
				+ "Displaying "
				+ input
				+ " Data:\n\n");
		
		//Print the effort logs and defect logs of that project
		String filePath2 = "src/application/DefectLog.txt";
		String filePath3 = "src/application/EffortLogs.txt";
		newline = "";
		String effortLogs = printLogs(filePath3, input);
		newline = "";
		String defectLogs = printLogs(filePath2, input);
		
		outputBox.appendText("Effort Logs for " + input + ":\n");
		outputBox.appendText(effortLogs);
		outputBox.appendText("\n");
		outputBox.appendText("Defect Logs for " + input + ":\n");
		outputBox.appendText(defectLogs);
		
		} else {
		//name of project not found, show error
		PauseTransition pause = new PauseTransition(Duration.seconds(1));
		resultLabel.setTextFill(Color.RED);
		resultLabel.setText("Error: Enter a Keyword");
		pause.setOnFinished(f -> resultLabel.setText(""));
        pause.play();
		}
	}
}

private static boolean searchForProject(String filePath, String projectName) {
    try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
        String line;
        newline = "";
        while ((line = br.readLine()) != null) {
            // Check if the line contains the project name
            if (line.equals(projectName + ",")) {
                return true;
            }
        }
    } catch (IOException e) {
        e.printStackTrace();
    }

    // Project name not found in the file
    return false;
}

private static String printLogs(String filePath, String projectName) {
    try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
        String line;
        while ((line = br.readLine()) != null) {
            // Check if the line starts with the specified project name
            if (line.startsWith(projectName)) {
            	newline += line;
            	newline += "\n";
            }
        }
    } catch (IOException e) {
        e.printStackTrace();
    }
	return newline;
}

private String readTextFile(File file) {
    StringBuilder content = new StringBuilder();

    try (BufferedReader br = new BufferedReader(new FileReader(file))) {
        String line;
        while ((line = br.readLine()) != null) {
            content.append(line).append("\n");
        }
    } catch (IOException e) {
        e.printStackTrace();
    }

    return content.toString();
}
 
 public void BackToMenu(ActionEvent e) throws IOException
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

}
