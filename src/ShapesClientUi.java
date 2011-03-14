import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Random;

import javax.swing.*;

/*
 * 
 * Wrapper for DrawPanel
 * 
 */

public class ShapesClientUi extends JFrame implements MouseListener
{
	// Gui Elements
	private Drawpanel drawArea;
	private GridBagLayout layout;
	private GridBagConstraints constraint;
	private JButton clearCanvasButton;
	private JButton randomShapesButton;
	private JButton sendShapesButton;
	private boolean debug = true;
	private Color drawColor;
	private static final long serialVersionUID = 1L;
	private JTextField colorBox;
	private Random rand;
	
	// Shapes Elements
	private Shape[] shapes;
	
	public ShapesClientUi()
	{		
		this.setTitle("Client");
		this.rand = new Random();
		this.init();	
		this.createRandomShapes();
	}
	
	
	private int getRNum( int max )
	{		
		return this.rand.nextInt( max );		
	}	
	
	
	private boolean getRBool()
	{
		return this.rand.nextBoolean();	
	}
	
	
	private void createRandomShapes()
	{
		this.shapes = new Shape[ 10 + this.getRNum( 5 ) ];

		int maxW = this.drawArea.getWidth();
		int maxH = this.drawArea.getHeight();		
		Color color;	
		
		for( int p = 0; p < this.shapes.length; p++ )
		{			
			color = new Color( this.getRNum( 255 ) , this.getRNum( 255 ) , this.getRNum( 255 ) );	
			
			if( p % 3 == 0 )
			{
				this.shapes[ p ] = new Line( this.getRNum( maxW ) , this.getRNum( maxH ) , this.getRNum( maxW ) , this.getRNum( maxH ) , color , this.getRBool() );
				this.shapes[ p ].draw( this.drawArea.getGraphics() );
			}
			else if( p % 3 == 1 )
			{
				this.shapes[ p ] = new Rectangle( this.getRNum( maxW ) , this.getRNum( maxH ) , this.getRNum( maxW ) , this.getRNum( maxH ) , color , this.getRBool() );
				this.shapes[ p ].draw( this.drawArea.getGraphics() );
			}
			else
			{
				this.shapes[ p ] = new Oval( this.getRNum( maxW ) , this.getRNum( maxH ) , this.getRNum( maxW ) , this.getRNum( maxH ) , color , this.getRBool() );
				this.shapes[ p ].draw( this.drawArea.getGraphics() );
			}			
		}
		
		this.drawArea.repaint();
		
	}
		
	
	@Override
	public void mouseClicked(MouseEvent arg0) 
	{
		this.debug( "Mouse clicked event" );
		
		if( arg0.getSource().getClass().getName() == "javax.swing.JButton" )
		{
			JButton ref = (JButton) arg0.getSource();
			if( ref.getActionCommand() == "canvasClear" )
			{
				Graphics g = this.drawArea.getGraphics();
				g.setColor(Color.white);
			    g.fillRect(0, 0, getWidth(), getHeight());
				this.debug( "Clear canvas button clicked" );
			}
			else if ( ref.getActionCommand() == "chooseColor" )
			{
				this.drawColor = JColorChooser.showDialog(((Component) arg0.getSource()).getParent(), "Demo", Color.blue);	
				this.colorBox.setBackground(this.drawColor);	
			}
			else if ( ref.getActionCommand() == "randomShapes" )
			{
				this.drawArea.createRandomShapes();	
				this.drawArea.repaint();
			}
			else if ( ref.getActionCommand() == "sendShapes" )
			{
				this.sendShapesToServer();
			}
		}
		
	}
	
	public void sendShapesToServer()
	{
		ShapesClient client = new ShapesClient();
		client.send( this.drawArea.getSerializedShapes() );
		
		
	}

	@Override
	public void mouseEntered(MouseEvent arg0) 
	{
	
	}

	@Override
	public void mouseExited(MouseEvent arg0) 
	{
		
	}

	@Override
	public void mousePressed(MouseEvent arg0) 
	{

	}

	@Override
	public void mouseReleased(MouseEvent arg0) 
	{

	}
	
	private void init()
	{
		this.drawColor = Color.red;
		this.setBounds(10,10,800,600);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);		
		this.layout = new GridBagLayout();
		this.setLayout(this.layout);	
		this.constraint = new GridBagConstraints();	
		this.constraint.fill = GridBagConstraints.BOTH;
				
		this.drawArea = new Drawpanel( true );
		this.drawArea.setMinimumSize( new Dimension( 750 , 500 ) );
		this.drawArea.setPreferredSize( new Dimension( 750 , 500 ) );
		this.drawArea.setMaximumSize( new Dimension( 750 , 500 ) );
		this.constraint.gridx = 0;
		this.constraint.gridy = 1;
		this.constraint.gridwidth = 7;
		this.constraint.insets = new Insets(5, 5, 5, 5);
		this.drawArea.setBackground(Color.WHITE);
		this.add( this.drawArea , this.constraint );
		this.drawArea.addMouseListener(this);		
	
		this.clearCanvasButton = new JButton();
		this.clearCanvasButton.setText( "Clear Canvas" );
		this.clearCanvasButton.addMouseListener(this);
		this.clearCanvasButton.setActionCommand("canvasClear");
		this.constraint.gridx = 0;
		this.constraint.gridy = 2;
		this.constraint.gridwidth = 1;
		this.constraint.insets = new Insets(5, 5, 5, 5);
		this.add( this.clearCanvasButton , this.constraint );		
		
		this.randomShapesButton = new JButton();
		this.randomShapesButton.setText( "Random Shapes" );
		this.randomShapesButton.addMouseListener(this);
		this.randomShapesButton.setActionCommand("randomShapes");
		this.constraint.gridx = 1;
		this.constraint.gridy = 2;
		this.constraint.gridwidth = 1;
		this.constraint.insets = new Insets(5, 5, 5, 5);
		this.add( this.randomShapesButton , this.constraint );		
		
		this.sendShapesButton = new JButton();
		this.sendShapesButton.setText( "Send shapes to server" );
		this.sendShapesButton.addMouseListener(this);
		this.sendShapesButton.setActionCommand("sendShapes");
		this.constraint.gridx = 2;
		this.constraint.gridy = 2;
		this.constraint.gridwidth = 1;
		this.constraint.insets = new Insets(5, 5, 5, 5);
		this.add( this.sendShapesButton , this.constraint );	
		
		this.pack(); 		
	}
	
	
	private void debug( String message )
	{
		if( this.debug == true )
		{
			System.out.println( message );
		}		
	}	

}


