import java.awt.*;
import java.lang.Object;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.imageio.ImageIO;
import javax.swing.JPanel;


import java.io.File;
import java.io.IOException;


public class HangmanFigure extends JPanel 
{
	private int guesses;
	private Image image= null;
	
	public HangmanFigure() 
	{
		super();
		guesses = 0;
		setPreferredSize(new Dimension(300, 300));
		setOpaque(true);
	}
	
	public void paintComponent(Graphics g) 
	{
		g.setColor(Color.ORANGE.darker());
		((Graphics2D) g).setStroke(new BasicStroke(10));
		try
		{
            image = ImageIO.read(new File("background.png"));
            image = image.getScaledInstance(300, 300, Image.SCALE_SMOOTH);
		    g.drawImage(image, 0, 0, null);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		//File headImage = new File("head.jpg");
		


		
		
		// base
		if(guesses > 0) 
		{
			g.drawLine(1, 290, 290, 290);
		}
		
		// right wall
		if(guesses > 1) 
		{
			g.drawLine(290, 290, 290, 10);
		}
		
		// top line
		if(guesses > 2) 
		{
			g.drawLine(150, 10, 290, 10);
		}
		
		// hanging line
		if(guesses > 3) 
		{
			g.drawLine(150, 10, 150, 70);
		}
		
		// face
		if(guesses > 4) 
		{
		   try
		   {
               image = ImageIO.read(new File("head.png"));
               image = image.getScaledInstance(75, 250, Image.SCALE_SMOOTH);
		       g.drawImage(image,125-12,40, null);
		   }
		   catch(Exception e)
		   {
			   //throw new Exception("head file not found");    //this line doesn't work
		   }	
	
		}
		
		// body
		if(guesses > 5) 
		{
			   try
			   {
	               image = ImageIO.read(new File("torso.png"));
	               image = image.getScaledInstance(75, 250, Image.SCALE_SMOOTH);
			       g.drawImage(image,125-12,40, null);
			   }
			   catch(Exception e)
			   {
			   }	
		}
		
		// right hand
		if(guesses > 6) 
		{
			   try
			   {
	               image = ImageIO.read(new File("right_arm.png"));
	               image = image.getScaledInstance(75, 250, Image.SCALE_SMOOTH);
			       g.drawImage(image,125-12,40, null);
			   }
			   catch(Exception e)
			   {
			   }	
		}
		
		// left hand
		if(guesses > 7) 
		{
			   try
			   {
	               image = ImageIO.read(new File("left_arm.png"));
	               image = image.getScaledInstance(75, 250, Image.SCALE_SMOOTH);
			       g.drawImage(image,125-12,40, null);
			   }
			   catch(Exception e)
			   {
			   }	
		}
		
		// left leg
		if(guesses > 8) 
		{
			   try
			   {
	               image = ImageIO.read(new File("right_leg.png"));
	               image = image.getScaledInstance(75, 250, Image.SCALE_SMOOTH);
			       g.drawImage(image,125-12,40, null);
			   }
			   catch(Exception e)
			   {
			   }	
		}
		
		// right leg
		if(guesses > 9) 
		{
			   try
			   {
	               image = ImageIO.read(new File("whole_body.png"));
	               image = image.getScaledInstance(75, 250, Image.SCALE_SMOOTH);
			       g.drawImage(image,125-12,40, null);
			   }
			   catch(Exception e)
			   {
			   }	
		}
		
	}
	
	public void set() 
	{
		guesses++;
		paintComponent(getGraphics());
	}
}