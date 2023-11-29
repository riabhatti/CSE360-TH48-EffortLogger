package application;

import java.io.IOException;
import java.net.URL;
/*
 * Author: Jarrod Cruz
 * Summary: This controller implements a timer that tracks how long the user is active for. The user can start, pause,
 *  and reset the timer
 * 
 */
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.util.Duration;

public class TimerController implements Initializable{
	private Stage stage;
	private Scene scene;
	private Parent root;
	
//	Timeline timeline;
//	LocalTime currentTime = LocalTime.parse("00:00:00");
    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm:ss");

    @FXML
    private Button gobackbut;

    @FXML
    private Button resetbut;

    @FXML
    private Button startbut;

    @FXML
    private Button stopbut;

    @FXML
    private Label timeLabel;

    @FXML
    // Reset button functionality
    void reset(ActionEvent event) {
    	if (startbut.isDisable()) {
            Main.timeline.stop();
            startbut.setDisable(false);
            Main.currentTime = LocalTime.parse("00:00:00");
            timeLabel.setText(Main.currentTime.format(dtf));
        }
    }

    @FXML
    // Start button functionality
    void start(ActionEvent event) {
    	Main.timeline.play();
        startbut.setDisable(true);
    }

    @FXML
    // Pause button functionality
    void stop(ActionEvent event) {
    	 if (Main.timeline.getStatus().equals(Animation.Status.PAUSED)) {
    		 Main.timeline.play();
             stopbut.setText("Pause");
         } else if (Main.timeline.getStatus().equals(Animation.Status.RUNNING)) {
        	 Main.timeline.pause();
             stopbut.setText("Continue");
         }
    }

	@Override
	// Initializer that creates this timer
	public void initialize(URL arg0, ResourceBundle arg1) {
		Main.timeline = new Timeline(new KeyFrame(Duration.millis(1000), ae -> incrementTime()));
		Main.timeline.setCycleCount(Animation.INDEFINITE);
		timeLabel.setText(Main.currentTime.format(dtf));
	}
	// Constantly updates the time and increases it
	private void incrementTime() {
		Main.currentTime = Main.currentTime.plusSeconds(1);
        timeLabel.setText(Main.currentTime.format(dtf));
    }
	
	// Back button
	public void BackToMenu(ActionEvent e) throws IOException
	{	
		FXMLLoader loader = new FXMLLoader(getClass().getResource("MainMenu.fxml"));
		root = loader.load();
		MainMenuController mainMenuController = loader.getController();
		mainMenuController.WelcomeMessage();
		
		stage = (Stage)((Node)e.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}

}