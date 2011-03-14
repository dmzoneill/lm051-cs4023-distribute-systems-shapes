import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Random;

import javax.swing.*;

/*
 * 
 * Wrapper for DrawPanel
 * 
 */

public class Assignment3 extends JFrame implements MouseListener
{
	// Gui Elements
	private Drawpanel drawArea;
	private GridBagLayout layout;
	private GridBagConstraints constraint;
	private Point mouseDownPoint;
	private Point mouseUpPoint;
	private JButton clearCanvasButton;
	private JButton randomShapesButton;
	private JButton colorChooseButton;
	private ButtonGroup bgroup;
	private JRadioButton circleButton;
	private JRadioButton rectangleButton;
	private JRadioButton triangleButton;
	private boolean debug = true;
	private Color drawColor;
	private static final long serialVersionUID = 1L;
	private JTextField colorBox;
	private Random rand;
	
	// Shapes Elements
	private Shape[] shapes;
	private Line[] lines;
	private Rectangle[] rectangles;
	private Oval[] ovals;
	
	public Assignment3()
	{		
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

		this.lines = new Line[ 5 + this.getRNum( 5 ) ];
		this.rectangles = new Rectangle[ 5 + this.getRNum( 5 ) ];
		this.ovals = new Oval[ 5 + this.getRNum( 5 ) ];

		int maxW = this.drawArea.getWidth();
		int maxH = this.drawArea.getHeight();		
		Color color;	
		
		for( int p = 0; p < this.lines.length; p++ )
		{
			color = new Color( this.getRNum( 255 ) , this.getRNum( 255 ) , this.getRNum( 255 ) );			
			this.lines[ p ] = new Line( this.getRNum( maxW ) , this.getRNum( maxH ) , this.getRNum( maxW ) , this.getRNum( maxH ) , color , this.getRBool() );
			this.lines[ p ].draw( this.drawArea.getGraphics() );
		}
				
		
		for( int p = 0; p < this.rectangles.length; p++ )
		{				
			color = new Color( this.getRNum( 255 ) , this.getRNum( 255 ) , this.getRNum( 255 ) );			
			this.rectangles[ p ] = new Rectangle( this.getRNum( maxW ) , this.getRNum( maxH ) , this.getRNum( maxW ) , this.getRNum( maxH ) , color , this.getRBool() );
			this.rectangles[ p ].draw( this.drawArea.getGraphics() );
		}
		
		for( int p = 0; p < this.ovals.length; p++ )
		{				
			color = new Color( this.getRNum( 255 ) , this.getRNum( 255 ) , this.getRNum( 255 ) );			
			this.ovals[ p ] = new Oval( this.getRNum( maxW ) , this.getRNum( maxH ) , this.getRNum( maxW ) , this.getRNum( maxH ) , color , this.getRBool() );
			this.ovals[ p ].draw( this.drawArea.getGraphics() );
		}
		
		this.drawArea.repaint();
		
	}
		
	
	private void drawShape()
	{
		
		String action = this.bgroup.getSelection().getActionCommand();
		
		if( action == "circle" )
		{
			this.drawElipse();			
		}
		else if( action == "rectangle" )
		{
			this.drawRectangle();			
		}
		else
		{
			this.drawTriangle();
		}
		
	}
	
	private void drawRectangle()
	{		
		Graphics g = this.drawArea.getGraphics();
		g.setColor( this.drawColor );
		g.fillRect(this.mouseDownPoint.x,this.mouseDownPoint.y, this.mouseUpPoint.x - this.mouseDownPoint.x, this.mouseUpPoint.y - this.mouseDownPoint.y);
		this.debug( "Draw rectangle" );
	
	}
	
	private void drawElipse()
	{
		Graphics g = this.drawArea.getGraphics();
		g.setColor( this.drawColor );
		g.fillOval( this.mouseDownPoint.x,this.mouseDownPoint.y, this.mouseUpPoint.x - this.mouseDownPoint.x, this.mouseUpPoint.y - this.mouseDownPoint.y);
		this.debug( "Draw elipse" );
	}
	
