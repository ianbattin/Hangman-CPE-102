import java.awt.*;
import java.lang.Object;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.*;
import javax.imageio.ImageIO;
import javax.swing.JPanel;
import java.io.*;
import javax.imageio.ImageIO;
import javax.swing.JPanel;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;


public class HangmanFigure extends JPanel 
{
	private int guesses;
	private Image head= null;//Image.IO.read(new File("head.jpg"));
	private Image image= null;
	private File imageFile;
	
	public HangmanFigure() 
	{
		super();
		guesses = 0;
		setPreferredSize(new Dimension(300, 300));
		setOpaque(true);
	}
	
	public void paintComponent(Graphics g) 
	{
		g.setColor(Color.BLACK);
		
		//File headImage = new File("head.jpg");
		
		try
		{		
		    head = ImageIO.read(new File("whole body.jpg"));
		}
		catch(Exception e)
		{
			//throw Exception("BLAH");
		}

		
		
		// base
		if(guesses > 0) 
		{
			g.drawLine(1, 299, 299, 299);
		}
		
		// right wall
		if(guesses > 1) 
		{
			g.drawLine(299, 299, 299, 1);
		}
		
		// top line
		if(guesses > 2) 
		{
			g.drawLine(150, 1, 299, 1);
		}
		
		// hanging line
		if(guesses > 3) 
		{
			g.drawLine(150, 1, 150, 70);
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