import java.awt.Color;
import java.awt.Graphics;


public class Line extends Shape
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public Line()
	{
		this.color = Color.black;
		this.fill = false;
		this.x1 = 0;
		this.y1 = 0;
		this.x2 = 0;
		this.y2 = 0;
	}
	
	public Line( int x1 , int y1 , int x2 , int y2 , Color color , boolean fill )
	{
		this.color = color;
		this.fill = fill;
		this.x1 = x1;
		this.y1 = y1;
		this.x2 = x2;
		this.y2 = y2;
		
		this.debug( "New Line : [" + this.x1 + "," + this.y1 + "][" + this.x2 + "," + this.y2 + "]"  );
		
	}
	
	public void draw( Graphics g )
	{
		g.setColor( this.color );
		g.drawLine( this.x1 , this.y1 , this.x2 , this.y2 );
		
		this.debug( "Draw Line : [" + this.x1 + "," + this.y1 + "][" + this.x2 + "," + this.y2 + "]" );
		
	}

}
