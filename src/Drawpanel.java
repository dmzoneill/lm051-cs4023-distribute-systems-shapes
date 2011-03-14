import java.awt.Color;
import java.awt.Graphics;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.JPanel;


public class Drawpanel extends JPanel 
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// Shapes Elements
	private Shape[] shapes;
	private Random rand;
	private ArrayList <ObjectOutputStream> shapesSerialized;

	public Drawpanel( boolean draw )
	{		
		this.init();	
		if( draw == true )
		{
			this.createRandomShapes();
		}
	} 
	
	
	public void init()
	{
		this.setBackground( Color.WHITE );
		this.rand = new Random();
	}
	
	
	private int getRNum( int max )
	{		
		return this.rand.nextInt( max );		
	}	
	
	
	private boolean getRBool()
	{
		return this.rand.nextBoolean();	
	}
	
	
	public void createRandomShapes()
	{

		this.shapes = new Shape[ 15 + this.getRNum( 5 ) ];

		int maxW = 750;
		int maxH = 500;		
		Color color;	
		int randShape = 0;
		
		for( int p = 0; p < this.shapes.length; p++ )
		{
			randShape = this.rand.nextInt( 3 );
			color = new Color( this.getRNum( 255 ) , this.getRNum( 255 ) , this.getRNum( 255 ) );
			
			if( randShape == 0 )
			{							
				this.shapes[ p ] = new Line( this.getRNum( maxW ) , this.getRNum( maxH ) , this.getRNum( maxW ) , this.getRNum( maxH ) , color , this.getRBool() );
			}
			else if( randShape == 1 )
			{
				this.shapes[ p ] = new Rectangle( this.getRNum( maxW ) , this.getRNum( maxH ) , this.getRNum( maxW ) , this.getRNum( maxH ) , color , this.getRBool() );
			}
			else
			{
				this.shapes[ p ] = new Oval( this.getRNum( maxW ) , this.getRNum( maxH ) , this.getRNum( maxW ) , this.getRNum( maxH ) , color , this.getRBool() );
			}
			
			
			try
			{
				this.shapesSerialized.add( this.shapes[ p ].getSerialized() );
			}
			catch( Exception exp )
			{
				
			}
			
		}		
	}		
	
	
	public ArrayList <ObjectOutputStream> getSerializedShapes()
	{
		return this.shapesSerialized;
	}
	
	public void addSerializedShapes( ObjectOutputStream obj )
	{
		this.shapesSerialized.add( obj );
	}
	

	public void paintComponent( Graphics g )
	{
		super.paintComponent( g );

		for ( Shape shape : this.shapes )
		{
			shape.draw( g );
		} 		
	}


}
