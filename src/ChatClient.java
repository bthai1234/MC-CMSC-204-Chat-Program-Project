import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * 
 * @author bthai
 * Chat client for for chat program
 */
public class ChatClient implements Runnable, ChatClientInterface {
	private int CHAT_ROOM_PORT = 0;
	private BorderPane frame = new BorderPane();
	private TextField textField = new TextField();
	private TextArea chatArea = new TextArea();
	private Stage stage;
	private String screenName = "";
	public BufferedReader in;
	public PrintWriter out;
	

	public ChatClient(int port) {
		CHAT_ROOM_PORT =  port;
		stage = new Stage();
		stage.setScene(new Scene(frame, 500, 200));
		stage.setY(400);
		stage.setX(400);
		stage.show();
		stage.setTitle("chat client");
		textField.setEditable(false);
		chatArea.setEditable(false);
		frame.setTop(textField);
		frame.setCenter(chatArea);
		frame.setVisible(true);
		
		textField.setOnAction(Event->{
			out.println(textField.getText());
			textField.setText("");
		});
		
		

			
	}
	
	/**
     * Prompt for and return the desired screen name.
     */
	public String getName() {
		// TODO Auto-generated method stub
		return JOptionPane.showInputDialog(null, "Enter Name");
	}

	/**
     * @return the port integer
     */
	public int getServerPort() {
		// TODO Auto-generated method stub
		return CHAT_ROOM_PORT;
	}
	
	/**
     * @return String The local address
     */
	public String getLocalAddress() {
		// TODO Auto-generated method stub
		return "127.0.0.1";
	}

	/**
	 * Logic for chat client, conects to server and sends input to it. writes servers Message to text area
	 */
	public void run() {

		Socket socket;
		try {
			
			socket = new Socket("127.0.0.1", getServerPort());
			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			out = new PrintWriter(socket.getOutputStream(),true);
			while(true) {
				String line = in.readLine();
				if(line.startsWith("SUBMITNAME")){
					System.out.println("Enter name");
					screenName = getName();
					out.println(screenName);
				}else if(line.startsWith("NAMEACCEPTED")) {
					System.out.println("name accepted");
					textField.setEditable(true);
				}else if(line.startsWith("Nametaken")) {
					System.out.println("name already taken");
					JOptionPane.showMessageDialog(null, "Screen Name " + screenName + " is tanken");
					screenName = getName();
					out.println(screenName);
				}else if(line.startsWith("NameCantBeEmpty")){
					System.out.println("empty");
					JOptionPane.showMessageDialog(null, "name cant be left blank");
					screenName = getName();
					out.println(screenName);
				}
				else if(line.startsWith("MESSAGE")) {
					System.out.println("Message recived: " + line);
					chatArea.appendText(line.substring(8) + "\n");
				}
				stage.setOnCloseRequest(Event->{
					out.println("LEAVING");
				});
				
			}
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	
	
}
