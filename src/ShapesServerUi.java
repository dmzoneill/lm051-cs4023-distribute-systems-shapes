import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.*;

/*
 * 
 * Wrapper for DrawPanel
 * 
 */

public class ShapesServerUi extends JFrame
{
	// Gui Elements
	private Drawpanel drawArea;
	private GridBagLayout layout;
	private GridBagConstraints constraint;	
	private static final long serialVersionUID = 1L;
	
	public ShapesServerUi()
	{	
		System.out.println( "Epic" );

		try
		{
			ShapesServer server = new ShapesServer();
			server.listen();
		}
		catch( Exception e)
		{
			e.printStackTrace();
		}
		
		this.setTitle("Server");
		this.init();			
	}
	
		
	private void init()
	{		
		this.setBounds(10,10,800,600);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);		
		this.layout = new GridBagLayout();
		this.setLayout(this.layout);	
		this.constraint = new GridBagConstraints();	
		this.constraint.fill = GridBagConstraints.BOTH;
				
		
		this.drawArea = new Drawpanel( false );
		this.drawArea.setMinimumSize( new Dimension( 750 , 500 ) );
		this.drawArea.setPreferredSize( new Dimension( 750 , 500 ) );
		this.drawArea.setMaximumSize( new Dimension( 750 , 500 ) );
		this.constraint.gridx = 0;
		this.constraint.gridy = 0;
		this.constraint.gridwidth = 0;
		this.constraint.insets = new Insets(5, 5, 5, 5);
		this.drawArea.setBackground(Color.WHITE);
		this.add( this.drawArea , this.constraint );				
		
		this.pack(); 		
	}
	

}


