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
		// Only accept the shape classes (and the JDK types they contain) from the network;
		// reject everything else to prevent deserialization gadget attacks.
		this.in.setObjectInputFilter( ObjectInputFilter.Config.createFilter(
				"Shape;Line;Oval;Rectangle;java.awt.Color;java.awt.color.*;java.lang.*;java.util.*;maxdepth=20;!*" ) );

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
