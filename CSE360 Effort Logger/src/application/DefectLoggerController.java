package application;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
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

public class DefectLoggerController implements Initializable{
	private Parent root;
	private Stage stage;
	private Scene scene;
	
	
	@FXML
	private ChoiceBox<String> projectSelect;
	@FXML
	private ChoiceBox<String> defectSelect;
	@FXML 
	private Button enterDefectButton;
	@FXML 
	private Button returnToMenuButton;
	@FXML
	private TextArea noteText;
	@FXML
	private Label outputLabel;
	
	private String[] projects = EffortLogController.readAndPopulateArray(EffortLogController.filePath);
	
	private String[] defects = {"Bug", "Logic Error", "Timeout", "Security Defect", "Syntax Error"};
	
	public void enterLog(ActionEvent e) {
		PauseTransition pause = new PauseTransition(Duration.seconds(1));
		if (projectSelect.getValue() != null && projectSelect.getValue() != null && !noteText.getText().isEmpty()) {
			outputLabel.setTextFill(Color.GREEN);
			outputLabel.setText("Defect Log Successfully Entered!");
			try (BufferedWriter bw = new BufferedWriter(new FileWriter(Main.defectLogsFile, true))) {
		        bw.write(projectSelect.getValue() + " -- " + defectSelect.getValue() + ": " + noteText.getText());
		        bw.newLine();
		    } catch (IOException err) {
		        err.printStackTrace();
		    }
			projectSelect.setValue(null);
			defectSelect.setValue(null);
			noteText.clear();
			pause.setOnFinished(f -> outputLabel.setText(""));
	        pause.play();
		} else if (projectSelect.getValue() == null && outputLabel.getText().isEmpty()) {
			outputLabel.setTextFill(Color.RED);
			outputLabel.setText("Please Select A Project");
			pause.setOnFinished(f -> outputLabel.setText(""));
	        pause.play();
		} else if (defectSelect.getValue() == null && outputLabel.getText().isEmpty()) {
			outputLabel.setTextFill(Color.RED);
			outputLabel.setText("Please Select A Defect");
			pause.setOnFinished(f -> outputLabel.setText(""));
	        pause.play();
		} else if (noteText.getText().isEmpty() && outputLabel.getText().isEmpty()) {
			outputLabel.setTextFill(Color.RED);
			outputLabel.setText("Please Enter a Note");
			pause.setOnFinished(f -> outputLabel.setText(""));
	        pause.play();
		}
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
		defectSelect.getItems().addAll(defects);
		noteText.setWrapText(true);
	}
}