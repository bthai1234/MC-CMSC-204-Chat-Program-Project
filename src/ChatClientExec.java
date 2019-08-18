import javafx.application.Application;


/**
 * @author bthai
 *runs ChatClient class in a new thread
 */
public class ChatClientExec implements ChatClientExecInterface {
	private final int PORT;
	
	/**
	 * constructor
	 * @param port
	 */
	public ChatClientExec(int port) {
		PORT = port;
	}

	/**
	 *runs ChatClient class in a new thread
	 */
	public void startClient() throws Exception 
	{
		try
		{
			ChatClient client = new ChatClient(PORT);
			Thread t = new Thread(client);
			t.start();
			
			Thread.sleep(100);
		}
		catch (InterruptedException exception)
        {
        	
        }
      
	    
	}
	
	/**
	 *runs ChatClient class in a new thread
	 */
	public void startClient(int port) throws Exception 
	{
		try
		{
			ChatClient client = new ChatClient(port);
			Thread t = new Thread(client);
			t.start();
			
			Thread.sleep(100);
			 
		}
		catch (InterruptedException exception)
        {
        	
        }
     
	    
	}



}