	private void drawTriangle()
	{
		Graphics g = this.drawArea.getGraphics();
		Polygon p = new Polygon();
		p.addPoint( this.mouseDownPoint.x + ( (this.mouseUpPoint.x - this.mouseDownPoint.x ) / 2 ) , this.mouseDownPoint.y );
		p.addPoint( this.mouseDownPoint.x , this.mouseUpPoint.y );
		p.addPoint( this.mouseUpPoint.x , this.mouseUpPoint.y );
		g.setColor( this.drawColor );
		g.fillPolygon(p);
		this.debug( "Draw triangle" );
	}
	

	

	
	@Override
	public void mouseClicked(MouseEvent arg0) 
	{
		// TODO Auto-generated method stub
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
		}
		
	}

	@Override
	public void mouseEntered(MouseEvent arg0) 
	{
		// TODO Auto-generated method stub
		this.debug( "Mouse entered canvas" );
		
	}

	@Override
	public void mouseExited(MouseEvent arg0) 
	{
		// TODO Auto-generated method stub
		this.debug( "Mouse exited canvas" );
		
	}

	@Override
	public void mousePressed(MouseEvent arg0) 
	{
		this.mouseDownPoint = arg0.getPoint();		
		this.debug( "Mouse down event" );
	}

	@Override
	public void mouseReleased(MouseEvent arg0) 
	{
		this.mouseUpPoint = arg0.getPoint();			
		this.drawShape();
		this.debug( "Mouse up event" );
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
				
		JLabel label = new JLabel();
		label.setText("Select shape below and draw in free space");
		this.constraint.gridx = 0;
		this.constraint.gridy = 0;
		this.constraint.gridwidth = 7;
		this.constraint.insets = new Insets(5, 5, 5, 5);
		this.add( label , this.constraint );
		
		this.drawArea = new Drawpanel();
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
		
		this.bgroup = new ButtonGroup();		
		this.circleButton = new JRadioButton( "Elipse" , true );
		this.circleButton.setActionCommand("circle");
		this.rectangleButton = new JRadioButton( "Rectangle" , false );
		this.rectangleButton.setActionCommand("rectangle");
		this.triangleButton = new JRadioButton( "Triangle" , false );
		this.triangleButton.setActionCommand("triangle");
		this.bgroup.add( this.circleButton );
		this.bgroup.add( this.rectangleButton );
		this.bgroup.add( this.triangleButton );
		this.constraint.gridx = 2;
		this.constraint.gridy = 2;
		this.constraint.gridwidth = 1;
		this.constraint.insets = new Insets(5, 5, 5, 5);
		this.add( this.circleButton , this.constraint );
		this.constraint.gridx = 3;
		this.constraint.gridy = 2;
		this.constraint.gridwidth = 1;
		this.constraint.insets = new Insets(5, 5, 5, 5);
		this.add( this.rectangleButton , this.constraint );
		this.constraint.gridx = 4;
		this.constraint.gridy = 2;
		this.constraint.gridwidth = 1;
		this.constraint.insets = new Insets(5, 5, 5, 5);
		this.add( this.triangleButton , this.constraint );		
		
		this.colorChooseButton = new JButton();
		this.colorChooseButton.setText( "Choose Color" );
		this.colorChooseButton.addMouseListener(this);
		this.colorChooseButton.setActionCommand( "chooseColor" );
		this.constraint.gridx = 5;
		this.constraint.gridy = 2;
		this.constraint.gridwidth = 1;
		this.constraint.insets = new Insets(5, 5, 5, 5);
		this.add( this.colorChooseButton , this.constraint );	
		
		this.colorBox = new JTextField( "        " );
		this.colorBox.setEditable(false);
		this.constraint.gridx = 6;
		this.constraint.gridy = 2;
		this.constraint.gridwidth = 1;
		this.colorBox.setBackground(this.drawColor);	
		this.colorBox.setForeground(this.drawColor);
		this.add( this.colorBox , this.constraint );			
		
		this.pack(); 		
	}
	
	
	private void debug( String message )
	{
		if( this.debug == true )
		{
			System.out.println( message );
		}		
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) 
	{
		// TODO Auto-generated method stub
		Assignment3 drawpanel = new Assignment3();
		drawpanel.setVisible(true);
	}

}


