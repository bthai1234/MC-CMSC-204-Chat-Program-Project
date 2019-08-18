import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

/**
 * chat server Class, handles server logic for the chat program
 * @author bthai
 *
 */
public class ChatServer implements Runnable
{
	private static Set<String> namesList = new HashSet<String>();;
	private static Set<PrintWriter> writerlist = new HashSet<PrintWriter>();
	public int PORT = 0;
	private String name;
	public Socket client = null;
	public BufferedReader in = null;
	public PrintWriter out = null;
	
	/**
	 * constructor
	 * @param port
	 */
	public ChatServer(int port){
		PORT = port;
	}
	
	/**
	 * checks to see if the user's inputed screen name is already in use or not, if not it adds the name to the list of names 
	 * and inserts the clients printwriter into a static set for later output to all other clients. 
	 * and moves the client and  the next set of logic to a new thread in the ServerThread class  
	 * 
	 */
	public void run() {
		ServerSocket listen = null;
		
		try	
		{
			listen = new ServerSocket(PORT);
			System.out.println("Server started");
			while(true)
			{
				client = listen.accept();
				in = new BufferedReader(new InputStreamReader(client.getInputStream()));
				out = new PrintWriter(client.getOutputStream(),true);
				
				name = null;
				while(name == null) 
				{
					out.println("SUBMITNAME");
					name = in.readLine();
					
					if(name.equals("")) 
					{
						out.print("NameCantBeEmpty");
						name = null;
					}
					else if(namesList.contains(name)) 
					{
						out.print("Nametaken");
						name = null;
					}
					else 
					{ 
						namesList.add(name);	
						//out.println("NAMEACCEPTED");
						System.out.println("The Name you entered was: " + name);
						
					}
				}
				
				
				out.println("NAMEACCEPTED");
				
				writerlist.add(out);
				ServerThread newServerThread = new ServerThread(in, out, name);
				Thread t = new Thread(newServerThread);
				t.start();
					
					
			}	
		}
		catch (IOException e) 
		{
				// TODO Auto-generated catch block
				e.printStackTrace();
		}
	}
	
	/**
	 * @author bthai
	 *recives text from the conected client and sends the the text it recives to all print writers in the static set 
	 *to be outputed to all other active clients 
	 */
	private class ServerThread implements Runnable {
		BufferedReader in;
		PrintWriter out;
		String name;
		
		/**
		 * constructor
		 * @param input
		 * @param output
		 * @param name
		 */
		public ServerThread(BufferedReader input, PrintWriter output, String name )
		{
			this.in = input;
			this.out = output;
			this.name = name;
			
		}

		/**
		 * recives text from the conected client and sends the the text it recives to all print writers in the static set 
		 *to be outputed to all other active clients 
		 */
		public void run() {
			try {
				for(PrintWriter writer: writerlist) {
					System.out.println(writerlist.size());
					writer.println("MESSAGE Server >> The User " + this.name + " Has Joined The Server!");
				}
				while(true) {
					String input;
					System.out.print("client " + this.name + " enter text: ");
					input = in.readLine();
					if(input == null) {
						return;
					}else if(input.equals("LEAVING")) {
						for(PrintWriter writer: writerlist) {
							System.out.println(writerlist.size());
							writer.println("MESSAGE Server >> The User " + this.name + " Has Left The Server!");
						}
						namesList.remove(this.name);
						writerlist.remove(out);
						try {
							client.close();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}else {
						for(PrintWriter writer: writerlist) {
							System.out.println(writerlist.size());
							writer.println("MESSAGE " + this.name + ": " + input);
						}
					}
				
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			finally
			{
				
				if(name != null) {
					namesList.remove(name);
				}
				if(out != null) {
					writerlist.remove(out);
				}
				try {
					client.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
		}
	}

		
}








	

