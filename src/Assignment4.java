

public class Assignment4 implements Runnable
{
	private static Thread thread1;
	
	public Assignment4()
	{		
		
	}
	
	@Override
	public void run() 
	{
		// TODO Auto-generated method stub
		ShapesServerUi server = new ShapesServerUi();
		server.setVisible(true);
		
	}
	
			/**
	 * @param args
	 */
	public static void main(String[] args) 
	{
		Assignment4.thread1 = new Thread( new Assignment4() );
		thread1.start();
		
		// TODO Auto-generated method stub
		ShapesClientUi client = new ShapesClientUi();
		client.setVisible(true);
		
	}

			
	
}


