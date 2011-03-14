import java.io.*;
import java.net.*;
import java.util.ArrayList;

public class ShapesServer 
{
	ServerSocket listenerSocket;
	Socket connection = null;
	ObjectInputStream in;
	private ArrayList <ObjectOutputStream> shapesSerialized;
	
	public void listen() throws IOException, ClassNotFoundException
	{
		this.listenerSocket = new ServerSocket( 56000 , 10 );
		this.connection = this.listenerSocket.accept();		
		System.out.println("Connection received from " + this.connection.getInetAddress().getHostName());
		this.in = new ObjectInputStream( this.connection.getInputStream() );

		do
		{
			try
			{
				this.shapesSerialized.add( (ObjectOutputStream) in.readObject() );					
				
			}
			catch( ClassNotFoundException classnot )
			{
				System.err.println("????");
			}
		}
		while( in.readObject() != null );
		
	}	

}
