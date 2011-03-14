import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;

public class ShapesClient 
{
	Socket senderSocket;
	ObjectOutputStream out;
 	
 	private ArrayList <ObjectOutputStream> shapesSerialized;
 	
 	private void send()
 	{
 		try
 		{
 			this.senderSocket = new Socket( "localhost" , 5600 );
 			this.out = new ObjectOutputStream( this.senderSocket.getOutputStream() );
 			this.out.flush();
			
 			 			
			for( int h = 0; h < this.shapesSerialized.size(); h++ )				
			{
				try
				{
					this.out.writeObject( this.shapesSerialized.get(h) );
					this.out.flush();
				}
				catch( IOException ioException )
				{
					ioException.printStackTrace();
				}			
			}

		}
		catch( UnknownHostException unknownHost )
		{
			System.err.println( "You are trying to connect to an unknown host!" );
		}
		catch(IOException ioException)
		{
			ioException.printStackTrace();
		}
		finally
		{
			try
			{
				this.out.close();
				this.senderSocket.close();
			}
			catch(IOException ioException)
			{
				ioException.printStackTrace();
			}
		}
 	}
 	
 	
 	public void send( ArrayList <ObjectOutputStream> shapesSerialized )
 	{
 		this.shapesSerialized = shapesSerialized;
 		this.send();
 	}

}
