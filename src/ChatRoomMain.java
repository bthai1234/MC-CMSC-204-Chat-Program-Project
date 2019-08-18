import java.io.IOException;

import javax.swing.JOptionPane;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * main starting gui for chat program
 * @author bthai
 *
 */
public class ChatRoomMain extends Application 
{
	private final int PORT = 8800;
	private ChatServerExec serverExec;
	private ChatClientExec clientExec;

	
	//start Panel innerclass
	/**
	 * inter panel class for gui
	 * @author benth
	 */
	public class Panel extends VBox
	{
		private Label header = new Label("Chat Client");
		private Label directions = new Label();
		private Button startServerButton = new Button("Start Server"); 
		private Button startClientButton = new Button("Start Client");
		private Button exit = new Button("exit");
		private HBox buttonBox = new HBox();
		private boolean serverStarted = false;
		private VBox vbox = new VBox();
		
		public Panel() {
			
			buttonBox.getChildren().addAll(startServerButton,startClientButton,exit);
			vbox.getChildren().addAll(header,directions);
			directions.setText("1. Start Server\n2. start client\n3. enter screen name in client's Gui\n4. start more clients\n 5.enter a message in a clients gui ");
			directions.setAlignment(Pos.CENTER);
			header.setStyle("-fx-font-weight: Bold;" + "-fx-font-Size: 20");
			header.setPadding(new Insets(0,0,10,0));
			directions.setPadding(new Insets(0,0,10,0));
			buttonBox.setAlignment(Pos.CENTER);
			buttonBox.setSpacing(10);
			vbox.setAlignment(Pos.CENTER);
			this.getChildren().addAll(vbox, buttonBox);

			startServerButton.setOnAction(Event ->{
				if(serverStarted == false) {
					serverStarted = true;
					serverExec = new ChatServerExec(PORT);
					serverExec.startServer(PORT);
				}
			});
			
			startClientButton.setOnAction(Event ->{
				clientExec = new ChatClientExec(PORT);
				
				try {
					clientExec.startClient(PORT);
					
					//clientExec = null;
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			});
			
			
			
			exit.setOnAction(Event ->{
				
				System.exit(0);				
			});
			
		}
		
	}//end of Panel inner class
	
	/**
	 * gui Application start
	 */
	public void start(Stage stage) throws IOException 
	{
		stage.setTitle("Graph Program");
		Panel root = new Panel();
		stage.setScene(new Scene(root,400,200));
		stage.setMinWidth(250);
		stage.show();
		stage.setOnCloseRequest(Event->{
			System.exit(0);
		});
	}

	/**
	 * Main
	 * @param args
	 */
	public static void main(String[] args) 
	{
		   launch(args);
	}

}
	