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

public class CreateNewProjectController implements Initializable{
	private Parent root;
	private Stage stage;
	private Scene scene;
	
	@FXML
	private TextField projectName;
	@FXML
	private Label outputLabel;
	@FXML
	private Button returnButton;
	@FXML
	private Button newProjectButton;
	
	//public static final File projectFile = new File("src/application/Projects.txt");
	
	public void createNewProject(ActionEvent e) {
		String pjName = projectName.getText();
		
		PauseTransition pause = new PauseTransition(Duration.seconds(1));
		if (pjName.length() != 0) {
			outputLabel.setTextFill(Color.GREEN);
			outputLabel.setText("Project Successfully Created!");
			try (BufferedWriter bw = new BufferedWriter(new FileWriter(Main.projectFile, true))) {
		        bw.write(pjName + ",\n ");
		    } catch (IOException err) {
		        err.printStackTrace();
		    }
			//projectName.setText(null);
			pause.setOnFinished(f -> outputLabel.setText(""));
	        pause.play();
		} else if (pjName.length() == 0) {
			outputLabel.setTextFill(Color.RED);
			outputLabel.setText("Please Enter a Name for the Project");
			pause.setOnFinished(f -> outputLabel.setText(""));
	        pause.play();
	}
	}
	
	public void returnToEffortLogger(ActionEvent e) throws IOException
	{	
		FXMLLoader loader = new FXMLLoader(getClass().getResource("EffortLog.fxml"));
		root = loader.load();
		
		stage = (Stage)((Node)e.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		
	}
	
}