import java.awt.Color;
import java.awt.Graphics;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.io.ByteArrayOutputStream;

/*
 * 
 * Prep work for assignment 3
 * 
 */

public abstract class Shape implements iDrawables, Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected int x1;
	protected int y1;	
	protected int x2;
	protected int y2;
	protected Color color;
	protected boolean fill;
	protected boolean debug = true;
				
	// four setters
	public void setUpperLeftX( int x )
	{
		this.x1 = ( x > 0 ) ? x : 0;
	}
	
	public void setUpperLeftY( int y )
	{
		this.x1 = ( y > 0 ) ? y : 0;
	}
	
	public void setWidth( int x )
	{
		this.x1 = ( this.x1 < this.x2 ) ? this.x1 : this.x2;
		this.x2 = this.x1 + x;
	}
	
	public void setHeight( int y )
	{
		this.y1 = ( this.y1 < this.y2 ) ? this.y1 : this.y2;
		this.y2 = this.y1 + y;
	}
	
	public void setColor( Color color )
	{
		this.color = color;
	}
	
	public void setFill( boolean fill )
	{
		this.fill = fill;
	}
	
	
	// six getters
	public boolean getFill()
	{
		return this.fill;
	}
	
	public Color getColor()
	{
		return this.color;
	}
	
	public int getUpperLeftX()
	{
		return ( x1 < x2 ) ? x1 : x2;		
	}
	
	public int getUpperLeftY()
	{
		return ( y1 < y2 ) ? y1 : y2;
	}
	
	public int getWidth()
	{
		return Math.abs( x1 - x2 );
	}
	
	public int getHeight()
	{
		return Math.abs( y1 - y2 );
	}
	
	public ObjectOutputStream getSerialized()
	{
		try
		{
			ObjectOutputStream obj_out = new ObjectOutputStream( new ByteArrayOutputStream() );			
			obj_out.writeObject( this );
			return obj_out; 
		}
		catch( Exception e )
		{
			return null;
		}		
	}
	
	public abstract void draw( Graphics g );
	
	public void debug( String message )
	{
		if( this.debug == true )
		{
			System.out.println( message );
		}		
	}
}
