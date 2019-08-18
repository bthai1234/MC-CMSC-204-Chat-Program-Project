
/**
*runs ChatServer class in a new thread
*/
public class ChatServerExec implements ChatServerExecInterface {
	
	int port=0;
	
	/**
	 * constructor
	 * @param port
	 */
	public ChatServerExec(int port)
	{
		this.port = port;
	}
	
	/**
	*runs ChatServer class in a new thread
	*/
	public void startServer(int port) 
	{
	
		try
		{
			ChatServer server = new ChatServer(port);
			Thread t = new Thread(server);
			//server.startServerThread();
			t.start();
			Thread.sleep(100);
		}
		catch (InterruptedException exception)
        {
        	
        }

	
	}
	
	/**
	*runs ChatServer class in a new thread
	*/
	public void startServer() 
	{
	
		try
		{
			ChatServer server = new ChatServer(port);
			Thread t = new Thread(server);
			//server.startServerThread();
			t.start();
			Thread.sleep(100);
		}
		catch (InterruptedException exception)
        {
        	
        }
 
	
	}

}
